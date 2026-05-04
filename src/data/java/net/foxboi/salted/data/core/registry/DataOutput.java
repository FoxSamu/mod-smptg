package net.foxboi.salted.data.core.registry;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import net.minecraft.core.*;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;

import net.foxboi.salted.data.core.DataCore;

final class DataOutput {
    private final HolderLookup.Provider lookups;
    private final Map<ResourceKey<? extends Registry<?>>, RegistryOutput<?>> outputs;

    public DataOutput(HolderLookup.Provider lookups) {
        this.lookups = lookups;
        this.outputs = DataCore.dynamicRegistries()
                .filter(it -> lookups.lookup(it.key()).isPresent())
                .collect(Collectors.toMap(
                        RegistryDataLoader.RegistryData::key,
                        it -> RegistryOutput.of(lookups, it)
                ));
    }

    public HolderLookup.Provider getLookups() {
        return lookups;
    }

    public Map<ResourceKey<? extends Registry<?>>, RegistryOutput<?>> getOutputs() {
        return outputs;
    }

    @SuppressWarnings("unchecked")
    public <T> void accept(RegistrySetBuilder.RegistryStub<T> stub) {
        stub.bootstrap().run((RegistryOutput<T>) outputs.get(stub.key()));
    }

    public <T> PackOutput.PathProvider getRegistryPathProvider(PackOutput output, RegistryOutput<T> registry) {
        var regKey = registry.getRegistryKey();
        var regId = regKey.identifier();

        var directory = regId.getNamespace().equals(Identifier.DEFAULT_NAMESPACE) || !outputs.containsKey(regKey)
                ? regId.getPath()
                : regId.getNamespace() + "/" + regId.getPath();

        return output.createPathProvider(PackOutput.Target.DATA_PACK, directory);
    }

    private <T> CompletableFuture<?> saveRegistry(PackOutput output, CachedOutput cache, RegistryOutput<T> registry) {
        var futures = new ArrayList<CompletableFuture<?>>();
        var pathProvider = getRegistryPathProvider(output, registry);

        for (var entry : registry.getResources().entrySet()) {
            futures.add(DataProvider.saveStable(
                    cache,
                    lookups,
                    registry.getElementCodec(),
                    entry.getValue(),
                    pathProvider.json(entry.getKey())
            ));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    public CompletableFuture<?> save(PackOutput output, CachedOutput cache) {
        var futures = new ArrayList<CompletableFuture<?>>();

        for (var registry : outputs.values()) {
            futures.add(saveRegistry(output, cache, registry));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }
}
