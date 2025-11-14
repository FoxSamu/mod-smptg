package net.foxboi.salted.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public BlockTagProvider(FabricDataOutput out, CompletableFuture<HolderLookup.Provider> regs) {
        super(out, regs);
    }

    @Override
    protected void addTags(HolderLookup.Provider regs) {
        var toolTags = new ToolTags((block, tag) -> valueLookupBuilder(tag).add(block));
        ModBlocks.tools(toolTags);

        valueLookupBuilder(BlockTags.ENDERMAN_HOLDABLE)
                .add(ModBlocks.SALT_BLOCK)
                .add(ModBlocks.ASH_BLOCK);

        valueLookupBuilder(BlockTags.SLABS)
                .add(ModBlocks.ROCKSALT_SLAB)
                .add(ModBlocks.ROCKSALT_BRICK_SLAB)
                .add(ModBlocks.ASH_BRICK_SLAB);

        valueLookupBuilder(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.ASPEN_SLAB)
                .add(ModBlocks.BEECH_SLAB)
                .add(ModBlocks.MAPLE_SLAB)
                .add(ModBlocks.REDWOOD_SLAB)
                .add(ModBlocks.DEAD_WOOD_SLAB);

        valueLookupBuilder(BlockTags.STAIRS)
                .add(ModBlocks.ROCKSALT_STAIRS)
                .add(ModBlocks.ROCKSALT_BRICK_STAIRS)
                .add(ModBlocks.ASH_BRICK_STAIRS);

        valueLookupBuilder(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.ASPEN_STAIRS)
                .add(ModBlocks.BEECH_STAIRS)
                .add(ModBlocks.MAPLE_STAIRS)
                .add(ModBlocks.REDWOOD_STAIRS)
                .add(ModBlocks.DEAD_WOOD_STAIRS);

        valueLookupBuilder(BlockTags.WALLS)
                .add(ModBlocks.ROCKSALT_WALL)
                .add(ModBlocks.ROCKSALT_BRICK_WALL);

        valueLookupBuilder(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.ASPEN_FENCE)
                .add(ModBlocks.BEECH_FENCE)
                .add(ModBlocks.MAPLE_FENCE)
                .add(ModBlocks.REDWOOD_FENCE)
                .add(ModBlocks.DEAD_WOOD_FENCE);

        valueLookupBuilder(BlockTags.FENCE_GATES)
                .add(ModBlocks.ASPEN_FENCE_GATE)
                .add(ModBlocks.BEECH_FENCE_GATE)
                .add(ModBlocks.MAPLE_FENCE_GATE)
                .add(ModBlocks.REDWOOD_FENCE_GATE)
                .add(ModBlocks.DEAD_WOOD_FENCE_GATE);

        valueLookupBuilder(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.ASPEN_BUTTON)
                .add(ModBlocks.BEECH_BUTTON)
                .add(ModBlocks.MAPLE_BUTTON)
                .add(ModBlocks.REDWOOD_BUTTON)
                .add(ModBlocks.DEAD_WOOD_BUTTON);

        valueLookupBuilder(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.ASPEN_PRESSURE_PLATE)
                .add(ModBlocks.BEECH_PRESSURE_PLATE)
                .add(ModBlocks.MAPLE_PRESSURE_PLATE)
                .add(ModBlocks.REDWOOD_PRESSURE_PLATE)
                .add(ModBlocks.DEAD_WOOD_PRESSURE_PLATE);

        valueLookupBuilder(BlockTags.STANDING_SIGNS)
                .add(ModBlocks.ASPEN_SIGN)
                .add(ModBlocks.BEECH_SIGN)
                .add(ModBlocks.MAPLE_SIGN)
                .add(ModBlocks.REDWOOD_SIGN)
                .add(ModBlocks.DEAD_WOOD_SIGN);

        valueLookupBuilder(BlockTags.WALL_SIGNS)
                .add(ModBlocks.ASPEN_WALL_SIGN)
                .add(ModBlocks.BEECH_WALL_SIGN)
                .add(ModBlocks.MAPLE_WALL_SIGN)
                .add(ModBlocks.REDWOOD_WALL_SIGN)
                .add(ModBlocks.DEAD_WOOD_WALL_SIGN);

        valueLookupBuilder(BlockTags.CEILING_HANGING_SIGNS)
                .add(ModBlocks.ASPEN_HANGING_SIGN)
                .add(ModBlocks.BEECH_HANGING_SIGN)
                .add(ModBlocks.MAPLE_HANGING_SIGN)
                .add(ModBlocks.REDWOOD_HANGING_SIGN)
                .add(ModBlocks.DEAD_WOOD_HANGING_SIGN);

        valueLookupBuilder(BlockTags.WALL_HANGING_SIGNS)
                .add(ModBlocks.ASPEN_WALL_HANGING_SIGN)
                .add(ModBlocks.BEECH_WALL_HANGING_SIGN)
                .add(ModBlocks.MAPLE_WALL_HANGING_SIGN)
                .add(ModBlocks.REDWOOD_WALL_HANGING_SIGN)
                .add(ModBlocks.DEAD_WOOD_WALL_HANGING_SIGN);

        valueLookupBuilder(BlockTags.LOGS_THAT_BURN)
                .addTag(ModBlockTags.ASPEN_LOGS)
                .addTag(ModBlockTags.BEECH_LOGS)
                .addTag(ModBlockTags.MAPLE_LOGS)
                .addTag(ModBlockTags.REDWOOD_LOGS)
                .addTag(ModBlockTags.DEAD_LOGS);

        valueLookupBuilder(BlockTags.OVERWORLD_NATURAL_LOGS)
                .add(ModBlocks.ASPEN_LOG)
                .add(ModBlocks.BEECH_LOG)
                .add(ModBlocks.MAPLE_LOG)
                .add(ModBlocks.REDWOOD_LOG)
                .add(ModBlocks.DEAD_LOG);

        valueLookupBuilder(BlockTags.WOODEN_SHELVES)
                .add(ModBlocks.ASPEN_SHELF)
                .add(ModBlocks.BEECH_SHELF)
                .add(ModBlocks.MAPLE_SHELF)
                .add(ModBlocks.REDWOOD_SHELF)
                .add(ModBlocks.DEAD_WOOD_SHELF);

        valueLookupBuilder(BlockTags.LEAVES)
                .add(ModBlocks.ASPEN_LEAVES)
                .add(ModBlocks.BEECH_LEAVES)
                .add(ModBlocks.RED_MAPLE_LEAVES)
                .add(ModBlocks.ORANGE_MAPLE_LEAVES)
                .add(ModBlocks.YELLOW_MAPLE_LEAVES)
                .add(ModBlocks.REDWOOD_LEAVES)
                .add(ModBlocks.DEAD_LEAVES);

        valueLookupBuilder(BlockTags.SMALL_FLOWERS)
                .add(ModBlocks.LAVENDER);

        valueLookupBuilder(BlockTags.FLOWERS)
                .add(ModBlocks.LAVENDER)
                .add(ModBlocks.TALL_LAVENDER)
                .add(ModBlocks.GLOBE_THISTLE);

        valueLookupBuilder(BlockTags.BEE_ATTRACTIVE)
                .add(ModBlocks.LAVENDER)
                .add(ModBlocks.TALL_LAVENDER)
                .add(ModBlocks.GLOBE_THISTLE);

        valueLookupBuilder(BlockTags.DIRT)
                .add(ModBlocks.MOSSY_DIRT);

        createTreeMushroomReplacable(BlockTags.REPLACEABLE_BY_TREES, false);
        createTreeMushroomReplacable(BlockTags.REPLACEABLE_BY_MUSHROOMS, true);

        createInfiniburn(BlockTags.INFINIBURN_NETHER);
        createInfiniburn(BlockTags.INFINIBURN_OVERWORLD);
        createInfiniburn(BlockTags.INFINIBURN_END);

        valueLookupBuilder(ModBlockTags.ASPEN_LOGS)
                .add(ModBlocks.ASPEN_LOG)
                .add(ModBlocks.ASPEN_WOOD)
                .add(ModBlocks.STRIPPED_ASPEN_LOG)
                .add(ModBlocks.STRIPPED_ASPEN_WOOD);

        valueLookupBuilder(ModBlockTags.BEECH_LOGS)
                .add(ModBlocks.BEECH_LOG)
                .add(ModBlocks.BEECH_WOOD)
                .add(ModBlocks.STRIPPED_BEECH_LOG)
                .add(ModBlocks.STRIPPED_BEECH_WOOD);

        valueLookupBuilder(ModBlockTags.MAPLE_LOGS)
                .add(ModBlocks.MAPLE_LOG)
                .add(ModBlocks.MAPLE_WOOD)
                .add(ModBlocks.STRIPPED_MAPLE_LOG)
                .add(ModBlocks.STRIPPED_MAPLE_WOOD);

        valueLookupBuilder(ModBlockTags.REDWOOD_LOGS)
                .add(ModBlocks.REDWOOD_LOG)
                .add(ModBlocks.REDWOOD_WOOD)
                .add(ModBlocks.STRIPPED_REDWOOD_LOG)
                .add(ModBlocks.STRIPPED_REDWOOD_WOOD);

        valueLookupBuilder(ModBlockTags.DEAD_LOGS)
                .add(ModBlocks.DEAD_LOG)
                .add(ModBlocks.DEAD_WOOD)
                .add(ModBlocks.STRIPPED_DEAD_LOG)
                .add(ModBlocks.STRIPPED_DEAD_WOOD);

        valueLookupBuilder(ModBlockTags.GROWS_SALT)
                .addTag(ModBlockTags.SALT_ORES)
                .addTag(ModBlockTags.GROWS_SALT_MORE_LIKELY)
                .add(ModBlocks.ROCKSALT)
                .add(ModBlocks.ROCKSALT_SLAB)
                .add(ModBlocks.ROCKSALT_STAIRS)
                .add(ModBlocks.ROCKSALT_WALL)
                .add(ModBlocks.ROCKSALT_BRICKS)
                .add(ModBlocks.ROCKSALT_BRICK_SLAB)
                .add(ModBlocks.ROCKSALT_BRICK_STAIRS)
                .add(ModBlocks.ROCKSALT_BRICK_WALL);

        valueLookupBuilder(ModBlockTags.GROWS_SALT_MORE_LIKELY)
                .add(ModBlocks.SALT_BLOCK);

        valueLookupBuilder(ModBlockTags.APPLIES_SALT_TO_DROPS)
                .add(ModBlocks.SALT_BLOCK)
                .add(ModBlocks.SALT_CRUST);

        valueLookupBuilder(ModBlockTags.DAMAGES_SLIMES)
                .addTag(ModBlockTags.GROWS_SALT)
                .addTag(ModBlockTags.GROWS_SALT_MORE_LIKELY)
                .add(ModBlocks.SALT_CRYSTAL);

        valueLookupBuilder(ModBlockTags.SALT_CRUST_CAN_REPLACE)
                .addTag(vanilla(BlockTags.DIRT))
                .addTag(vanilla(BlockTags.SAND))
                .add(Blocks.GRAVEL)
                .add(Blocks.CLAY)
                .add(ModBlocks.SALT_BLOCK);

        valueLookupBuilder(ModBlockTags.OVERWORLD_STONE)
                .add(Blocks.STONE)
                .add(Blocks.DEEPSLATE)
                .add(Blocks.GRANITE)
                .add(Blocks.ANDESITE)
                .add(Blocks.DIORITE)
                .add(Blocks.TUFF);

        valueLookupBuilder(ModBlockTags.CAVE_PLANT_CAN_GROW_ON)
                .addTag(vanilla(BlockTags.DIRT))
                .addTag(vanilla(BlockTags.SAND))
                .addTag(ModBlockTags.OVERWORLD_STONE)
                .add(Blocks.GRAVEL)
                .add(Blocks.FARMLAND);

        valueLookupBuilder(ModBlockTags.ASH_PLANT_CAN_GROW_ON)
                .add(Blocks.NETHERRACK)
                .add(ModBlocks.ASH_BLOCK)
                .add(ModBlocks.PACKED_ASH);

        valueLookupBuilder(ModBlockTags.SALT_ORES)
                .add(ModBlocks.SALT_ORE)
                .add(ModBlocks.DEEPSLATE_SALT_ORE)
                .add(ModBlocks.NETHER_SALT_ORE);

        valueLookupBuilder(ConventionalBlockTags.ORES_IN_GROUND_STONE)
                .add(ModBlocks.SALT_ORE);

        valueLookupBuilder(ConventionalBlockTags.ORES_IN_GROUND_DEEPSLATE)
                .add(ModBlocks.DEEPSLATE_SALT_ORE);

        valueLookupBuilder(ConventionalBlockTags.ORES_IN_GROUND_NETHERRACK)
                .add(ModBlocks.NETHER_SALT_ORE);

        valueLookupBuilder(ConventionalBlockTags.CLUSTERS)
                .add(ModBlocks.SALT_CRYSTAL);
    }

    private void createInfiniburn(TagKey<Block> tag) {
        valueLookupBuilder(tag)
                .add(ModBlocks.BURNED_STEM)
                .add(ModBlocks.BURNED_HYPHAE)
                .add(ModBlocks.ASH_BLOCK)
                .add(ModBlocks.PACKED_ASH);
    }

    private void createTreeMushroomReplacable(TagKey<Block> tag, boolean mushroom) {
        valueLookupBuilder(tag)
                .add(ModBlocks.ASHCREEP)
                .add(ModBlocks.CAVE_GRASS)
                .add(ModBlocks.GRASS_SPROUTS)
                .add(ModBlocks.LAVENDER)
                .add(ModBlocks.CATTAIL)
                .add(ModBlocks.BARLEY)
                .add(ModBlocks.CLOVERS);

        if (mushroom) {
            valueLookupBuilder(tag)
                    .add(ModBlocks.TALL_BARLEY)
                    .add(ModBlocks.TALL_CATTAIL)
                    .add(ModBlocks.TALL_LAVENDER)
                    .add(ModBlocks.GLOBE_THISTLE)
                    .add(ModBlocks.DRIPMOSS);
        }
    }

    private TagKey<Block> vanilla(TagKey<Block> tag) {
        getOrCreateRawBuilder(tag);

        return tag;
    }
}
