package net.foxboi.salted.common.misc.biome;

import java.util.*;
import java.util.function.BiPredicate;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.foxboi.salted.common.misc.biome.color.BiomeFoliageColorExtension;
import net.foxboi.salted.common.misc.biome.color.BiomeSpecialEffectsInj;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.modifier.AttributeModifier;
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
    public BiomeEditor waterColor(int color) {
        context.getEffects().setWaterColor(color);
        return this;
    }

    @Override
    public BiomeEditor grassColor(OptionalInt color) {
        context.getEffects().setGrassColorOverride(color);
        return this;
    }

    @Override
    public BiomeEditor foliageColor(OptionalInt color) {
        context.getEffects().setFoliageColorOverride(color);
        return this;
    }

    @Override
    public BiomeEditor dryFoliageColor(OptionalInt color) {
        context.getEffects().setDryFoliageColorOverride(color);
        return this;
    }

    @Override
    public BiomeEditor darkRedFoliageColor(OptionalInt color) {
        var inj = (BiomeSpecialEffectsInj) context.getEffects();
        var ext = inj.smptg$getFoliageColorExtension();

        var newExt = new BiomeFoliageColorExtension(
                color.isPresent() ? Optional.of(color.getAsInt()) : Optional.empty(),
                ext.redFoliageColorOverride(),
                ext.goldenFoliageColorOverride(),
                ext.yellowFoliageColorOverride()
        );

        inj.smptg$setFoliageColorExtension(newExt);

        return this;
    }

    @Override
    public BiomeEditor redFoliageColor(OptionalInt color) {
        var inj = (BiomeSpecialEffectsInj) context.getEffects();
        var ext = inj.smptg$getFoliageColorExtension();

        var newExt = new BiomeFoliageColorExtension(
                ext.darkRedFoliageColorOverride(),
                color.isPresent() ? Optional.of(color.getAsInt()) : Optional.empty(),
                ext.goldenFoliageColorOverride(),
                ext.yellowFoliageColorOverride()
        );

        inj.smptg$setFoliageColorExtension(newExt);

        return this;
    }

    @Override
    public BiomeEditor goldenFoliageColor(OptionalInt color) {
        var inj = (BiomeSpecialEffectsInj) context.getEffects();
        var ext = inj.smptg$getFoliageColorExtension();

        var newExt = new BiomeFoliageColorExtension(
                ext.darkRedFoliageColorOverride(),
                ext.redFoliageColorOverride(),
                color.isPresent() ? Optional.of(color.getAsInt()) : Optional.empty(),
                ext.yellowFoliageColorOverride()
        );

        inj.smptg$setFoliageColorExtension(newExt);

        return this;
    }

    @Override
    public BiomeEditor yellowFoliageColor(OptionalInt color) {
        var inj = (BiomeSpecialEffectsInj) context.getEffects();
        var ext = inj.smptg$getFoliageColorExtension();

        var newExt = new BiomeFoliageColorExtension(
                ext.darkRedFoliageColorOverride(),
                ext.redFoliageColorOverride(),
                ext.goldenFoliageColorOverride(),
                color.isPresent() ? Optional.of(color.getAsInt()) : Optional.empty()
        );

        inj.smptg$setFoliageColorExtension(newExt);

        return this;
    }

    @Override
    public BiomeEditor grassColorModifier(BiomeSpecialEffects.GrassColorModifier modifier) {
        context.getEffects().setGrassColorModifier(modifier);
        return this;
    }

    @Override
    public <V> BiomeEditor putAttribute(EnvironmentAttribute<V> attribute, V value) {
        context.getAttributes().set(attribute, value);
        return this;
    }

    @Override
    public <V, P> BiomeEditor modifyAttribute(EnvironmentAttribute<V> attribute, AttributeModifier<V, P> modifier, P parameter) {
        context.getAttributes().setModifier(attribute, modifier, parameter);
        return this;
    }

    @Override
    public BiomeEditor putAttributes(EnvironmentAttributeMap map) {
        context.getAttributes().addAll(map);
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
        context.getMobSpawnSettings().addSpawn(category, spawn, weight);
        return this;
    }

    @Override
    public BiomeEditor addMobCharge(EntityType<?> entity, double energyBudget, double charge) {
        context.getMobSpawnSettings().addMobCharge(entity, charge, energyBudget);
        return this;
    }

    @Override
    public BiomeEditor clearMobCharge(EntityType<?> entity) {
        context.getMobSpawnSettings().clearMobCharge(entity);
        return this;
    }

    @Override
    public BiomeEditor creatureGenerationProbability(float probability) {
        context.getMobSpawnSettings().setCreatureGenerationProbability(probability);
        return this;
    }

    @Override
    public BiomeEditor removeSpawns(BiPredicate<MobCategory, MobSpawnSettings.SpawnerData> spawnPredicate) {
        context.getMobSpawnSettings().removeSpawns(spawnPredicate);
        return this;
    }
}
