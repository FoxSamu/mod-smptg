package net.foxboi.salted.common.levelgen.biome.modifications;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.foxboi.salted.common.levelgen.ModVegetationPlacements;

import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.VEGETAL_DECORATION;

public record ForestBaseModification() implements Consumer<BiomeModificationContext> {
    @Override
    public void accept(BiomeModificationContext ctx) {
        var settings = ctx.getGenerationSettings();

        settings.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_GRASS_SPROUTS);
        settings.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CATTAIL_NEAR_WATER);
        settings.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CATTAIL_IN_WATER);
    }
}
