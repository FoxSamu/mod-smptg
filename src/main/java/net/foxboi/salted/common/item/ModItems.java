package net.foxboi.salted.common.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.FuelValues;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.fabricmc.fabric.api.registry.FuelValueEvents;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.entity.ModEntityTypes;
import net.foxboi.salted.common.misc.Translator;
import net.foxboi.salted.common.misc.reg.GameRegistry;

/**
 * Salted's collection of {@link Item}s. Each {@link Item} instance is provided here in a static field.
 */
@SuppressWarnings("unused")
public record ModItems() {
    // IMPORTANT NOTES WHEN ADDING ITEMS
    // - Register your items to relevant Fabric registries in init() if needed
    // - Go through all the configuration methods below and supply the correct configuration for the item
    // - Go through all the configurations in ModItemData (under the data source set) as well
    // - All configuration methods reside above the factory methods

    public static final GameRegistry<Item> REGISTRY = Smptg.REGISTRAR.game(Registries.ITEM);


    // ITEMS
    // =============================================

    // Wood

    public static final Item ASPEN_LOG = registerBlock(ModBlocks.ASPEN_LOG);
    public static final Item ASPEN_WOOD = registerBlock(ModBlocks.ASPEN_WOOD);
    public static final Item STRIPPED_ASPEN_LOG = registerBlock(ModBlocks.STRIPPED_ASPEN_LOG);
    public static final Item STRIPPED_ASPEN_WOOD = registerBlock(ModBlocks.STRIPPED_ASPEN_WOOD);
    public static final Item ASPEN_LEAVES = registerBlock(ModBlocks.ASPEN_LEAVES);
    public static final Item ASPEN_PLANKS = registerBlock(ModBlocks.ASPEN_PLANKS);
    public static final Item ASPEN_SLAB = registerBlock(ModBlocks.ASPEN_SLAB);
    public static final Item ASPEN_STAIRS = registerBlock(ModBlocks.ASPEN_STAIRS);
    public static final Item ASPEN_FENCE = registerBlock(ModBlocks.ASPEN_FENCE);
    public static final Item ASPEN_FENCE_GATE = registerBlock(ModBlocks.ASPEN_FENCE_GATE);
    public static final Item ASPEN_PRESSURE_PLATE = registerBlock(ModBlocks.ASPEN_PRESSURE_PLATE);
    public static final Item ASPEN_BUTTON = registerBlock(ModBlocks.ASPEN_BUTTON);
    public static final Item ASPEN_DOOR = registerBlock(ModBlocks.ASPEN_DOOR);
    public static final Item ASPEN_TRAPDOOR = registerBlock(ModBlocks.ASPEN_TRAPDOOR);
    public static final Item ASPEN_SHELF = registerBlock(ModBlocks.ASPEN_SHELF);
    public static final Item ASPEN_SIGN = register(ModBlocks.ASPEN_SIGN, signItem(ModBlocks.ASPEN_SIGN, ModBlocks.ASPEN_WALL_SIGN), new Item.Properties().stacksTo(16));
    public static final Item ASPEN_HANGING_SIGN = register(ModBlocks.ASPEN_HANGING_SIGN, hangingSignItem(ModBlocks.ASPEN_HANGING_SIGN, ModBlocks.ASPEN_WALL_HANGING_SIGN), new Item.Properties().stacksTo(16));
    public static final Item ASPEN_BOAT = register("aspen_boat", boatItem(ModEntityTypes.ASPEN_BOAT), new Item.Properties().stacksTo(1));
    public static final Item ASPEN_CHEST_BOAT = register("aspen_chest_boat", boatItem(ModEntityTypes.ASPEN_CHEST_BOAT), new Item.Properties().stacksTo(1));

    public static final Item BEECH_LOG = registerBlock(ModBlocks.BEECH_LOG);
    public static final Item BEECH_WOOD = registerBlock(ModBlocks.BEECH_WOOD);
    public static final Item STRIPPED_BEECH_LOG = registerBlock(ModBlocks.STRIPPED_BEECH_LOG);
    public static final Item STRIPPED_BEECH_WOOD = registerBlock(ModBlocks.STRIPPED_BEECH_WOOD);
    public static final Item BEECH_LEAVES = registerBlock(ModBlocks.BEECH_LEAVES);
    public static final Item BEECH_PLANKS = registerBlock(ModBlocks.BEECH_PLANKS);
    public static final Item BEECH_SLAB = registerBlock(ModBlocks.BEECH_SLAB);
    public static final Item BEECH_STAIRS = registerBlock(ModBlocks.BEECH_STAIRS);
    public static final Item BEECH_FENCE = registerBlock(ModBlocks.BEECH_FENCE);
    public static final Item BEECH_FENCE_GATE = registerBlock(ModBlocks.BEECH_FENCE_GATE);
    public static final Item BEECH_PRESSURE_PLATE = registerBlock(ModBlocks.BEECH_PRESSURE_PLATE);
    public static final Item BEECH_BUTTON = registerBlock(ModBlocks.BEECH_BUTTON);
    public static final Item BEECH_DOOR = registerBlock(ModBlocks.BEECH_DOOR);
    public static final Item BEECH_TRAPDOOR = registerBlock(ModBlocks.BEECH_TRAPDOOR);
    public static final Item BEECH_SHELF = registerBlock(ModBlocks.BEECH_SHELF);
    public static final Item BEECH_SIGN = register(ModBlocks.BEECH_SIGN, signItem(ModBlocks.BEECH_SIGN, ModBlocks.BEECH_WALL_SIGN), new Item.Properties().stacksTo(16));
    public static final Item BEECH_HANGING_SIGN = register(ModBlocks.BEECH_HANGING_SIGN, hangingSignItem(ModBlocks.BEECH_HANGING_SIGN, ModBlocks.BEECH_WALL_HANGING_SIGN), new Item.Properties().stacksTo(16));
    public static final Item BEECH_BOAT = register("beech_boat", boatItem(ModEntityTypes.BEECH_BOAT), new Item.Properties().stacksTo(1));
    public static final Item BEECH_CHEST_BOAT = register("beech_chest_boat", boatItem(ModEntityTypes.BEECH_CHEST_BOAT), new Item.Properties().stacksTo(1));

    public static final Item MAPLE_LOG = registerBlock(ModBlocks.MAPLE_LOG);
    public static final Item MAPLE_WOOD = registerBlock(ModBlocks.MAPLE_WOOD);
    public static final Item STRIPPED_MAPLE_LOG = registerBlock(ModBlocks.STRIPPED_MAPLE_LOG);
    public static final Item STRIPPED_MAPLE_WOOD = registerBlock(ModBlocks.STRIPPED_MAPLE_WOOD);
    public static final Item RED_MAPLE_LEAVES = registerBlock(ModBlocks.RED_MAPLE_LEAVES);
    public static final Item ORANGE_MAPLE_LEAVES = registerBlock(ModBlocks.ORANGE_MAPLE_LEAVES);
    public static final Item YELLOW_MAPLE_LEAVES = registerBlock(ModBlocks.YELLOW_MAPLE_LEAVES);
    public static final Item MAPLE_PLANKS = registerBlock(ModBlocks.MAPLE_PLANKS);
    public static final Item MAPLE_SLAB = registerBlock(ModBlocks.MAPLE_SLAB);
    public static final Item MAPLE_STAIRS = registerBlock(ModBlocks.MAPLE_STAIRS);
    public static final Item MAPLE_FENCE = registerBlock(ModBlocks.MAPLE_FENCE);
    public static final Item MAPLE_FENCE_GATE = registerBlock(ModBlocks.MAPLE_FENCE_GATE);
    public static final Item MAPLE_PRESSURE_PLATE = registerBlock(ModBlocks.MAPLE_PRESSURE_PLATE);
    public static final Item MAPLE_BUTTON = registerBlock(ModBlocks.MAPLE_BUTTON);
    public static final Item MAPLE_DOOR = registerBlock(ModBlocks.MAPLE_DOOR);
    public static final Item MAPLE_TRAPDOOR = registerBlock(ModBlocks.MAPLE_TRAPDOOR);
    public static final Item MAPLE_SHELF = registerBlock(ModBlocks.MAPLE_SHELF);
    public static final Item MAPLE_SIGN = register(ModBlocks.MAPLE_SIGN, signItem(ModBlocks.MAPLE_SIGN, ModBlocks.MAPLE_WALL_SIGN), new Item.Properties().stacksTo(16));
    public static final Item MAPLE_HANGING_SIGN = register(ModBlocks.MAPLE_HANGING_SIGN, hangingSignItem(ModBlocks.MAPLE_HANGING_SIGN, ModBlocks.MAPLE_WALL_HANGING_SIGN), new Item.Properties().stacksTo(16));
    public static final Item MAPLE_BOAT = register("maple_boat", boatItem(ModEntityTypes.MAPLE_BOAT), new Item.Properties().stacksTo(1));
    public static final Item MAPLE_CHEST_BOAT = register("maple_chest_boat", boatItem(ModEntityTypes.MAPLE_CHEST_BOAT), new Item.Properties().stacksTo(1));

    public static final Item REDWOOD_LOG = registerBlock(ModBlocks.REDWOOD_LOG);
    public static final Item REDWOOD_WOOD = registerBlock(ModBlocks.REDWOOD_WOOD);
    public static final Item STRIPPED_REDWOOD_LOG = registerBlock(ModBlocks.STRIPPED_REDWOOD_LOG);
    public static final Item STRIPPED_REDWOOD_WOOD = registerBlock(ModBlocks.STRIPPED_REDWOOD_WOOD);
    public static final Item REDWOOD_LEAVES = registerBlock(ModBlocks.REDWOOD_LEAVES);
    public static final Item REDWOOD_PLANKS = registerBlock(ModBlocks.REDWOOD_PLANKS);
    public static final Item REDWOOD_SLAB = registerBlock(ModBlocks.REDWOOD_SLAB);
    public static final Item REDWOOD_STAIRS = registerBlock(ModBlocks.REDWOOD_STAIRS);
    public static final Item REDWOOD_FENCE = registerBlock(ModBlocks.REDWOOD_FENCE);
    public static final Item REDWOOD_FENCE_GATE = registerBlock(ModBlocks.REDWOOD_FENCE_GATE);
    public static final Item REDWOOD_PRESSURE_PLATE = registerBlock(ModBlocks.REDWOOD_PRESSURE_PLATE);
    public static final Item REDWOOD_BUTTON = registerBlock(ModBlocks.REDWOOD_BUTTON);
    public static final Item REDWOOD_DOOR = registerBlock(ModBlocks.REDWOOD_DOOR);
    public static final Item REDWOOD_TRAPDOOR = registerBlock(ModBlocks.REDWOOD_TRAPDOOR);
    public static final Item REDWOOD_SHELF = registerBlock(ModBlocks.REDWOOD_SHELF);
    public static final Item REDWOOD_SIGN = register(ModBlocks.REDWOOD_SIGN, signItem(ModBlocks.REDWOOD_SIGN, ModBlocks.REDWOOD_WALL_SIGN), new Item.Properties().stacksTo(16));
    public static final Item REDWOOD_HANGING_SIGN = register(ModBlocks.REDWOOD_HANGING_SIGN, hangingSignItem(ModBlocks.REDWOOD_HANGING_SIGN, ModBlocks.REDWOOD_WALL_HANGING_SIGN), new Item.Properties().stacksTo(16));
    public static final Item REDWOOD_BOAT = register("redwood_boat", boatItem(ModEntityTypes.REDWOOD_BOAT), new Item.Properties().stacksTo(1));
    public static final Item REDWOOD_CHEST_BOAT = register("redwood_chest_boat", boatItem(ModEntityTypes.REDWOOD_CHEST_BOAT), new Item.Properties().stacksTo(1));

    public static final Item DEAD_LOG = registerBlock(ModBlocks.DEAD_LOG);
    public static final Item DEAD_WOOD = registerBlock(ModBlocks.DEAD_WOOD);
    public static final Item STRIPPED_DEAD_LOG = registerBlock(ModBlocks.STRIPPED_DEAD_LOG);
    public static final Item STRIPPED_DEAD_WOOD = registerBlock(ModBlocks.STRIPPED_DEAD_WOOD);
    public static final Item DEAD_LEAVES = registerBlock(ModBlocks.DEAD_LEAVES);
    public static final Item DEAD_WOOD_PLANKS = registerBlock(ModBlocks.DEAD_WOOD_PLANKS);
    public static final Item DEAD_WOOD_SLAB = registerBlock(ModBlocks.DEAD_WOOD_SLAB);
    public static final Item DEAD_WOOD_STAIRS = registerBlock(ModBlocks.DEAD_WOOD_STAIRS);
    public static final Item DEAD_WOOD_FENCE = registerBlock(ModBlocks.DEAD_WOOD_FENCE);
    public static final Item DEAD_WOOD_FENCE_GATE = registerBlock(ModBlocks.DEAD_WOOD_FENCE_GATE);
    public static final Item DEAD_WOOD_PRESSURE_PLATE = registerBlock(ModBlocks.DEAD_WOOD_PRESSURE_PLATE);
    public static final Item DEAD_WOOD_BUTTON = registerBlock(ModBlocks.DEAD_WOOD_BUTTON);
    public static final Item DEAD_WOOD_DOOR = registerBlock(ModBlocks.DEAD_WOOD_DOOR);
    public static final Item DEAD_WOOD_TRAPDOOR = registerBlock(ModBlocks.DEAD_WOOD_TRAPDOOR);
    public static final Item DEAD_WOOD_SHELF = registerBlock(ModBlocks.DEAD_WOOD_SHELF);
    public static final Item DEAD_WOOD_SIGN = register(ModBlocks.DEAD_WOOD_SIGN, signItem(ModBlocks.DEAD_WOOD_SIGN, ModBlocks.DEAD_WOOD_WALL_SIGN), new Item.Properties().stacksTo(16));
    public static final Item DEAD_WOOD_HANGING_SIGN = register(ModBlocks.DEAD_WOOD_HANGING_SIGN, hangingSignItem(ModBlocks.DEAD_WOOD_HANGING_SIGN, ModBlocks.DEAD_WOOD_WALL_HANGING_SIGN), new Item.Properties().stacksTo(16));
    public static final Item DEAD_WOOD_BOAT = register("dead_wood_boat", boatItem(ModEntityTypes.DEAD_WOOD_BOAT), new Item.Properties().stacksTo(1));
    public static final Item DEAD_WOOD_CHEST_BOAT = register("dead_wood_chest_boat", boatItem(ModEntityTypes.DEAD_WOOD_CHEST_BOAT), new Item.Properties().stacksTo(1));

    public static final Item BURNED_STEM = registerBlock(ModBlocks.BURNED_STEM);
    public static final Item BURNED_HYPHAE = registerBlock(ModBlocks.BURNED_HYPHAE);

    public static final Item ASH_LAYER = registerBlock(ModBlocks.ASH_LAYER);
    public static final Item ASH_BLOCK = registerBlock(ModBlocks.ASH_BLOCK);
    public static final Item PACKED_ASH = registerBlock(ModBlocks.PACKED_ASH);
    public static final Item ASH_BRICKS = registerBlock(ModBlocks.ASH_BRICKS);
    public static final Item ASH_BRICK_SLAB = registerBlock(ModBlocks.ASH_BRICK_SLAB);
    public static final Item ASH_BRICK_STAIRS = registerBlock(ModBlocks.ASH_BRICK_STAIRS);

    public static final Item ASHVINE = registerBlock(ModBlocks.ASHVINE);
    public static final Item ASHCREEP = registerBlock(ModBlocks.ASHCREEP);
    public static final Item EMBERGRASS = registerBlock(ModBlocks.EMBERGRASS);
    public static final Item EMBERWEED = registerBlock(ModBlocks.EMBERWEED);
    public static final Item EMBERS = registerBlock(ModBlocks.EMBERS);

    // Soils

    public static final Item MOSSY_DIRT = registerBlock(ModBlocks.MOSSY_DIRT);
    public static final Item PEAT = registerBlock(ModBlocks.PEAT);
    public static final Item GRASSY_PEAT = registerBlock(ModBlocks.GRASSY_PEAT);
    public static final Item MOSSY_PEAT = registerBlock(ModBlocks.MOSSY_PEAT);
    public static final Item COARSE_PEAT = registerBlock(ModBlocks.COARSE_PEAT);
    public static final Item DRIED_PEAT = registerBlock(ModBlocks.DRIED_PEAT);
    public static final Item GRASSY_LIMESTONE = registerBlock(ModBlocks.GRASSY_LIMESTONE);

    // Stones

    public static final Item LIMESTONE = registerBlock(ModBlocks.LIMESTONE);
    public static final Item LIMESTONE_STAIRS = registerBlock(ModBlocks.LIMESTONE_STAIRS);
    public static final Item LIMESTONE_SLAB = registerBlock(ModBlocks.LIMESTONE_SLAB);
    public static final Item LIMESTONE_WALL = registerBlock(ModBlocks.LIMESTONE_WALL);
    public static final Item POLISHED_LIMESTONE = registerBlock(ModBlocks.POLISHED_LIMESTONE);
    public static final Item POLISHED_LIMESTONE_STAIRS = registerBlock(ModBlocks.POLISHED_LIMESTONE_STAIRS);
    public static final Item POLISHED_LIMESTONE_SLAB = registerBlock(ModBlocks.POLISHED_LIMESTONE_SLAB);
    public static final Item POLISHED_LIMESTONE_WALL = registerBlock(ModBlocks.POLISHED_LIMESTONE_WALL);
    public static final Item LIMESTONE_BRICKS = registerBlock(ModBlocks.LIMESTONE_BRICKS);
    public static final Item LIMESTONE_BRICK_STAIRS = registerBlock(ModBlocks.LIMESTONE_BRICK_STAIRS);
    public static final Item LIMESTONE_BRICK_SLAB = registerBlock(ModBlocks.LIMESTONE_BRICK_SLAB);
    public static final Item LIMESTONE_BRICK_WALL = registerBlock(ModBlocks.LIMESTONE_BRICK_WALL);
    public static final Item LIMESTONE_TILES = registerBlock(ModBlocks.LIMESTONE_TILES);
    public static final Item LIMESTONE_TILE_STAIRS = registerBlock(ModBlocks.LIMESTONE_TILE_STAIRS);
    public static final Item LIMESTONE_TILE_SLAB = registerBlock(ModBlocks.LIMESTONE_TILE_SLAB);
    public static final Item LIMESTONE_TILE_WALL = registerBlock(ModBlocks.LIMESTONE_TILE_WALL);
    public static final Item CRACKED_LIMESTONE_BRICKS = registerBlock(ModBlocks.CRACKED_LIMESTONE_BRICKS);
    public static final Item CRACKED_LIMESTONE_BRICK_STAIRS = registerBlock(ModBlocks.CRACKED_LIMESTONE_BRICK_STAIRS);
    public static final Item CRACKED_LIMESTONE_BRICK_SLAB = registerBlock(ModBlocks.CRACKED_LIMESTONE_BRICK_SLAB);
    public static final Item CRACKED_LIMESTONE_BRICK_WALL = registerBlock(ModBlocks.CRACKED_LIMESTONE_BRICK_WALL);
    public static final Item CHISELED_LIMESTONE = registerBlock(ModBlocks.CHISELED_LIMESTONE);
    public static final Item POINTED_LIMESTONE = registerBlock(ModBlocks.POINTED_LIMESTONE);
    public static final Item LIMESTONE_BRAZIER = registerBlock(ModBlocks.LIMESTONE_BRAZIER);
    public static final Item SOUL_LIMESTONE_BRAZIER = registerBlock(ModBlocks.SOUL_LIMESTONE_BRAZIER);

    public static final Item LIMESTONE_COAL_ORE = registerBlock(ModBlocks.LIMESTONE_COAL_ORE);
    public static final Item LIMESTONE_COPPER_ORE = registerBlock(ModBlocks.LIMESTONE_COPPER_ORE);
    public static final Item LIMESTONE_IRON_ORE = registerBlock(ModBlocks.LIMESTONE_IRON_ORE);
    public static final Item LIMESTONE_GOLD_ORE = registerBlock(ModBlocks.LIMESTONE_GOLD_ORE);
    public static final Item LIMESTONE_DIAMOND_ORE = registerBlock(ModBlocks.LIMESTONE_DIAMOND_ORE);
    public static final Item LIMESTONE_LAPIS_ORE = registerBlock(ModBlocks.LIMESTONE_LAPIS_ORE);
    public static final Item LIMESTONE_EMERALD_ORE = registerBlock(ModBlocks.LIMESTONE_EMERALD_ORE);
    public static final Item LIMESTONE_REDSTONE_ORE = registerBlock(ModBlocks.LIMESTONE_REDSTONE_ORE);

    // Plants

    public static final Item CLOVERS = registerBlock(ModBlocks.CLOVERS);
    public static final Item GRASS_SPROUTS = registerBlock(ModBlocks.GRASS_SPROUTS);
    public static final Item BARLEY = registerBlock(ModBlocks.BARLEY);
    public static final Item TALL_BARLEY = register(ModBlocks.TALL_BARLEY, doubleBlockItem(ModBlocks.TALL_BARLEY));
    public static final Item CATTAIL = registerBlock(ModBlocks.CATTAIL);
    public static final Item TALL_CATTAIL = register(ModBlocks.TALL_CATTAIL, doubleBlockItem(ModBlocks.TALL_CATTAIL));
    public static final Item LAVENDER = registerBlock(ModBlocks.LAVENDER);
    public static final Item TALL_LAVENDER = register(ModBlocks.TALL_LAVENDER, doubleBlockItem(ModBlocks.TALL_LAVENDER));
    public static final Item CAVE_GRASS = registerBlock(ModBlocks.CAVE_GRASS);
    public static final Item DRIPMOSS = registerBlock(ModBlocks.DRIPMOSS);
    public static final Item PATCHMOSS = registerBlock(ModBlocks.PATCHMOSS);
    public static final Item GLOBE_THISTLE = register(ModBlocks.GLOBE_THISTLE, doubleBlockItem(ModBlocks.GLOBE_THISTLE));
    public static final Item SHELF_FUNGUS = register(ModBlocks.SHELF_FUNGUS, diagonallyAttachableBlockItem(ModBlocks.SHELF_FUNGUS));


    // INITIALISATION
    // =============================================

    public static void init() {
        // Register creative inventory tab setup events
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(ModItems::buildingBlocksTab);
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(ModItems::naturalBlocksTab);
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(ModItems::functionalBlocksTab);
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(ModItems::toolsAndUtilitiesTab);
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(ModItems::foodAndDrinksTab);
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(ModItems::ingredientsTab);

        FuelValueEvents.BUILD.register((builder, context) -> setupFuel(builder, context.baseSmeltTime()));
    }

    public static void setupFuel(FuelValues.Builder builder, int baseUnit) {
    }



    // =====================================================================================================
    //   CONFIGURATION METHODS
    // =====================================================================================================


    // TABS
    // =============================================

    // Try to follow Minecraft's order in which items are put into tabs. When a block or a set of blocks has
    // vanilla variants and SMPTG variants, make sure the vanilla variants appear first.

    public static void buildingBlocksTab(FabricCreativeModeTabOutput out) {
        out.insertAfter(
                Items.PALE_OAK_BUTTON,
                ASPEN_LOG,
                ASPEN_WOOD,
                STRIPPED_ASPEN_LOG,
                STRIPPED_ASPEN_WOOD,
                ASPEN_PLANKS,
                ASPEN_STAIRS,
                ASPEN_SLAB,
                ASPEN_FENCE,
                ASPEN_FENCE_GATE,
                ASPEN_DOOR,
                ASPEN_TRAPDOOR,
                ASPEN_PRESSURE_PLATE,
                ASPEN_BUTTON,

                BEECH_LOG,
                BEECH_WOOD,
                STRIPPED_BEECH_LOG,
                STRIPPED_BEECH_WOOD,
                BEECH_PLANKS,
                BEECH_STAIRS,
                BEECH_SLAB,
                BEECH_FENCE,
                BEECH_FENCE_GATE,
                BEECH_DOOR,
                BEECH_TRAPDOOR,
                BEECH_PRESSURE_PLATE,
                BEECH_BUTTON,

                MAPLE_LOG,
                MAPLE_WOOD,
                STRIPPED_MAPLE_LOG,
                STRIPPED_MAPLE_WOOD,
                MAPLE_PLANKS,
                MAPLE_STAIRS,
                MAPLE_SLAB,
                MAPLE_FENCE,
                MAPLE_FENCE_GATE,
                MAPLE_DOOR,
                MAPLE_TRAPDOOR,
                MAPLE_PRESSURE_PLATE,
                MAPLE_BUTTON,

                REDWOOD_LOG,
                REDWOOD_WOOD,
                STRIPPED_REDWOOD_LOG,
                STRIPPED_REDWOOD_WOOD,
                REDWOOD_PLANKS,
                REDWOOD_STAIRS,
                REDWOOD_SLAB,
                REDWOOD_FENCE,
                REDWOOD_FENCE_GATE,
                REDWOOD_DOOR,
                REDWOOD_TRAPDOOR,
                REDWOOD_PRESSURE_PLATE,
                REDWOOD_BUTTON,

                DEAD_LOG,
                DEAD_WOOD,
                STRIPPED_DEAD_LOG,
                STRIPPED_DEAD_WOOD,
                DEAD_WOOD_PLANKS,
                DEAD_WOOD_STAIRS,
                DEAD_WOOD_SLAB,
                DEAD_WOOD_FENCE,
                DEAD_WOOD_FENCE_GATE,
                DEAD_WOOD_DOOR,
                DEAD_WOOD_TRAPDOOR,
                DEAD_WOOD_PRESSURE_PLATE,
                DEAD_WOOD_BUTTON
        );

        out.insertAfter(
                Items.WARPED_BUTTON,
                BURNED_STEM,
                BURNED_HYPHAE
        );

        out.insertBefore(
                Items.DEEPSLATE,
                LIMESTONE,
                LIMESTONE_STAIRS,
                LIMESTONE_SLAB,
                LIMESTONE_WALL,

                CHISELED_LIMESTONE,
                POLISHED_LIMESTONE,
                POLISHED_LIMESTONE_STAIRS,
                POLISHED_LIMESTONE_SLAB,
                POLISHED_LIMESTONE_WALL,

                LIMESTONE_BRICKS,
                LIMESTONE_BRICK_STAIRS,
                LIMESTONE_BRICK_SLAB,
                LIMESTONE_BRICK_WALL,

                CRACKED_LIMESTONE_BRICKS,
                CRACKED_LIMESTONE_BRICK_STAIRS,
                CRACKED_LIMESTONE_BRICK_SLAB,
                CRACKED_LIMESTONE_BRICK_WALL,

                LIMESTONE_TILES,
                LIMESTONE_TILE_STAIRS,
                LIMESTONE_TILE_SLAB,
                LIMESTONE_TILE_WALL
        );

        out.insertBefore(
                Items.SEA_LANTERN,
                PACKED_ASH,
                ASH_BRICKS,
                ASH_BRICK_SLAB,
                ASH_BRICK_STAIRS
        );

        out.insertBefore(
                Items.PACKED_MUD,
                DRIED_PEAT
        );
    }

    public static void naturalBlocksTab(FabricCreativeModeTabOutput out) {
        out.insertAfter(
                Items.GRASS_BLOCK,
                MOSSY_DIRT
        );
        out.insertAfter(
                Items.MUD,
                GRASSY_PEAT,
                MOSSY_PEAT,
                PEAT,
                COARSE_PEAT
        );
        out.insertBefore(
                Items.DEEPSLATE,
                LIMESTONE,
                GRASSY_LIMESTONE
        );
        out.insertBefore(
                Items.DEEPSLATE_COAL_ORE,
                LIMESTONE_COAL_ORE
        );
        out.insertBefore(
                Items.DEEPSLATE_COPPER_ORE,
                LIMESTONE_COPPER_ORE
        );
        out.insertBefore(
                Items.DEEPSLATE_IRON_ORE,
                LIMESTONE_IRON_ORE
        );
        out.insertBefore(
                Items.DEEPSLATE_GOLD_ORE,
                LIMESTONE_GOLD_ORE
        );
        out.insertBefore(
                Items.DEEPSLATE_DIAMOND_ORE,
                LIMESTONE_DIAMOND_ORE
        );
        out.insertBefore(
                Items.DEEPSLATE_LAPIS_ORE,
                LIMESTONE_LAPIS_ORE
        );
        out.insertBefore(
                Items.DEEPSLATE_REDSTONE_ORE,
                LIMESTONE_REDSTONE_ORE
        );
        out.insertBefore(
                Items.DEEPSLATE_EMERALD_ORE,
                LIMESTONE_EMERALD_ORE
        );
        out.insertAfter(
                Items.POINTED_DRIPSTONE,
                POINTED_LIMESTONE
        );
        out.insertAfter(
                Items.PALE_OAK_LOG,
                ASPEN_LOG,
                BEECH_LOG,
                MAPLE_LOG,
                REDWOOD_LOG,
                DEAD_LOG,
                BURNED_STEM
        );
        out.insertBefore(
                Items.PRISMARINE,

                ASH_BLOCK,
                ASH_LAYER,
                PACKED_ASH
        );

        out.insertAfter(
                Items.PALE_OAK_LEAVES,
                ASPEN_LEAVES,
                BEECH_LEAVES,
                RED_MAPLE_LEAVES,
                ORANGE_MAPLE_LEAVES,
                YELLOW_MAPLE_LEAVES,
                REDWOOD_LEAVES,
                DEAD_LEAVES
        );

        out.insertBefore(
                Items.SHORT_GRASS,
                GRASS_SPROUTS
        );

        out.insertAfter(
                Items.BUSH,
                CLOVERS,
                BARLEY,
                CATTAIL,
                LAVENDER,
                CAVE_GRASS,
                DRIPMOSS
        );

        out.insertAfter(
                Items.TALL_GRASS,
                TALL_BARLEY,
                TALL_CATTAIL,
                TALL_LAVENDER,
                GLOBE_THISTLE
        );

        out.insertAfter(
                Items.NETHER_SPROUTS,
                ASHCREEP,
                EMBERGRASS,
                EMBERWEED,
                EMBERS
        );

        out.insertAfter(
                Items.TWISTING_VINES,
                ASHVINE
        );

        out.insertAfter(
                Items.RED_MUSHROOM,
                SHELF_FUNGUS
        );

        out.insertAfter(
                Items.MOSS_CARPET,
                PATCHMOSS
        );
    }

    public static void functionalBlocksTab(FabricCreativeModeTabOutput out) {
        out.insertAfter(
                Items.PALE_OAK_SHELF,
                ASPEN_SHELF,
                BEECH_SHELF,
                MAPLE_SHELF,
                REDWOOD_SHELF,
                DEAD_WOOD_SHELF
        );
        out.insertAfter(
                Items.PALE_OAK_HANGING_SIGN,
                ASPEN_SIGN,
                ASPEN_HANGING_SIGN,
                BEECH_SIGN,
                BEECH_HANGING_SIGN,
                MAPLE_SIGN,
                MAPLE_HANGING_SIGN,
                REDWOOD_SIGN,
                REDWOOD_HANGING_SIGN,
                DEAD_WOOD_SIGN,
                DEAD_WOOD_HANGING_SIGN
        );
        out.insertAfter(
                Items.SOUL_CAMPFIRE,
                LIMESTONE_BRAZIER,
                SOUL_LIMESTONE_BRAZIER
        );
    }

    public static void toolsAndUtilitiesTab(FabricCreativeModeTabOutput out) {
        out.insertAfter(
                Items.PALE_OAK_CHEST_BOAT,
                ASPEN_BOAT,
                ASPEN_CHEST_BOAT,
                BEECH_BOAT,
                BEECH_CHEST_BOAT,
                MAPLE_BOAT,
                MAPLE_CHEST_BOAT,
                REDWOOD_BOAT,
                REDWOOD_CHEST_BOAT,
                DEAD_WOOD_BOAT,
                DEAD_WOOD_CHEST_BOAT
        );
    }

    public static void foodAndDrinksTab(FabricCreativeModeTabOutput out) {
    }

    public static void ingredientsTab(FabricCreativeModeTabOutput out) {
    }


    // TRANSLATION
    // =============================================

    public static void translate(Translator<Item> translator) {
        // Block items receive the translation ID of the block, so they
        // don't need to be set here.

        translator.name(ASPEN_BOAT, "Aspen Boat");
        translator.name(ASPEN_CHEST_BOAT, "Aspen Chest Boat");

        translator.name(BEECH_BOAT, "Beech Boat");
        translator.name(BEECH_CHEST_BOAT, "Beech Chest Boat");

        translator.name(MAPLE_BOAT, "Maple Boat");
        translator.name(MAPLE_CHEST_BOAT, "Maple Chest Boat");

        translator.name(REDWOOD_BOAT, "Redwood Boat");
        translator.name(REDWOOD_CHEST_BOAT, "Redwood Chest Boat");

        translator.name(DEAD_WOOD_BOAT, "Dead Wood Boat");
        translator.name(DEAD_WOOD_CHEST_BOAT, "Dead Wood Chest Boat");
    }



    // ========================================================================================
    // Thought you were done? HAH NO
    // All item data now resides in the data source set, under ModItemData.
    // Be sure to fill in item data there as well to properly generate resources.
    //
    // However, if you added a block item you're all set by now so don't bother.
    // ========================================================================================




    // =====================================================================================================
    //   FACTORY METHODS
    // =====================================================================================================


    // FACTORIES
    // =============================================

    private static ItemFactory item() {
        return Item::new;
    }

    private static ItemFactory boatItem(EntityType<? extends AbstractBoat> type) {
        return props -> new BoatItem(type, props);
    }

    private static ItemFactory blockItem(Block block) {
        return props -> new BlockItem(block, props.overrideDescription(block.getDescriptionId()));
    }

    private static ItemFactory doubleBlockItem(Block block) {
        return props -> new DoubleHighBlockItem(block, props.overrideDescription(block.getDescriptionId()));
    }

    private static ItemFactory diagonallyAttachableBlockItem(Block block) {
        return props -> new DiagonallyAttachableBlockItem(block, props.overrideDescription(block.getDescriptionId()));
    }

    private static ItemFactory signItem(Block block, Block wallBlock) {
        return props -> new SignItem(block, wallBlock, props.overrideDescription(block.getDescriptionId()));
    }

    private static ItemFactory hangingSignItem(Block block, Block wallBlock) {
        return props -> new HangingSignItem(block, wallBlock, props.overrideDescription(block.getDescriptionId()));
    }


    // REGISTRY
    // =============================================

    private static Item registerBlock(Block block) {
        return register(block, blockItem(block));
    }

    private static Item registerBlock(Block block, Item.Properties properties) {
        return register(block, blockItem(block), properties);
    }

    private static Item register(Block copyName, ItemFactory factory) {
        return register(copyName, factory, new Item.Properties());
    }

    private static Item register(Block copyName, ItemFactory factory, Item.Properties properties) {
        var blockKey = ModBlocks.REGISTRY.keyOf(copyName);
        var itemKey = ResourceKey.create(Registries.ITEM, blockKey.identifier());

        return REGISTRY.register(itemKey, factory, properties);
    }

    private static Item register(String name, ItemFactory factory) {
        return register(name, factory, new Item.Properties());
    }

    private static Item register(String name, ItemFactory factory, Item.Properties properties) {
        return REGISTRY.register(name, factory, properties);
    }
}
