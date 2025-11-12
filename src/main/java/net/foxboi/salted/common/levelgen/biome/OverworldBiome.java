package net.foxboi.salted.common.levelgen.biome;

import net.minecraft.world.level.biome.*;

import static net.foxboi.salted.common.levelgen.biome.ModBiomeDefaultFeatures.*;
import static net.minecraft.data.worldgen.BiomeDefaultFeatures.*;

public abstract class OverworldBiome extends AbstractBiome {
    @Override
    protected boolean precipitation() {
        return true;
    }

    @Override
    protected void spawning(MobSpawnSettings.Builder builder) {
        commonSpawns(builder);
    }

    @Override
    protected void generation(BiomeGenerationSettings.Builder builder) {
        addGlobalGeneration(builder);
        addDefaultOres(builder);
        addDefaultSoftDisks(builder);
    }

    @Override
    protected void effects(BiomeSpecialEffects.Builder builder) {
        defaultEffects(builder, temperature());
    }
}
