package net.foxboi.salted.common.util;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.foxboi.salted.common.Smptg;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class DataRegistry<T> {
    private static final Map<ResourceKey<? extends Registry<?>>, DataRegistry<?>> ALL = new HashMap<>();

    private final Map<ResourceKey<T>, Definition<T>> map = new LinkedHashMap<>();
    private final ResourceKey<? extends Registry<T>> registryKey;

    private DataRegistry(ResourceKey<? extends Registry<T>> registryKey) {
        this.registryKey = registryKey;
    }

    @SuppressWarnings("unchecked")
    public static <T> DataRegistry<T> of(ResourceKey<? extends Registry<T>> registryKey) {
        return (DataRegistry<T>) ALL.computeIfAbsent(registryKey, k -> new DataRegistry<>(registryKey));
    }

    public ResourceKey<T> register(ResourceKey<T> key, Definition<T> definition) {
        map.put(key, definition);
        return key;
    }

    public ResourceKey<T> register(ResourceLocation id, Definition<T> definition) {
        return register(ResourceKey.create(registryKey, id), definition);
    }

    public ResourceKey<T> register(String name, Definition<T> definition) {
        return register(Smptg.key(registryKey, name), definition);
    }

    public void forEach(BiConsumer<? super ResourceKey<T>, ? super Definition<T>> action) {
        map.forEach(action);
    }

    private void deposit(BootstrapContext<T> context) {
        forEach((key, definition) -> context.register(key, definition.create(key, DefinitionContext.fromBootstrapContext(context))));
    }

    private void deposit(RegistrySetBuilder builder) {
        builder.add(registryKey, this::deposit);
    }

    private void deposit(FabricDynamicRegistryProvider.Entries entries) {
        forEach((key, definition) -> entries.add(key, definition.create(key, DefinitionContext.fromGeneratorEntries(entries))));
    }

    public static void depositAll(FabricDynamicRegistryProvider.Entries entries) {
        ALL.forEach((key, reg) -> reg.deposit(entries));
    }

    public static void depositAll(RegistrySetBuilder builder) {
        ALL.forEach((key, reg) -> reg.deposit(builder));
    }
}
