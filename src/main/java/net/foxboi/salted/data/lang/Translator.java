package net.foxboi.salted.data.lang;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface Translator<T> {
    void name(T object, String name);

    static Translator<String> rawKey(FabricLanguageProvider.TranslationBuilder builder) {
        return builder::add;
    }

    static Translator<Block> block(FabricLanguageProvider.TranslationBuilder builder) {
        return builder::add;
    }

    static Translator<Item> item(FabricLanguageProvider.TranslationBuilder builder) {
        return builder::add;
    }

    static Translator<EntityType<?>> entity(FabricLanguageProvider.TranslationBuilder builder) {
        return builder::add;
    }

    static <T> Translator<ResourceKey<T>> resource(String type, FabricLanguageProvider.TranslationBuilder builder) {
        return (key, name) -> builder.add(key.location().toLanguageKey(type), name);
    }

    static <T> Translator<TagKey<T>> tag(FabricLanguageProvider.TranslationBuilder builder) {
        return builder::add;
    }
}
