package net.foxboi.salted.common.levelgen.biome.modifications;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.foxboi.salted.common.levelgen.ModVegetationPlacements;

import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.VEGETAL_DECORATION;

public record SwampModification() implements Consumer<BiomeModificationContext> {
    @Override
    public void accept(BiomeModificationContext ctx) {
        var settings = ctx.getGenerationSettings();

        settings.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_GRASS_SPROUTS_EXTRA_COMMON);
        settings.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CATTAIL_SWAMP);
        settings.addFeature(VEGETAL_DECORATION, ModVegetationPlacements.PATCH_CATTAIL_IN_WATER_SWAMP);
    }
}
