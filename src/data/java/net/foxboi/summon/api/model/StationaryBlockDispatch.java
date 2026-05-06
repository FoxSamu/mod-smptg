package net.foxboi.summon.api.model;

import net.minecraft.world.level.block.Block;

public record StationaryBlockDispatch(Block block, WeightedVariants variants) implements BlockDispatch {
    @Override
    public void save(BlockDispatchSink sink) {
        sink.save(block, modelSink -> ModelJson.createStaticBlockDispatch(variants.toJson(modelSink)));
    }
}
