package net.foxboi.salted.common.util;

import java.util.Set;

import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.levelgen.ModVegetationFeatures;
import net.foxboi.salted.common.levelgen.ModVegetationPlacements;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public abstract class BonemealGrassGrower {
    public static final Set<Block> GRASS_VALID_BLOCKS = Set.of(
            Blocks.GRASS_BLOCK,
            ModBlocks.MOSSY_DIRT
    );

    protected final ServerLevel level;
    protected final RandomSource rng;
    protected final BlockPos pos;

    protected BonemealGrassGrower(ServerLevel level, RandomSource rng, BlockPos pos) {
        this.level = level;
        this.rng = rng;
        this.pos = pos;
    }

    protected abstract boolean isValidBlockToGrowOn(BlockState state);

    protected abstract void growAtPos(ServerLevel level, RandomSource rng, BlockPos pos);

    public void apply() {
        var pos = this.pos.above();

        var mpos = pos.mutable();
        var belowMpos = pos.mutable();

        attempts:
        for (var attempt = 0; attempt < 128; attempt++) {
            mpos.set(pos);

            // Move to nearby location
            for (int i = 0; i < attempt / 16; i++) {
                mpos.move(rng.nextInt(3) - 1, (rng.nextInt(3) - 1) * rng.nextInt(3) / 2, rng.nextInt(3) - 1);
                belowMpos.setWithOffset(mpos, Direction.DOWN);

                if (!isValidBlockToGrowOn(level.getBlockState(belowMpos))) {
                    continue attempts;
                }

                if (level.getBlockState(mpos).isCollisionShapeFullBlock(level, mpos)) {
                    continue attempts;
                }
            }

            growAtPos(level, rng, mpos);
        }
    }

    public static void grass(ServerLevel level, RandomSource rng, BlockPos pos) {
        new Grass(level, rng, pos).apply();
    }

    public static void grassAndFlowers(ServerLevel level, RandomSource rng, BlockPos pos) {
        new GrassAndFlowers(level, rng, pos).apply();
    }

    public static void grassAndMoss(ServerLevel level, RandomSource rng, BlockPos pos) {
        new GrassAndMoss(level, rng, pos).apply();
    }

    private static class Grass extends BonemealGrassGrower {
        private final Holder<PlacedFeature> grassFeature;

        public Grass(ServerLevel level, RandomSource rng, BlockPos pos) {
            super(level, rng, pos);

            grassFeature = level.registryAccess()
                    .lookupOrThrow(Registries.PLACED_FEATURE)
                    .get(VegetationPlacements.GRASS_BONEMEAL)
                    .orElse(null);
        }

        @Override
        protected boolean isValidBlockToGrowOn(BlockState state) {
            return GRASS_VALID_BLOCKS.contains(state.getBlock());
        }

        protected Holder<PlacedFeature> getGrowFeature(ServerLevel level, RandomSource rng, BlockPos pos) {
            return grassFeature;
        }

        @Override
        protected void growAtPos(ServerLevel level, RandomSource rng, BlockPos pos) {
            var localState = level.getBlockState(pos);

            // Randomly grow short grass into tall grass
            if (localState.is(Blocks.SHORT_GRASS) && rng.nextInt(10) == 0) {
                var shortGrassBonemealable = (BonemealableBlock) Blocks.SHORT_GRASS;

                if (shortGrassBonemealable.isValidBonemealTarget(level, pos, localState)) {
                    shortGrassBonemealable.performBonemeal(level, rng, pos, localState);
                }
            }

            // If no air at this position, we can't grow anything here
            if (!localState.isAir()) {
                return;
            }

            // Place plant feature
            var growFeature = getGrowFeature(level, rng, pos);
            if (growFeature != null) {
                growFeature.value().place(level, level.getChunkSource().getGenerator(), rng, pos);
            }
        }
    }

    private static class GrassAndFlowers extends Grass {
        public GrassAndFlowers(ServerLevel level, RandomSource rng, BlockPos pos) {
            super(level, rng, pos);
        }

        protected Holder<PlacedFeature> getGrowFeature(ServerLevel level, RandomSource rng, BlockPos pos) {
            if (rng.nextInt(8) == 0) {
                var flowers = level.getBiome(pos).value().getGenerationSettings().getFlowerFeatures();

                if (!flowers.isEmpty()) {
                    var flowerIndex = rng.nextInt(flowers.size());
                    return ((RandomPatchConfiguration) (flowers.get(flowerIndex)).config()).feature();
                }
            }

            return super.getGrowFeature(level, rng, pos);
        }
    }

    private static class GrassAndMoss extends Grass {
        private final Holder<PlacedFeature> mossFeature;

        public GrassAndMoss(ServerLevel level, RandomSource rng, BlockPos pos) {
            super(level, rng, pos);

            mossFeature = level.registryAccess()
                    .lookupOrThrow(Registries.PLACED_FEATURE)
                    .get(ModVegetationPlacements.MOSS_CARPET_BONEMEAL)
                    .orElse(null);
        }


        protected Holder<PlacedFeature> getGrowFeature(ServerLevel level, RandomSource rng, BlockPos pos) {
            if (rng.nextInt(3) == 0) {
                return mossFeature;
            }

            return super.getGrowFeature(level, rng, pos);
        }
    }
}
