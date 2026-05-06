package net.foxboi.salted.data.core.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface ItemTint {
    JsonElement toJson();

    static ItemTint constant(int rgb) {
        return () -> {
            var obj = new JsonObject();
            obj.addProperty("type", "minecraft:constant");
            obj.addProperty("value", rgb);
            return obj;
        };
    }

    private static ItemTint simpleDefaulted(String type, int defaultRgb) {
        return () -> {
            var obj = new JsonObject();
            obj.addProperty("type", type);
            obj.addProperty("default", defaultRgb);
            return obj;
        };
    }

    static ItemTint dye(int defaultRgb) {
        return simpleDefaulted("minecraft:dye", defaultRgb);
    }

    static ItemTint firework(int defaultRgb) {
        return simpleDefaulted("minecraft:firework", defaultRgb);
    }

    static ItemTint mapColor(int defaultRgb) {
        return simpleDefaulted("minecraft:map_color", defaultRgb);
    }

    static ItemTint potion(int defaultRgb) {
        return simpleDefaulted("minecraft:potion", defaultRgb);
    }

    static ItemTint team(int defaultRgb) {
        return simpleDefaulted("minecraft:team", defaultRgb);
    }

    static ItemTint customModelData(int index, int defaultRgb) {
        return () -> {
            var obj = new JsonObject();
            obj.addProperty("type", "minecraft:custom_model_data");
            obj.addProperty("index", index);
            obj.addProperty("default", defaultRgb);
            return obj;
        };
    }

    static ItemTint grass(float temperature, float downfall) {
        return () -> {
            var obj = new JsonObject();
            obj.addProperty("type", "minecraft:grass");
            obj.addProperty("temperature", temperature);
            obj.addProperty("downfall", downfall);
            return obj;
        };
    }

    static ItemTint grass() {
        return grass(.5f, 1f);
    }
}
