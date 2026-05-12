package net.foxboi.summon.api.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

public record TextureVariable(String name) implements TextureReference {
    public TextureVariable {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive("#" + name);
    }

    static DataResult<TextureVariable> parse(JsonElement json) {
        return CODEC.parse(JsonOps.INSTANCE, json);
    }

    static TextureVariable parseOrThrow(JsonElement json) {
        return parse(json).getOrThrow();
    }

    public static final Codec<TextureVariable> CODEC = Codec.STRING.flatXmap(
            name -> name.startsWith("#") && name.length() > 1
                    ? DataResult.success(new TextureVariable(name.substring(1)))
                    : DataResult.error(() -> "Invalid texture variable: " + name),

            variable -> DataResult.success("#" + variable.name)
    );
}
