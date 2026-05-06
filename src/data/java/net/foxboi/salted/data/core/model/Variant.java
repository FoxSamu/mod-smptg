package net.foxboi.salted.data.core.model;

import java.util.function.Consumer;

import com.google.gson.JsonObject;

public interface Variant extends Transformable<Variant> {
    JsonObject toJson(ModelSink modelSink);

    Variant copy();

    @Override
    default Variant transform(Consumer<VariantTransform> transform) {
        return this;
    }

    default Variant uvlock(boolean uvlock) {
        return this;
    }

    static WeightedVariants weighted() {
        return WeightedVariants.of();
    }
}
