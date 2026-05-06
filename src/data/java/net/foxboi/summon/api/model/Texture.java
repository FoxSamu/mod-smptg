package net.foxboi.summon.api.model;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public record Texture(Identifier name, boolean alwaysTranslucent) {
    public static Texture of(Identifier name, boolean alwaysTranslucent) {
        return new Texture(name, alwaysTranslucent);
    }

    public static Texture of(Identifier name) {
        return of(name, false);
    }

    public static Texture of(Block block, String suffix, boolean alwaysTranslucent) {
        return of(blockSprite(block, suffix), alwaysTranslucent);
    }

    public static Texture of(Block block, String suffix) {
        return of(blockSprite(block, suffix));
    }

    public static Texture of(Block block, boolean alwaysTranslucent) {
        return of(blockSprite(block), alwaysTranslucent);
    }

    public static Texture of(Block block) {
        return of(blockSprite(block));
    }

    public static Texture of(Item item, String suffix, boolean alwaysTranslucent) {
        return of(itemSprite(item, suffix), alwaysTranslucent);
    }

    public static Texture of(Item item, String suffix) {
        return of(itemSprite(item, suffix));
    }

    public static Texture of(Item item, boolean alwaysTranslucent) {
        return of(itemSprite(item), alwaysTranslucent);
    }

    public static Texture of(Item item) {
        return of(itemSprite(item));
    }

    public Texture withPrefix(String prefix) {
        if (prefix.isEmpty()) {
            return this;
        }

        return new Texture(name.withPrefix(prefix), alwaysTranslucent);
    }

    public Texture withSuffix(String suffix) {
        if (suffix.isEmpty()) {
            return this;
        }

        return new Texture(name.withSuffix(suffix), alwaysTranslucent);
    }

    public Texture withAlwaysTranslucent() {
        if (alwaysTranslucent) {
            return this;
        }

        return new Texture(name, true);
    }

    public JsonElement toJson() {
        var base = name.toString();

        if (alwaysTranslucent) {
            var object = new JsonObject();
            object.addProperty("sprite", base);
            object.addProperty("force_translucent", true);
            return object;
        } else {
            return new JsonPrimitive(base);
        }
    }

    public static Identifier blockSprite(Block block, String suffix) {
        return blockSprite(block).withSuffix(suffix);
    }

    public static Identifier blockSprite(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).withPrefix("block/");
    }

    public static Identifier itemSprite(Item item, String suffix) {
        return itemSprite(item).withSuffix(suffix);
    }

    public static Identifier itemSprite(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).withPrefix("item/");
    }
}
