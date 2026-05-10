package net.foxboi.salted.common.block;

import java.util.function.Function;
import java.util.stream.DoubleStream;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.mojang.serialization.MapCodec;
import net.foxboi.salted.common.misc.Misc;

public abstract class AbstractSpeleothemBlock extends Block implements Fallable, SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape REQUIRED_DRIP_GAP = Block.column(4, 0, 16);

    private static int maxCauldronSearchDistance;

    protected final Function<BlockState, VoxelShape> shapes;
    protected final double maxOffset;
    protected final double dripY;

    protected final boolean canGrow;
    protected final boolean canDrip;

    public AbstractSpeleothemBlock(boolean canGrow, boolean canDrip, Function<BlockState, VoxelShape> shapes, Properties properties) {
        super(properties);

        registerDefaultState(
                stateDefinition.any()
                        .setValue(DIRECTION, Direction.DOWN)
                        .setValue(THICKNESS, DripstoneThickness.TIP)
                        .setValue(WATERLOGGED, false)
        );

        this.canGrow = canGrow;
        this.canDrip = canDrip;
        this.shapes = shapes;
        this.maxOffset = stateDefinition.getPossibleStates()
                .stream()
                .map(shapes)
                .flatMapToDouble(shape -> DoubleStream.of(
                        shape.min(Direction.Axis.X),
                        shape.min(Direction.Axis.Z),
                        1 - shape.max(Direction.Axis.X),
                        1 - shape.max(Direction.Axis.Z)
                ))
                .filter(offset -> offset < 0.25)
                .min()
                .orElse(0.25);

        this.dripY = stateDefinition.getPossibleStates()
                .stream()
                .map(shapes)
                .mapToDouble(shape -> shape.min(Direction.Axis.Y))
                .min()
                .orElseThrow(); // It must be present as there is at least one block state

        maxCauldronSearchDistance = Math.max(maxCauldronSearchDistance, getMaxCauldronDripDistance());
    }

    @Override
    protected abstract MapCodec<? extends AbstractSpeleothemBlock> codec();

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION, THICKNESS, WATERLOGGED);
    }




    // ===========================================================================
    // Basic Block Logic
    // ===========================================================================

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canExistAt(level, pos, state.getValue(DIRECTION));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();

        var dir = computeTipDirection(level, pos, context.getNearestLookingVerticalDirection().getOpposite());
        if (dir == null) {
            return null; // No canPlaceAt found, can't place
        }

        var shouldMerge = !context.isSecondaryUseActive();
        var thickness = computeThickness(level, pos, dir, shouldMerge);

        return defaultBlockState()
                .setValue(DIRECTION, dir)
                .setValue(THICKNESS, thickness)
                .setValue(WATERLOGGED, level.getFluidState(pos).is(Fluids.WATER));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction updateDir, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        // Schedule fluid tick if waterlogged
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        // If the neighbour update was horizontal, we have nothing to update
        if (updateDir.getAxis().isHorizontal()) {
            return state;
        }

        var speleothemDir = state.getValue(DIRECTION);

        // If the neighbour update was behind us and we lost canPlaceAt, break
        if (updateDir == speleothemDir.getOpposite() && !canSurvive(state, level, pos)) {
            return onSupportLost(state, level, ticks, pos, speleothemDir);
        }

        // Otherwise update thickness
        var shouldMerge = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
        var newThickness = computeThickness(level, pos, speleothemDir, shouldMerge);
        return state.setValue(THICKNESS, newThickness);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        if (state.getValue(DIRECTION) == Direction.UP && state.getValue(THICKNESS) == DripstoneThickness.TIP) {
            impaleOnStalagmite(entity, fallDistance);
            return;
        }

        super.fallOn(level, state, pos, entity, fallDistance);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapes.apply(state).move(state.getOffset(pos));
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return Shapes.empty();
    }

    @Override
    protected boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    protected float getMaxHorizontalOffset() {
        return (float) maxOffset;
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.defaultFluidState() : super.getFluidState(state);
    }

    private static final double FALL_DISTANCE_OFFSET = 2.5;
    private static final float FALL_DAMAGE_FACTOR = 2;

    protected void impaleOnStalagmite(Entity entity, double fallDistance) {
        entity.causeFallDamage(fallDistance + FALL_DISTANCE_OFFSET, FALL_DAMAGE_FACTOR, entity.damageSources().stalagmite());
    }

    @Nullable
    private Direction computeTipDirection(LevelReader level, BlockPos pos, Direction direction) {
        // Try given direction, otherwise try opposite

        if (canExistAt(level, pos, direction)) {
            return direction;
        }

        var oppositeDirection = direction.getOpposite();
        if (canExistAt(level, pos, oppositeDirection)) {
            return oppositeDirection;
        }

        return null;
    }

    protected final DripstoneThickness computeThickness(LevelReader level, BlockPos pos, Direction dir, boolean shouldMerge) {
        var reverseDir = dir.getOpposite();

        var aheadPos = pos.relative(dir);
        var aheadState = level.getBlockState(aheadPos);

        if (isSelf(aheadState, reverseDir)) {
            // We bumped into a speleothem in the opposite direction, merge if needed

            // As this method is called to change the shape after a neighbour update, if that neighbour
            // is a merge tip, we should also become a merge tip even though shouldMerge says otherwise.
            // If we do not, that state will become a merge tip merging into nothing and we need to avoid
            // that.
            var aheadThickness = aheadState.getValue(THICKNESS);
            return shouldMerge || aheadThickness == DripstoneThickness.TIP_MERGE
                    ? DripstoneThickness.TIP_MERGE
                    : DripstoneThickness.TIP;
        }

        if (!isSelf(aheadState, dir)) {
            // We are the end of the speleothem, become tip

            return DripstoneThickness.TIP;
        }


        var aheadThickness = aheadState.getValue(THICKNESS);
        if (aheadThickness == DripstoneThickness.TIP || aheadThickness == DripstoneThickness.TIP_MERGE) {
            // The block ahead in the speleothem is a tip, become frustum

            return DripstoneThickness.FRUSTUM;
        }


        // We are not the tip or a frustum, become a middle or base block

        var behindPos = pos.relative(reverseDir);
        var behindState = level.getBlockState(behindPos);
        return !isSelf(behindState, dir)
                ? DripstoneThickness.BASE
                : DripstoneThickness.MIDDLE;
    }

    protected boolean isSupport(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
        // Should check if tick is scheduled
        // if (direction == Direction.DOWN && ticks.getBlockTicks().hasScheduledTick(pos, this)) {
        //     return state; // Tick for falling already scheduled
        // }
        return state.isFaceSturdy(level, pos, face);
    }

    protected final boolean isSelf(BlockState state, Direction direction) {
        return state.is(this) && state.getValue(DIRECTION) == direction;
    }

    protected final boolean isSelf(BlockState state, Direction direction, DripstoneThickness thickness) {
        return isSelf(state, direction) && state.getValue(THICKNESS) == thickness;
    }

    protected final boolean canExistAt(LevelReader level, BlockPos pos, Direction tipDirection) {
        var behindPos = pos.relative(tipDirection.getOpposite());
        var behindState = level.getBlockState(behindPos);

        return isSupport(behindState, level, behindPos, tipDirection) || isSelf(behindState, tipDirection);
    }

    protected static Function<BlockState, VoxelShape> createShapes(double baseWidth, double middleWidth, double frustumWidth, double tipWidth, double tipLength) {
        var merge = Block.column(tipWidth, 0, 16);
        var upTip = Block.column(tipWidth, 0, tipLength);
        var downTip = Block.column(tipWidth, 16 - tipLength, 16);
        var frustum = Block.column(frustumWidth, 0, 16);
        var middle = Block.column(middleWidth, 0, 16);
        var base = Block.column(baseWidth, 0, 16);

        return state -> switch (state.getValue(THICKNESS)) {
            case TIP -> state.getValue(DIRECTION) == Direction.UP
                    ? upTip
                    : downTip;

            case TIP_MERGE -> merge;
            case FRUSTUM -> frustum;
            case MIDDLE -> middle;
            case BASE -> base;
        };
    }





    // ===========================================================================
    // Search Logic
    // ===========================================================================

    protected final boolean isTip(BlockState state, boolean allowMergeTip) {
        if (!state.is(this)) {
            return false;
        }

        var thickness = state.getValue(THICKNESS);
        return thickness == DripstoneThickness.TIP || allowMergeTip && thickness == DripstoneThickness.TIP_MERGE;
    }

    @Nullable
    protected final BlockPos findTip(BlockPos startPos, BlockGetter level, int maxSteps, boolean allowMergeTip) {
        var state = level.getBlockState(startPos);
        if (isTip(state, allowMergeTip)) {
            return startPos;
        }

        var dir = state.getValue(DIRECTION);
        return Misc.searchInDirection(
                startPos, level, dir, maxSteps,
                (_, it) -> isSelf(it, dir),
                it -> isTip(it, allowMergeTip)
        );
    }

    @Nullable
    protected final BlockPos findRoot(BlockPos startPos, BlockGetter level, int maxSteps) {
        var state = level.getBlockState(startPos);
        if (!state.is(this)) {
            return startPos;
        }

        var dir = state.getValue(DIRECTION);
        return Misc.searchInDirection(
                startPos, level, dir.getOpposite(), maxSteps,
                (_, it) -> isSelf(it, dir),
                it -> !it.is(this)
        );
    }

    @Nullable
    private static BlockPos findCauldron(BlockPos startPos, BlockGetter level, int maxSteps, Fluid fluid) {
        return Misc.searchInDirection(
                startPos, level, Direction.DOWN, maxSteps,
                (pos, itState) -> canDripThrough(level, pos, itState),
                state -> state.getBlock() instanceof AbstractCauldronBlock cauldron && cauldron.canReceiveStalactiteDrip(fluid)
        );
    }

    @Nullable
    private static BlockPos findTipFromCauldron(BlockPos startPos, BlockGetter level) {
        return Misc.searchInDirection(
                startPos, level, Direction.UP, maxCauldronSearchDistance,
                (pos, itState) -> canDripThrough(level, pos, itState),
                state -> state.getBlock() instanceof AbstractSpeleothemBlock
                        && state.getValue(DIRECTION) == Direction.DOWN
                        && state.getValue(THICKNESS) == DripstoneThickness.TIP
        );
    }





    // ===========================================================================
    // Growth / Drip Logic
    // ===========================================================================

    public static void tryFillCauldron(BlockState state, ServerLevel level, BlockPos pos) {
        var tipPos = findTipFromCauldron(pos, level);
        if (tipPos == null) {
            return;
        }

        var tipState = level.getBlockState(tipPos);
        if (tipState.getBlock() instanceof AbstractSpeleothemBlock speleothemBlock) {
            speleothemBlock.fillCauldron(level, tipPos, state, pos);
        }
    }

    private void fillCauldron(ServerLevel level, BlockPos tipPos, BlockState cauldronState, BlockPos cauldronPos) {
        var rootPos = findRoot(tipPos, level, getMaxFluidTransferDistance());
        if (rootPos == null) {
            return;
        }

        var drip = getTransferFluid(level.getBlockState(rootPos), level, rootPos);
        if (!(drip instanceof TransferFluid(var fluid, var _, var canFillCauldrons, var _))) {
            return;
        }

        if (!canFillCauldrons) {
            return;
        }

        if (!(cauldronState.getBlock() instanceof AbstractCauldronBlock cauldronBlock)) {
            return;
        }

        cauldronBlock.receiveStalactiteDrip(cauldronState, level, cauldronPos, fluid);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rng) {
        if (canDrip) {
            doRandomDrip(state, level, pos, rng);
        }

        if (canGrow) {
            doRandomGrow(state, level, pos, rng);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rng) {
        if (!canDrip || !canDrip(state)) {
            return;
        }

        if (!isRandomParticleTick(rng)) {
            return;
        }

        var rootPos = findRoot(pos, level, getMaxFluidTransferDistance());
        if (rootPos == null) {
            return;
        }

        var drip = getDripParticle(level.getBlockState(rootPos), level, rootPos, rng);
        if (drip == null) {
            return;
        }

        spawnDripParticle(level, pos, state, drip);
    }

    private void doRandomGrow(BlockState state, ServerLevel level, BlockPos pos, RandomSource rng) {
        if (isRandomGrowTick(rng) && isStalactiteBase(state, level, pos)) {
            tryGrow(level, pos, rng);
        }
    }

    private void doRandomDrip(BlockState state, ServerLevel level, BlockPos pos, RandomSource rng) {
        if (!isRandomDripTick(rng)) {
            return;
        }

        var rootPos = findRoot(pos, level, getMaxFluidTransferDistance());
        if (rootPos == null) {
            return;
        }

        var drip = getTransferFluid(level.getBlockState(rootPos), level, rootPos);
        if (!(drip instanceof TransferFluid(var fluid, var probability, var canFillCauldrons, var afterTransfer))) {
            return;
        }

        if (rng.nextFloat() > probability) {
            return;
        }

        var tipPos = findTip(pos, level, getMaxFluidTransferDistance(), false);
        if (tipPos == null) {
            return;
        }

        if (canFillCauldrons) {
            var cauldronPos = findCauldron(tipPos, level, getMaxCauldronDripDistance(), fluid);
            if (cauldronPos != null) {
                var fallDistance = tipPos.getY() - cauldronPos.getY();
                var delay = 50 + fallDistance;
                var cauldronState = level.getBlockState(cauldronPos);
                level.scheduleTick(cauldronPos, cauldronState.getBlock(), delay);
            }
        }

        level.levelEvent(LevelEvent.DRIPSTONE_DRIP, tipPos, 0);
        if (afterTransfer != null) {
            afterTransfer.run();
        }
    }


    protected int getMaxGrowLength() {
        return 7;
    }

    protected int getMaxStalagmiteGrowDistance() {
        return 10;
    }

    protected int getMaxFluidTransferDistance() {
        return 11;
    }

    protected int getMaxCauldronDripDistance() {
        return 11;
    }

    protected static final float RANDOM_GROW_PROBABILITY = Misc.randomTickOnceEveryNDays(5);

    protected boolean isRandomGrowTick(RandomSource rng) {
        return rng.nextFloat() < RANDOM_GROW_PROBABILITY;
    }

    private static final float RANDOM_DRIP_PROBABILITY = 90 / 512f;

    protected boolean isRandomDripTick(RandomSource rng) {
        return rng.nextFloat() < RANDOM_DRIP_PROBABILITY;
    }

    private static final float RANDOM_PARTICLE_PROBABILITY = .12f;

    protected boolean isRandomParticleTick(RandomSource rng) {
        return rng.nextFloat() < RANDOM_PARTICLE_PROBABILITY;
    }

    protected GrowMode getGrowMode(BlockState state, LevelReader level, BlockPos pos) {
        return GrowMode.NONE;
    }

    protected enum GrowMode {
        NONE,
        GROW_SELF_ONLY,
        MAYBE_GROW_STALAGMITE_BELOW
    }

    protected TransferFluid getTransferFluid(BlockState state, Level level, BlockPos pos) {
        var abovePos = pos.above();
        var aboveState = level.getBlockState(abovePos);

        if (aboveState.is(Blocks.MUD)) {
            if (level.environmentAttributes().getValue(EnvironmentAttributes.WATER_EVAPORATES, abovePos)) {
                return null;
            }

            return new TransferFluid(Fluids.WATER, 1, false, () -> {
                level.setBlock(pos, Blocks.CLAY.defaultBlockState(), UPDATE_ALL);
            });
        }

        var aboveFluid = aboveState.getFluidState();

        if (aboveFluid.isSourceOfType(Fluids.WATER)) {
            return new TransferFluid(Fluids.WATER, 1, true, null);
        }

        if (aboveFluid.isSourceOfType(Fluids.LAVA)) {
            return new TransferFluid(Fluids.LAVA, 1 / 3f, true, null);
        }

        return null;
    }

    protected ParticleOptions getDripParticle(BlockState state, LevelReader level, BlockPos pos, RandomSource rng) {
        var abovePos = pos.above();
        var aboveState = level.getBlockState(abovePos);
        var aboveFluid = aboveState.getFluidState();

        var particle = (ParticleOptions) null;
        var probability = 1/6f;

        if (aboveState.is(Blocks.MUD)) {
            if (!level.environmentAttributes().getValue(EnvironmentAttributes.WATER_EVAPORATES, abovePos)) {
                particle = ParticleTypes.DRIPPING_DRIPSTONE_WATER;
                probability = 1;
            }
        } else if (aboveFluid.isSourceOfType(Fluids.WATER)) {
            particle = ParticleTypes.DRIPPING_DRIPSTONE_WATER;
            probability = 1;
        } else if (aboveFluid.isSourceOfType(Fluids.LAVA)) {
            particle = ParticleTypes.DRIPPING_DRIPSTONE_LAVA;
            probability = 1;
        }

        if (rng.nextFloat() > probability) {
            return null;
        }

        if (particle == null) {
            return level.environmentAttributes().getValue(EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE, abovePos);
        }

        return particle;
    }

    protected record TransferFluid(Fluid fluid, float probability, boolean canFillCauldrons, Runnable afterTransfer) {
    }


    private boolean isStalactiteBase(BlockState state, LevelReader level, BlockPos pos) {
        return isSelf(state, Direction.DOWN) && !level.getBlockState(pos.above()).is(this);
    }

    private boolean canDrip(BlockState state) {
        return isSelf(state, Direction.DOWN)
                && state.getValue(THICKNESS) == DripstoneThickness.TIP
                && !state.getValue(WATERLOGGED);
    }

    private static boolean canDripThrough(BlockGetter level, BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return true; // Can drip through air
        }

        if (state.isSolidRender()) {
            return false; // Cannot drip through solid blocks
        }

        if (!state.getFluidState().isEmpty()) {
            return false; // Cannot drip through fluid
        }

        return !Shapes.joinIsNotEmpty(REQUIRED_DRIP_GAP, state.getCollisionShape(level, pos), BooleanOp.AND);
    }

    private boolean canTipGrow(BlockState state, ServerLevel level, BlockPos pos) {
        var growDir = state.getValue(DIRECTION);
        var growPos = pos.relative(growDir);
        var growInto = level.getBlockState(growPos);

        if (!growInto.getFluidState().isEmpty()) {
            return false; // Cannot grow into fluid
        }

        if (growInto.isAir()) {
            return true; // Nothing here, can grow here
        }

        // If there is a matching tip, we can merge with it
        return isSelf(growInto, growDir.getOpposite(), DripstoneThickness.TIP);
    }

    private void tryGrow(ServerLevel level, BlockPos basePos, RandomSource rng) {
        var supportPos = basePos.above();
        var supportState = level.getBlockState(supportPos);

        // Check if we can grow from the canPlaceAt block
        var mode = getGrowMode(supportState, level, supportPos);
        if (mode == GrowMode.NONE) {
            return; // Cannot grow from source block
        }

        // Find tip position
        var tipPos = findTip(basePos, level, getMaxGrowLength(), false);
        if (tipPos == null) {
            return; // There is no tip or we are too long
        }


        var tipState = level.getBlockState(tipPos);
        if (!canDrip(tipState) || !canTipGrow(tipState, level, tipPos)) {
            return; // No growing possible
        }

        if (rng.nextBoolean()) {
            grow(level, tipPos, Direction.DOWN);
        } else if (mode == GrowMode.MAYBE_GROW_STALAGMITE_BELOW) {
            dripDownAndGrowBelow(level, tipPos);
        }
    }

    private void grow(ServerLevel level, BlockPos tipPos, Direction direction) {
        var replacePos = tipPos.relative(direction);
        var replaceState = level.getBlockState(replacePos);

        if (isSelf(replaceState, direction.getOpposite(), DripstoneThickness.TIP)) {
            // Merge tips

            var up = replaceState.getValue(DIRECTION) == Direction.UP;

            var stalactitePos = up ? tipPos : tipPos.below();
            var stalagmitePos = up ? tipPos.above() : tipPos;

            place(level, stalactitePos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
            place(level, stalagmitePos, Direction.UP, DripstoneThickness.TIP_MERGE);
        } else {
            // Grow

            place(level, replacePos, direction, DripstoneThickness.TIP);
        }
    }

    private void dripDownAndGrowBelow(ServerLevel level, BlockPos pos) {
        var mpos = pos.mutable();

        var maxRange = getMaxStalagmiteGrowDistance();
        for (var i = 0; i < maxRange; i++) {
            mpos.move(Direction.DOWN);

            var state = level.getBlockState(mpos);
            if (!state.getFluidState().isEmpty()) {
                return; // Cannot drip through fluid
            }

            if (isSelf(state, Direction.UP, DripstoneThickness.TIP) && canTipGrow(state, level, mpos)) {
                grow(level, mpos, Direction.UP);
                return;
            }

            if (canExistAt(level, mpos, Direction.UP) && !level.isWaterAt(mpos.below())) {
                grow(level, mpos.below(), Direction.UP);
                return;
            }

            if (!canDripThrough(level, mpos, state)) {
                return;
            }
        }
    }

    protected void place(LevelAccessor level, BlockPos pos, Direction direction, DripstoneThickness thickness) {
        var state = defaultBlockState()
                .setValue(DIRECTION, direction)
                .setValue(THICKNESS, thickness)
                .setValue(WATERLOGGED, level.getFluidState(pos).is(Fluids.WATER));

        level.setBlock(pos, state, 3);
    }

    private void spawnDripParticle(Level level, BlockPos tipPos, BlockState tipState, ParticleOptions particle) {
        var offset = tipState.getOffset(tipPos);

        var pixelSize = 0.0625;
        var x = tipPos.getX() + 0.5 + offset.x;
        var y = tipPos.getY() + dripY - pixelSize;
        var z = tipPos.getZ() + 0.5 + offset.z;

        level.addParticle(particle, x, y, z, 0, 0, 0);
    }





    // ===========================================================================
    // Falling Logic
    // ===========================================================================

    protected static final int FALL_DELAY = 2;
    protected static final int BREAK_DELAY = 1;

    protected BlockState onSupportLost(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction tipDirection) {
        if (ticks.getBlockTicks().hasScheduledTick(pos, this)) {
            return state;
        }

        var delay = state.getValue(DIRECTION) == Direction.DOWN ? FALL_DELAY : BREAK_DELAY;
        ticks.scheduleTick(pos, this, delay);
        return state;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (canSurvive(state, level, pos)) {
            return;
        }

        if (isSelf(state, Direction.UP)) {
            // Stalagmite break
            level.destroyBlock(pos, true);
        } else {
            // Stalactites fall
            fall(state, level, pos);
        }
    }

    protected void fall(BlockState state, ServerLevel level, BlockPos pos) {
        var mpos = pos.mutable();
        var fallState = state;

        while (isSelf(fallState, Direction.DOWN)) {
            var fallingBlock = FallingBlockEntity.fall(level, mpos, fallState);

            if (isTip(fallState, true)) {
                var size = 1 + pos.getY() - mpos.getY();

                fallingBlock.setHurtsEntities(
                        getStalactiteImpalingDamagePerFallenDistance(size),
                        getMaxStalactiteImpalingDamage(size)
                );
                break;
            }

            mpos.move(Direction.DOWN);
            fallState = level.getBlockState(mpos);
        }
    }

    private static final int MAX_STALACTITE_DAMAGE_SIZE = 6;
    private static final float DAMAGE_PER_DISTANCE_AND_SIZE = 1f;

    protected float getStalactiteImpalingDamagePerFallenDistance(int size) {
        return DAMAGE_PER_DISTANCE_AND_SIZE * Math.min(size, MAX_STALACTITE_DAMAGE_SIZE);
    }

    protected int getMaxStalactiteImpalingDamage(int size) {
        return 40;
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


    private static final double MIN_TRIDENT_VELOCITY_TO_BREAK = .6;

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

        if (projectile.getDeltaMovement().length() <= MIN_TRIDENT_VELOCITY_TO_BREAK) {
            return; // Projectile is too slow
        }

        level.destroyBlock(blockPos, true);
    }
}
