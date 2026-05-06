package net.foxboi.salted.data.core.model;

import java.util.function.Function;

import net.minecraft.world.level.block.Block;

public final class VariantsBlockDispatch implements BlockDispatch {
    private final Block block;
    private StateDispatch<WeightedVariants> variants;

    public VariantsBlockDispatch(Block block, StateDispatch<WeightedVariants> variants) {
        this.block = block;
        this.variants = variants;
    }

    public VariantsBlockDispatch map(Function<WeightedVariants, WeightedVariants> map) {
        variants = variants.map(map);
        return this;
    }

    public VariantsBlockDispatch flatMap(Function<WeightedVariants, StateDispatch<WeightedVariants>> map) {
        variants = variants.flatMap(map);
        return this;
    }

    public VariantsBlockDispatch flatMap(StateDispatch<Function<WeightedVariants, WeightedVariants>> map) {
        variants = variants.flatMap(map);
        return this;
    }

    @Override
    public void save(BlockDispatchSink sink) {
        sink.save(block, modelSink -> ModelJson.createVariantsDispatch(variants.toJson(v -> v.toJson(modelSink))));
    }
}
