package net.foxboi.salted.common.misc.biome;

import java.util.*;
import java.util.function.BiPredicate;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
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

public class BiomeModifier implements BiomeEditor {
    private final BiomeModificationContext context;

    public BiomeModifier(BiomeModificationContext context) {
        this.context = context;
    }

    @Override
    public BiomeEditor hasPrecipitation(boolean precipitation) {
        context.getWeather().setPrecipitation(precipitation);
        return this;
    }

    @Override
    public BiomeEditor temperature(float temperature) {
        context.getWeather().setTemperature(temperature);
        return this;
    }

    @Override
    public BiomeEditor downfall(float downfall) {
        context.getWeather().setDownfall(downfall);
        return this;
    }

    @Override
    public BiomeEditor fogColor(int color) {
        context.getEffects().setFogColor(color);
        return this;
    }

    @Override
    public BiomeEditor waterColor(int color) {
        context.getEffects().setWaterColor(color);
        return this;
    }

    @Override
    public BiomeEditor waterFogColor(int color) {
        context.getEffects().setWaterFogColor(color);
        return this;
    }

    @Override
    public BiomeEditor skyColor(int color) {
        context.getEffects().setSkyColor(color);
        return this;
    }

    @Override
    public BiomeEditor grassColor(OptionalInt color) {
        context.getEffects().setGrassColor(color);
        return this;
    }

    @Override
    public BiomeEditor foliageColor(OptionalInt color) {
        context.getEffects().setFoliageColor(color);
        return this;
    }

    @Override
    public BiomeEditor dryFoliageColor(OptionalInt color) {
        var inj = (BiomeModificationContextEffectsContextInj) context.getEffects();
        if (color.isPresent()) {
            inj.smptg$setDryFoliageColor(Optional.of(color.getAsInt()));
        } else {
            inj.smptg$setDryFoliageColor(Optional.empty());
        }
        return this;
    }

    @Override
    public BiomeEditor grassColorModifier(BiomeSpecialEffects.GrassColorModifier modifier) {
        context.getEffects().setGrassColorModifier(modifier);
        return this;
    }

    @Override
    public BiomeEditor musicVolume(float volume) {
        context.getEffects().setMusicVolume(volume);
        return this;
    }

    @Override
    public BiomeEditor backgroundMusic(Optional<WeightedList<Music>> music) {
        context.getEffects().setMusic(music);
        return this;
    }

    @Override
    public BiomeEditor ambientLoopSound(Optional<Holder<SoundEvent>> sound) {
        context.getEffects().setAmbientSound(sound);
        return this;
    }

    @Override
    public BiomeEditor ambientAdditionsSound(Optional<AmbientAdditionsSettings> sound) {
        context.getEffects().setAdditionsSound(sound);
        return this;
    }

    @Override
    public BiomeEditor ambientMoodSound(Optional<AmbientMoodSettings> sound) {
        context.getEffects().setMoodSound(sound);
        return this;
    }

    @Override
    public BiomeEditor particles(Optional<AmbientParticleSettings> particles) {
        context.getEffects().setParticleConfig(particles);
        return this;
    }

    @Override
    public BiomeEditor addFeature(GenerationStep.Decoration step, ResourceKey<PlacedFeature> feature) {
        context.getGenerationSettings().addFeature(step, feature);
        return this;
    }

    @Override
    public BiomeEditor addCarver(ResourceKey<ConfiguredWorldCarver<?>> carver) {
        context.getGenerationSettings().addCarver(carver);
        return this;
    }

    @Override
    public BiomeEditor removeFeature(GenerationStep.Decoration step, ResourceKey<PlacedFeature> feature) {
        context.getGenerationSettings().removeFeature(step, feature);
        return this;
    }

    @Override
    public BiomeEditor removeCarver(ResourceKey<ConfiguredWorldCarver<?>> carver) {
        context.getGenerationSettings().removeCarver(carver);
        return this;
    }

    @Override
    public BiomeEditor addSpawn(MobCategory category, int weight, MobSpawnSettings.SpawnerData spawn) {
        context.getSpawnSettings().addSpawn(category, spawn, weight);
        return this;
    }

    @Override
    public BiomeEditor addMobCharge(EntityType<?> entity, double energyBudget, double charge) {
        context.getSpawnSettings().setSpawnCost(entity, charge, energyBudget);
        return this;
    }

    @Override
    public BiomeEditor clearMobCharge(EntityType<?> entity) {
        context.getSpawnSettings().clearSpawnCost(entity);
        return this;
    }

    @Override
    public BiomeEditor creatureGenerationProbability(float probability) {
        context.getSpawnSettings().setCreatureSpawnProbability(probability);
        return this;
    }

    @Override
    public BiomeEditor removeSpawns(BiPredicate<MobCategory, MobSpawnSettings.SpawnerData> spawnPredicate) {
        context.getSpawnSettings().removeSpawns(spawnPredicate);
        return this;
    }
}
