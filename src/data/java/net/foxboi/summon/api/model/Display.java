package net.foxboi.summon.api.model;

import java.util.List;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class Display {
    private double
            rotationX,
            rotationY,
            rotationZ;
    private double
            translationX,
            translationY,
            translationZ;
    private double
            scaleX,
            scaleY,
            scaleZ;

    private Display() {
    }

    private Display(Display copy) {
        this.rotationX = copy.rotationX;
        this.rotationY = copy.rotationY;
        this.rotationZ = copy.rotationZ;
        this.translationX = copy.translationX;
        this.translationY = copy.translationY;
        this.translationZ = copy.translationZ;
        this.scaleX = copy.scaleX;
        this.scaleY = copy.scaleY;
        this.scaleZ = copy.scaleZ;
    }

    public Display copy() {
        return new Display(this);
    }

    public double rotationX() {
        return rotationX;
    }

    public double rotationY() {
        return rotationY;
    }

    public double rotationZ() {
        return rotationZ;
    }

    public double translationX() {
        return translationX;
    }

    public double translationY() {
        return translationY;
    }

    public double translationZ() {
        return translationZ;
    }

    public double scaleX() {
        return scaleX;
    }

    public double scaleY() {
        return scaleY;
    }

    public double scaleZ() {
        return scaleZ;
    }

    public Display rotation(double x, double y, double z) {
        rotationX = x;
        rotationY = y;
        rotationZ = z;
        return this;
    }

    public Display translation(double x, double y, double z) {
        translationX = x;
        translationY = y;
        translationZ = z;
        return this;
    }

    public Display scale(double x, double y, double z) {
        scaleX = x;
        scaleY = y;
        scaleZ = z;
        return this;
    }

    public static Display display() {
        return new Display();
    }

    public static DataResult<Display> parse(JsonElement json) {
        return CODEC.parse(JsonOps.INSTANCE, json);
    }

    public static Display parseOrThrow(JsonElement json) {
        return parse(json).getOrThrow();
    }

    public JsonElement toJson() {
        return CODEC.encodeStart(JsonOps.INSTANCE, this).getOrThrow();
    }

    private List<Double> rotationForCodec() {
        return List.of(rotationX, rotationY, rotationZ);
    }

    private List<Double> translationForCodec() {
        return List.of(translationX, translationY, translationZ);
    }

    private List<Double> scaleForCodec() {
        return List.of(scaleX, scaleY, scaleZ);
    }

    private static Display fromCodec(List<Double> rotation, List<Double> translation, List<Double> scale) {
        return new Display()
                .rotation(rotation.get(0), rotation.get(1), rotation.get(2))
                .translation(translation.get(0), translation.get(1), translation.get(2))
                .scale(scale.get(0), scale.get(1), scale.get(2));
    }

    public static final Codec<Display> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.DOUBLE.listOf(3, 3).fieldOf("rotation").forGetter(Display::rotationForCodec),
            Codec.DOUBLE.listOf(3, 3).fieldOf("translation").forGetter(Display::translationForCodec),
            Codec.DOUBLE.listOf(3, 3).fieldOf("scale").forGetter(Display::scaleForCodec)
    ).apply(i, Display::fromCodec));
}
