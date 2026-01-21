package net.foxboi.salted.common.levelgen.biome.modifications;

import java.util.Optional;
import java.util.function.Consumer;

import net.foxboi.salted.common.levelgen.ModVegetationPlacements;
import net.foxboi.salted.common.misc.biome.BiomeEditor;

import static net.minecraft.world.level.levelgen.GenerationStep.Decoration.VEGETAL_DECORATION;

public record PaleGardenModification() implements Consumer<BiomeEditor> {
    @Override
    public void accept(BiomeEditor ctx) {
        ctx.darkRedFoliageColor(0x5E444B);
        ctx.redFoliageColor(0x8D7C76);
        ctx.goldenFoliageColor(0x9C8E7C);
        ctx.yellowFoliageColor(0xB0AE97);
    }
}
