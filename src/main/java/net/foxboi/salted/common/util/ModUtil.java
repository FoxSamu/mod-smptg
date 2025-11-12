package net.foxboi.salted.common.util;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class ModUtil {
    public static BlockPos getDoubleBlockPos(BlockPos pos, BlockState state) {
        return switch (state.getValueOrElse(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER)) {
            case UPPER -> pos.below();
            case LOWER -> pos;
        };
    }

    public static final List<Direction> DIRECTIONS = List.of(Direction.values());
    public static final List<Direction.Axis> AXES = List.of(Direction.Axis.values());
    public static final List<Direction.AxisDirection> AXIS_DIRECTIONS = List.of(Direction.AxisDirection.values());
    public static final List<Direction.Plane> PLANES = List.of(Direction.Plane.values());
    public static final List<Direction> HORIZONTAL_DIRECTIONS = Direction.Plane.HORIZONTAL.stream().toList();
    public static final List<Direction> X_DIRECTIONS = Direction.Plane.HORIZONTAL.stream().filter(Direction.Axis.X).toList();
    public static final List<Direction> Z_DIRECTIONS = Direction.Plane.HORIZONTAL.stream().filter(Direction.Axis.Z).toList();
    public static final List<Direction> VERTICAL_DIRECTIONS = Direction.Plane.VERTICAL.stream().toList();
}
