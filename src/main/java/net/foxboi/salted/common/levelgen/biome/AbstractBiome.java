package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.util.Definition;
import net.foxboi.salted.common.util.DefinitionContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.*;

public abstract class AbstractBiome implements Definition<Biome> {
    @Override
    public Biome create(ResourceKey<Biome> key, DefinitionContext context) {
        var features = context.lookupOrThrow(Registries.PLACED_FEATURE);
        var carvers = context.lookupOrThrow(Registries.CONFIGURED_CARVER);

        var effects = new BiomeSpecialEffects.Builder();
        effects(effects);

        var spawning = new MobSpawnSettings.Builder();
        spawning(spawning);

        var generation = new BiomeGenerationSettings.Builder(features, carvers);
        generation(generation);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(precipitation())
                .temperature(temperature())
                .downfall(downfall())
                .specialEffects(effects.build())
                .mobSpawnSettings(spawning.build())
                .generationSettings(generation.build())
                .build();
    }

    protected abstract boolean precipitation();
    protected abstract float temperature();
    protected abstract float downfall();
    protected abstract void spawning(MobSpawnSettings.Builder builder);
    protected abstract void generation(BiomeGenerationSettings.Builder builder);
    protected abstract void effects(BiomeSpecialEffects.Builder builder);
}
