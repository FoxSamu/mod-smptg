package net.foxboi.salted.common.levelgen.biome.modifications;

import net.minecraft.data.worldgen.placement.VegetationPlacements;

import net.foxboi.salted.common.levelgen.ModVegetationPlacements;
import net.foxboi.salted.common.misc.biome.BiomeEditor;

import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.VEGETAL_DECORATION;

public class BirchForestModifications extends ForestBaseModification {
    private final boolean oldGrowth;

    public BirchForestModifications(boolean oldGrowth) {
        this.oldGrowth = oldGrowth;
    }

    @Override
    public void accept(BiomeEditor ctx) {
        if (oldGrowth) {
            ctx.removeFeature(VEGETAL_DECORATION, VegetationPlacements.BIRCH_TALL);
            ctx.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.TREES_OLD_GROWTH_BIRCH_FOREST);
        } else {
            ctx.removeFeature(VEGETAL_DECORATION, VegetationPlacements.TREES_BIRCH);
            ctx.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.TREES_BIRCH_FOREST);
        }

        super.accept(ctx);
    }
}
