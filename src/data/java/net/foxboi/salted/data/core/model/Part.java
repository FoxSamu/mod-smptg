package net.foxboi.salted.data.core.model;

import com.google.gson.JsonObject;

public record Part(PartSelector selector, WeightedVariants variants) {
    public Part(WeightedVariants variants) {
        this(PartSelector.ALWAYS_TRUE, variants);
    }

    public Part copy() {
        return new Part(selector, variants.copy());
    }

    public JsonObject toJson(ModelSink modelSink) {
        var object = new JsonObject();
        if (!selector.isAlwaysTrue()) {
            object.add("when", selector.toSelectorJson());
        }

        object.add("apply", variants.toJson(modelSink));
        return object;
    }
}
