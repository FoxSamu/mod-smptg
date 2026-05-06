package net.foxboi.salted.data.core.model;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

import net.minecraft.core.Direction;

import com.google.gson.JsonObject;
import com.mojang.math.OctahedralGroup;

public final class VariantTransform implements Transformable<VariantTransform> {
    // There are only 24 possible rotations, OctahedralGroup covers all of these
    // so we can use that to easily compute composite transforms
    private OctahedralGroup group = OctahedralGroup.IDENTITY;

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
        return ROTATIONS.get(group)[0];
    }

    public int yRot() {
        return ROTATIONS.get(group)[1];
    }

    public int zRot() {
        return ROTATIONS.get(group)[2];
    }

    public VariantTransform reset() {
        group = OctahedralGroup.IDENTITY;
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
        group = other.group.compose(group);
        return this;
    }

    @Override
    public VariantTransform x(int x) {
        validateRot(x);
        group = X_ROTS[index(x)].compose(group);
        return this;
    }

    @Override
    public VariantTransform y(int y) {
        validateRot(y);
        group = Y_ROTS[index(y)].compose(group);
        return this;
    }

    @Override
    public VariantTransform z(int z) {
        validateRot(z);
        group = Z_ROTS[index(z)].compose(group);
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



    private static final OctahedralGroup[] X_ROTS = {
            OctahedralGroup.IDENTITY,
            OctahedralGroup.BLOCK_ROT_X_90,
            OctahedralGroup.BLOCK_ROT_X_180,
            OctahedralGroup.BLOCK_ROT_X_270,
    };
    private static final OctahedralGroup[] Y_ROTS = {
            OctahedralGroup.IDENTITY,
            OctahedralGroup.BLOCK_ROT_Y_90,
            OctahedralGroup.BLOCK_ROT_Y_180,
            OctahedralGroup.BLOCK_ROT_Y_270,
    };
    private static final OctahedralGroup[] Z_ROTS = {
            OctahedralGroup.IDENTITY,
            OctahedralGroup.BLOCK_ROT_Z_90,
            OctahedralGroup.BLOCK_ROT_Z_180,
            OctahedralGroup.BLOCK_ROT_Z_270,
    };

    private static final Map<OctahedralGroup, int[]> ROTATIONS = new EnumMap<>(OctahedralGroup.class);

    private static int index(int angle) {
        return (angle / 90) & 3;
    }

    static {
        for (int x = 0; x < 4; x++) {
            var gx = X_ROTS[x];

            for (int y = 0; y < 4; y++) {
                var gy = Y_ROTS[y];

                for (int z = 0; z < 4; z++) {
                    var gz = Z_ROTS[z];

                    var g = gz.compose(gy).compose(gx);
                    var current = ROTATIONS.get(g);

                    // Use the one with the lowest Z so that we have only XY rotations
                    // where possible
                    if (current == null || current[2] > z * 90) {
                        ROTATIONS.put(g, new int[] { x * 90, y * 90, z * 90 });
                    }
                }
            }
        }
    }
}
