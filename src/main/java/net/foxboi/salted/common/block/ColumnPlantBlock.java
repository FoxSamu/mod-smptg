package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ColumnPlantBlock extends AbstractColumnPlantBlock implements PlantBlock {
    public static final MapCodec<ColumnPlantBlock> CODEC = PlantConfig.plantBlockCodec(ColumnPlantBlock::new);

    private final PlantConfig plantConfig;
    private final VoxelShape endShape;
    private final VoxelShape columnShape;

    @Override
    protected MapCodec<? extends ColumnPlantBlock> codec() {
        return CODEC;
    }

    public ColumnPlantBlock(PlantConfig plantConfig, Properties properties) {
        super(Direction.UP, properties);

        this.plantConfig = plantConfig;
        this.endShape = plantConfig.upShape();
        this.columnShape = plantConfig.columnShape();
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
        var shape = state.getValue(SHAPE) != ColumnPlantShape.BODY ? endShape : columnShape;

        if (plantConfig.hasShapeOffset()) {
            shape = shape.move(state.getOffset(pos).multiply(1, 0, 1));
        }

        return shape;
    }
}
