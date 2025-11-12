package net.foxboi.salted.data.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.foxboi.salted.common.levelgen.biome.ModBiomes;
import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.entity.ModEntityTypes;
import net.foxboi.salted.common.item.ModItemTags;
import net.foxboi.salted.common.item.ModItems;
import net.foxboi.salted.data.advancement.AdvancementProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class DefaultTranslationProvider extends FabricLanguageProvider {
    public DefaultTranslationProvider(FabricDataOutput out, CompletableFuture<HolderLookup.Provider> regs) {
        super(out, "en_us", regs);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider regs, TranslationBuilder builder) {
        ModBlockTags.translate(Translator.tag(builder));
        ModItemTags.translate(Translator.tag(builder));

        ModBlocks.translate(Translator.block(builder));
        ModItems.translate(Translator.item(builder));
        ModEntityTypes.translate(Translator.entity(builder));

        ModBiomes.translate(Translator.resource("biome", builder));

        AdvancementProvider.translate(Translator.rawKey(builder));
    }
}
