package net.foxboi.salted.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;

import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.data.ModBlockData;
import net.foxboi.summon.api.tags.LookupTagSet;
import net.foxboi.summon.api.tags.TagProvider;

public class BlockTagSet extends LookupTagSet<Block> {
    public BlockTagSet(TagProvider provider) {
        super(provider, BuiltInRegistries.BLOCK);
    }

    @Override
    protected void register(HolderLookup.Provider lookups) {
        var toolTags = new ToolTags((block, tag) -> builder(tag).element(block));
        ModBlockData.tools(toolTags);

        builder(BlockTags.ENDERMAN_HOLDABLE)
                .tag(ModBlockTags.ANY_PEAT)
                .element(ModBlocks.GRASSY_LIMESTONE)
                .element(ModBlocks.ASH_BLOCK)
                .element(ModBlocks.EMBERGRASS)
                .element(ModBlocks.EMBERWEED)
                .element(ModBlocks.LAVENDER)
                .element(ModBlocks.BARLEY)
                .element(ModBlocks.CATTAIL);

        builder(BlockTags.SLABS)
                .element(ModBlocks.ASH_BRICK_SLAB)
                .element(ModBlocks.LIMESTONE_SLAB)
                .element(ModBlocks.LIMESTONE_BRICK_SLAB)
                .element(ModBlocks.LIMESTONE_TILE_SLAB)
                .element(ModBlocks.CRACKED_LIMESTONE_BRICK_SLAB)
                .element(ModBlocks.POLISHED_LIMESTONE_SLAB);

        builder(BlockTags.WOODEN_SLABS)
                .element(ModBlocks.ASPEN_SLAB)
                .element(ModBlocks.BEECH_SLAB)
                .element(ModBlocks.MAPLE_SLAB)
                .element(ModBlocks.REDWOOD_SLAB)
                .element(ModBlocks.DEAD_WOOD_SLAB);

        builder(BlockTags.STAIRS)
                .element(ModBlocks.ASH_BRICK_STAIRS)
                .element(ModBlocks.LIMESTONE_STAIRS)
                .element(ModBlocks.LIMESTONE_BRICK_STAIRS)
                .element(ModBlocks.LIMESTONE_TILE_STAIRS)
                .element(ModBlocks.CRACKED_LIMESTONE_BRICK_STAIRS)
                .element(ModBlocks.POLISHED_LIMESTONE_STAIRS);

        builder(BlockTags.WOODEN_STAIRS)
                .element(ModBlocks.ASPEN_STAIRS)
                .element(ModBlocks.BEECH_STAIRS)
                .element(ModBlocks.MAPLE_STAIRS)
                .element(ModBlocks.REDWOOD_STAIRS)
                .element(ModBlocks.DEAD_WOOD_STAIRS);

        builder(BlockTags.WALLS)
                .element(ModBlocks.LIMESTONE_WALL)
                .element(ModBlocks.LIMESTONE_BRICK_WALL)
                .element(ModBlocks.LIMESTONE_TILE_WALL)
                .element(ModBlocks.CRACKED_LIMESTONE_BRICK_WALL)
                .element(ModBlocks.POLISHED_LIMESTONE_WALL);

        builder(BlockTags.WOODEN_FENCES)
                .element(ModBlocks.ASPEN_FENCE)
                .element(ModBlocks.BEECH_FENCE)
                .element(ModBlocks.MAPLE_FENCE)
                .element(ModBlocks.REDWOOD_FENCE)
                .element(ModBlocks.DEAD_WOOD_FENCE);

        builder(BlockTags.FENCE_GATES)
                .element(ModBlocks.ASPEN_FENCE_GATE)
                .element(ModBlocks.BEECH_FENCE_GATE)
                .element(ModBlocks.MAPLE_FENCE_GATE)
                .element(ModBlocks.REDWOOD_FENCE_GATE)
                .element(ModBlocks.DEAD_WOOD_FENCE_GATE);

        builder(BlockTags.WOODEN_BUTTONS)
                .element(ModBlocks.ASPEN_BUTTON)
                .element(ModBlocks.BEECH_BUTTON)
                .element(ModBlocks.MAPLE_BUTTON)
                .element(ModBlocks.REDWOOD_BUTTON)
                .element(ModBlocks.DEAD_WOOD_BUTTON);

        builder(BlockTags.WOODEN_PRESSURE_PLATES)
                .element(ModBlocks.ASPEN_PRESSURE_PLATE)
                .element(ModBlocks.BEECH_PRESSURE_PLATE)
                .element(ModBlocks.MAPLE_PRESSURE_PLATE)
                .element(ModBlocks.REDWOOD_PRESSURE_PLATE)
                .element(ModBlocks.DEAD_WOOD_PRESSURE_PLATE);

        builder(BlockTags.STANDING_SIGNS)
                .element(ModBlocks.ASPEN_SIGN)
                .element(ModBlocks.BEECH_SIGN)
                .element(ModBlocks.MAPLE_SIGN)
                .element(ModBlocks.REDWOOD_SIGN)
                .element(ModBlocks.DEAD_WOOD_SIGN);

        builder(BlockTags.WALL_SIGNS)
                .element(ModBlocks.ASPEN_WALL_SIGN)
                .element(ModBlocks.BEECH_WALL_SIGN)
                .element(ModBlocks.MAPLE_WALL_SIGN)
                .element(ModBlocks.REDWOOD_WALL_SIGN)
                .element(ModBlocks.DEAD_WOOD_WALL_SIGN);

        builder(BlockTags.CEILING_HANGING_SIGNS)
                .element(ModBlocks.ASPEN_HANGING_SIGN)
                .element(ModBlocks.BEECH_HANGING_SIGN)
                .element(ModBlocks.MAPLE_HANGING_SIGN)
                .element(ModBlocks.REDWOOD_HANGING_SIGN)
                .element(ModBlocks.DEAD_WOOD_HANGING_SIGN);

        builder(BlockTags.WALL_HANGING_SIGNS)
                .element(ModBlocks.ASPEN_WALL_HANGING_SIGN)
                .element(ModBlocks.BEECH_WALL_HANGING_SIGN)
                .element(ModBlocks.MAPLE_WALL_HANGING_SIGN)
                .element(ModBlocks.REDWOOD_WALL_HANGING_SIGN)
                .element(ModBlocks.DEAD_WOOD_WALL_HANGING_SIGN);

        builder(BlockTags.LOGS_THAT_BURN)
                .tag(ModBlockTags.ASPEN_LOGS)
                .tag(ModBlockTags.BEECH_LOGS)
                .tag(ModBlockTags.MAPLE_LOGS)
                .tag(ModBlockTags.REDWOOD_LOGS)
                .tag(ModBlockTags.DEAD_LOGS);

        builder(BlockTags.OVERWORLD_NATURAL_LOGS)
                .element(ModBlocks.ASPEN_LOG)
                .element(ModBlocks.BEECH_LOG)
                .element(ModBlocks.MAPLE_LOG)
                .element(ModBlocks.REDWOOD_LOG)
                .element(ModBlocks.DEAD_LOG);

        builder(BlockTags.WOODEN_SHELVES)
                .element(ModBlocks.ASPEN_SHELF)
                .element(ModBlocks.BEECH_SHELF)
                .element(ModBlocks.MAPLE_SHELF)
                .element(ModBlocks.REDWOOD_SHELF)
                .element(ModBlocks.DEAD_WOOD_SHELF);

        builder(BlockTags.LEAVES)
                .element(ModBlocks.ASPEN_LEAVES)
                .element(ModBlocks.BEECH_LEAVES)
                .element(ModBlocks.RED_MAPLE_LEAVES)
                .element(ModBlocks.ORANGE_MAPLE_LEAVES)
                .element(ModBlocks.YELLOW_MAPLE_LEAVES)
                .element(ModBlocks.REDWOOD_LEAVES)
                .element(ModBlocks.DEAD_LEAVES);

        builder(BlockTags.SMALL_FLOWERS)
                .element(ModBlocks.FLOWERING_HEATH)
                .element(ModBlocks.LAVENDER);

        builder(BlockTags.FLOWERS)
                .element(ModBlocks.LAVENDER)
                .element(ModBlocks.TALL_LAVENDER)
                .element(ModBlocks.GLOBE_THISTLE);

        builder(BlockTags.BEE_ATTRACTIVE)
                .element(ModBlocks.FLOWERING_HEATH)
                .element(ModBlocks.LAVENDER)
                .element(ModBlocks.TALL_LAVENDER)
                .element(ModBlocks.GLOBE_THISTLE);

        builder(ModBlockTags.BRAZIERS)
                .element(ModBlocks.LIMESTONE_BRAZIER)
                .element(ModBlocks.SOUL_LIMESTONE_BRAZIER);

        builder(BlockTags.GRASS_BLOCKS)
                .element(ModBlocks.MOSSY_DIRT)
                .element(ModBlocks.GRASSY_PEAT)
                .element(ModBlocks.MOSSY_PEAT)
                .element(ModBlocks.GRASSY_LIMESTONE);

        builder(ModBlockTags.PEAT)
                .element(ModBlocks.PEAT)
                .element(ModBlocks.COARSE_PEAT);

        builder(ModBlockTags.ANY_PEAT)
                .tag(ModBlockTags.PEAT)
                .element(ModBlocks.GRASSY_PEAT)
                .element(ModBlocks.MOSSY_PEAT)
                .element(ModBlocks.DRIED_PEAT);

        builder(BlockTags.SUBSTRATE_OVERWORLD)
                .tag(ModBlockTags.PEAT);

        builder(BlockTags.OVERRIDES_MUSHROOM_LIGHT_REQUIREMENT)
                .tag(ModBlockTags.PEAT)
                .element(ModBlocks.DRIED_PEAT)
                .element(ModBlocks.GRASSY_PEAT)
                .element(ModBlocks.MOSSY_PEAT);

        builder(ModBlockTags.GRASS_SPREAD_SOURCE)
                .element(Blocks.GRASS_BLOCK)
                .element(ModBlocks.GRASSY_PEAT)
                .element(ModBlocks.GRASSY_LIMESTONE);

        builder(ModBlockTags.TRANSFERS_FLUID_TO_PEAT)
                .tag(BlockTags.SUBSTRATE_OVERWORLD)
                .tag(ModBlockTags.ANY_PEAT);

        builder(BlockTags.OVERWORLD_CARVER_REPLACEABLES)
                .element(ModBlocks.LIMESTONE);

        builder(BlockTags.NETHER_CARVER_REPLACEABLES)
                .element(ModBlocks.ASH_BLOCK)
                .element(ModBlocks.PACKED_ASH);

        builder(ModBlockTags.LIMESTONE_ORE_REPLACEABLES)
                .element(ModBlocks.LIMESTONE);

        builder(BlockTags.COAL_ORES)
                .element(ModBlocks.LIMESTONE_COAL_ORE);

        builder(BlockTags.COPPER_ORES)
                .element(ModBlocks.LIMESTONE_COPPER_ORE);

        builder(BlockTags.IRON_ORES)
                .element(ModBlocks.LIMESTONE_IRON_ORE);

        builder(BlockTags.GOLD_ORES)
                .element(ModBlocks.LIMESTONE_GOLD_ORE);

        builder(BlockTags.DIAMOND_ORES)
                .element(ModBlocks.LIMESTONE_DIAMOND_ORE);

        builder(BlockTags.EMERALD_ORES)
                .element(ModBlocks.LIMESTONE_EMERALD_ORE);

        builder(BlockTags.REDSTONE_ORES)
                .element(ModBlocks.LIMESTONE_REDSTONE_ORE);

        builder(BlockTags.LAPIS_ORES)
                .element(ModBlocks.LIMESTONE_LAPIS_ORE);

        createTreeMushroomReplacable(BlockTags.REPLACEABLE_BY_TREES, false);
        createTreeMushroomReplacable(BlockTags.REPLACEABLE_BY_MUSHROOMS, true);

        createInfiniburn(BlockTags.INFINIBURN_NETHER);
        createInfiniburn(BlockTags.INFINIBURN_OVERWORLD);
        createInfiniburn(BlockTags.INFINIBURN_END);

        builder(ModBlockTags.ASPEN_LOGS)
                .element(ModBlocks.ASPEN_LOG)
                .element(ModBlocks.ASPEN_WOOD)
                .element(ModBlocks.STRIPPED_ASPEN_LOG)
                .element(ModBlocks.STRIPPED_ASPEN_WOOD);

        builder(ModBlockTags.BEECH_LOGS)
                .element(ModBlocks.BEECH_LOG)
                .element(ModBlocks.BEECH_WOOD)
                .element(ModBlocks.STRIPPED_BEECH_LOG)
                .element(ModBlocks.STRIPPED_BEECH_WOOD);

        builder(ModBlockTags.MAPLE_LOGS)
                .element(ModBlocks.MAPLE_LOG)
                .element(ModBlocks.MAPLE_WOOD)
                .element(ModBlocks.STRIPPED_MAPLE_LOG)
                .element(ModBlocks.STRIPPED_MAPLE_WOOD);

        builder(ModBlockTags.REDWOOD_LOGS)
                .element(ModBlocks.REDWOOD_LOG)
                .element(ModBlocks.REDWOOD_WOOD)
                .element(ModBlocks.STRIPPED_REDWOOD_LOG)
                .element(ModBlocks.STRIPPED_REDWOOD_WOOD);

        builder(ModBlockTags.DEAD_LOGS)
                .element(ModBlocks.DEAD_LOG)
                .element(ModBlocks.DEAD_WOOD)
                .element(ModBlocks.STRIPPED_DEAD_LOG)
                .element(ModBlocks.STRIPPED_DEAD_WOOD);

        builder(ModBlockTags.DAMAGES_SLIMES);

        builder(BlockTags.BASE_STONE_OVERWORLD);

        builder(ModBlockTags.LIMESTONE_REPLACEABLE)
                .tag(BlockTags.BASE_STONE_OVERWORLD);

        builder(BlockTags.SUPPORTS_DRY_VEGETATION)
                .element(ModBlocks.LIMESTONE);

        builder(ModBlockTags.SUPPORTS_CAVE_VEGETATION)
                .tag(BlockTags.SUPPORTS_DRY_VEGETATION)
                .tag(BlockTags.SAND)
                .tag(BlockTags.BASE_STONE_OVERWORLD)
                .element(Blocks.GRAVEL)
                .element(Blocks.FARMLAND)
                .element(Blocks.DRIPSTONE_BLOCK)
                .element(ModBlocks.LIMESTONE);

        builder(ModBlockTags.SUPPORTS_ASH_VEGETATION)
                .tag(BlockTags.SUPPORTS_DRY_VEGETATION)
                .element(Blocks.NETHERRACK)
                .element(ModBlocks.ASH_BLOCK)
                .element(ModBlocks.PACKED_ASH);

        builder(ModBlockTags.SUPPORTS_WATER_VEGETATION)
                .tag(BlockTags.SUPPORTS_VEGETATION)
                .tag(BlockTags.SAND)
                .element(Blocks.CLAY)
                .element(Blocks.MUD)
                .element(Blocks.GRAVEL);

        builder(ModBlockTags.SUPPORTS_EMBER_VEGETATION)
                .tag(BlockTags.SUPPORTS_DRY_VEGETATION)
                .element(Blocks.NETHERRACK)
                .element(Blocks.CRIMSON_NYLIUM)
                .element(Blocks.WARPED_NYLIUM)
                .element(Blocks.SOUL_SAND)
                .element(Blocks.SOUL_SOIL)
                .element(ModBlocks.ASH_BLOCK)
                .element(ModBlocks.PACKED_ASH);

        builder(ModBlockTags.HEATH)
                .element(ModBlocks.HEATH)
                .element(ModBlocks.FLOWERING_HEATH);

        builder(ConventionalBlockTags.ORES_IN_GROUND_STONE);

        builder(ConventionalBlockTags.ORES_IN_GROUND_DEEPSLATE);

        builder(ConventionalBlockTags.ORES_IN_GROUND_NETHERRACK);

        builder(ConventionalBlockTags.CLUSTERS);
    }

    private void createInfiniburn(TagKey<Block> tag) {
        builder(tag)
                .tag(ModBlockTags.ANY_PEAT)
                .element(ModBlocks.BURNED_STEM)
                .element(ModBlocks.BURNED_HYPHAE)
                .element(ModBlocks.ASH_BLOCK)
                .element(ModBlocks.PACKED_ASH);
    }

    private void createTreeMushroomReplacable(TagKey<Block> tag, boolean mushroom) {
        builder(tag)
                .element(ModBlocks.ASHCREEP)
                .element(ModBlocks.CAVE_GRASS)
                .element(ModBlocks.GRASS_SPROUTS)
                .element(ModBlocks.LAVENDER)
                .element(ModBlocks.CATTAIL)
                .element(ModBlocks.BARLEY)
                .element(ModBlocks.CLOVERS);

        if (mushroom) {
            builder(tag)
                    .element(ModBlocks.TALL_BARLEY)
                    .element(ModBlocks.TALL_CATTAIL)
                    .element(ModBlocks.TALL_LAVENDER)
                    .element(ModBlocks.GLOBE_THISTLE)
                    .element(ModBlocks.DRIPMOSS);
        }
    }
}
