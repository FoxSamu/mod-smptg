package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class SegmentedFlowersBlock extends SegmentedPlantBlock {
    public static final MapCodec<SegmentedFlowersBlock> CODEC = PlantConfig.plantBlockCodec(SegmentedFlowersBlock::new);

    public static final IntegerProperty AMOUNT = BlockStateProperties.FLOWER_AMOUNT;

    @Override
    protected MapCodec<? extends SegmentedFlowersBlock> codec() {
        return CODEC;
    }

    public SegmentedFlowersBlock(PlantConfig plantConfig, Properties properties) {
        super(plantConfig, properties);
    }

    @Override
    public IntegerProperty getSegmentAmountProperty() {
        return AMOUNT;
    }
}
