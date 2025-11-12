package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingShortPlantBlock extends VegetationBlock implements PlantBlock {
    public static final MapCodec<HangingShortPlantBlock> CODEC = PlantConfig.plantBlockCodec(HangingShortPlantBlock::new);

    private final PlantConfig plantConfig;
    private final VoxelShape shape;

    @Override
    public MapCodec<? extends HangingShortPlantBlock> codec() {
        return CODEC;
    }

    public HangingShortPlantBlock(PlantConfig plantConfig, Properties properties) {
        super(properties);

        this.plantConfig = plantConfig;
        this.shape = plantConfig.downShape();
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
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        var abovePos = pos.above();
        return mayPlaceOn(level.getBlockState(abovePos), level, abovePos);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        if (plantConfig.hasShapeOffset()) {
            return shape.move(state.getOffset(pos).multiply(1, 0, 1));
        }

        return shape;
    }
}
