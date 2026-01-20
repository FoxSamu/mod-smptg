package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.misc.biome.BiomeBuilder;
import net.foxboi.salted.common.misc.biome.BiomeEditor;
import net.foxboi.salted.common.misc.data.Definition;
import net.foxboi.salted.common.misc.data.DefinitionContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.*;

public abstract class AbstractBiome implements Definition<Biome> {
    @Override
    public Biome create(ResourceKey<Biome> key, DefinitionContext context) {
        var features = context.lookupOrThrow(Registries.PLACED_FEATURE);
        var carvers = context.lookupOrThrow(Registries.CONFIGURED_CARVER);

        var biomeBuilder = new BiomeBuilder(features, carvers);

        attributes(biomeBuilder);
        effects(biomeBuilder);
        generation(biomeBuilder);
        spawning(biomeBuilder);

        biomeBuilder
                .hasPrecipitation(precipitation())
                .temperature(temperature())
                .downfall(downfall());

        return biomeBuilder.build();
    }

    protected abstract boolean precipitation();
    protected abstract float temperature();
    protected abstract float downfall();
    protected abstract void attributes(BiomeEditor builder);
    protected abstract void effects(BiomeEditor builder);
    protected abstract void generation(BiomeEditor builder);
    protected abstract void spawning(BiomeEditor builder);
}
