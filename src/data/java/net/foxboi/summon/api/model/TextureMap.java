package net.foxboi.summon.api.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import com.google.gson.JsonObject;

public final class TextureMap {
    private final Map<String, Texture> textures = new LinkedHashMap<>();

    private TextureMap() {
    }

    public static TextureMap map() {
        return new TextureMap();
    }

    public static TextureMap layers(Texture... layers) {
        var map = TextureMap.map();
        var i = 0;
        for (var layer : layers) {
            map.put("layer" + i, layer);
            i++;
        }
        return map;
    }

    public static TextureMap layers(Identifier... layers) {
        var map = TextureMap.map();
        var i = 0;
        for (var layer : layers) {
            map.put("layer" + i, layer);
            i++;
        }
        return map;
    }

    public TextureMap clear() {
        textures.clear();
        return this;
    }

    public TextureMap put(String key, Texture texture) {
        textures.put(key, texture);
        return this;
    }

    public TextureMap put(String key, Identifier texture, boolean alwaysTranslucent) {
        return put(key, Texture.of(texture, alwaysTranslucent));
    }

    public TextureMap put(String key, Identifier texture) {
        return put(key, Texture.of(texture));
    }

    public TextureMap put(String key, Block block, String suffix, boolean alwaysTranslucent) {
        return put(key, Texture.of(block, suffix, alwaysTranslucent));
    }

    public TextureMap put(String key, Block block, String suffix) {
        return put(key, Texture.of(block, suffix));
    }

    public TextureMap put(String key, Block block, boolean alwaysTranslucent) {
        return put(key, Texture.of(block, alwaysTranslucent));
    }

    public TextureMap put(String key, Block block) {
        return put(key, Texture.of(block));
    }

    public TextureMap put(String key, Item item, String suffix, boolean alwaysTranslucent) {
        return put(key, Texture.of(item, suffix, alwaysTranslucent));
    }

    public TextureMap put(String key, Item item, String suffix) {
        return put(key, Texture.of(item, suffix));
    }

    public TextureMap put(String key, Item item, boolean alwaysTranslucent) {
        return put(key, Texture.of(item, alwaysTranslucent));
    }

    public TextureMap put(String key, Item item) {
        return put(key, Texture.of(item));
    }

    public TextureMap put(TextureKey key, Texture texture) {
        return put(key.name(), texture);
    }

    public TextureMap put(TextureKey key, Identifier texture, boolean alwaysTranslucent) {
        return put(key.name(), texture, alwaysTranslucent);
    }

    public TextureMap put(TextureKey key, Identifier texture) {
        return put(key.name(), texture);
    }

    public TextureMap put(TextureKey key, Block block, String suffix, boolean alwaysTranslucent) {
        return put(key.name(), block, suffix, alwaysTranslucent);
    }

    public TextureMap put(TextureKey key, Block block, String suffix) {
        return put(key.name(), block, suffix);
    }

    public TextureMap put(TextureKey key, Block block, boolean alwaysTranslucent) {
        return put(key.name(), block, alwaysTranslucent);
    }

    public TextureMap put(TextureKey key, Block block) {
        return put(key.name(), block);
    }

    public TextureMap put(TextureKey key, Item item, String suffix, boolean alwaysTranslucent) {
        return put(key.name(), item, suffix, alwaysTranslucent);
    }

    public TextureMap put(TextureKey key, Item item, String suffix) {
        return put(key.name(), item, suffix);
    }

    public TextureMap put(TextureKey key, Item item, boolean alwaysTranslucent) {
        return put(key.name(), item, alwaysTranslucent);
    }

    public TextureMap put(TextureKey key, Item item) {
        return put(key.name(), item);
    }

    public TextureMap putAll(TextureMap map) {
        return putAll(map.textures);
    }

    public TextureMap putAll(Map<String, Texture> map) {
        textures.putAll(map);
        return this;
    }

    public TextureMap map(BiFunction<String, Texture, Texture> map) {
        for (var entry : textures.entrySet()) {
            entry.setValue(map.apply(entry.getKey(), entry.getValue()));
        }
        return this;
    }

    public TextureMap filter(BiPredicate<String, Texture> filter) {
        textures.entrySet().removeIf(e -> !filter.test(e.getKey(), e.getValue()));
        return this;
    }

    public void forEach(BiConsumer<String, Texture> function) {
        textures.forEach(function);
    }

    public boolean contains(String key) {
        return textures.containsKey(key);
    }

    public boolean contains(TextureKey key) {
        while (key != null) {
            if (contains(key.name())) {
                return true;
            }

            key = key.parent();
        }

        return false;
    }

    public Texture get(String key) {
        return textures.get(key);
    }

    public Texture get(TextureKey key) {
        while (key != null) {
            var tex = get(key.name());
            if (tex != null) {
                return tex;
            }

            key = key.parent();
        }

        return null;
    }

    public TextureMap translate(Collection<TextureKey> keys) {
        var current = Map.copyOf(textures);

        textures.clear();

        for (var key : keys) {
            var originalName = key.name();

            while (key != null) {
                var tex = current.get(key.name());

                if (tex != null) {
                    put(originalName, tex);
                    break;
                }

                key = key.parent();
            }

            if (key == null) {
                throw new NoSuchElementException("Missing texture key: " + originalName);
            }
        }

        return this;
    }

    public TextureMap copy() {
        return TextureMap.map().putAll(this);
    }

    public TextureMap copy(String fromKey, String toKey) {
        return put(toKey, get(fromKey));
    }

    public TextureMap copy(TextureKey fromKey, String toKey) {
        return put(toKey, get(fromKey));
    }

    public TextureMap copy(String fromKey, TextureKey toKey) {
        return put(toKey, get(fromKey));
    }

    public TextureMap copy(TextureKey fromKey, TextureKey toKey) {
        return put(toKey, get(fromKey));
    }

    public JsonObject toJson() {
        var object = new JsonObject();
        forEach((key, texture) -> object.add(key, texture.toJson()));
        return object;
    }

    public static TextureMap empty() {
        return TextureMap.map();
    }

    public static TextureMap merge(TextureMap... maps) {
        var result = TextureMap.map();
        for (var map : maps) {
            result.putAll(map);
        }
        return result;
    }
}
