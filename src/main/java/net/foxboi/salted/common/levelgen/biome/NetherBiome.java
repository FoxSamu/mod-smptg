package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.misc.biome.BiomeEditor;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeFeatures.*;
import static net.foxboi.salted.common.levelgen.biome.VanillaBiomeFeatures.commonSpawns;

public abstract class NetherBiome extends AbstractBiome {
    @Override
    protected boolean precipitation() {
        return false;
    }

    @Override
    protected float temperature() {
        return 2f;
    }

    @Override
    protected float downfall() {
        return 0f;
    }

    @Override
    protected void spawning(BiomeEditor builder) {
    }

    @Override
    protected void generation(BiomeEditor builder) {
        addNetherGlobalGeneration(builder);
    }

    @Override
    protected void effects(BiomeEditor builder) {
        defaultEffects(builder);
    }

    @Override
    protected void attributes(BiomeEditor builder) {
    }
}
