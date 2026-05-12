package net.foxboi.summon.api.model;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

import static net.foxboi.summon.api.model.Texture.blockSprite;
import static net.foxboi.summon.api.model.Texture.itemSprite;

public sealed interface TextureReference permits Texture, TextureVariable {
    JsonElement toJson();

    static TextureVariable variable(String name) {
        return new TextureVariable(name);
    }

    static Texture texture(Identifier name, boolean alwaysTranslucent) {
        return Texture.of(name, alwaysTranslucent);
    }

    static Texture texture(Identifier name) {
        return texture(name, false);
    }

    static Texture texture(Block block, String suffix, boolean alwaysTranslucent) {
        return texture(blockSprite(block, suffix), alwaysTranslucent);
    }

    static Texture texture(Block block, String suffix) {
        return texture(blockSprite(block, suffix));
    }

    static Texture texture(Block block, boolean alwaysTranslucent) {
        return texture(blockSprite(block), alwaysTranslucent);
    }

    static Texture texture(Block block) {
        return texture(blockSprite(block));
    }

    static Texture texture(Item item, String suffix, boolean alwaysTranslucent) {
        return texture(itemSprite(item, suffix), alwaysTranslucent);
    }

    static Texture texture(Item item, String suffix) {
        return texture(itemSprite(item, suffix));
    }

    static Texture texture(Item item, boolean alwaysTranslucent) {
        return texture(itemSprite(item), alwaysTranslucent);
    }

    static Texture texture(Item item) {
        return texture(itemSprite(item));
    }

    static DataResult<TextureReference> parse(JsonElement json) {
        return CODEC.parse(JsonOps.INSTANCE, json);
    }

    static TextureReference parseOrThrow(JsonElement json) {
        return parse(json).getOrThrow();
    }

    static TextureReference of(String texture) {
        if (texture.startsWith("#")) {
            if (texture.length() == 1) {
                throw new IllegalArgumentException("Variable reference cannot be empty (i.e. '#')");
            }

            return new TextureVariable(texture.substring(1));
        } else {
            var id = Identifier.tryParse(texture);
            if (id == null) {
                throw new IllegalArgumentException("Invalid identifier: " + texture);
            }

            return Texture.of(id);
        }
    }

    Codec<TextureReference> CODEC = Codec.either(TextureVariable.CODEC, Texture.CODEC).xmap(
            either -> Either.unwrap(either),
            ref -> switch (ref) {
                case Texture texture -> Either.right(texture);
                case TextureVariable variable -> Either.left(variable);
            }
    );
}
