package net.foxboi.summon.api.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import net.minecraft.core.Direction;

import com.google.gson.JsonElement;
import com.mojang.math.Quadrant;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Face {
    private double
            u1 = 0,
            v1 = 0,
            u2 = 16,
            v2 = 16;

    private String texture = "#missingno";

    private Direction cullface = null;
    private Quadrant rotation = Quadrant.R0;
    private int tintindex = -1;

    private Face() {
    }

    private Face(Face copy) {
        this.u1 = copy.u1;
        this.v1 = copy.v1;
        this.u2 = copy.u2;
        this.v2 = copy.v2;
        this.texture = copy.texture;
        this.cullface = copy.cullface;
        this.rotation = copy.rotation;
        this.tintindex = copy.tintindex;
    }

    public Face copy() {
        return new Face(this);
    }

    public Face uv(double u1, double v1, double u2, double v2) {
        this.u1 = u1;
        this.v1 = v1;
        this.u2 = u2;
        this.v2 = v2;
        return this;
    }

    public double u1() {
        return u1;
    }

    public double v1() {
        return v1;
    }

    public double u2() {
        return u2;
    }

    public double v2() {
        return v2;
    }

    public double[] uv() {
        return new double[] { u1, v1, u2, v2 };
    }

    public Face texture(String texture) {
        this.texture = Objects.requireNonNull(texture, "texture must not be null");
        return this;
    }

    public String texture() {
        return texture;
    }

    public Face cullface(Direction cullface) {
        this.cullface = cullface;
        return this;
    }

    public Direction cullface() {
        return cullface;
    }

    private Quadrant toQuadrant(int rotation) {
        if (rotation % 90 != 0) {
            throw new IllegalArgumentException("Rotation must be in steps of 90 degrees, got " + rotation);
        }

        return switch ((rotation / 90) & 3) {
            case 0 -> Quadrant.R0;
            case 90 -> Quadrant.R90;
            case 180 -> Quadrant.R180;
            case 270 -> Quadrant.R270;
            default -> throw new AssertionError();
        };
    }

    public Face rotation(int rotation) {
        this.rotation = toQuadrant(rotation);
        return this;
    }

    public Face rotation(Quadrant rotation) {
        this.rotation = rotation == null ? Quadrant.R0 : rotation;
        return this;
    }

    public Quadrant rotationQuadrant() {
        return rotation;
    }

    public int rotation() {
        return rotation.shift * 90;
    }

    public Face tintindex(int tintindex) {
        this.tintindex = tintindex;
        return this;
    }

    public int tintindex() {
        return tintindex;
    }

    public static Face face() {
        return new Face();
    }

    public static DataResult<Face> parse(JsonElement json) {
        return CODEC.parse(JsonOps.INSTANCE, json);
    }

    public static Face parseOrThrow(JsonElement json) {
        return parse(json).getOrThrow();
    }

    public JsonElement toJson() {
        return CODEC.encodeStart(JsonOps.INSTANCE, this).getOrThrow();
    }

    private List<Double> uvForCodec() {
        return List.of(u1, v1, u2, v2);
    }

    private Optional<Direction> cullfaceForCodec() {
        return Optional.ofNullable(cullface);
    }

    private static Face fromCodec(List<Double> uv, String texture, Optional<Direction> cullface, Quadrant rotation, int tintindex) {
        return new Face()
                .uv(uv.get(0), uv.get(1), uv.get(2), uv.get(3))
                .texture(texture)
                .cullface(cullface.orElse(null))
                .rotation(rotation)
                .tintindex(tintindex);
    }

    public static final Codec<Face> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.DOUBLE.listOf(4, 4).fieldOf("uv").forGetter(Face::uvForCodec),
            Codec.STRING.fieldOf("texture").forGetter(Face::texture),
            Direction.CODEC.optionalFieldOf("cullface").forGetter(Face::cullfaceForCodec),
            Quadrant.CODEC.fieldOf("rotation").forGetter(Face::rotationQuadrant),
            Codec.INT.optionalFieldOf("tintindex", -1).forGetter(Face::tintindex)
    ).apply(i, Face::fromCodec));
}
