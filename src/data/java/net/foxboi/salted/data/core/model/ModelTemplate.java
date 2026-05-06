package net.foxboi.salted.data.core.model;

import java.util.List;
import java.util.function.Function;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface ModelTemplate {
    Model create(Identifier id, TextureMap textures);

    default BlockModelTemplate block(Function<Block, TextureMap> map) {
        return (block, suffix) -> create(block, suffix, map.apply(block));
    }

    default BlockModelTemplate blockItem(Function<Block, TextureMap> map) {
        return (block, suffix) -> create(block.asItem(), suffix, map.apply(block));
    }

    default ItemModelTemplate item(Function<Item, TextureMap> map) {
        return (item, suffix) -> create(item, suffix, map.apply(item));
    }

    default Model create(Block block, TextureMap textures) {
        return create(Model.blockModelId(block), textures);
    }

    default Model create(Block block, String suffix, TextureMap textures) {
        return create(Model.blockModelId(block, suffix), textures);
    }

    default Model create(Item item, TextureMap textures) {
        return create(Model.itemModelId(item), textures);
    }

    default Model create(Item item, String suffix, TextureMap textures) {
        return create(Model.itemModelId(item, suffix), textures);
    }

    static ModelTemplate of(Identifier parent, String suffix, TextureKey... textures) {
        return (id, map) -> Model.inherit(id.withSuffix(suffix), parent, map.copy().translate(List.of(textures)));
    }

    static ModelTemplate of(Identifier parent, TextureKey... textures) {
        return of(parent, "", textures);
    }
}
