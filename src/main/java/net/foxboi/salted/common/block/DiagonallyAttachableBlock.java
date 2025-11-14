package net.foxboi.salted.common.block;

import java.util.Map;

import com.mojang.serialization.MapCodec;
import net.foxboi.salted.common.misc.DiagonalDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * A block that can be placed not only on horizontal faces of a block, but also on its corners. Base block logic used
 * by shelf fungi.
 */
public class DiagonallyAttachableBlock extends Block {
    public static final MapCodec<DiagonallyAttachableBlock> CODEC = simpleCodec(DiagonallyAttachableBlock::new);

    public static final EnumProperty<DiagonalDirection> FACING = EnumProperty.create("facing", DiagonalDirection.class);

    @Override
    protected MapCodec<? extends DiagonallyAttachableBlock> codec() {
        return CODEC;
    }

    public DiagonallyAttachableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        var facing = state.getValue(FACING);
        var nearPos = facing.offset(pos, -1);
        var nearState = level.getBlockState(nearPos);

        if (facing.isDiagonal()) {
            return isCornerAvailable(level, nearPos, facing);
        } else {
            return nearState.isFaceSturdy(level, nearPos, facing.getBestCardinalDirection());
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos nearPos, BlockState nearState, RandomSource rng) {
        if (!canSurvive(state, level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, level, ticks, pos, direction, nearPos, nearState, rng);
    }

    private static final double CORNER_SUPPORT_SIZE = 3;

    private static final Map<DiagonalDirection, VoxelShape> CORNER_SHAPES = Map.of(
            DiagonalDirection.NORTH_WEST, box(0, 0, 0, CORNER_SUPPORT_SIZE, 16, CORNER_SUPPORT_SIZE),
            DiagonalDirection.NORTH_EAST, box(16 - CORNER_SUPPORT_SIZE, 0, 0, 16, 16, CORNER_SUPPORT_SIZE),
            DiagonalDirection.SOUTH_EAST, box(16 - CORNER_SUPPORT_SIZE, 0, 16 - CORNER_SUPPORT_SIZE, 16, 16, 16),
            DiagonalDirection.SOUTH_WEST, box(0, 0, 16 - CORNER_SUPPORT_SIZE, CORNER_SUPPORT_SIZE, 16, 16)
    );

    public static boolean isCornerAvailable(BlockGetter level, BlockPos pos, DiagonalDirection dir) {
        if (!dir.isDiagonal()) {
            return true;
        }

        var state = level.getBlockState(pos);

        if (!intersectsCorner(state.getBlockSupportShape(level, pos), CORNER_SHAPES.get(dir))) {
            return false;
        }

        var xStep = dir.getStepX();
        var zStep = dir.getStepZ();

        var xPos = pos.offset(xStep, 0, 0);
        var zPos = pos.offset(0, 0, zStep);

        var xDir = dir.getMirrorX();
        var zDir = dir.getMirrorZ();

        var xState = level.getBlockState(xPos);
        var zState = level.getBlockState(zPos);

        if (intersectsCorner(xState.getBlockSupportShape(level, xPos), CORNER_SHAPES.get(xDir))) {
            return false;
        }

        if (intersectsCorner(zState.getBlockSupportShape(level, zPos), CORNER_SHAPES.get(zDir))) {
            return false;
        }

        return true;
    }

    private static boolean intersectsCorner(VoxelShape blockShape, VoxelShape cornerShape) {
        // We want the block shape to fully intersect the corner shape. Thus, if we subtract the block shape
        // from the corner shape, we should get an empty shape.
        return !Shapes.joinIsNotEmpty(cornerShape, blockShape, BooleanOp.ONLY_FIRST);
    }

    private static final DiagonalDirection[] DIAGONAL_UPDATE_ORDER = {
            DiagonalDirection.NORTH_EAST,
            DiagonalDirection.NORTH_WEST,
            DiagonalDirection.SOUTH_EAST,
            DiagonalDirection.SOUTH_WEST,
    };

    public static void updateDiagonallyAttachedBlocks(Level level, BlockPos pos, BlockState state, int flags, int recursionDepth) {
        var mpos = pos.mutable();

        for (var ddir : DIAGONAL_UPDATE_ORDER) {
            mpos.setWithOffset(pos, ddir.getStepX(), 0, ddir.getStepZ());

            var nearState = level.getBlockState(mpos);
            if (nearState.getBlock() instanceof DiagonallyAttachableBlock) {
                level.neighborShapeChanged(ddir.getBestCardinalDirection(), mpos, pos, state, flags, recursionDepth);
            }
        }
    }
}
