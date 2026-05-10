package net.foxboi.salted.common.levelgen;

import java.util.OptionalInt;
import java.util.function.BiPredicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import net.foxboi.salted.common.misc.Range;

public class LevelgenUtil {

    public static boolean isEmptyOrWater(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER);
    }

    public static boolean isEmptyOrWaterOrLava(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER) || state.is(Blocks.LAVA);
    }

    public static boolean isEmptyOrWater(LevelAccessor level, BlockPos pos) {
        return level.isStateAtPosition(pos, LevelgenUtil::isEmptyOrWater);
    }

    public static boolean isEmptyOrWaterOrLava(LevelAccessor level, BlockPos pos) {
        return level.isStateAtPosition(pos, LevelgenUtil::isEmptyOrWaterOrLava);
    }

    public static Range scanColumn(WorldGenLevel level, BlockPos pos, int searchRange, BiPredicate<WorldGenLevel, BlockPos> pathPredicate, BiPredicate<WorldGenLevel, BlockPos> edgePredicate) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        if (!pathPredicate.test(level, mutablePos)) {
            return Range.EMPTY;
        }

        var nearestEmptyY = pos.getY();
        var ceiling = scanDirection(level, searchRange, pathPredicate, edgePredicate, mutablePos, nearestEmptyY, Direction.UP);
        var floor = scanDirection(level, searchRange, pathPredicate, edgePredicate, mutablePos, nearestEmptyY, Direction.DOWN);
        if (ceiling.isEmpty() || floor.isEmpty()) {
            return Range.EMPTY;
        }

        return new Range(floor, ceiling);
    }

    private static OptionalInt scanDirection(WorldGenLevel level, int searchRange, BiPredicate<WorldGenLevel, BlockPos> pathPredicate, BiPredicate<WorldGenLevel, BlockPos> edgePredicate, BlockPos.MutableBlockPos mpos, int nearestEmptyY, Direction direction) {
        mpos.setY(nearestEmptyY);

        for (var i = 1; i < searchRange; i++) {
            if (!pathPredicate.test(level, mpos)) {
                break;
            }

            mpos.move(direction);
        }

        return edgePredicate.test(level, mpos)
                ? OptionalInt.of(mpos.getY())
                : OptionalInt.empty();
    }
}
