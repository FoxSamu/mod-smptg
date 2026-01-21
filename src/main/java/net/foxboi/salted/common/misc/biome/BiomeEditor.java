package net.foxboi.salted.common.misc.biome;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiPredicate;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.attribute.*;
import net.minecraft.world.attribute.modifier.AttributeModifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public interface BiomeEditor {
    // Weather
    BiomeEditor hasPrecipitation(boolean precipitation);

    BiomeEditor temperature(float temperature);

    BiomeEditor downfall(float downfall);

    // Effects
    BiomeEditor waterColor(int color);

    BiomeEditor grassColor(OptionalInt color);

    default BiomeEditor grassColor(int color) {
        return grassColor(OptionalInt.of(color));
    }

    BiomeEditor foliageColor(OptionalInt color);

    default BiomeEditor foliageColor(int color) {
        return foliageColor(OptionalInt.of(color));
    }

    BiomeEditor dryFoliageColor(OptionalInt color);

    default BiomeEditor darkRedFoliageColor(int color) {
        return darkRedFoliageColor(OptionalInt.of(color));
    }

    BiomeEditor darkRedFoliageColor(OptionalInt color);

    default BiomeEditor redFoliageColor(int color) {
        return redFoliageColor(OptionalInt.of(color));
    }

    BiomeEditor redFoliageColor(OptionalInt color);

    default BiomeEditor goldenFoliageColor(int color) {
        return goldenFoliageColor(OptionalInt.of(color));
    }

    BiomeEditor goldenFoliageColor(OptionalInt color);

    default BiomeEditor yellowFoliageColor(int color) {
        return yellowFoliageColor(OptionalInt.of(color));
    }

    BiomeEditor yellowFoliageColor(OptionalInt color);

    default BiomeEditor dryFoliageColor(int color) {
        return dryFoliageColor(OptionalInt.of(color));
    }

    BiomeEditor grassColorModifier(BiomeSpecialEffects.GrassColorModifier modifier);

    // Environment attributes
    <V> BiomeEditor putAttribute(EnvironmentAttribute<V> attribute, V value);

    <V, P> BiomeEditor modifyAttribute(EnvironmentAttribute<V> attribute, AttributeModifier<V, P> modifier, P parameter);

    BiomeEditor putAttributes(EnvironmentAttributeMap map);

    default BiomeEditor putAttributes(EnvironmentAttributeMap.Builder map) {
        return putAttributes(map.build());
    }

    // Generation
    BiomeEditor addFeature(GenerationStep.Decoration step, ResourceKey<PlacedFeature> feature);

    BiomeEditor addCarver(ResourceKey<ConfiguredWorldCarver<?>> carver);

    BiomeEditor removeFeature(GenerationStep.Decoration step, ResourceKey<PlacedFeature> feature);

    BiomeEditor removeCarver(ResourceKey<ConfiguredWorldCarver<?>> carver);

    // Spawns
    BiomeEditor addSpawn(MobCategory category, int weight, MobSpawnSettings.SpawnerData spawn);

    BiomeEditor addMobCharge(EntityType<?> entity, double energyBudget, double charge);

    BiomeEditor clearMobCharge(EntityType<?> entity);

    BiomeEditor creatureGenerationProbability(float probability);

    BiomeEditor removeSpawns(BiPredicate<MobCategory, MobSpawnSettings.SpawnerData> spawnPredicate);
}
