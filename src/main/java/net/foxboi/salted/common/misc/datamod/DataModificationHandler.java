package net.foxboi.salted.common.misc.datamod;

import java.util.*;

import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import net.foxboi.salted.common.levelgen.modifications.NetherNoiseModifier;

public class DataModificationHandler {
    private static final Map<ResourceKey<?>, List<DataModifier<?>>> MODIFIERS = new HashMap<>();
    private static final Map<ResourceKey<? extends Registry<?>>, List<DataModifier<?>>> REGISTRY_MODIFIERS = new HashMap<>();

    public static void init() {
        addModifier(NoiseGeneratorSettings.NETHER, new NetherNoiseModifier());
    }

    public static <T> void addModifier(ResourceKey<T> key, DataModifier<T> modifier) {
        MODIFIERS.computeIfAbsent(key, _ -> new ArrayList<>())
                .add(modifier);
    }

    public static <T> void addRegistryModifier(ResourceKey<Registry<T>> key, DataModifier<T> modifier) {
        REGISTRY_MODIFIERS.computeIfAbsent(key, _ -> new ArrayList<>())
                .add(modifier);
    }

    @SuppressWarnings("unchecked")
    public static <T> T modify(ResourceKey<T> key, T value, RegistryOps<?> ops) {
        var lookup = new HolderGetter.Provider() {
            @Override
            public <U> Optional<HolderGetter<U>> lookup(ResourceKey<? extends Registry<? extends U>> key) {
                return ops.getter(key);
            }
        };

        for (var dataModifier : MODIFIERS.getOrDefault(key, List.of())) {
            value = ((DataModifier<T>) dataModifier).modify(key, value, lookup);
        }

        for (var registryModifier : REGISTRY_MODIFIERS.getOrDefault(key.registryKey(), List.of())) {
            value = ((DataModifier<T>) registryModifier).modify(key, value, lookup);
        }

        return value;
    }
}
