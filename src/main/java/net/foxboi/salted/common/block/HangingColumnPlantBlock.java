package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingColumnPlantBlock extends AbstractColumnPlantBlock implements PlantBlock {
    public static final MapCodec<HangingColumnPlantBlock> CODEC = PlantConfig.plantBlockCodec(HangingColumnPlantBlock::new);

    private final PlantConfig plantConfig;
    private final VoxelShape endShape;
    private final VoxelShape columnShape;

    @Override
    protected MapCodec<? extends HangingColumnPlantBlock> codec() {
        return CODEC;
    }

    public HangingColumnPlantBlock(PlantConfig plantConfig, Properties properties) {
        super(Direction.DOWN, properties);

        this.plantConfig = plantConfig;
        this.endShape = plantConfig.downShape();
        this.columnShape = plantConfig.columnShape();
    }

    @Override
    public PlantConfig getPlantConfig() {
        return PlantConfig.of(plantConfig);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return plantConfig.canGrowOn(state, level, pos, Direction.DOWN);
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
