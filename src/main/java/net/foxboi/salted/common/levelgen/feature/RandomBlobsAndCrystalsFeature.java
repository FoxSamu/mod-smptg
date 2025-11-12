package net.foxboi.salted.common.levelgen.feature;

import net.foxboi.salted.common.block.SaltCrystalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.Fluids;

public class RandomBlobsAndCrystalsFeature extends Feature<RandomBlobsAndCrystalsConfig> {
    public RandomBlobsAndCrystalsFeature() {
        super(RandomBlobsAndCrystalsConfig.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<RandomBlobsAndCrystalsConfig> ctx) {
        var level = ctx.level();
        var pos = ctx.origin();
        var rng = ctx.random();
        var config = ctx.config();

        var it = config.iterations().sample(rng);
        var xzs = config.xzSpread().sample(rng);
        var ys = config.ySpread().sample(rng);

        var mpos = pos.mutable();
        var placed = false;

        for (var i = 0; i < it; i++) {
            var x = rng.nextInt(0, xzs) - rng.nextInt(0, xzs);
            var y = rng.nextInt(0, ys) - rng.nextInt(0, ys);
            var z = rng.nextInt(0, xzs) - rng.nextInt(0, xzs);

            mpos.setWithOffset(pos, x, y, z);
            placed |= createBlob(level, mpos, rng, config);
        }

        return placed;
    }

    private boolean createBlob(WorldGenLevel level, BlockPos.MutableBlockPos mpos, RandomSource rng, RandomBlobsAndCrystalsConfig config) {
        var placed = placeBlock(level, mpos, rng, config);
        for (Direction dir : Direction.values()) {
            mpos.move(dir);
            placed |= placeBlock(level, mpos, rng, config);
            mpos.move(dir, -1);
        }

        return placed;
    }

    private boolean placeBlock(WorldGenLevel level, BlockPos pos, RandomSource rng, RandomBlobsAndCrystalsConfig config) {
        var replace = level.getBlockState(pos);

        var blob = config.blob().orElse(null);
        if (blob != null && config.blobPredicate().test(level, pos)) {
            return level.setBlock(pos, blob.getState(rng, pos), Block.UPDATE_ALL_IMMEDIATE);
        }

        return tryPlaceSaltCrystal(level, pos, replace, rng, config);
    }

    private boolean tryPlaceSaltCrystal(WorldGenLevel level, BlockPos pos, BlockState replace, RandomSource rng, RandomBlobsAndCrystalsConfig config) {
        var crystal = config.crystal().orElse(null);
        if (crystal == null) {
            return false;
        }

        if (rng.nextFloat() < config.crystalChance()) {
            return false;
        }

        if (config.crystalPredicate().test(level, pos)) {
            var fluid = level.getFluidState(pos);
            var waterlogged = fluid.isSourceOfType(Fluids.WATER);

            var state = crystal.getState(rng, pos)
                    .trySetValue(SaltCrystalBlock.WATERLOGGED, waterlogged);

            if (config.tryRotateCrystals() && state.hasProperty(SaltCrystalBlock.FACING)) {
                for (Direction dir : Direction.allShuffled(rng)) {
                    state = state.setValue(SaltCrystalBlock.FACING, dir);

                    if (state.canSurvive(level, pos)) {
                        return level.setBlock(pos, state, Block.UPDATE_ALL_IMMEDIATE);
                    }
                }
            } else {
                if (state.canSurvive(level, pos)) {
                    return level.setBlock(pos, state, Block.UPDATE_ALL_IMMEDIATE);
                }
            }
        }

        return false;
    }
}
