package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShortPlantBlock extends VegetationBlock implements PlantBlock {
    public static final MapCodec<ShortPlantBlock> CODEC = PlantConfig.plantBlockCodec(ShortPlantBlock::new);

    private final PlantConfig plantConfig;
    private final VoxelShape shape;

    @Override
    public MapCodec<? extends ShortPlantBlock> codec() {
        return CODEC;
    }

    public ShortPlantBlock(PlantConfig plantConfig, BlockBehaviour.Properties properties) {
        super(properties);

        this.plantConfig = plantConfig;
        this.shape = plantConfig.upShape();
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
        if (plantConfig.hasShapeOffset() && state.hasOffsetFunction()) {
            return shape.move(state.getOffset(pos).multiply(1, 0, 1));
        }

        return shape;
    }
}
