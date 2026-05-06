package net.foxboi.salted.data.core.model;

import java.util.function.Consumer;

import net.minecraft.core.Direction;

import com.google.gson.JsonObject;

public final class BaseVariant implements Variant {
    private final Model model;
    private final VariantTransform transform = VariantTransform.of();
    private boolean uvlock;

    public BaseVariant(Model model) {
        this.model = model;
    }

    public static BaseVariant of(Model model) {
        return new BaseVariant(model);
    }

    public static BaseVariant of(Model model, VariantTransform transform) {
        return new BaseVariant(model).transform(transform);
    }

    @Override
    public BaseVariant copy() {
        return new BaseVariant(model)
                .transform(transform)
                .uvlock(uvlock);
    }

    public Model model() {
        return model;
    }

    public VariantTransform transform() {
        return transform;
    }

    public boolean uvlock() {
        return uvlock;
    }

    @Override
    public BaseVariant transform(VariantTransform transform) {
        Variant.super.transform(transform);
        return this;
    }

    @Override
    public BaseVariant x(int x) {
        Variant.super.x(x);
        return this;
    }

    @Override
    public BaseVariant y(int y) {
        Variant.super.y(y);
        return this;
    }

    @Override
    public BaseVariant z(int z) {
        Variant.super.z(z);
        return this;
    }

    @Override
    public BaseVariant rot(Direction.Axis axis, int angle) {
        Variant.super.rot(axis, angle);
        return this;
    }

    @Override
    public BaseVariant rot(Direction axis, int angle) {
        Variant.super.rot(axis, angle);
        return this;
    }

    @Override
    public BaseVariant rot(int x, int y, int z) {
        Variant.super.rot(x, y, z);
        return this;
    }

    @Override
    public BaseVariant rot(int x, int y) {
        Variant.super.rot(x, y);
        return this;
    }

    @Override
    public BaseVariant transform(Consumer<VariantTransform> transform) {
        transform.accept(this.transform);
        return this;
    }

    @Override
    public BaseVariant uvlock(boolean uvlock) {
        this.uvlock = uvlock;
        return this;
    }

    @Override
    public JsonObject toJson(ModelSink modelSink) {
        var model = this.model.save(modelSink);

        var json = new JsonObject();
        json.addProperty("model", model.toShortString());
        transform.addToJson(json);

        if (uvlock) {
            json.addProperty("uvlock", true);
        }

        return json;
    }
}
