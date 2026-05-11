package net.foxboi.salted.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class BrazierBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<BrazierBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Codec.intRange(0, 1000).fieldOf("fire_damage").forGetter(b -> b.fireDamage),
            propertiesCodec()
    ).apply(i, BrazierBlock::new));

    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty FRAMED = BooleanProperty.create("framed");

    private static final VoxelShape BASE_SHAPE = column(16, 0, 3);
    private static final VoxelShape FRAMED_SHAPE = createFramedShape();

    private final int fireDamage;

    public BrazierBlock(int fireDamage, Properties properties) {
        super(properties);
        this.fireDamage = fireDamage;

        registerDefaultState(
                stateDefinition.any()
                        .setValue(LIT, false)
                        .setValue(WATERLOGGED, false)
                        .setValue(FRAMED, false)
        );
    }

    @Override
    protected MapCodec<? extends BrazierBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, WATERLOGGED, FRAMED);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(FRAMED) ? FRAMED_SHAPE : BASE_SHAPE;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (directionToNeighbour == Direction.UP) {
            var framed = neighbourState.isFaceSturdy(level, neighbourPos, Direction.DOWN);
            state = state.setValue(FRAMED, framed);
        }

        return state;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();

        var abovePos = pos.above();
        var aboveState = level.getBlockState(abovePos);

        var framed = aboveState.isFaceSturdy(level, abovePos, Direction.DOWN);
        var waterlogged = level.isWaterAt(pos);

        return defaultBlockState()
                .setValue(FRAMED, framed)
                .setValue(LIT, false)
                .setValue(WATERLOGGED, waterlogged);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            for (int i = 0; i < 4; i++) {
                particle(level, pos);
            }
        }
    }

    private static void particle(Level level, BlockPos pos) {
        var random = level.getRandom();

        level.addParticle(
                random.nextBoolean() ? ParticleTypes.LARGE_SMOKE : ParticleTypes.SMOKE,
                pos.getX() + 0.5 + random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1),
                pos.getY() + 0.4,
                pos.getZ() + 0.5 + random.nextDouble() / 4 * (random.nextBoolean() ? 1 : -1),
                0.0,
                0.005,
                0.0
        );
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
        // Extinguish when becoming waterlogged

        if (state.getValue(BlockStateProperties.WATERLOGGED) || !fluidState.is(Fluids.WATER)) {
            return false;
        }

        if (state.getValue(LIT)) {
            if (!level.isClientSide()) {
                level.playSound(null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1f, 1f);
            }

            dowse(null, level, pos);
        }

        level.setBlock(pos, state.setValue(WATERLOGGED, true).setValue(LIT, false), 3);
        level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
        return true;
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult blockHit, Projectile projectile) {
        var pos = blockHit.getBlockPos();
        if (!(level instanceof ServerLevel sl)) {
            return;
        }

        if (!projectile.isOnFire() || !projectile.mayInteract(sl, pos)) {
            return;
        }

        if (state.getValue(LIT) || state.getValue(WATERLOGGED)) {
            return;
        }

        level.setBlock(pos, state.setValue(BlockStateProperties.LIT, true), 11);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (state.getValue(LIT) && entity instanceof LivingEntity) {
            entity.hurt(level.damageSources().inFire(), fireDamage);
        }

        super.entityInside(state, level, pos, entity, effectApplier, isPrecise);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.defaultFluidState() : super.getFluidState(state);
    }

    private static VoxelShape createFramedShape() {
        // Create a full block and then carve the empty bits out
        var shape = Shapes.block();
        shape = Shapes.join(shape, column(10, 3, 16), BooleanOp.ONLY_FIRST);
        shape = Shapes.join(shape, column(16, 10, 3, 13), BooleanOp.ONLY_FIRST);
        shape = Shapes.join(shape, column(10, 16, 3, 13), BooleanOp.ONLY_FIRST);
        return shape;
    }

    public static boolean canLight(BlockState state) {
        return state.is(ModBlockTags.BRAZIERS, s -> s.hasProperty(WATERLOGGED) && s.hasProperty(LIT))
                && !state.getValue(WATERLOGGED)
                && !state.getValue(LIT);
    }

    public static void dowse(Entity source, LevelAccessor level, BlockPos pos) {
        if (level.isClientSide() && level instanceof Level anActualLevel) {
            for (var i = 0; i < 20; i++) {
                particle(anActualLevel, pos);
            }
        }

        level.gameEvent(source, GameEvent.BLOCK_CHANGE, pos);
    }
}
