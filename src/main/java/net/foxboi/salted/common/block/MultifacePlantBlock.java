package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MultifacePlantBlock extends AbstractMultifaceBlock implements PlantBlock, SpreadingMultifaceBlock {
    public static final MapCodec<MultifacePlantBlock> CODEC = PlantConfig.plantBlockCodec(MultifacePlantBlock::new);

    private final MultifaceSpreader spreader = createMultifaceSpreader();
    private final PlantConfig plantConfig;

    @Override
    public MapCodec<MultifacePlantBlock> codec() {
        return CODEC;
    }

    public MultifacePlantBlock(PlantConfig plantConfig, BlockBehaviour.Properties properties) {
        super(plantConfig.width(), plantConfig.height(), properties);
        this.plantConfig = plantConfig;
    }

    public MultifacePlantBlock(BlockBehaviour.Properties properties) {
        this(PlantConfig.defaultMultiface(), properties);
    }

    @Override
    public boolean canAttachTo(BlockGetter level, Direction face, BlockPos supportPos, BlockState supportState) {
        return plantConfig.canGrowOn(supportState, level, supportPos, face.getOpposite());
    }

    @Override
    public PlantConfig getPlantConfig() {
        return PlantConfig.of(plantConfig);
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return spreader;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        var superShape = super.getShape(state, level, pos, context);

        if (state.hasOffsetFunction() && plantConfig.hasShapeOffset()) {
            superShape = superShape.move(state.getOffset(pos));
        }

        return superShape;
    }
}
