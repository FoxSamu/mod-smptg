package net.foxboi.salted.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class PeatUtil {

    public enum FluidType {
        EMPTY,
        WATER,
        LAVA
    }


    public static boolean isBurning(ServerLevel level, BlockPos pos) {
        return level.getBlockState(pos.above()).is(BlockTags.FIRE);
    }

    public static boolean isNearLava(ServerLevel level, BlockPos pos) {
        return findFluid(level, pos.mutable(), 2) == FluidType.LAVA;
    }

    public static boolean isNearWater(ServerLevel level, BlockPos pos) {
        return findFluid(level, pos.mutable(), 2) == FluidType.WATER;
    }

    public static FluidType findFluid(ServerLevel level, BlockPos.MutableBlockPos mpos, int depth) {
        if (level.getFluidState(mpos).is(Fluids.LAVA)) {
            return FluidType.LAVA;
        }

        if (level.getFluidState(mpos).is(Fluids.WATER)) {
            return FluidType.WATER;
        }

        if (depth == 0) {
            return FluidType.EMPTY;
        }

        if (!level.getBlockState(mpos).is(ModBlockTags.TRANSFERS_FLUID_TO_PEAT)) {
            return FluidType.EMPTY;
        }

        var fluid = FluidType.EMPTY;

        fluid = findFluid(level, mpos, Direction.NORTH, fluid, depth - 1);
        fluid = findFluid(level, mpos, Direction.EAST, fluid, depth - 1);
        fluid = findFluid(level, mpos, Direction.SOUTH, fluid, depth - 1);
        fluid = findFluid(level, mpos, Direction.WEST, fluid, depth - 1);
        fluid = findFluid(level, mpos, Direction.UP, fluid, depth - 1);
        fluid = findFluid(level, mpos, Direction.DOWN, fluid, depth - 1);

        return fluid;
    }

    private static FluidType findFluid(ServerLevel level, BlockPos.MutableBlockPos mpos, Direction dir, FluidType current, int depth) {
        if (current == FluidType.LAVA) {
            return current;
        }

        try {
            mpos.move(dir);
            var fluid = findFluid(level, mpos, depth);
            if (fluid.ordinal() > current.ordinal()) {
                return fluid;
            }
            return current;
        } finally {
            mpos.move(dir, -1);
        }
    }
}
