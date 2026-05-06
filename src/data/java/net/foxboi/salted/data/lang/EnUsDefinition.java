package net.foxboi.salted.data.lang;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.entity.ModEntityTypes;
import net.foxboi.salted.common.item.ModItemTags;
import net.foxboi.salted.common.item.ModItems;
import net.foxboi.salted.common.levelgen.biome.ModBiomes;
import net.foxboi.salted.common.misc.Translator;
import net.foxboi.salted.data.advancement.ModAdvancementProvider;
import net.foxboi.summon.api.lang.LanguageDefinition;

public class EnUsDefinition extends LanguageDefinition {
    public EnUsDefinition() {
        super("en_us");
    }

    @Override
    protected void setup(HolderLookup.Provider lookups) {
        ModBlockTags.translate(tag());
        ModItemTags.translate(tag());

        ModBlocks.translate(block());
        ModItems.translate(item());
        ModEntityTypes.translate(entity());

        ModBiomes.translate(resource("biome"));

        ModAdvancementProvider.translate(rawKey());
    }

    private Translator<String> rawKey() {
        return this::add;
    }

    private Translator<Block> block() {
        return this::add;
    }

    private Translator<Item> item() {
        return this::add;
    }

    private Translator<EntityType<?>> entity() {
        return this::add;
    }

    private <T> Translator<ResourceKey<T>> resource(String type) {
        return (key, name) -> add(key.identifier().toLanguageKey(type), name);
    }

    private <T> Translator<TagKey<T>> tag() {
        return this::add;
    }
}
