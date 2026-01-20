package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.misc.biome.BiomeEditor;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeFeatures.*;
import static net.foxboi.salted.common.levelgen.biome.VanillaBiomeFeatures.*;

public abstract class OverworldBiome extends AbstractBiome {
    @Override
    protected boolean precipitation() {
        return true;
    }

    @Override
    protected void spawning(BiomeEditor builder) {
        commonSpawns(builder);
    }

    @Override
    protected void generation(BiomeEditor builder) {
        addGlobalGeneration(builder);
    }

    @Override
    protected void effects(BiomeEditor builder) {
        defaultEffects(builder);
    }

    @Override
    protected void attributes(BiomeEditor builder) {
        defaultAttributes(builder, temperature());
    }
}
