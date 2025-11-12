package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MultilayerBlock extends Block {
	public static final MapCodec<MultilayerBlock> CODEC = simpleCodec(MultilayerBlock::new);

	public static final int MAX_HEIGHT = 8;
    public static final int HEIGHT_IMPASSABLE = 4;

	public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;

	private static final VoxelShape[] SHAPES = boxes(MAX_HEIGHT, it -> column(16, 0, it * 2));

	public MultilayerBlock(BlockBehaviour.Properties properties) {
		super(properties);

		registerDefaultState(
                stateDefinition.any()
                        .setValue(LAYERS, 1)
        );
	}

	@Override
	protected boolean isPathfindable(BlockState state, PathComputationType type) {
		return type == PathComputationType.LAND && state.getValue(LAYERS) < HEIGHT_IMPASSABLE;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		return SHAPES[state.getValue(LAYERS)];
	}

	@Override
	protected boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	@Override
	protected float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
		return state.getValue(LAYERS) == MAX_HEIGHT ? .2f : 1f;
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		var below = level.getBlockState(pos.below());

        return below.isFaceSturdy(level, pos.below(), Direction.UP);
	}

	@Override
	protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction dir, BlockPos nearPos, BlockState nearState, RandomSource rng) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, level, ticks, pos, dir, nearPos, nearState, rng);
    }

	@Override
	protected boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
		var layers = (int) state.getValue(LAYERS);

		if (!ctx.getItemInHand().is(asItem()) || layers >= MAX_HEIGHT) {
			return layers == 1;
		}

        if (ctx.replacingClickedOnBlock()) {
            return ctx.getClickedFace() == Direction.UP;
        }

        return true;
    }

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		var state = ctx.getLevel().getBlockState(ctx.getClickedPos());

		if (state.is(this)) {
			return state.setValue(LAYERS, Math.min(MAX_HEIGHT, state.getValue(LAYERS) + 1));
		}

        return super.getStateForPlacement(ctx);
    }

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LAYERS);
	}

    @Override
    public MapCodec<MultilayerBlock> codec() {
        return CODEC;
    }
}
