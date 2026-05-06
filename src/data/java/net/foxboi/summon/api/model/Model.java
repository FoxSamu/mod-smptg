package net.foxboi.summon.api.model;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.foxboi.salted.data.model.TextureKeys;

public interface Model {
    Identifier save(ModelSink sink);

    static Reference reference(Identifier id) {
        return new Reference(id);
    }

    static Reference reference(Block block) {
        return reference(blockModelId(block));
    }

    static Reference reference(Block block, String suffix) {
        return reference(blockModelId(block, suffix));
    }

    static Reference reference(Item item) {
        return reference(itemModelId(item));
    }

    static Reference reference(Item item, String suffix) {
        return reference(itemModelId(item, suffix));
    }

    record Reference(Identifier id) implements Model {
        @Override
        public Identifier save(ModelSink sink) {
            return id;
        }
    }

    static OnlyParticle particleOnly(Identifier id, Texture texture) {
        return new OnlyParticle(id, texture);
    }

    static OnlyParticle particleOnly(Block block, Texture texture) {
        return particleOnly(blockModelId(block), texture);
    }

    static OnlyParticle particleOnly(Block block, String suffix, Texture texture) {
        return particleOnly(blockModelId(block, suffix), texture);
    }

    record OnlyParticle(Identifier id, Texture texture) implements Model {
        @Override
        public Identifier save(ModelSink sink) {
            sink.save(id, () -> ModelJson.createParticleModel(TextureMap.map().put(TextureKeys.PARTICLE, texture)));
            return id;
        }
    }

    static Inherit inherit(Identifier id, Model parent, TextureMap map) {
        return new Inherit(id, parent, map.copy());
    }

    static Inherit inherit(Identifier id, Identifier parent, TextureMap map) {
        return inherit(id, reference(parent), map);
    }

    static Inherit inherit(Block block, Model parent, TextureMap map) {
        return inherit(blockModelId(block), parent, map);
    }

    static Inherit inherit(Block block, Identifier parent, TextureMap map) {
        return inherit(blockModelId(block), parent, map);
    }

    static Inherit inherit(Block block, String suffix, Model parent, TextureMap map) {
        return inherit(blockModelId(block, suffix), parent, map);
    }

    static Inherit inherit(Block block, String suffix, Identifier parent, TextureMap map) {
        return inherit(blockModelId(block, suffix), parent, map);
    }

    static Inherit inherit(Item item, Model parent, TextureMap map) {
        return inherit(itemModelId(item), parent, map);
    }

    static Inherit inherit(Item item, Identifier parent, TextureMap map) {
        return inherit(itemModelId(item), parent, map);
    }

    static Inherit inherit(Item item, String suffix, Model parent, TextureMap map) {
        return inherit(itemModelId(item, suffix), parent, map);
    }

    static Inherit inherit(Item item, String suffix, Identifier parent, TextureMap map) {
        return inherit(itemModelId(item, suffix), parent, map);
    }

    record Inherit(Identifier id, Model parent, TextureMap map) implements Model {
        @Override
        public Identifier save(ModelSink sink) {
            sink.save(id, () -> ModelJson.createInheritedModel(parent.save(sink), map));
            return id;
        }
    }

    Identifier GENERATED_ITEM = Identifier.withDefaultNamespace("item/generated");
    Identifier HANDHELD_ITEM = Identifier.withDefaultNamespace("item/handheld");
    Identifier HANDHELD_ROD_ITEM = Identifier.withDefaultNamespace("item/handheld_rod");

    static Generated generated(Identifier id, Identifier type, Texture... textures) {
        var layers = textures.length == 0
                ? List.of(Texture.of(id)) // Fall back to model id if no layers are defined
                : List.of(textures);

        return new Generated(id, type, layers);
    }

    static Generated generated(Item item, Identifier type, Texture... textures) {
        return generated(itemModelId(item), type, textures);
    }

    static Generated generated(Item item, String suffix, Identifier type, Texture... textures) {
        return generated(itemModelId(item, suffix), type, textures);
    }

    static Generated generated(Identifier id, Texture... textures) {
        return generated(id, GENERATED_ITEM, textures);
    }

    static Generated generated(Item item, Texture... textures) {
        return generated(itemModelId(item), textures);
    }

    static Generated generated(Item item, String suffix, Texture... textures) {
        return generated(itemModelId(item, suffix), textures);
    }

    static Generated handheld(Identifier id, Texture... textures) {
        return generated(id, HANDHELD_ITEM, textures);
    }

    static Generated handheld(Item item, Texture... textures) {
        return handheld(itemModelId(item), textures);
    }

    static Generated handheld(Item item, String suffix, Texture... textures) {
        return handheld(itemModelId(item, suffix), textures);
    }

    static Generated rod(Identifier id, Texture... textures) {
        return generated(id, HANDHELD_ROD_ITEM, textures);
    }

    static Generated rod(Item item, Texture... textures) {
        return rod(itemModelId(item), textures);
    }

    static Generated rod(Item item, String suffix, Texture... textures) {
        return rod(itemModelId(item, suffix), textures);
    }

    record Generated(Identifier id, Identifier type, List<Texture> layers) implements Model {
        @Override
        public Identifier save(ModelSink sink) {
            sink.save(id, () -> ModelJson.createGeneratedModel(type, layers));
            return id;
        }
    }

    static Identifier blockModelId(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).withPrefix("block/");
    }

    static Identifier blockModelId(Block block, String suffix) {
        return blockModelId(block).withSuffix(suffix);
    }

    static Identifier itemModelId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).withPrefix("item/");
    }

    static Identifier itemModelId(Item item, String suffix) {
        return itemModelId(item).withSuffix(suffix);
    }
}
