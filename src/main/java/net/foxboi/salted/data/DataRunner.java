/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.foxboi.salted.data;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.foxboi.salted.common.Smptg;
import net.minecraft.core.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.util.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataProvider;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import org.apache.logging.log4j.Logger;

/**
 * Similar to {@code FabricDataGenHelper} but allows us to have more control over when to generate data.
 */
public final class DataRunner {
	private static final Logger LOGGER = Smptg.LOGGER;

    private static final String MODE = System.getProperty("smptg.datagen");
    private static final boolean TERMINATE = Objects.equals(MODE, "run_and_stop");
    private static final boolean ON_RELOAD = Objects.equals(MODE, "run_on_reload");

    private static final boolean STRICT = System.getProperty("smptg.datagen.strict") != null;
    private static final String OUTPUT_DIRECTORY = System.getProperty("smptg.datagen.output");
    private static final String COPY_TO_DIRECTORY = System.getProperty("smptg.datagen.copy_to");

    public static boolean shouldRunAndStop() {
        return TERMINATE;
    }

    public static boolean shouldRunOnResourceReload() {
        return ON_RELOAD;
    }

    public static boolean enabled() {
        return ON_RELOAD || TERMINATE;
    }

    public static void run() {
        try {
            var outPath = Paths.get(Objects.requireNonNull(OUTPUT_DIRECTORY, "No output directory set"));
            runDatagen(outPath, STRICT);

            if (COPY_TO_DIRECTORY != null) {
                LOGGER.info("Copying generated resources");
                copyFiles(outPath, Paths.get(COPY_TO_DIRECTORY));
            }
        } catch (Throwable e) {
            if (TERMINATE) {
                LOGGER.fatal("Datagen failed", e);
                System.exit(1);
            } else {
                throw e;
            }
        }
    }

    private static void copyFiles(Path from, Path to) {
        try {
            if (from.getFileName().toString().equals(".cache")) {
                return; // Don't copy cache directory
            }

            if (Files.isDirectory(from)) {
                Files.createDirectories(to);

                try (var contents = Files.list(from)) {
                    contents.forEach(file -> {
                        var dest = to.resolve(from.relativize(file));
                        copyFiles(file, dest);
                    });
                }
            } else {
                Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }
    }

	private static void runDatagen(Path outputDir, boolean strictValidation) {
        var loader = FabricLoader.getInstance();
        var entrypoint = new SmptgData();

        // Setup registry lookup
		var lookups = initLookups();

        // Setup key order; this temporarily alters the FIXED_ORDER_FIELDS field
		var keyOrder = (Object2IntOpenHashMap<String>) DataProvider.FIXED_ORDER_FIELDS;
		var defaultKeyOrder = new Object2IntOpenHashMap<>(keyOrder);
        var customKeys = new HashSet<String>(); // Keep track of the keys we added
        entrypoint.addJsonKeySortOrders((key, value) -> {
            Objects.requireNonNull(key, "Tried to register a priority for a null key");

            keyOrder.put(key, value);
            customKeys.add(key);
        });

        // Get effective mod container
        var effectiveContainer = loader.getModContainer(Smptg.ID).orElseThrow(() -> new RuntimeException("SMPTG is not loaded while its code is running?!"));

        var effectiveId = entrypoint.getEffectiveModId();
        if (effectiveId != null) {
            effectiveContainer = loader.getModContainer(effectiveId).orElseThrow(() -> new RuntimeException("Failed to find effective mod container for mod id (%s)".formatted(effectiveId)));
        }

        // Start generator
        @SuppressWarnings("UnstableApiUsage") // We need FabricDataGenerator here
        var gen = new FabricDataGenerator(outputDir, effectiveContainer, strictValidation, lookups);
        entrypoint.onInitializeDataGenerator(gen);

        try {
            gen.run();
        } catch (IOException exc) {
            throw new UncheckedIOException(exc);
        }

        // Reset key order field
        keyOrder.keySet().removeAll(customKeys);
        keyOrder.putAll(defaultKeyOrder);
	}

    private static CompletableFuture<HolderLookup.Provider> initLookups() {
        var loader = FabricLoader.getInstance();
        var epcs = loader.getEntrypointContainers("fabric-datagen", DataGeneratorEntrypoint.class);
        if (epcs.isEmpty()) {
            LOGGER.warn("No data generator entrypoints are defined");
        }

        var eps = epcs.stream().map(EntrypointContainer::getEntrypoint).toList();
        return CompletableFuture.supplyAsync(() -> LookupBuilder.buildLookup(eps), Util.backgroundExecutor());
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
            for (var data : DynamicRegistries.getDynamicRegistries()) {
                getOrCreateBootstrap(data.key());
            }
        }

        @SuppressWarnings("unchecked")
        private <T> MergedBootstrap<T> getOrCreateBootstrap(ResourceKey<? extends Registry<T>> key) {
            return (MergedBootstrap<T>) bootstraps.computeIfAbsent(key, it -> new MergedBootstrap<>(key));
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

        static HolderLookup.Provider buildLookup(List<DataGeneratorEntrypoint> entrypoints) {
            var lookupBuilder = new LookupBuilder();
            lookupBuilder.add(VanillaRegistries.BUILDER);

            for (var entrypoint : entrypoints) {
                var regBuilder = new RegistrySetBuilder();
                entrypoint.buildRegistry(regBuilder);
                lookupBuilder.add(regBuilder);
            }

            return lookupBuilder.build();
        }
    }
}
