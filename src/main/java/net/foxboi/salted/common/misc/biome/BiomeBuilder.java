package net.foxboi.salted.common.misc.biome;

import java.util.*;
import java.util.function.BiPredicate;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.attribute.*;
import net.minecraft.world.attribute.modifier.AttributeModifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BiomeBuilder implements BiomeEditor {
    private final HolderGetter<PlacedFeature> featureReg;
    private final HolderGetter<ConfiguredWorldCarver<?>> carverReg;

    public BiomeBuilder(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
        this.featureReg = features;
        this.carverReg = carvers;
    }

    private boolean precipitation = true;
    private float temperature = 0.5f;
    private float downfall = 0.5f;

    @Override
    public BiomeEditor hasPrecipitation(boolean precipitation) {
        this.precipitation = precipitation;
        return this;
    }

    @Override
    public BiomeEditor temperature(float temperature) {
        this.temperature = temperature;
        return this;
    }

    @Override
    public BiomeEditor downfall(float downfall) {
        this.downfall = downfall;
        return this;
    }

    private int waterColor;
    private OptionalInt grassColor = OptionalInt.empty();
    private OptionalInt foliageColor = OptionalInt.empty();
    private OptionalInt dryFoliageColor = OptionalInt.empty();
    private BiomeSpecialEffects.GrassColorModifier grassColorModifier = BiomeSpecialEffects.GrassColorModifier.NONE;

    private final EnvironmentAttributeMap.Builder attributes = EnvironmentAttributeMap.builder();

    @Override
    public BiomeEditor waterColor(int color) {
        this.waterColor = color;
        return this;
    }

    @Override
    public BiomeEditor grassColor(OptionalInt color) {
        this.grassColor = color;
        return this;
    }

    @Override
    public BiomeEditor foliageColor(OptionalInt color) {
        this.foliageColor = color;
        return this;
    }

    @Override
    public BiomeEditor dryFoliageColor(OptionalInt color) {
        this.dryFoliageColor = color;
        return this;
    }

    @Override
    public BiomeEditor grassColorModifier(BiomeSpecialEffects.GrassColorModifier modifier) {
        this.grassColorModifier = modifier;
        return this;
    }

    @Override
    public <V> BiomeEditor putAttribute(EnvironmentAttribute<V> attribute, V value) {
        attributes.set(attribute, value);
        return this;
    }

    @Override
    public <V, P> BiomeEditor modifyAttribute(EnvironmentAttribute<V> attribute, AttributeModifier<V, P> modifier, P parameter) {
        attributes.modify(attribute, modifier, parameter);
        return this;
    }

    @Override
    public BiomeEditor putAttributes(EnvironmentAttributeMap map) {
        attributes.putAll(map);
        return this;
    }

    private final EnumMap<GenerationStep.Decoration, Set<ResourceKey<PlacedFeature>>> features = new EnumMap<>(GenerationStep.Decoration.class);
    private final Set<ResourceKey<ConfiguredWorldCarver<?>>> carvers = new LinkedHashSet<>();

    @Override
    public BiomeEditor addFeature(GenerationStep.Decoration step, ResourceKey<PlacedFeature> feature) {
        features.computeIfAbsent(step, it -> new LinkedHashSet<>())
                .add(feature);
        return this;
    }

    @Override
    public BiomeEditor addCarver(ResourceKey<ConfiguredWorldCarver<?>> carver) {
        carvers.add(carver);
        return this;
    }

    @Override
    public BiomeEditor removeFeature(GenerationStep.Decoration step, ResourceKey<PlacedFeature> feature) {
        if (features.containsKey(step)) {
            features.get(step).remove(feature);
        }
        return this;
    }

    @Override
    public BiomeEditor removeCarver(ResourceKey<ConfiguredWorldCarver<?>> carver) {
        carvers.remove(carver);
        return this;
    }

    private final EnumMap<MobCategory, List<Weighted<MobSpawnSettings.SpawnerData>>> spawns = new EnumMap<>(MobCategory.class);
    private final Map<EntityType<?>, MobSpawnSettings.MobSpawnCost> spawnCharges = new HashMap<>();
    private float creatureGenerationProbability = 0.1f;

    @Override
    public BiomeEditor addSpawn(MobCategory category, int weight, MobSpawnSettings.SpawnerData spawn) {
        spawns.computeIfAbsent(category, it -> new ArrayList<>())
                .add(new Weighted<>(spawn, weight));
        return this;
    }

    @Override
    public BiomeEditor removeSpawns(BiPredicate<MobCategory, MobSpawnSettings.SpawnerData> spawnPredicate) {
        spawns.forEach((cat, list) -> list.removeIf(weighted -> spawnPredicate.test(cat, weighted.value())));
        return this;
    }

    @Override
    public BiomeEditor addMobCharge(EntityType<?> entity, double energyBudget, double charge) {
        spawnCharges.put(entity, new MobSpawnSettings.MobSpawnCost(energyBudget, charge));
        return this;
    }

    @Override
    public BiomeEditor clearMobCharge(EntityType<?> entity) {
        spawnCharges.remove(entity);
        return this;
    }

    @Override
    public BiomeEditor creatureGenerationProbability(float probability) {
        this.creatureGenerationProbability = probability;
        return this;
    }

    private BiomeSpecialEffects effects() {
        var effects = new BiomeSpecialEffects.Builder()
                .waterColor(waterColor)
                .grassColorModifier(grassColorModifier);

        grassColor.ifPresent(effects::grassColorOverride);
        foliageColor.ifPresent(effects::foliageColorOverride);
        dryFoliageColor.ifPresent(effects::dryFoliageColorOverride);

        return effects.build();
    }

    private MobSpawnSettings spawns() {
        var spawns = new MobSpawnSettings.Builder();

        this.spawns.forEach((cat, weights) -> {
            for (var weight : weights) {
                spawns.addSpawn(cat, weight.weight(), weight.value());
            }
        });

        this.spawnCharges.forEach((type, cost) -> spawns.addMobCharge(type, cost.energyBudget(), cost.charge()));

        return spawns
                .creatureGenerationProbability(creatureGenerationProbability)
                .build();
    }

    private BiomeGenerationSettings generation() {
        var generation = new BiomeGenerationSettings.Builder(featureReg, carverReg);

        features.forEach((step, features) -> {
            for (var key : features) {
                generation.addFeature(step, key);
            }
        });

        for (var key : carvers) {
            generation.addCarver(key);
        }

        return generation.build();
    }

    public Biome build() {
        return new Biome.BiomeBuilder()
                .putAttributes(attributes)
                .temperature(temperature)
                .downfall(downfall)
                .hasPrecipitation(precipitation)
                .specialEffects(effects())
                .mobSpawnSettings(spawns())
                .generationSettings(generation())
                .build();
    }
}
