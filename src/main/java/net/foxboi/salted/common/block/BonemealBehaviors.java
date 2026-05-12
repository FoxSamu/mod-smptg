package net.foxboi.salted.common.block;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public record BonemealBehaviors() {
    /**
     * Bonemeal behavior that does nothing.
     */
    public static BonemealableBlock nothing() {
        return Nothing.INSTANCE;
    }

    private enum Nothing implements BonemealableBlock {
        INSTANCE;

        @Override
        public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
            return false;
        }

        @Override
        public boolean isBonemealSuccess(Level level, RandomSource rng, BlockPos pos, BlockState state) {
            return false;
        }

        @Override
        public void performBonemeal(ServerLevel level, RandomSource rng, BlockPos pos, BlockState state) {
        }
    }


    /**
     * Bonemeal behavior that places a short plant at the position of this plant.
     */
    public static BonemealableBlock growIntoShortPlant(Supplier<BlockState> shortPlant) {
        return new GrowIntoShortPlant(shortPlant);
    }

    private record GrowIntoShortPlant(Supplier<BlockState> shortPlant) implements BonemealableBlock {
        @Override
        public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
            return true;
        }

        @Override
        public boolean isBonemealSuccess(Level level, RandomSource rng, BlockPos pos, BlockState state) {
            return true;
        }

        @Override
        public void performBonemeal(ServerLevel level, RandomSource rng, BlockPos pos, BlockState state) {
            level.setBlock(pos, shortPlant.get(), 2);
        }
    }


    /**
     * Bonemeal behavior that places a tall plant at the position of this plant.
     */
    public static BonemealableBlock growIntoTallPlant(Supplier<BlockState> shortPlant) {
        return new GrowIntoTallPlant(shortPlant);
    }

    private record GrowIntoTallPlant(Supplier<BlockState> tallPlant) implements BonemealableBlock {
        @Override
        public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
            return tallPlant.get().canSurvive(level, pos) && level.isEmptyBlock(pos.above());
        }

        @Override
        public boolean isBonemealSuccess(Level level, RandomSource rng, BlockPos pos, BlockState state) {
            return true;
        }

        @Override
        public void performBonemeal(ServerLevel level, RandomSource rng, BlockPos pos, BlockState state) {
            DoublePlantBlock.placeAt(level, tallPlant.get(), pos, 2);
        }
    }


    /**
     * Bonemeal behavior that throws the plant as an item when bonemealed.
     */
    public static BonemealableBlock throwMyselfAsItem() {
        return ThrowMyselfAsItem.INSTANCE;
    }

    private enum ThrowMyselfAsItem implements BonemealableBlock {
        INSTANCE;

        @Override
        public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
            return true;
        }

        @Override
        public boolean isBonemealSuccess(Level level, RandomSource rng, BlockPos pos, BlockState state) {
            return true;
        }

        @Override
        public void performBonemeal(ServerLevel level, RandomSource rng, BlockPos pos, BlockState state) {
            Block.popResource(level, pos, new ItemStack(state.getBlock()));
        }
    }


    /**
     * Bonemeal behavior that grows a segmentable block.
     */
    public static BonemealableBlock growSegmentedPlant() {
        return GrowSegmentableBlock.INSTANCE;
    }

    private enum GrowSegmentableBlock implements BonemealableBlock {
        INSTANCE;

        @Override
        public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
            return true;
        }

        @Override
        public boolean isBonemealSuccess(Level level, RandomSource rng, BlockPos pos, BlockState state) {
            return true;
        }

        @Override
        public void performBonemeal(ServerLevel level, RandomSource rng, BlockPos pos, BlockState state) {
            var block = state.getBlock();

            if (block instanceof SegmentableBlock segmentableBlock) {
                var amountProperty = segmentableBlock.getSegmentAmountProperty();
                var amount = (int) state.getValue(amountProperty);

                if (amount < 4) {
                    level.setBlock(pos, state.setValue(amountProperty, amount + 1), 2);
                    return;
                }
            }

            Block.popResource(level, pos, new ItemStack(block));
        }
    }


    /**
     * Bonemeal behavior that turns a segmentable block into another.
     */
    public static BonemealableBlock replaceSegmentedPlant(Supplier<BlockState> into) {
        return new ReplaceSegmentedBlock(into);
    }

    private record ReplaceSegmentedBlock(Supplier<BlockState> into) implements BonemealableBlock {
        @Override
        public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
            return true;
        }

        @Override
        public boolean isBonemealSuccess(Level level, RandomSource rng, BlockPos pos, BlockState state) {
            return true;
        }

        @Override
        public void performBonemeal(ServerLevel level, RandomSource rng, BlockPos pos, BlockState state) {
            var block = state.getBlock();

            var result = into.get();

            var segments = 4;
            var facing = Direction.NORTH;

            if (block instanceof SegmentableBlock segmentableBlock) {
                var amountProperty = segmentableBlock.getSegmentAmountProperty();
                segments = state.getValue(amountProperty);
                facing = state.getValueOrElse(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH);
            }

            if (result.getBlock() instanceof SegmentableBlock segmentableBlock) {
                var amountProperty = segmentableBlock.getSegmentAmountProperty();
                result = result.setValue(amountProperty, segments)
                        .trySetValue(BlockStateProperties.HORIZONTAL_FACING, facing);
            }

            level.setBlock(pos, result, 2);
        }
    }


    /**
     * Bonemeal behavior that spreads the plant to a neighbor position.
     */
    public static BonemealableBlock spreadToNeighbour() {
        return SpreadToNeighbour.INSTANCE;
    }

    private enum SpreadToNeighbour implements BonemealableBlock {
        INSTANCE;

        @Override
        public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
            return BonemealableBlock.hasSpreadableNeighbourPos(level, pos, state);
        }

        @Override
        public boolean isBonemealSuccess(Level level, RandomSource rng, BlockPos pos, BlockState state) {
            return true;
        }

        @Override
        public void performBonemeal(ServerLevel level, RandomSource rng, BlockPos pos, BlockState state) {
            BonemealableBlock
                    .findSpreadableNeighbourPos(level, pos, state)
                    .ifPresent(it -> level.setBlockAndUpdate(it, state));
        }
    }


    /**
     * Bonemeal behavior that grows a column plant by a random amount.
     */
    public static BonemealableBlock growColumnPlant(IntProvider amount) {
        return new GrowColumnPlantBlock(amount);
    }

    /**
     * Bonemeal behavior that grows a column plant by a random amount.
     */
    public static BonemealableBlock growColumnPlant(int min, int max) {
        return new GrowColumnPlantBlock(UniformInt.of(min, max));
    }

    private record GrowColumnPlantBlock(IntProvider amount) implements BonemealableBlock {
        @Override
        public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
            if (state.getBlock() instanceof AbstractColumnPlantBlock columnPlant) {
                return columnPlant.canGrowAtLeast(level, pos, 1);
            }
            return false;
        }

        @Override
        public boolean isBonemealSuccess(Level level, RandomSource rng, BlockPos pos, BlockState state) {
            return true;
        }

        @Override
        public void performBonemeal(ServerLevel level, RandomSource rng, BlockPos pos, BlockState state) {
            if (state.getBlock() instanceof AbstractColumnPlantBlock columnPlant) {
                columnPlant.grow(level, pos, amount.sample(rng));
            }
        }
    }

    public static BonemealableBlock spreadMultifaceBlock() {
        return SpreadMultifaceBlock.INSTANCE;
    }

    private enum SpreadMultifaceBlock implements BonemealableBlock {
        INSTANCE;

        private MultifaceSpreader getSpreader(Block block) {
            if (block instanceof MultifaceSpreadeableBlock mfsb) {
                return mfsb.getSpreader();
            }

            if (block instanceof SpreadingMultifaceBlock smfb) {
                return smfb.getSpreader();
            }

            return null;
        }

        @Override
        public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
            var spreader = getSpreader(state.getBlock());
            if (spreader == null) {
                return false;
            }

            return Direction.stream().anyMatch(it -> spreader.canSpreadInAnyDirection(state, level, pos, it));
        }

        @Override
        public boolean isBonemealSuccess(Level level, RandomSource rng, BlockPos pos, BlockState state) {
            return getSpreader(state.getBlock()) != null;
        }

        @Override
        public void performBonemeal(ServerLevel level, RandomSource rng, BlockPos pos, BlockState state) {
            var spreader = getSpreader(state.getBlock());
            if (spreader == null) {
                return;
            }

            spreader.spreadFromRandomFaceTowardRandomDirection(state, level, pos, rng);
        }
    }
}
