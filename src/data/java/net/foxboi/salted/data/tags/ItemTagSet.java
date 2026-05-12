package net.foxboi.salted.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;

import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.item.ModItemTags;
import net.foxboi.salted.common.item.ModItems;
import net.foxboi.summon.api.tags.LookupTagSet;
import net.foxboi.summon.api.tags.TagProvider;

public class ItemTagSet extends LookupTagSet<Item> {
    public ItemTagSet(TagProvider provider) {
        super(provider, BuiltInRegistries.ITEM);
    }

    @Override
    protected void register(HolderLookup.Provider lookups) {
        builder(ItemTags.BOATS)
                .element(ModItems.ASPEN_BOAT)
                .element(ModItems.BEECH_BOAT)
                .element(ModItems.MAPLE_BOAT)
                .element(ModItems.REDWOOD_BOAT)
                .element(ModItems.DEAD_WOOD_BOAT);

        builder(ItemTags.CHEST_BOATS)
                .element(ModItems.ASPEN_CHEST_BOAT)
                .element(ModItems.BEECH_CHEST_BOAT)
                .element(ModItems.MAPLE_CHEST_BOAT)
                .element(ModItems.REDWOOD_CHEST_BOAT)
                .element(ModItems.DEAD_WOOD_CHEST_BOAT);


        builder(ItemTags.SLABS)
                .copyFrom(BlockTags.SLABS);

        builder(ItemTags.STAIRS)
                .copyFrom(BlockTags.STAIRS);

        builder(ItemTags.WALLS)
                .copyFrom(BlockTags.WALLS);

        builder(ItemTags.GRASS_BLOCKS)
                .copyFrom(BlockTags.GRASS_BLOCKS);

        builder(ItemTags.WOODEN_SLABS)
                .copyFrom(BlockTags.WOODEN_SLABS);

        builder(ItemTags.WOODEN_STAIRS)
                .copyFrom(BlockTags.WOODEN_STAIRS);

        builder(ItemTags.WOODEN_FENCES)
                .copyFrom(BlockTags.WOODEN_FENCES);

        builder(ItemTags.FENCE_GATES)
                .copyFrom(BlockTags.FENCE_GATES);

        builder(ItemTags.WOODEN_BUTTONS)
                .copyFrom(BlockTags.WOODEN_BUTTONS);

        builder(ItemTags.WOODEN_PRESSURE_PLATES)
                .copyFrom(BlockTags.WOODEN_PRESSURE_PLATES);

        builder(ItemTags.SIGNS)
                .copyFrom(BlockTags.STANDING_SIGNS);

        builder(ItemTags.HANGING_SIGNS)
                .copyFrom(BlockTags.CEILING_HANGING_SIGNS);

        builder(ItemTags.WOODEN_SHELVES)
                .copyFrom(BlockTags.WOODEN_SHELVES);

        builder(ItemTags.LOGS_THAT_BURN)
                .copyFrom(BlockTags.LOGS_THAT_BURN);

        builder(ItemTags.LEAVES)
                .copyFrom(BlockTags.LEAVES);

        builder(ItemTags.DIRT)
                .copyFrom(BlockTags.DIRT);

        builder(ModItemTags.PEAT)
                .copyFrom(ModBlockTags.PEAT);

        builder(ModItemTags.BRAZIERS)
                .copyFrom(ModBlockTags.BRAZIERS);

        builder(ModItemTags.ANY_PEAT)
                .copyFrom(ModBlockTags.ANY_PEAT);

        builder(ItemTags.SMALL_FLOWERS)
                .copyFrom(BlockTags.SMALL_FLOWERS);

        builder(ItemTags.FLOWERS)
                .copyFrom(BlockTags.FLOWERS);

        builder(ItemTags.BEE_FOOD)
                .copyFrom(BlockTags.BEE_ATTRACTIVE);

        builder(ModItemTags.ASPEN_LOGS)
                .copyFrom(ModBlockTags.ASPEN_LOGS);

        builder(ModItemTags.BEECH_LOGS)
                .copyFrom(ModBlockTags.BEECH_LOGS);

        builder(ModItemTags.MAPLE_LOGS)
                .copyFrom(ModBlockTags.MAPLE_LOGS);

        builder(ModItemTags.REDWOOD_LOGS)
                .copyFrom(ModBlockTags.REDWOOD_LOGS);

        builder(ModItemTags.DEAD_LOGS)
                .copyFrom(ModBlockTags.DEAD_LOGS);

        builder(ModItemTags.HEATH)
                .copyFrom(ModBlockTags.HEATH);

        builder(ConventionalItemTags.ORES_IN_GROUND_STONE)
                .copyFrom(ConventionalBlockTags.ORES_IN_GROUND_STONE);

        builder(ConventionalItemTags.ORES_IN_GROUND_DEEPSLATE)
                .copyFrom(ConventionalBlockTags.ORES_IN_GROUND_DEEPSLATE);

        builder(ConventionalItemTags.ORES_IN_GROUND_NETHERRACK)
                .copyFrom(ConventionalBlockTags.ORES_IN_GROUND_NETHERRACK);

        builder(ItemTags.COAL_ORES)
                .copyFrom(BlockTags.COAL_ORES);
        builder(ItemTags.COPPER_ORES)
                .copyFrom(BlockTags.COPPER_ORES);
        builder(ItemTags.IRON_ORES)
                .copyFrom(BlockTags.IRON_ORES);
        builder(ItemTags.GOLD_ORES)
                .copyFrom(BlockTags.GOLD_ORES);
        builder(ItemTags.DIAMOND_ORES)
                .copyFrom(BlockTags.DIAMOND_ORES);
        builder(ItemTags.EMERALD_ORES)
                .copyFrom(BlockTags.EMERALD_ORES);
        builder(ItemTags.LAPIS_ORES)
                .copyFrom(BlockTags.LAPIS_ORES);
        builder(ItemTags.REDSTONE_ORES)
                .copyFrom(BlockTags.REDSTONE_ORES);
    }

}
