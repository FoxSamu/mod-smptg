package net.foxboi.summon.api.model;

import java.util.function.Consumer;

import net.minecraft.core.Direction;

import com.google.gson.JsonObject;

public final class VariantTransform implements Transformable<VariantTransform> {
    // Using group theory to rotate a minecraft
    // block. Truly phenomenal.
    private S4 permutation = S4.IDENTITY;

    private VariantTransform() {
    }

    public static VariantTransform of() {
        return new VariantTransform();
    }

    public static VariantTransform ofX(int x) {
        return of().x(x);
    }

    public static VariantTransform ofY(int y) {
        return of().y(y);
    }

    public static VariantTransform ofZ(int z) {
        return of().z(z);
    }

    public static VariantTransform of(Direction.Axis axis, int angle) {
        return of().rot(axis, angle);
    }

    public static VariantTransform of(Direction axis, int angle) {
        return of().rot(axis, angle);
    }

    public static VariantTransform of(int x, int y) {
        return of().rot(x, y);
    }

    public static VariantTransform of(int x, int y, int z) {
        return of().rot(x, y, z);
    }

    public VariantTransform copy() {
        return new VariantTransform().transform(this);
    }

    public int xRot() {
        return permutation.x();
    }

    public int yRot() {
        return permutation.y();
    }

    public int zRot() {
        return permutation.z();
    }

    public VariantTransform reset() {
        permutation = S4.IDENTITY;
        return this;
    }

    private static void validateRot(int rot) {
        if (rot % 90 != 0) {
            throw new IllegalArgumentException("Rotations must be in increments of 90");
        }
    }

    @Override
    public VariantTransform transform(Consumer<VariantTransform> transform) {
        transform.accept(this);
        return this;
    }

    @Override
    public VariantTransform transform(VariantTransform other) {
        permutation = other.permutation.compose(permutation);
        return this;
    }

    @Override
    public VariantTransform x(int x) {
        validateRot(x);
        permutation = permutation.x(x);
        return this;
    }

    @Override
    public VariantTransform y(int y) {
        validateRot(y);
        permutation = permutation.y(y);
        return this;
    }

    @Override
    public VariantTransform z(int z) {
        validateRot(z);
        permutation = permutation.z(z);
        return this;
    }

    public void addToJson(JsonObject json) {
        var xRot = xRot();
        var yRot = yRot();
        var zRot = zRot();

        if (xRot != 0) {
            json.addProperty("x", xRot);
        }
        if (yRot != 0) {
            json.addProperty("y", yRot);
        }
        if (zRot != 0) {
            json.addProperty("z", zRot);
        }
    }
}
