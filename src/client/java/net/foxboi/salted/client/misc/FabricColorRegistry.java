package net.foxboi.salted.client.misc;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;

import net.foxboi.salted.common.misc.ColorRegistry;
import net.foxboi.salted.common.misc.Misc;
import net.foxboi.salted.common.misc.biome.color.FoliageColorMap;

import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FabricColorRegistry implements ColorRegistry {
    private static final BlockTintSource GRASS = new BlockTintSource() {
        @Override
        public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
            return BiomeColors.getAverageGrassColor(level, Misc.getDoubleBlockPos(pos, state));
        }

        @Override
        public int color(BlockState state) {
            return GrassColor.getDefaultColor();
        }
    };

    private static final BlockTintSource FOLIAGE = new BlockTintSource() {
        @Override
        public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
            return BiomeColors.getAverageFoliageColor(level, Misc.getDoubleBlockPos(pos, state));
        }

        @Override
        public int color(BlockState state) {
            return 0xFF48B518;
        }
    };

    private static final BlockTintSource DRY_FOLIAGE = new BlockTintSource() {
        @Override
        public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
            return BiomeColors.getAverageDryFoliageColor(level, Misc.getDoubleBlockPos(pos, state));
        }

        @Override
        public int color(BlockState state) {
            return 0xFF5C3C32;
        }
    };

    private static final BlockTintSource WATER = new BlockTintSource() {
        @Override
        public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
            return BiomeColors.getAverageWaterColor(level, Misc.getDoubleBlockPos(pos, state));
        }

        @Override
        public int color(BlockState state) {
            return 0xFFFFFFFF;
        }
    };

    private static final BlockTintSource DARK_RED_FOLIAGE = foliage(FoliageColorMap.DARK_RED);
    private static final BlockTintSource RED_FOLIAGE = foliage(FoliageColorMap.RED);
    private static final BlockTintSource GOLDEN_FOLIAGE = foliage(FoliageColorMap.GOLDEN);
    private static final BlockTintSource YELLOW_FOLIAGE = foliage(FoliageColorMap.YELLOW);

    // We compute goldgreen foliage by mixing yellow and default green
    private static final BlockTintSource GOLDGREEN_FOLIAGE = new BlockTintSource() {
        @Override
        public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
            return ARGB.srgbLerp(0.65f, YELLOW_FOLIAGE.colorInWorld(state, level, pos), FOLIAGE.colorInWorld(state, level, pos));
        }

        @Override
        public int color(BlockState state) {
            return ARGB.srgbLerp(0.65f, YELLOW_FOLIAGE.color(state), FOLIAGE.color(state));
        }
    };

    private static BlockTintSource withParicleColor(BlockTintSource src, boolean colorParticles) {
        return colorParticles ? src : new BlockTintSource() {
            @Override
            public int colorAsTerrainParticle(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return 0xFFFFFFFF;
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return src.colorInWorld(state, level, pos);
            }

            @Override
            public int color(BlockState state) {
                return src.color(state);
            }
        };
    }

    private static BlockTintSource foliage(FoliageColorMap map) {
        return new BlockTintSource() {
            @Override
            public int color(BlockState state) {
                return map.getDefault();
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return level.getBlockTint(Misc.getDoubleBlockPos(pos, state), map);
            }
        };
    }

    @Override
    @Deprecated
    public void color(Block block, Identifier colorId) {
    }

    @Override
    public void foliage(Block block, boolean colorParticles, int indices) {
        BlockColorRegistry.register(
                create(indices, withParicleColor(FOLIAGE, colorParticles)),
                block
        );
    }

    @Override
    public void dryFoliage(Block block, boolean colorParticles, int indices) {
        BlockColorRegistry.register(
                create(indices, withParicleColor(DRY_FOLIAGE, colorParticles)),
                block
        );
    }

    @Override
    public void water(Block block, boolean colorParticles, int indices) {
        BlockColorRegistry.register(
                create(indices, withParicleColor(WATER, colorParticles)),
                block
        );
    }

    @Override
    public void grass(Block block, boolean colorParticles, int indices) {
        BlockColorRegistry.register(
                create(indices, withParicleColor(GRASS, colorParticles)),
                block
        );
    }

    @Override
    public void darkRedFoliage(Block block, boolean colorParticles, int indices) {
        BlockColorRegistry.register(
                create(indices, withParicleColor(DARK_RED_FOLIAGE, colorParticles)),
                block
        );
    }

    @Override
    public void redFoliage(Block block, boolean colorParticles, int indices) {
        BlockColorRegistry.register(
                create(indices, withParicleColor(RED_FOLIAGE, colorParticles)),
                block
        );
    }

    @Override
    public void goldenFoliage(Block block, boolean colorParticles, int indices) {
        BlockColorRegistry.register(
                create(indices, withParicleColor(GOLDEN_FOLIAGE, colorParticles)),
                block
        );
    }

    @Override
    public void goldgreenFoliage(Block block, boolean colorParticles, int indices) {
        BlockColorRegistry.register(
                create(indices, withParicleColor(GOLDGREEN_FOLIAGE, colorParticles)),
                block
        );
    }

    @Override
    public void yellowFoliage(Block block, boolean colorParticles, int indices) {
        BlockColorRegistry.register(
                create(indices, withParicleColor(YELLOW_FOLIAGE, colorParticles)),
                block
        );
    }

    @Override
    public void solid(Block block, int rgb, boolean colorParticles, int indices) {
        BlockColorRegistry.register(
                create(indices, new BlockTintSource() {
                    @Override
                    public int colorAsTerrainParticle(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                        return colorParticles ? 0xFF000000 | rgb : 0xFFFFFFFF;
                    }

                    @Override
                    public int color(BlockState state) {
                        return 0xFF000000 | rgb;
                    }
                }),
                block
        );
    }

    private static List<BlockTintSource> create(int indices, BlockTintSource src) {
        return IntStream.range(0, indices).mapToObj(_ -> src).toList();
    }
}
