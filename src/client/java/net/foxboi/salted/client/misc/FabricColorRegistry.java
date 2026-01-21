package net.foxboi.salted.client.misc;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.foxboi.salted.common.misc.ColorRegistry;
import net.foxboi.salted.common.misc.Misc;
import net.foxboi.salted.common.misc.biome.color.FoliageColorMap;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;

public class FabricColorRegistry implements ColorRegistry {
    private static final BlockColor GRASS =
            (state, level, pos, tint) -> level != null && pos != null
                    ? BiomeColors.getAverageGrassColor(level, Misc.getDoubleBlockPos(pos, state))
                    : GrassColor.getDefaultColor();

    private static final BlockColor FOLIAGE =
            (state, level, pos, tint) -> level != null && pos != null
                    ? BiomeColors.getAverageFoliageColor(level, Misc.getDoubleBlockPos(pos, state))
                    : 0xFF48B518;

    private static final BlockColor DRY_FOLIAGE =
            (state, level, pos, tint) -> level != null && pos != null
                    ? BiomeColors.getAverageDryFoliageColor(level, Misc.getDoubleBlockPos(pos, state))
                    : 0xFF5C3C32;

    private static final BlockColor WATER =
            (state, level, pos, tint) -> level != null && pos != null
                    ? BiomeColors.getAverageWaterColor(level, Misc.getDoubleBlockPos(pos, state))
                    : 0xFFFFFFFF;

    private static final BlockColor DARK_RED_FOLIAGE = foliage(FoliageColorMap.DARK_RED);
    private static final BlockColor RED_FOLIAGE = foliage(FoliageColorMap.RED);
    private static final BlockColor GOLDEN_FOLIAGE = foliage(FoliageColorMap.GOLDEN);
    private static final BlockColor YELLOW_FOLIAGE = foliage(FoliageColorMap.YELLOW);

    // We compute goldgreen foliage by mixing yellow and default green
    private static final BlockColor GOLDGREEN_FOLIAGE = (state, level, pos, tint) ->
            ARGB.srgbLerp(0.65f, YELLOW_FOLIAGE.getColor(state, level, pos, tint), FOLIAGE.getColor(state, level, pos, tint));

    private static BlockColor foliage(FoliageColorMap map) {
        return (state, level, pos, tint) -> level != null && pos != null
                ? level.getBlockTint(Misc.getDoubleBlockPos(pos, state), map)
                : map.getDefault();
    }

    @Override
    @Deprecated
    public void color(Block block, Identifier colorId) {
    }

    @Override
    public void foliage(Block block) {
        ColorProviderRegistry.BLOCK.register(
                FOLIAGE,
                block
        );
    }

    @Override
    public void dryFoliage(Block block) {
        ColorProviderRegistry.BLOCK.register(
                DRY_FOLIAGE,
                block
        );
    }

    @Override
    public void water(Block block) {
        ColorProviderRegistry.BLOCK.register(
                WATER,
                block
        );
    }

    @Override
    public void grass(Block block) {
        ColorProviderRegistry.BLOCK.register(
                GRASS,
                block
        );
    }

    @Override
    public void darkRedFoliage(Block block) {
        ColorProviderRegistry.BLOCK.register(
                DARK_RED_FOLIAGE,
                block
        );
    }

    @Override
    public void redFoliage(Block block) {
        ColorProviderRegistry.BLOCK.register(
                RED_FOLIAGE,
                block
        );
    }

    @Override
    public void goldenFoliage(Block block) {
        ColorProviderRegistry.BLOCK.register(
                GOLDEN_FOLIAGE,
                block
        );
    }

    @Override
    public void goldgreenFoliage(Block block) {
        ColorProviderRegistry.BLOCK.register(
                GOLDGREEN_FOLIAGE,
                block
        );
    }

    @Override
    public void yellowFoliage(Block block) {
        ColorProviderRegistry.BLOCK.register(
                YELLOW_FOLIAGE,
                block
        );
    }

    @Override
    public void solid(Block block, int rgb) {
        ColorProviderRegistry.BLOCK.register(
                (state, level, pos, tint) -> 0xFF000000 | rgb,
                block
        );
    }
}
