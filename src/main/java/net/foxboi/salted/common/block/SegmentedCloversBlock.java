package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class SegmentedCloversBlock extends SegmentedPlantBlock {
    public static final MapCodec<SegmentedCloversBlock> CODEC = PlantConfig.plantBlockCodec(SegmentedCloversBlock::new);

    public static final IntegerProperty AMOUNT = IntegerProperty.create("clover_amount", 1, 4);

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    public SegmentedCloversBlock(PlantConfig plantConfig, Properties properties) {
        super(plantConfig, properties);
    }

    @Override
    public IntegerProperty getSegmentAmountProperty() {
        return AMOUNT;
    }
}
