package net.foxboi.salted.client;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.foxboi.salted.client.color.BiomeColorTint;
import net.foxboi.salted.common.util.ColorRegistry;
import net.foxboi.salted.common.util.ModUtil;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;

public class MinecraftColorRegistry implements ColorRegistry {
    private static final BlockColor GRASS =
            (state, level, pos, tint) -> level != null && pos != null
                    ? BiomeColors.getAverageGrassColor(level, ModUtil.getDoubleBlockPos(pos, state))
                    : GrassColor.getDefaultColor();

    private static final BlockColor FOLIAGE =
            (state, level, pos, tint) -> level != null && pos != null
                    ? BiomeColors.getAverageFoliageColor(level, ModUtil.getDoubleBlockPos(pos, state))
                    : 0xFF48B518;

    private static final BlockColor DRY_FOLIAGE =
            (state, level, pos, tint) -> level != null && pos != null
                    ? BiomeColors.getAverageDryFoliageColor(level, ModUtil.getDoubleBlockPos(pos, state))
                    : 0xFF5C3C32;

    private static final BlockColor WATER =
            (state, level, pos, tint) -> level != null && pos != null
                    ? BiomeColors.getAverageWaterColor(level, ModUtil.getDoubleBlockPos(pos, state))
                    : 0xFFFFFFFF;

    private final BlockColors colors;

    public MinecraftColorRegistry(BlockColors colors) {
        this.colors = colors;
    }

    @Override
    public void color(Block block, ResourceLocation colorId) {
        colors.register(
                new BiomeColorTint(colorId),
                block
        );
    }

    @Override
    public void foliage(Block block) {
        colors.register(
                FOLIAGE,
                block
        );
    }

    @Override
    public void dryFoliage(Block block) {
        colors.register(
                DRY_FOLIAGE,
                block
        );
    }

    @Override
    public void water(Block block) {
        colors.register(
                WATER,
                block
        );
    }

    @Override
    public void grass(Block block) {
        colors.register(
                GRASS,
                block
        );
    }

    @Override
    public void solid(Block block, int rgb) {
        colors.register(
                (state, level, pos, tint) -> 0xFF000000 | rgb,
                block
        );
    }
}
