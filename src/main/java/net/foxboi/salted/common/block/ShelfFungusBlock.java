package net.foxboi.salted.common.block;

import java.util.EnumMap;
import java.util.Map;

import com.mojang.serialization.MapCodec;
import net.foxboi.salted.common.misc.DiagonalDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShelfFungusBlock extends DiagonallyAttachableBlock {
    public static final MapCodec<ShelfFungusBlock> CODEC = simpleCodec(ShelfFungusBlock::new);

    private static final int SHAPE_Y_MIN = 6;
    private static final int SHAPE_Y_MAX = 16 - SHAPE_Y_MIN;
    private static final int SHAPE_DEPTH = 5;
    private static final int SHAPE_DIAG_DEPTH = 6;
    private static final int SHAPE_H_MIN = 3;
    private static final int SHAPE_H_MAX = 16 - SHAPE_H_MIN;

    private static final Map<DiagonalDirection, VoxelShape> SHAPES = new EnumMap<>(Map.of(
            DiagonalDirection.NORTH, box(
                    SHAPE_H_MIN, SHAPE_Y_MIN, 16 - SHAPE_DEPTH,
                    SHAPE_H_MAX, SHAPE_Y_MAX, 16
            ),
            DiagonalDirection.SOUTH, box(
                    SHAPE_H_MIN, SHAPE_Y_MIN, 0,
                    SHAPE_H_MAX, SHAPE_Y_MAX, SHAPE_DEPTH
            ),
            DiagonalDirection.WEST, box(
                    16 - SHAPE_DEPTH, SHAPE_Y_MIN, SHAPE_H_MIN,
                    16, SHAPE_Y_MAX, SHAPE_H_MAX
            ),
            DiagonalDirection.EAST, box(
                    0, SHAPE_Y_MIN, SHAPE_H_MIN,
                    SHAPE_DEPTH, SHAPE_Y_MAX, SHAPE_H_MAX
            ),
            DiagonalDirection.NORTH_WEST, box(
                    16 - SHAPE_DIAG_DEPTH, SHAPE_Y_MIN, 16 - SHAPE_DIAG_DEPTH,
                    16, SHAPE_Y_MAX, 16
            ),
            DiagonalDirection.NORTH_EAST, box(
                    0, SHAPE_Y_MIN, 16 - SHAPE_DIAG_DEPTH,
                    SHAPE_DIAG_DEPTH, SHAPE_Y_MAX, 16
            ),
            DiagonalDirection.SOUTH_EAST, box(
                    0, SHAPE_Y_MIN, 0,
                    SHAPE_DIAG_DEPTH, SHAPE_Y_MAX, SHAPE_DIAG_DEPTH
            ),
            DiagonalDirection.SOUTH_WEST, box(
                    16 - SHAPE_DIAG_DEPTH, SHAPE_Y_MIN, 0,
                    16, SHAPE_Y_MAX, SHAPE_DIAG_DEPTH
            )
    ));

    @Override
    protected MapCodec<ShelfFungusBlock> codec() {
        return CODEC;
    }

    public ShelfFungusBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected float getMaxHorizontalOffset() {
        return 0f;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return SHAPES.get(state.getValue(FACING));
    }
}
