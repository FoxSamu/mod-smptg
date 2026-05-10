package net.foxboi.salted.common.block;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.MapCodec;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jspecify.annotations.Nullable;

@SuppressWarnings("deprecation")
public class DripstoneSpeleothemBlock extends Block implements SimpleWaterloggedBlock, Fallable {
    public static final MapCodec<DripstoneSpeleothemBlock> CODEC = simpleCodec(DripstoneSpeleothemBlock::new);

    public static final EnumProperty<Direction> TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // TODO properly refactor this into an understandable and modular group of blocks we can reuse

    private static final int MAX_SEARCH_LENGTH_WHEN_CHECKING_DRIP_TYPE = 11;
    private static final int MAX_SEARCH_LENGTH_BETWEEN_STALACTITE_TIP_AND_CAULDRON = 11;

    private static final int DELAY_BEFORE_FALLING = 2;
    private static final int DELAY_BEFORE_BREAKING = 1;

    private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK = .02f;
    private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK_IF_UNDER_LIQUID_SOURCE = .12f;

    private static final float WATER_TRANSFER_PROBABILITY_PER_RANDOM_TICK = 90 / 512f;
    private static final float LAVA_TRANSFER_PROBABILITY_PER_RANDOM_TICK = 30 / 512f;

    private static final double MIN_TRIDENT_VELOCITY_TO_BREAK_DRIPSTONE = 0.6;

    private static final int STALACTITE_MAX_DAMAGE = 40;
    private static final int MAX_STALACTITE_HEIGHT_FOR_DAMAGE_CALCULATION = 6;
    private static final float STALACTITE_DAMAGE_PER_FALL_DISTANCE_AND_SIZE = 1f;
    private static final float STALAGMITE_FALL_DISTANCE_OFFSET = 2.5f;
    private static final int STALAGMITE_FALL_DAMAGE_MODIFIER = 2;

    private static final float AVERAGE_DAYS_PER_GROWTH = 5;

    // Let the average days per growth be N
    // - There are 24000 ticks in a minecraft day (20 minutes)
    // - Each chunk section receives 3 random ticks per game tick, distributed randomly over 4096 blocks
    // - Each block thus receives 3 / 4096 random ticks per game tick
    // - Over a period of N days, that is (3 / 4096) * 24000 N random ticks (= roughly 17 ticks)
    // - Hence each time we receive a random tick, we should have a 1 / ((3 / 4096) * 24000 N) chance to grow
    private static final float GROWTH_PROBABILITY_PER_RANDOM_TICK = 1 / ((3 / 4096f) * 24000 * AVERAGE_DAYS_PER_GROWTH);

    private static final int MAX_GROWTH_LENGTH = 7;
    private static final int MAX_STALAGMITE_SEARCH_RANGE_WHEN_GROWING = 10;

    private static final VoxelShape SHAPE_TIP_MERGE = Block.column(6, 0, 16);
    private static final VoxelShape SHAPE_TIP_UP = Block.column(6, 0, 13);
    private static final VoxelShape SHAPE_TIP_DOWN = Block.column(6, 3, 16);
    private static final VoxelShape SHAPE_FRUSTUM = Block.column(8, 0, 16);
    private static final VoxelShape SHAPE_MIDDLE = Block.column(10, 0, 16);
    private static final VoxelShape SHAPE_BASE = Block.column(12, 0, 16);

    private static final double STALACTITE_DRIP_START_PIXEL = SHAPE_TIP_DOWN.min(Direction.Axis.Y);
    private static final double MAX_HORIZONTAL_OFFSET = SHAPE_BASE.min(Direction.Axis.X);

    private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.column(4, 0, 16);

    @Override
    public MapCodec<DripstoneSpeleothemBlock> codec() {
        return CODEC;
    }

    public DripstoneSpeleothemBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(
                stateDefinition.any()
                        .setValue(TIP_DIRECTION, Direction.UP)
                        .setValue(THICKNESS, DripstoneThickness.TIP)
                        .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canStalactiteExist(level, pos, state.getValue(TIP_DIRECTION));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction dir, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        // Schedule fluid tick
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (dir != Direction.UP && dir != Direction.DOWN) {
            return state;
        }

        var tipDirection = state.getValue(TIP_DIRECTION);
        if (tipDirection == Direction.DOWN && ticks.getBlockTicks().hasScheduledTick(pos, this)) {
            return state; // Tick for falling already scheduled
        }

        if (dir == tipDirection.getOpposite() && !canSurvive(state, level, pos)) {
            if (tipDirection == Direction.DOWN) {
                ticks.scheduleTick(pos, this, DELAY_BEFORE_FALLING); // Fall
            } else {
                ticks.scheduleTick(pos, this, DELAY_BEFORE_BREAKING); // Break
            }

            return state;
        }

        // Otherwise compute thickness
        var mergeOpposingTips = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
        var newThickness = calcThickness(level, pos, tipDirection, mergeOpposingTips);
        return state.setValue(THICKNESS, newThickness);
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult blockHit, Projectile projectile) {
        // Break from trident
        if (!(level instanceof ServerLevel serverLevel)) {
            return; // Client side!
        }

        var blockPos = blockHit.getBlockPos();
        if (!projectile.mayInteract(serverLevel, blockPos) || !projectile.mayBreak(serverLevel)) {
            return; // Projectile cannot interact or break blocks
        }

        if (!(projectile instanceof ThrownTrident)) {
            return; // Projectile is not a trident
        }

        if (projectile.getDeltaMovement().length() <= MIN_TRIDENT_VELOCITY_TO_BREAK_DRIPSTONE) {
            return; // Projectile is too slow
        }

        level.destroyBlock(blockPos, true);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        if (state.getValue(TIP_DIRECTION) == Direction.UP && state.getValue(THICKNESS) == DripstoneThickness.TIP) {
            entity.causeFallDamage(
                    fallDistance + STALAGMITE_FALL_DISTANCE_OFFSET,
                    STALAGMITE_FALL_DAMAGE_MODIFIER,
                    level.damageSources().stalagmite()
            );
            return;
        }

        super.fallOn(level, state, pos, entity, fallDistance);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (!canDrip(state)) {
            return;
        }

        var randomValue = random.nextFloat();
        if (randomValue > DRIP_PROBABILITY_PER_ANIMATE_TICK_IF_UNDER_LIQUID_SOURCE) {
            return;
        }

        getFluidAboveStalactite(level, pos, state)
                .filter(fluidAbove -> randomValue < DRIP_PROBABILITY_PER_ANIMATE_TICK || canFillCauldron(fluidAbove.fluid))
                .ifPresent(fluidAbove -> spawnDripParticle(level, pos, state, fluidAbove.fluid, fluidAbove.pos));
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (canSurvive(state, level, pos)) {
            return;
        }

        if (isStalagmite(state)) {
            // Stalagmite break
            level.destroyBlock(pos, true);
        } else {
            // Stalactites fall
            spawnFallingStalactite(state, level, pos);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        maybeTransferFluid(state, level, pos, random.nextFloat());

        if (random.nextFloat() < GROWTH_PROBABILITY_PER_RANDOM_TICK && isStalactiteStartPos(state, level, pos)) {
            growStalactiteOrStalagmiteIfPossible(state, level, pos, random);
        }
    }

    public void maybeTransferFluid(BlockState state, ServerLevel level, BlockPos pos, float randomValue) {
        if (randomValue > WATER_TRANSFER_PROBABILITY_PER_RANDOM_TICK) {
            return;
        }

        if (!isStalactiteStartPos(state, level, pos)) {
            return;
        }

        var fluidInfo = getFluidAboveStalactite(level, pos, state);
        if (fluidInfo.isEmpty()) {
            return;
        }

        var fluid = fluidInfo.get().fluid;
        var transferProbability = 0f;

        if (fluid == Fluids.WATER) {
            transferProbability = WATER_TRANSFER_PROBABILITY_PER_RANDOM_TICK;
        } else if (fluid == Fluids.LAVA) {
            transferProbability = LAVA_TRANSFER_PROBABILITY_PER_RANDOM_TICK;
        } else {
            return;
        }

        if (randomValue >= transferProbability) {
            return;
        }

        var stalactiteTipPos = findTip(state, level, pos, MAX_SEARCH_LENGTH_WHEN_CHECKING_DRIP_TYPE, false);
        if (stalactiteTipPos == null) {
            return;
        }

        if (fluidInfo.get().sourceState.is(Blocks.MUD) && fluid == Fluids.WATER) {
            var newState = Blocks.CLAY.defaultBlockState();

            level.setBlockAndUpdate(fluidInfo.get().pos, newState);

            Block.pushEntitiesUp(fluidInfo.get().sourceState, newState, level, fluidInfo.get().pos);

            level.gameEvent(GameEvent.BLOCK_CHANGE, fluidInfo.get().pos, GameEvent.Context.of(newState));
            level.levelEvent(LevelEvent.DRIPSTONE_DRIP, stalactiteTipPos, 0);
        } else {
            var cauldronPos = findFillableCauldronBelowStalactiteTip(level, stalactiteTipPos, fluid);
            if (cauldronPos != null) {
                level.levelEvent(LevelEvent.DRIPSTONE_DRIP, stalactiteTipPos, 0);
                var fallDistance = stalactiteTipPos.getY() - cauldronPos.getY();
                var delay = 50 + fallDistance;
                var cauldronState = level.getBlockState(cauldronPos);
                level.scheduleTick(cauldronPos, cauldronState.getBlock(), delay);
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var defaultTipDirection = context.getNearestLookingVerticalDirection().getOpposite();

        var tipDirection = calculateTipDirection(level, pos, defaultTipDirection);
        if (tipDirection == null) {
            return null;
        }

        var mergeOpposingTips = !context.isSecondaryUseActive();
        var thickness = calcThickness(level, pos, tipDirection, mergeOpposingTips);
        return defaultBlockState()
                .setValue(TIP_DIRECTION, tipDirection)
                .setValue(THICKNESS, thickness)
                .setValue(WATERLOGGED, level.getFluidState(pos).is(Fluids.WATER));
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED)
                ? Fluids.WATER.getSource(false)
                : super.getFluidState(state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = switch (state.getValue(THICKNESS)) {
            case TIP_MERGE -> SHAPE_TIP_MERGE;
            case TIP -> state.getValue(TIP_DIRECTION) == Direction.DOWN ? SHAPE_TIP_DOWN : SHAPE_TIP_UP;
            case FRUSTUM -> SHAPE_FRUSTUM;
            case MIDDLE -> SHAPE_MIDDLE;
            case BASE -> SHAPE_BASE;
        };
        return shape.move(state.getOffset(pos));
    }

    @Override
    protected boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    protected float getMaxHorizontalOffset() {
        return (float) MAX_HORIZONTAL_OFFSET;
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity entity) {
        if (!entity.isSilent()) {
            level.levelEvent(LevelEvent.SOUND_POINTED_DRIPSTONE_LAND, pos, 0);
        }
    }

    @Override
    public DamageSource getFallDamageSource(Entity entity) {
        return entity.damageSources().fallingStalactite(entity);
    }

    private void spawnFallingStalactite(BlockState state, ServerLevel level, BlockPos pos) {
        var mpos = pos.mutable();
        var fallState = state;

        while (isStalactite(fallState)) {
            var fallingBlock = FallingBlockEntity.fall(level, mpos, fallState);

            if (isTip(fallState, true)) {
                var size = Math.max(1 + pos.getY() - mpos.getY(), MAX_STALACTITE_HEIGHT_FOR_DAMAGE_CALCULATION);
                var damagePerFallDistance = STALACTITE_DAMAGE_PER_FALL_DISTANCE_AND_SIZE * size;

                fallingBlock.setHurtsEntities(damagePerFallDistance, STALACTITE_MAX_DAMAGE);
                break;
            }

            mpos.move(Direction.DOWN);
            fallState = level.getBlockState(mpos);
        }
    }

    @VisibleForTesting
    public void growStalactiteOrStalagmiteIfPossible(BlockState stalactiteStartState, ServerLevel level, BlockPos stalactiteStartPos, RandomSource random) {
        var rootState = level.getBlockState(stalactiteStartPos.above(1));
        var stateAbove = level.getBlockState(stalactiteStartPos.above(2));

        if (!canGrow(rootState, stateAbove)) {
            return;
        }

        var stalactiteTipPos = findTip(stalactiteStartState, level, stalactiteStartPos, MAX_GROWTH_LENGTH, false);
        if (stalactiteTipPos == null) {
            return;
        }

        var stalactiteTipState = level.getBlockState(stalactiteTipPos);
        if (!canDrip(stalactiteTipState) || !canTipGrow(stalactiteTipState, level, stalactiteTipPos)) {
            return;
        }

        if (random.nextBoolean()) {
            grow(level, stalactiteTipPos, Direction.DOWN);
        } else {
            growStalagmiteBelow(level, stalactiteTipPos);
        }
    }

    private void growStalagmiteBelow(ServerLevel level, BlockPos posAboveStalagmite) {
        var pos = posAboveStalagmite.mutable();

        for (var i = 0; i < MAX_STALAGMITE_SEARCH_RANGE_WHEN_GROWING; i++) {
            pos.move(Direction.DOWN);
            BlockState state = level.getBlockState(pos);
            if (!state.getFluidState().isEmpty()) {
                return;
            }

            if (isUnmergedTipWithDirection(state, Direction.UP) && canTipGrow(state, level, pos)) {
                grow(level, pos, Direction.UP);
                return;
            }

            if (canStalactiteExist(level, pos, Direction.UP) && !level.isWaterAt(pos.below())) {
                grow(level, pos.below(), Direction.UP);
                return;
            }

            if (!canDripThrough(level, pos, state)) {
                return;
            }
        }
    }

    private void grow(ServerLevel level, BlockPos growFromPos, Direction growToDirection) {
        var targetPos = growFromPos.relative(growToDirection);
        var existingStateAtTargetPos = level.getBlockState(targetPos);

        if (isUnmergedTipWithDirection(existingStateAtTargetPos, growToDirection.getOpposite())) {
            createMergedTips(existingStateAtTargetPos, level, targetPos);
            return;
        }

        if (existingStateAtTargetPos.isAir() || existingStateAtTargetPos.is(Blocks.WATER)) {
            createStalactite(level, targetPos, growToDirection, DripstoneThickness.TIP);
        }
    }

    private void createStalactite(LevelAccessor level, BlockPos pos, Direction direction, DripstoneThickness thickness) {
        var state = defaultBlockState()
                .setValue(TIP_DIRECTION, direction)
                .setValue(THICKNESS, thickness)
                .setValue(WATERLOGGED, level.getFluidState(pos).is(Fluids.WATER));

        level.setBlock(pos, state, 3);
    }

    private void createMergedTips(BlockState tipState, LevelAccessor level, BlockPos tipPos) {
        var stalactitePos = (BlockPos) null;
        var stalagmitePos = (BlockPos) null;

        if (tipState.getValue(TIP_DIRECTION) == Direction.UP) {
            stalagmitePos = tipPos;
            stalactitePos = tipPos.above();
        } else {
            stalactitePos = tipPos;
            stalagmitePos = tipPos.below();
        }

        createStalactite(level, stalactitePos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
        createStalactite(level, stalagmitePos, Direction.UP, DripstoneThickness.TIP_MERGE);
    }

    public void spawnDripParticle(Level level, BlockPos stalactiteTipPos, BlockState stalactiteTipState) {
        getFluidAboveStalactite(level, stalactiteTipPos, stalactiteTipState)
                .ifPresent(fluidAbove -> spawnDripParticle(level, stalactiteTipPos, stalactiteTipState, fluidAbove.fluid, fluidAbove.pos));
    }

    private static void spawnDripParticle(Level level, BlockPos stalactiteTipPos, BlockState stalactiteTipState, Fluid fluidAbove, BlockPos posAbove) {
        var offset = stalactiteTipState.getOffset(stalactiteTipPos);

        var pixelSize = 0.0625;
        var x = stalactiteTipPos.getX() + 0.5 + offset.x;
        var y = stalactiteTipPos.getY() + STALACTITE_DRIP_START_PIXEL - pixelSize;
        var z = stalactiteTipPos.getZ() + 0.5 + offset.z;
        var dripParticle = getDripParticle(level, fluidAbove, posAbove);

        level.addParticle(dripParticle, x, y, z, 0, 0, 0);
    }

    @Nullable
    private BlockPos findTip(BlockState dripstoneState, LevelAccessor level, BlockPos dripstonePos, int maxSearchLength, boolean includeMergedTip) {
        if (isTip(dripstoneState, includeMergedTip)) {
            return dripstonePos;
        }

        var searchDirection = dripstoneState.getValue(TIP_DIRECTION);

        var pathPredicate = (BiPredicate<BlockPos, BlockState>) (_, state) -> {
            return state.is(this) && state.getValue(TIP_DIRECTION) == searchDirection;
        };

        return findBlockVertical(level, dripstonePos, searchDirection.getAxisDirection(), pathPredicate, dripstone -> isTip(dripstone, includeMergedTip), maxSearchLength).orElse(null);
    }

    @Nullable
    private Direction calculateTipDirection(LevelReader level, BlockPos pos, Direction defaultTipDirection) {
        if (canStalactiteExist(level, pos, defaultTipDirection)) {
            return defaultTipDirection;
        }

        if (!canStalactiteExist(level, pos, defaultTipDirection.getOpposite())) {
            return null;
        }

        return defaultTipDirection.getOpposite();
    }

    private DripstoneThickness calcThickness(LevelReader level, BlockPos pos, Direction tipDirection, boolean mergeOpposingTips) {
        var baseDirection = tipDirection.getOpposite();
        var inFrontState = level.getBlockState(pos.relative(tipDirection));

        if (isPointedDripstoneWithDirection(inFrontState, baseDirection)) {
            return !mergeOpposingTips && inFrontState.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
        }

        if (!isPointedDripstoneWithDirection(inFrontState, tipDirection)) {
            return DripstoneThickness.TIP;
        }

        var inFrontThickness = inFrontState.getValue(THICKNESS);

        if (inFrontThickness != DripstoneThickness.TIP && inFrontThickness != DripstoneThickness.TIP_MERGE) {
            var behindState = level.getBlockState(pos.relative(baseDirection));
            return !isPointedDripstoneWithDirection(behindState, tipDirection)
                    ? DripstoneThickness.BASE
                    : DripstoneThickness.MIDDLE;
        }

        return DripstoneThickness.FRUSTUM;
    }

    public boolean canDrip(BlockState state) {
        return isStalactite(state) && state.getValue(THICKNESS) == DripstoneThickness.TIP && !state.getValue(WATERLOGGED);
    }

    private boolean canTipGrow(BlockState tipState, ServerLevel level, BlockPos tipPos) {
        var growDirection = tipState.getValue(TIP_DIRECTION);
        var growPos = tipPos.relative(growDirection);
        var stateAtGrowPos = level.getBlockState(growPos);

        if (!stateAtGrowPos.getFluidState().isEmpty()) {
            return false;
        }

        return stateAtGrowPos.isAir() || isUnmergedTipWithDirection(stateAtGrowPos, growDirection.getOpposite());
    }

    private Optional<BlockPos> findRootBlock(Level level, BlockPos pos, BlockState dripStoneState, int maxSearchLength) {
        var tipDirection = dripStoneState.getValue(TIP_DIRECTION);
        var pathPredicate = (BiPredicate<BlockPos, BlockState>) (_, state) -> {
            return state.is(this) && state.getValue(TIP_DIRECTION) == tipDirection;
        };

        return findBlockVertical(level, pos, tipDirection.getOpposite().getAxisDirection(), pathPredicate, state -> !state.is(this), maxSearchLength);
    }

    private boolean canStalactiteExist(LevelReader level, BlockPos pos, Direction tipDirection) {
        var behindPos = pos.relative(tipDirection.getOpposite());
        var behindState = level.getBlockState(behindPos);

        return behindState.isFaceSturdy(level, behindPos, tipDirection) || isPointedDripstoneWithDirection(behindState, tipDirection);
    }

    private boolean isTip(BlockState state, boolean includeMergedTip) {
        if (!state.is(this)) {
            return false;
        }

        var thickness = state.getValue(THICKNESS);
        return thickness == DripstoneThickness.TIP || includeMergedTip && thickness == DripstoneThickness.TIP_MERGE;
    }

    private boolean isUnmergedTipWithDirection(BlockState state, Direction tipDirection) {
        return isTip(state, false) && state.getValue(TIP_DIRECTION) == tipDirection;
    }

    private boolean isStalactite(BlockState state) {
        return isPointedDripstoneWithDirection(state, Direction.DOWN);
    }

    private boolean isStalagmite(BlockState state) {
        return isPointedDripstoneWithDirection(state, Direction.UP);
    }

    private boolean isStalactiteStartPos(BlockState state, LevelReader level, BlockPos pos) {
        return isStalactite(state) && !level.getBlockState(pos.above()).is(this);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    private boolean isPointedDripstoneWithDirection(BlockState blockState, Direction tipDirection) {
        return blockState.is(this) && blockState.getValue(TIP_DIRECTION) == tipDirection;
    }

    @Nullable
    private static BlockPos findFillableCauldronBelowStalactiteTip(Level level, BlockPos stalactiteTipPos, Fluid fluid) {
        var cauldronPredicate = (Predicate<BlockState>) state -> {
            return state.getBlock() instanceof AbstractCauldronBlock cauldron && cauldron.canReceiveStalactiteDrip(fluid);
        };

        var pathPredicate = (BiPredicate<BlockPos, BlockState>) (pos, state) -> canDripThrough(level, pos, state);

        return findBlockVertical(level, stalactiteTipPos, Direction.DOWN.getAxisDirection(), pathPredicate, cauldronPredicate, MAX_SEARCH_LENGTH_BETWEEN_STALACTITE_TIP_AND_CAULDRON).orElse(null);
    }

    @Nullable
    public BlockPos findStalactiteTipAboveCauldron(Level level, BlockPos cauldronPos) {
        var pathPredicate = (BiPredicate<BlockPos, BlockState>) (pos, state) -> canDripThrough(level, pos, state);
        return findBlockVertical(level, cauldronPos, Direction.UP.getAxisDirection(), pathPredicate, this::canDrip, MAX_SEARCH_LENGTH_BETWEEN_STALACTITE_TIP_AND_CAULDRON).orElse(null);
    }

    public Fluid getCauldronFillFluidType(ServerLevel level, BlockPos stalactitePos) {
        return getFluidAboveStalactite(level, stalactitePos, level.getBlockState(stalactitePos))
                .map(fluidSource -> fluidSource.fluid)
                .filter(DripstoneSpeleothemBlock::canFillCauldron)
                .orElse(Fluids.EMPTY);
    }

    private Optional<DripstoneSpeleothemBlock.FluidInfo> getFluidAboveStalactite(Level level, BlockPos stalactitePos, BlockState stalactiteState) {
        if (!isStalactite(stalactiteState)) {
            return Optional.empty();
        }

        return findRootBlock(level, stalactitePos, stalactiteState, MAX_SEARCH_LENGTH_WHEN_CHECKING_DRIP_TYPE)
                .map(rootPos -> {
                    var abovePos = rootPos.above();
                    var aboveState = level.getBlockState(abovePos);

                    var fluid = aboveState.is(Blocks.MUD) && !level.environmentAttributes().getValue(EnvironmentAttributes.WATER_EVAPORATES, abovePos)
                            ? Fluids.WATER
                            : level.getFluidState(abovePos).getType();

                    return new FluidInfo(abovePos, fluid, aboveState);
                });
    }

    private static boolean canFillCauldron(Fluid fluidAbove) {
        return fluidAbove == Fluids.LAVA || fluidAbove == Fluids.WATER;
    }

    private static boolean canGrow(BlockState rootState, BlockState aboveState) {
        var fluidState = aboveState.getFluidState();
        return rootState.is(Blocks.DRIPSTONE_BLOCK) && fluidState.is(Fluids.WATER) && fluidState.isSource();
    }

    private static ParticleOptions getDripParticle(Level level, Fluid fluidAbove, BlockPos posAbove) {
        if (fluidAbove.isSame(Fluids.EMPTY)) {
            return level.environmentAttributes().getValue(EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE, posAbove);
        }

        return fluidAbove.is(FluidTags.LAVA)
                ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA
                : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
    }

    private static Optional<BlockPos> findBlockVertical(LevelAccessor level, BlockPos pos, Direction.AxisDirection axisDirection, BiPredicate<BlockPos, BlockState> pathPredicate, Predicate<BlockState> targetPredicate, int maxSteps) {
        var direction = Direction.get(axisDirection, Direction.Axis.Y);
        var mutablePos = pos.mutable();

        for (var i = 1; i < maxSteps; i++) {
            mutablePos.move(direction);
            var state = level.getBlockState(mutablePos);
            if (targetPredicate.test(state)) {
                return Optional.of(mutablePos.immutable());
            }

            if (level.isOutsideBuildHeight(mutablePos.getY()) || !pathPredicate.test(mutablePos, state)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    private static boolean canDripThrough(BlockGetter level, BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return true;
        }
        if (state.isSolidRender()) {
            return false;
        }
        if (!state.getFluidState().isEmpty()) {
            return false;
        }

        var collisionShape = state.getCollisionShape(level, pos);
        return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, collisionShape, BooleanOp.AND);
    }

    record FluidInfo(BlockPos pos, Fluid fluid, BlockState sourceState) {
    }
}
