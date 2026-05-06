package net.foxboi.salted.data.core.model;

import java.util.function.Consumer;

import net.minecraft.core.Direction;

public interface Transformable<T extends Transformable<T>> {
    T transform(Consumer<VariantTransform> transform);

    default T transform(VariantTransform transform) {
        return transform(xf -> xf.transform(transform));
    }

    default T x(int x) {
        return transform(xf -> xf.x(x));
    }

    default T y(int y) {
        return transform(xf -> xf.y(y));
    }

    default T z(int z) {
        return transform(xf -> xf.z(z));
    }

    default T rot(Direction.Axis axis, int angle) {
        return switch (axis) {
            case X -> x(angle);
            case Y -> y(angle);
            case Z -> z(angle);
        };
    }

    default T rot(Direction axis, int angle) {
        return switch (axis) {
            case WEST -> x(-angle);
            case EAST -> x(angle);
            case DOWN -> y(-angle);
            case UP -> y(angle);
            case NORTH -> z(-angle);
            case SOUTH -> z(angle);
        };
    }

    default T rot(int x, int y, int z) {
        return x(x).y(y).z(z);
    }

    default T rot(int x, int y) {
        return x(x).y(y);
    }
}
