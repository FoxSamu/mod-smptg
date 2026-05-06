package net.foxboi.summon.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraft.world.level.block.Block;

import com.google.gson.JsonArray;

public final class MultipartBlockDispatch implements BlockDispatch {
    private final Block block;
    private final List<Part> parts = new ArrayList<>();

    public MultipartBlockDispatch(Block block) {
        this.block = block;
    }

    public MultipartBlockDispatch part(Part part) {
        parts.add(part.copy());
        return this;
    }

    public MultipartBlockDispatch part(PartSelector selector, WeightedVariants variants) {
        return part(new Part(selector, variants));
    }

    public MultipartBlockDispatch part(WeightedVariants variants) {
        return part(new Part(variants));
    }

    public MultipartBlockDispatch part(PartSelector selector, Variant variant) {
        return part(selector, WeightedVariants.of(variant));
    }

    public MultipartBlockDispatch part(Variant variant) {
        return part(WeightedVariants.of(variant));
    }

    public MultipartBlockDispatch part(PartSelector selector, Model variant) {
        return part(selector, WeightedVariants.of(variant));
    }

    public MultipartBlockDispatch part(Model variant) {
        return part(WeightedVariants.of(variant));
    }

    public MultipartBlockDispatch part(PartSelector selector, Model variant, VariantTransform transform) {
        return part(selector, WeightedVariants.of(variant, transform));
    }

    public MultipartBlockDispatch part(Model variant, VariantTransform transform) {
        return part(WeightedVariants.of(variant, transform));
    }

    public MultipartBlockDispatch withDispatch(StateDispatch<WeightedVariants> dispatch) {
        dispatch.forEach(this::part);
        return this;
    }

    public MultipartBlockDispatch map(Function<WeightedVariants, WeightedVariants> map) {
        var iter = parts.listIterator();
        while (iter.hasNext()) {
            var next = iter.next();
            iter.set(new Part(next.selector(), map.apply(next.variants())));
        }
        return this;
    }

    public MultipartBlockDispatch apply(Consumer<MultipartBlockDispatch> config) {
        config.accept(this);
        return this;
    }

    private JsonArray toJson(ModelSink modelSink) {
        var array = new JsonArray();
        for (var part : parts) {
            array.add(part.toJson(modelSink));
        }
        return array;
    }

    @Override
    public void save(BlockDispatchSink sink) {
        sink.save(block, modelSink -> ModelJson.createMultipartDispatch(toJson(modelSink)));
    }
}
