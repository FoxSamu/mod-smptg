package net.foxboi.summon.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.Util;

import net.fabricmc.loader.api.FabricLoader;

import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.foxboi.summon.api.SummonModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Summon {
    public static final Logger LOGGER = LoggerFactory.getLogger("Summon");

    private static final String ENTRYPOINT = "summon";

    private static final RunMode MODE = switch (System.getProperty("summon.mode").toLowerCase()) {
        case "live" -> RunMode.LIVE;
        case "standalone" -> RunMode.STANDALONE;
        case "normal" -> RunMode.NORMAL;
        case null, default -> RunMode.DISABLED;
    };

    private static final String NAMESPACE = System.getProperty("summon.namespace");

    private static final String OUTPUT_DIRECTORY = System.getProperty("summon.output");
    private static final String COPY_TO_DIRECTORIES = System.getProperty("summon.copy_to");

    public static void runLive() {
        if (MODE == RunMode.LIVE) {
            LOGGER.info("Running data generator in LIVE mode");
            run();
        }
    }

    public static void runStandalone() {
        if (MODE == RunMode.STANDALONE) {
            standaloneSetup();

            LOGGER.info("Running data generator in STANDALONE mode");
            runAndExit();
        }
    }

    public static void runNormal() {
        if (MODE == RunMode.NORMAL) {
            LOGGER.info("Running data generator in NORMAL mode");
            runAndExit();
        }
    }

    private static void standaloneSetup() {
        // Init bare minimum needed for Minecraft to believe it exists
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();

        // Init mod
        for (var entrypoint : getDatagenEntrypoints()) {
            entrypoint.onInitializeStandaloneDatagen();
        }
    }

    private static void addJsonKeySortOrders(BiConsumer<String, Integer> ignored) {
        // Not used atm, but here if needed
    }

    private static void runAndExit() {
        try {
            run();
        } catch (Throwable e) {
            LOGGER.error("Failed to run data generator", e);
            System.exit(1);
        }
        System.exit(0);
    }

    private static void run() {
        Objects.requireNonNull(NAMESPACE, "No datagen namespace set");
        Objects.requireNonNull(OUTPUT_DIRECTORY, "No datagen output directory set");

        var outputDir = Paths.get(OUTPUT_DIRECTORY);

        var loader = FabricLoader.getInstance();

        // Setup registry lookup
        var lookups = CompletableFuture.supplyAsync(LookupBuilder::buildLookup, Util.backgroundExecutor());

        // Setup key order; this temporarily alters the FIXED_ORDER_FIELDS field
        var keyOrder = (Object2IntOpenHashMap<String>) DataProvider.FIXED_ORDER_FIELDS;
        var defaultKeyOrder = new Object2IntOpenHashMap<>(keyOrder);
        var customKeys = new HashSet<String>(); // Keep track of the keys we added

        addJsonKeySortOrders((key, value) -> {
            keyOrder.put(key, value.intValue());
            customKeys.add(key);
        });

        // Create FileCopyHandler
        var copyHandler = FileCopyHandler.none();

        if (COPY_TO_DIRECTORIES != null) {
            var copyTo = new ArrayList<Path>();
            for (var dir : COPY_TO_DIRECTORIES.split(";")) {
                copyTo.add(Paths.get(dir.trim()));
            }

            copyHandler = FileCopyHandler.copyTo(copyTo);
        }

        // Start generator
        var gen = new DataGeneratorImpl(NAMESPACE, outputDir, lookups, copyHandler);
        for (var entrypoint : getDatagenEntrypoints()) {
            entrypoint.setupDataGenerator(gen, loader.getEnvironmentType());
        }

        try {
            gen.run();
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }

        // Reset key order field
        keyOrder.keySet().removeAll(customKeys);
        keyOrder.putAll(defaultKeyOrder);
    }

    private static List<SummonModInitializer> getDatagenEntrypoints() {
        return FabricLoader.getInstance().getEntrypointContainers(ENTRYPOINT, SummonModInitializer.class).stream()
                .filter(it -> it.getProvider().getMetadata().getId().equals(NAMESPACE))
                .map(it -> it.getEntrypoint())
                .toList();
    }

    private static List<SummonModInitializer> getAllEntrypoints() {
        return FabricLoader.getInstance().getEntrypoints(ENTRYPOINT, SummonModInitializer.class);
    }

    private static class MergedBootstrap<T> implements RegistrySetBuilder.RegistryBootstrap<T> {
        private final ResourceKey<? extends Registry<T>> registry;
        private final List<RegistrySetBuilder.RegistryBootstrap<T>> bootstraps;
        private Lifecycle lifecycle;

        MergedBootstrap(ResourceKey<? extends Registry<T>> registry) {
            this.registry = registry;
            this.bootstraps = new ArrayList<>();
            this.lifecycle = Lifecycle.stable();
        }

        void add(RegistrySetBuilder.RegistryStub<T> stub) {
            bootstraps.add(stub.bootstrap());

            lifecycle = stub.lifecycle().add(lifecycle);
        }

        void apply(RegistrySetBuilder builder) {
            builder.add(registry, lifecycle, this);
        }

        @Override
        public void run(BootstrapContext<T> context) {
            for (var bootstrap : bootstraps) {
                bootstrap.run(context);
            }
        }
    }

    private static class LookupBuilder {
        private final Map<ResourceKey<?>, MergedBootstrap<?>> bootstraps = new HashMap<>();

        LookupBuilder() {
            // Add bootstraps for all vanilla registries
            RegistryHelper.dynamicRegistries()
                    .forEach(data -> getOrCreateBootstrap(data.key()));
        }

        @SuppressWarnings("unchecked")
        private <T> MergedBootstrap<T> getOrCreateBootstrap(ResourceKey<? extends Registry<T>> key) {
            return (MergedBootstrap<T>) bootstraps.computeIfAbsent(key, _ -> new MergedBootstrap<>(key));
        }

        private <T> void putStub(RegistrySetBuilder.RegistryStub<T> stub) {
            var data = getOrCreateBootstrap(stub.key());
            data.add(stub);
        }

        private void add(RegistrySetBuilder builder) {
            for (var stub : builder.entries) {
                putStub(stub);
            }
        }

        private HolderLookup.Provider build() {
            var merged = new RegistrySetBuilder();

            for (var bootstrap : bootstraps.values()) {
                bootstrap.apply(merged);
            }

            var lookup = merged.build(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY));
            VanillaRegistries.validateThatAllBiomeFeaturesHaveBiomeFilter(lookup);
            return lookup;
        }

        static HolderLookup.Provider buildLookup() {
            var lookupBuilder = new LookupBuilder();
            lookupBuilder.add(VanillaRegistries.BUILDER);

            for (var entrypoint : getAllEntrypoints()) {
                var regBuilder = new RegistrySetBuilder();
                entrypoint.setupRegistry(regBuilder);
                lookupBuilder.add(regBuilder);
            }

            return lookupBuilder.build();
        }
    }
}
