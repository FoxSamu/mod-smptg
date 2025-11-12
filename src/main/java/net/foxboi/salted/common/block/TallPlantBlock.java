package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TallPlantBlock extends DoublePlantBlock implements PlantBlock {
    public static final MapCodec<TallPlantBlock> CODEC = PlantConfig.plantBlockCodec(TallPlantBlock::new);

    private final PlantConfig plantConfig;
    private final VoxelShape lowerShape;
    private final VoxelShape upperShape;

    @Override
    public MapCodec<? extends TallPlantBlock> codec() {
        return CODEC;
    }

    public TallPlantBlock(PlantConfig plantConfig, Properties properties) {
        super(properties);
        this.plantConfig = plantConfig;

        this.lowerShape = plantConfig.columnShape();
        this.upperShape = plantConfig.upShape();
    }

    @Override
    public PlantConfig getPlantConfig() {
        return PlantConfig.of(plantConfig);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return plantConfig.canGrowOn(state, level, pos, Direction.UP);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        var shape = state.getValue(HALF) == DoubleBlockHalf.LOWER ? lowerShape : upperShape;
        if (plantConfig.hasShapeOffset()) {
            shape.move(state.getOffset(pos).multiply(1, 0, 1));
        }

        return shape;
    }
}
