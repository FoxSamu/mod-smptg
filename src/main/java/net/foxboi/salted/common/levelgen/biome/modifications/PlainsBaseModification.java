package net.foxboi.salted.common.levelgen.biome.modifications;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.foxboi.salted.common.levelgen.ModVegetationPlacements;
import net.foxboi.salted.common.misc.biome.BiomeEditor;

import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.*;

public record PlainsBaseModification() implements Consumer<BiomeEditor> {
    @Override
    public void accept(BiomeEditor ctx) {
        ctx.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_BARLEY_RARE);
        ctx.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_GRASS_SPROUTS_EXTRA_COMMON);
        ctx.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CATTAIL_NEAR_WATER);
        ctx.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CATTAIL_IN_WATER);
    }
}
