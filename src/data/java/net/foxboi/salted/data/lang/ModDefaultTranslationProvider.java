package net.foxboi.salted.data.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.foxboi.salted.common.levelgen.biome.ModBiomes;
import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.entity.ModEntityTypes;
import net.foxboi.salted.common.item.ModItemTags;
import net.foxboi.salted.common.item.ModItems;
import net.foxboi.salted.common.misc.Translator;
import net.foxboi.salted.data.advancement.ModAdvancementProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class ModDefaultTranslationProvider extends FabricLanguageProvider {
    public ModDefaultTranslationProvider(FabricPackOutput out, CompletableFuture<HolderLookup.Provider> regs) {
        super(out, "en_us", regs);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider regs, TranslationBuilder builder) {
        ModBlockTags.translate(tag(builder));
        ModItemTags.translate(tag(builder));

        ModBlocks.translate(block(builder));
        ModItems.translate(item(builder));
        ModEntityTypes.translate(entity(builder));

        ModBiomes.translate(resource("biome", builder));

        ModAdvancementProvider.translate(rawKey(builder));
    }

    private static Translator<String> rawKey(FabricLanguageProvider.TranslationBuilder builder) {
        return builder::add;
    }

    private static Translator<Block> block(FabricLanguageProvider.TranslationBuilder builder) {
        return builder::add;
    }

    private static Translator<Item> item(FabricLanguageProvider.TranslationBuilder builder) {
        return builder::add;
    }

    private static Translator<EntityType<?>> entity(FabricLanguageProvider.TranslationBuilder builder) {
        return builder::add;
    }

    private static <T> Translator<ResourceKey<T>> resource(String type, FabricLanguageProvider.TranslationBuilder builder) {
        return (key, name) -> builder.add(key.identifier().toLanguageKey(type), name);
    }

    private static <T> Translator<TagKey<T>> tag(FabricLanguageProvider.TranslationBuilder builder) {
        return builder::add;
    }
}
