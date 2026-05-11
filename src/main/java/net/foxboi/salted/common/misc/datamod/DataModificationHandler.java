package net.foxboi.salted.common.misc.datamod;

import java.util.*;

import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import net.foxboi.salted.common.levelgen.modifications.NetherNoiseModifier;
import net.foxboi.salted.common.levelgen.modifications.OreModifier;

public class DataModificationHandler {
    private static final Map<ResourceKey<?>, List<DataModifier<?>>> MODIFIERS = new HashMap<>();
    private static final Map<ResourceKey<? extends Registry<?>>, List<DataModifier<?>>> REGISTRY_MODIFIERS = new HashMap<>();

    public static void init() {
        addModifier(NoiseGeneratorSettings.NETHER, new NetherNoiseModifier());
        addModifier(OreFeatures.ORE_COAL, new OreModifier());
        addModifier(OreFeatures.ORE_COAL_BURIED, new OreModifier());
        addModifier(OreFeatures.ORE_IRON, new OreModifier());
        addModifier(OreFeatures.ORE_IRON_SMALL, new OreModifier());
        addModifier(OreFeatures.ORE_GOLD, new OreModifier());
        addModifier(OreFeatures.ORE_GOLD_BURIED, new OreModifier());
        addModifier(OreFeatures.ORE_REDSTONE, new OreModifier());
        addModifier(OreFeatures.ORE_DIAMOND_SMALL, new OreModifier());
        addModifier(OreFeatures.ORE_DIAMOND_MEDIUM, new OreModifier());
        addModifier(OreFeatures.ORE_DIAMOND_LARGE, new OreModifier());
        addModifier(OreFeatures.ORE_DIAMOND_BURIED, new OreModifier());
        addModifier(OreFeatures.ORE_DIAMOND_BURIED, new OreModifier());
        addModifier(OreFeatures.ORE_LAPIS, new OreModifier());
        addModifier(OreFeatures.ORE_LAPIS_BURIED, new OreModifier());
        addModifier(OreFeatures.ORE_EMERALD, new OreModifier());
        addModifier(OreFeatures.ORE_COPPPER_SMALL, new OreModifier());
        addModifier(OreFeatures.ORE_COPPER_LARGE, new OreModifier());
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
