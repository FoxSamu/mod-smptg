package net.foxboi.salted.data.core.model;

import java.util.List;

import net.minecraft.resources.Identifier;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public final class ModelJson {
    public static JsonObject createParticleModel(TextureMap textureMap) {
        var object = new JsonObject();
        object.add("textures", textureMap.toJson());
        return object;
    }

    public static JsonObject createInheritedModel(Identifier parent, TextureMap textureMap) {
        var object = new JsonObject();
        object.addProperty("parent", parent.toShortString());
        object.add("textures", textureMap.toJson());
        return object;
    }

    public static JsonObject createGeneratedModel(Identifier parent, List<Texture> textures) {
        var map = TextureMap.map();
        var i = 0;
        for (var texture : textures) {
            map.put("layer" + i, texture);
            i ++;
        }

        return createInheritedModel(parent, map);
    }

    public static JsonObject createVariantsDispatch(JsonObject variants) {
        var object = new JsonObject();
        object.add("variants", variants);
        return object;
    }

    public static JsonObject createMultipartDispatch(JsonArray multipart) {
        var object = new JsonObject();
        object.add("multipart", multipart);
        return object;
    }

    public static JsonObject createStaticBlockDispatch(JsonElement variants) {
        var variantsObj = new JsonObject();
        variantsObj.add("", variants);
        return createVariantsDispatch(variantsObj);
    }

    public static JsonObject createItemDispatch(JsonElement model, boolean swapAnimation, boolean oversizedInGui, float swapSpeedScale) {
        var object = new JsonObject();
        object.add("model", model);
        if (!swapAnimation) {
            object.addProperty("hand_animation_on_swap", false);
        }
        if (oversizedInGui) {
            object.addProperty("oversized_in_gui", true);
        }
        if (swapSpeedScale != 1f) {
            object.addProperty("swap_animation_scale", swapSpeedScale);
        }
        return object;
    }
}
