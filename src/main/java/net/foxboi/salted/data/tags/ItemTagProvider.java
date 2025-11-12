package net.foxboi.salted.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.item.ModItemTags;
import net.foxboi.salted.common.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> regs, BlockTagProvider blockTags) {
        super(output, regs, blockTags);
    }

    @Override
    protected void addTags(HolderLookup.Provider regs) {
        valueLookupBuilder(ModItemTags.SALTED_FOODS)
                .add(ModItems.SALTED_BEEF)
                .add(ModItems.SALTED_PORKCHOP)
                .add(ModItems.SALTED_MUTTON)
                .add(ModItems.SALTED_CHICKEN)
                .add(ModItems.COOKED_SALTED_BEEF)
                .add(ModItems.COOKED_SALTED_PORKCHOP)
                .add(ModItems.COOKED_SALTED_MUTTON)
                .add(ModItems.COOKED_SALTED_CHICKEN);

        valueLookupBuilder(ItemTags.BOATS)
                .add(ModItems.ASPEN_BOAT)
                .add(ModItems.BEECH_BOAT)
                .add(ModItems.MAPLE_BOAT)
                .add(ModItems.REDWOOD_BOAT)
                .add(ModItems.DEAD_WOOD_BOAT);

        valueLookupBuilder(ItemTags.CHEST_BOATS)
                .add(ModItems.ASPEN_CHEST_BOAT)
                .add(ModItems.BEECH_CHEST_BOAT)
                .add(ModItems.MAPLE_CHEST_BOAT)
                .add(ModItems.REDWOOD_CHEST_BOAT)
                .add(ModItems.DEAD_WOOD_CHEST_BOAT);

        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.WALLS, ItemTags.WALLS);

        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
        copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
        copy(BlockTags.WOODEN_SHELVES, ItemTags.WOODEN_SHELVES);
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);

        copy(BlockTags.DIRT, ItemTags.DIRT);

        copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        copy(BlockTags.FLOWERS, ItemTags.FLOWERS);
        copy(BlockTags.BEE_ATTRACTIVE, ItemTags.BEE_FOOD);

        copy(ModBlockTags.ASPEN_LOGS, ModItemTags.ASPEN_LOGS);
        copy(ModBlockTags.BEECH_LOGS, ModItemTags.BEECH_LOGS);
        copy(ModBlockTags.MAPLE_LOGS, ModItemTags.MAPLE_LOGS);
        copy(ModBlockTags.REDWOOD_LOGS, ModItemTags.REDWOOD_LOGS);
        copy(ModBlockTags.DEAD_LOGS, ModItemTags.DEAD_LOGS);

        copy(ModBlockTags.SALT_ORES, ModItemTags.SALT_ORES);

        copy(ConventionalBlockTags.ORES_IN_GROUND_STONE, ConventionalItemTags.ORES_IN_GROUND_STONE);
        copy(ConventionalBlockTags.ORES_IN_GROUND_DEEPSLATE, ConventionalItemTags.ORES_IN_GROUND_DEEPSLATE);
        copy(ConventionalBlockTags.ORES_IN_GROUND_NETHERRACK, ConventionalItemTags.ORES_IN_GROUND_NETHERRACK);
    }
}
