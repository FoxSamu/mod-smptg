package net.foxboi.summon.api.model;

import net.minecraft.world.level.block.Block;

public interface BlockDispatch {
    void save(BlockDispatchSink sink);

    static StationaryBlockDispatch of(Block block, WeightedVariants variants) {
        return new StationaryBlockDispatch(block, variants);
    }

    static StationaryBlockDispatch of(Block block, Model model) {
        return new StationaryBlockDispatch(block, WeightedVariants.of(model));
    }

    static StationaryBlockDispatch of(Block block, Model model, VariantTransform transform) {
        return new StationaryBlockDispatch(block, WeightedVariants.of(model, transform));
    }

    static VariantsBlockDispatch variants(Block block, StateDispatch<WeightedVariants> variants) {
        return new VariantsBlockDispatch(block, variants);
    }

    static VariantsBlockDispatch variants(Block block, WeightedVariants variants) {
        return variants(block, StateDispatch.dispatch(variants));
    }

    static VariantsBlockDispatch variants(Block block, Variant variant) {
        return variants(block, WeightedVariants.of(variant));
    }

    static VariantsBlockDispatch variants(Block block, Model model) {
        return variants(block, WeightedVariants.of(model));
    }

    static VariantsBlockDispatch variants(Block block, Model model, VariantTransform transform) {
        return variants(block, WeightedVariants.of(model, transform));
    }

    static MultipartBlockDispatch multipart(Block block) {
        return new MultipartBlockDispatch(block);
    }
}
