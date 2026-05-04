package net.foxboi.salted.data.core;

import java.util.stream.Stream;

import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;

public class DataCore {
    public static Stream<RegistryDataLoader.RegistryData<?>> dynamicRegistries() {
        return DynamicRegistries.getDynamicRegistries().stream();
    }

    public static Stream<ResourceKey<? extends Registry<?>>> dynamicRegistryKeys() {
        return dynamicRegistries()
                .map(RegistryDataLoader.RegistryData::key);
    }
}
