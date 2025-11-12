package net.foxboi.salted.common.block;

import java.util.Optional;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class PartiallyWaterloggableTallPlantBlock extends TallPlantBlock implements BucketPickup, LiquidBlockContainer, PlantBlock {
    public static final MapCodec<PartiallyWaterloggableTallPlantBlock> CODEC = PlantConfig.plantBlockCodec(PartiallyWaterloggableTallPlantBlock::new);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    @Override
    public MapCodec<PartiallyWaterloggableTallPlantBlock> codec() {
        return CODEC;
    }

    public PartiallyWaterloggableTallPlantBlock(PlantConfig config, Properties properties) {
        super(config, properties);

        registerDefaultState(
                stateDefinition.any()
                        .setValue(HALF, DoubleBlockHalf.LOWER)
                        .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction dir, BlockPos neighbour, BlockState neighbourState, RandomSource rng) {
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, level, ticks, pos, dir, neighbour, neighbourState, rng);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return isWaterlogged(state) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        var pos = ctx.getClickedPos();
        var above = pos.above();

        var level = ctx.getLevel();

        if (pos.getY() >= level.getMaxY()) {
            return null;
        }

        if (!level.getBlockState(above).canBeReplaced(ctx)) {
            return null;
        }

        if (level.getFluidState(above).isSourceOfType(Fluids.WATER)) {
            return null;
        }

        var state = defaultBlockState();

        if (level.getFluidState(pos).isSourceOfType(Fluids.WATER)) {
            state = state.setValue(WATERLOGGED, true);
        }

        return state;
    }

    private static boolean canBeWaterlogged(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER;
    }

    private static boolean isWaterlogged(BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER && state.getValue(WATERLOGGED);
    }

    @Override
    public boolean canPlaceLiquid(LivingEntity entity, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
        return fluid == Fluids.WATER && canBeWaterlogged(state);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fstate) {
        if (isWaterlogged(state) || !canBeWaterlogged(state) || fstate.getType() != Fluids.WATER) {
            return false;
        }

        if (!level.isClientSide()) {
            level.setBlock(pos, state.setValue(WATERLOGGED, true), 3);
            level.scheduleTick(pos, fstate.getType(), fstate.getType().getTickDelay(level));
        }

        return true;
    }

    @Override
    public ItemStack pickupBlock(@Nullable LivingEntity entity, LevelAccessor level, BlockPos pos, BlockState state) {
        if (!isWaterlogged(state)) {
            return ItemStack.EMPTY;
        }

        level.setBlock(pos, state.setValue(WATERLOGGED, false), 3);
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }

        return new ItemStack(Items.WATER_BUCKET);
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Fluids.WATER.getPickupSound();
    }
}
