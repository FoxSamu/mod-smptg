package net.foxboi.salted.client;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.foxboi.salted.client.color.BiomeColorTint;
import net.foxboi.salted.common.misc.ColorRegistry;
import net.foxboi.salted.common.misc.Misc;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.resources.Identifier;
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

    @Override
    public void color(Block block, Identifier colorId) {
        ColorProviderRegistry.BLOCK.register(
                new BiomeColorTint(colorId),
                block
        );
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
    public void solid(Block block, int rgb) {
        ColorProviderRegistry.BLOCK.register(
                (state, level, pos, tint) -> 0xFF000000 | rgb,
                block
        );
    }
}
