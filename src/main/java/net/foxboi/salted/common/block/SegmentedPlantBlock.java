package net.foxboi.salted.common.block;

import java.util.function.Function;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SegmentedPlantBlock extends VegetationBlock implements PlantBlock, SegmentableBlock {
    public static final MapCodec<SegmentedPlantBlock> CODEC = PlantConfig.plantBlockCodec(SegmentedPlantBlock::new);

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final IntegerProperty AMOUNT = BlockStateProperties.SEGMENT_AMOUNT;

    private final PlantConfig plantConfig;
    private final Function<BlockState, VoxelShape> shapes;

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    public SegmentedPlantBlock(PlantConfig plantConfig, Properties properties) {
        super(properties);
        this.plantConfig = plantConfig;
        this.shapes = getShapeForEachState(getShapeCalculator(FACING, getSegmentAmountProperty()));

        registerDefaultState(
                stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(getSegmentAmountProperty(), 1)
        );
    }

    @Override
    public PlantConfig getPlantConfig() {
        return PlantConfig.of(plantConfig);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, getSegmentAmountProperty());
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return getStateForPlacement(blockPlaceContext, this, getSegmentAmountProperty(), FACING);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext ctx) {
        return canBeReplaced(state, ctx, getSegmentAmountProperty()) || super.canBeReplaced(state, ctx);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext collisctxonContext) {
        var shape = shapes.apply(state);

        if (plantConfig.hasShapeOffset() && state.hasOffsetFunction()) {
            shape = shape.move(state.getOffset(pos).multiply(1, 0, 1));
        }

        return shape;
    }

    @Override
    public double getShapeHeight() {
        return plantConfig.height();
    }

    @Override
    public IntegerProperty getSegmentAmountProperty() {
        return AMOUNT;
    }

    @Override
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate(blockState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation(blockState.getValue(FACING)));
    }
}
