package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public abstract class AbstractColumnPlantBlock extends Block {
    public static final BooleanProperty BASE = BooleanProperty.create("base");
    public static final BooleanProperty END = BooleanProperty.create("end");

    protected final Direction growDir;

    public AbstractColumnPlantBlock(Direction growDir, Properties properties) {
        super(properties);

        registerDefaultState(
                stateDefinition.any()
                        .setValue(BASE, false)
                        .setValue(END, false)
        );

        this.growDir = growDir;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BASE, END);
    }

    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.isFaceSturdy(level, pos, Direction.DOWN);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        var supportPos = pos.relative(growDir, -1);
        var supportState = level.getBlockState(supportPos);

        return supportState.is(this) || mayPlaceOn(supportState, level, supportPos);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rng) {
        if (!canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction dir, BlockPos nearPos, BlockState nearState, RandomSource rng) {
        if (!canSurvive(state, level, pos)) {
            ticks.scheduleTick(pos, this, 2);
        }

        if (dir == growDir.getOpposite()) {
            state = state.setValue(BASE, !nearState.is(this));
        }

        if (dir == growDir) {
            state = state.setValue(END, !nearState.is(this));
        }

        return state;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        var level = ctx.getLevel();
        var pos = ctx.getClickedPos();
        var state = defaultBlockState();

        if (!canSurvive(state, level, pos)) {
            return null;
        }

        state = state.setValue(BASE, !level.getBlockState(pos.relative(growDir, -1)).is(this));
        state = state.setValue(END, !level.getBlockState(pos.relative(growDir)).is(this));

        return state;
    }

    @Override
    protected abstract MapCodec<? extends AbstractColumnPlantBlock> codec();

    public final BlockPos findBase(BlockGetter level, BlockPos pos) {
        var mpos = pos.mutable();

        while (level.getBlockState(mpos).is(this)) {
            mpos.move(growDir, -1);
        }

        return mpos.move(growDir).immutable();
    }

    public final BlockPos findEnd(BlockGetter level, BlockPos pos) {
        var mpos = pos.mutable();

        while (level.getBlockState(mpos).is(this)) {
            mpos.move(growDir);
        }

        return mpos.move(growDir, -1).immutable();
    }

    public final boolean canGrowAtLeast(LevelReader level, BlockPos pos, int amount) {
        var end = findEnd(level, pos);
        var mpos = end.mutable().move(growDir);

        for (int i = 0; i < amount; i++) {
            if (!canGrowInto(level, mpos)) {
                return false;
            }

            mpos.move(growDir);
        }

        return true;
    }

    private void fixEnds(ServerLevel level, BlockPos originalEnd, BlockPos newEnd) {
        if (originalEnd.equals(newEnd)) {
            return;
        }

        level.setBlockAndUpdate(originalEnd, level.getBlockState(originalEnd).setValue(END, false));
        level.setBlockAndUpdate(newEnd, level.getBlockState(originalEnd).setValue(END, true));
    }

    public final int grow(ServerLevel level, BlockPos pos, int amount) {
        var end = findEnd(level, pos);
        var mpos = end.mutable().move(growDir);

        for (int i = 0; i < amount; i++) {
            if (!canGrowInto(level, mpos)) {
                fixEnds(level, end, mpos.move(growDir, -1));
                return i;
            }

            mpos.move(growDir);
        }

        fixEnds(level, end, mpos.move(growDir, -1));
        return amount;
    }


    @SuppressWarnings("unused")
    protected boolean canGrowInto(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getFluidState().isEmpty() && state.canBeReplaced();
    }

    private boolean canGrowInto(LevelReader level, BlockPos pos) {
        if (!level.isInsideBuildHeight(pos.getY())) {
            return false;
        }

        if (!canGrowInto(level.getBlockState(pos), level, pos)) {
            return false;
        }

        return getGrowState(level, pos).canSurvive(level, pos);
    }

    @SuppressWarnings("unused")
    protected BlockState getGrowState(BlockState replaceState, LevelReader level, BlockPos pos) {
        return defaultBlockState();
    }

    private BlockState getGrowState(LevelReader level, BlockPos pos) {
        return getGrowState(level.getBlockState(pos), level, pos);
    }
}
