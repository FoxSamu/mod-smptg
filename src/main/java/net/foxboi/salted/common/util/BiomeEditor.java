package net.foxboi.salted.common.util;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.BiPredicate;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.random.WeightedList;
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
    BiomeEditor fogColor(int color);
    BiomeEditor waterColor(int color);
    BiomeEditor waterFogColor(int color);
    BiomeEditor skyColor(int color);

    BiomeEditor grassColor(OptionalInt color);
    BiomeEditor foliageColor(OptionalInt color);
    BiomeEditor dryFoliageColor(OptionalInt color);

    BiomeEditor grassColorModifier(BiomeSpecialEffects.GrassColorModifier modifier);

    BiomeEditor musicVolume(float volume);
    BiomeEditor music(Optional<WeightedList<Music>> music);

    BiomeEditor loopSound(Optional<Holder<SoundEvent>> sound);
    BiomeEditor additionsSound(Optional<AmbientAdditionsSettings> sound);
    BiomeEditor moodSound(Optional<AmbientMoodSettings> sound);

    BiomeEditor particles(Optional<AmbientParticleSettings> particles);

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
