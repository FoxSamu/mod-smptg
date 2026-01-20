package net.foxboi.salted.common.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.entity.ModEntityTypes;
import net.foxboi.salted.data.lang.Translator;
import net.foxboi.salted.data.model.ItemModels;
import net.foxboi.salted.data.model.ItemModelsImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;

/**
 * Salted's collection of {@link Item}s. Each {@link Item} instance is provided here in a static field.
 */
@SuppressWarnings("unused")
public record ModItems() {
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

    // Soils

    public static final Item MOSSY_DIRT = registerBlock(ModBlocks.MOSSY_DIRT);

    // Plants

    public static final Item CLOVERS = registerBlock(ModBlocks.CLOVERS);
    public static final Item GRASS_SPROUTS = registerBlock(ModBlocks.GRASS_SPROUTS);
    public static final Item BARLEY = registerBlock(ModBlocks.BARLEY);
    public static final Item TALL_BARLEY = registerBlock(ModBlocks.TALL_BARLEY);
    public static final Item CATTAIL = registerBlock(ModBlocks.CATTAIL);
    public static final Item TALL_CATTAIL = registerBlock(ModBlocks.TALL_CATTAIL);
    public static final Item LAVENDER = registerBlock(ModBlocks.LAVENDER);
    public static final Item TALL_LAVENDER = registerBlock(ModBlocks.TALL_LAVENDER);
    public static final Item CAVE_GRASS = registerBlock(ModBlocks.CAVE_GRASS);
    public static final Item DRIPMOSS = registerBlock(ModBlocks.DRIPMOSS);
    public static final Item PATCHMOSS = registerBlock(ModBlocks.PATCHMOSS);
    public static final Item GLOBE_THISTLE = registerBlock(ModBlocks.GLOBE_THISTLE);
    public static final Item SHELF_FUNGUS = register(ModBlocks.SHELF_FUNGUS, diagonallyAttachableBlockItem(ModBlocks.SHELF_FUNGUS));

    // Salt

    public static final Item SALT_BLOCK = registerBlock(ModBlocks.SALT_BLOCK);
    public static final Item SALT_CRUST = registerBlock(ModBlocks.SALT_CRUST);

    public static final Item ROCKSALT = registerBlock(ModBlocks.ROCKSALT);
    public static final Item ROCKSALT_SLAB = registerBlock(ModBlocks.ROCKSALT_SLAB);
    public static final Item ROCKSALT_STAIRS = registerBlock(ModBlocks.ROCKSALT_STAIRS);
    public static final Item ROCKSALT_WALL = registerBlock(ModBlocks.ROCKSALT_WALL);

    public static final Item ROCKSALT_BRICKS = registerBlock(ModBlocks.ROCKSALT_BRICKS);
    public static final Item ROCKSALT_BRICK_SLAB = registerBlock(ModBlocks.ROCKSALT_BRICK_SLAB);
    public static final Item ROCKSALT_BRICK_STAIRS = registerBlock(ModBlocks.ROCKSALT_BRICK_STAIRS);
    public static final Item ROCKSALT_BRICK_WALL = registerBlock(ModBlocks.ROCKSALT_BRICK_WALL);

    public static final Item SALT_ORE = registerBlock(ModBlocks.SALT_ORE);
    public static final Item DEEPSLATE_SALT_ORE = registerBlock(ModBlocks.DEEPSLATE_SALT_ORE);
    public static final Item NETHER_SALT_ORE = registerBlock(ModBlocks.NETHER_SALT_ORE);

    public static final Item SALT = register("salt", saltItem());
    public static final Item PINCH_OF_SALT = register("pinch_of_salt", item());
    public static final Item ROCKSALT_CHUNK = register("rocksalt_chunk", item());

    public static final Item SALTED_BEEF = register("salted_beef", saltedItem(Items.BEEF, 1, 0.2f));
    public static final Item SALTED_PORKCHOP = register("salted_porkchop", saltedItem(Items.PORKCHOP, 1, 0.2f));
    public static final Item SALTED_MUTTON = register("salted_mutton", saltedItem(Items.MUTTON, 1, 0.2f));
    public static final Item SALTED_CHICKEN = register("salted_chicken", saltedItem(Items.CHICKEN, 1, 0.2f));

    public static final Item COOKED_SALTED_BEEF = register("cooked_salted_beef", saltedItem(Items.COOKED_BEEF, 2, 2.3f));
    public static final Item COOKED_SALTED_PORKCHOP = register("cooked_salted_porkchop", saltedItem(Items.COOKED_PORKCHOP, 2, 2.3f));
    public static final Item COOKED_SALTED_MUTTON = register("cooked_salted_mutton", saltedItem(Items.COOKED_MUTTON, 2, 2.0f));
    public static final Item COOKED_SALTED_CHICKEN = register("cooked_salted_chicken", saltedItem(Items.COOKED_CHICKEN, 2, 2.0f));


    // INITIALISATION
    // =============================================

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(ModItems::buildingBlocksTab);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(ModItems::naturalBlocksTab);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(ModItems::functionalBlocksTab);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(ModItems::toolsAndUtilitiesTab);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(ModItems::foodAndDrinksTab);
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(ModItems::ingredientsTab);
    }


    // TABS
    // =============================================

    public static void buildingBlocksTab(FabricItemGroupEntries entries) {
        entries.addAfter(
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

        entries.addAfter(
                Items.WARPED_BUTTON,

                BURNED_STEM,
                BURNED_HYPHAE
        );

        entries.addBefore(
                Items.SEA_LANTERN,
                ROCKSALT,
                ROCKSALT_STAIRS,
                ROCKSALT_SLAB,
                ROCKSALT_WALL,

                ROCKSALT_BRICKS,
                ROCKSALT_BRICK_STAIRS,
                ROCKSALT_BRICK_SLAB,
                ROCKSALT_BRICK_WALL,

                PACKED_ASH,
                ASH_BRICKS,
                ASH_BRICK_SLAB,
                ASH_BRICK_STAIRS
        );
    }

    public static void naturalBlocksTab(FabricItemGroupEntries entries) {
        entries.addAfter(
                Items.PODZOL,
                MOSSY_DIRT
        );
        entries.addAfter(
                Items.PALE_OAK_LOG,
                ASPEN_LOG,
                BEECH_LOG,
                MAPLE_LOG,
                REDWOOD_LOG,
                DEAD_LOG,
                BURNED_STEM
        );
        entries.addBefore(
                Items.PRISMARINE,
                SALT_BLOCK,
                SALT_CRUST,
                ROCKSALT,

                ASH_BLOCK,
                ASH_LAYER,
                PACKED_ASH
        );

        entries.addAfter(
                Items.PALE_OAK_LEAVES,
                ASPEN_LEAVES,
                BEECH_LEAVES,
                RED_MAPLE_LEAVES,
                ORANGE_MAPLE_LEAVES,
                YELLOW_MAPLE_LEAVES,
                REDWOOD_LEAVES,
                DEAD_LEAVES
        );
        entries.addAfter(
                Items.DEEPSLATE_DIAMOND_ORE,
                SALT_ORE,
                DEEPSLATE_SALT_ORE
        );
        entries.addAfter(
                Items.NETHER_QUARTZ_ORE,
                NETHER_SALT_ORE
        );

        entries.addBefore(
                Items.SHORT_GRASS,
                GRASS_SPROUTS
        );

        entries.addAfter(
                Items.BUSH,
                CLOVERS,
                BARLEY,
                CATTAIL,
                LAVENDER,
                CAVE_GRASS,
                DRIPMOSS
        );

        entries.addAfter(
                Items.TALL_GRASS,
                TALL_BARLEY,
                TALL_CATTAIL,
                TALL_LAVENDER,
                GLOBE_THISTLE
        );

        entries.addAfter(
                Items.NETHER_SPROUTS,
                ASHCREEP
        );

        entries.addAfter(
                Items.TWISTING_VINES,
                ASHVINE
        );

        entries.addAfter(
                Items.RED_MUSHROOM,
                SHELF_FUNGUS
        );

        entries.addAfter(
                Items.MOSS_CARPET,
                PATCHMOSS
        );
    }

    public static void functionalBlocksTab(FabricItemGroupEntries entries) {
        entries.addAfter(
                Items.PALE_OAK_SHELF,
                ASPEN_SHELF,
                BEECH_SHELF,
                MAPLE_SHELF,
                REDWOOD_SHELF,
                DEAD_WOOD_SHELF
        );
        entries.addAfter(
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
    }

    public static void toolsAndUtilitiesTab(FabricItemGroupEntries entries) {
        entries.addAfter(
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

    public static void foodAndDrinksTab(FabricItemGroupEntries entries) {
        entries.addAfter(
                Items.COOKED_BEEF,
                SALTED_BEEF,
                COOKED_SALTED_BEEF
        );
        entries.addAfter(
                Items.COOKED_PORKCHOP,
                SALTED_PORKCHOP,
                COOKED_SALTED_PORKCHOP
        );
        entries.addAfter(
                Items.COOKED_MUTTON,
                SALTED_MUTTON,
                COOKED_SALTED_MUTTON
        );
        entries.addAfter(
                Items.COOKED_CHICKEN,
                SALTED_CHICKEN,
                COOKED_SALTED_CHICKEN
        );
    }

    public static void ingredientsTab(FabricItemGroupEntries entries) {
        entries.addAfter(
                Items.AMETHYST_SHARD,
                ROCKSALT_CHUNK
        );

        entries.addAfter(
                Items.BONE_MEAL,
                SALT,
                PINCH_OF_SALT
        );
    }


    // TRANSLATION
    // =============================================

    public static void translate(Translator<Item> translator) {
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

        translator.name(SALT, "Salt");
        translator.name(PINCH_OF_SALT, "Pinch of Salt");
        translator.name(ROCKSALT_CHUNK, "Rocksalt Chunk");

        translator.name(SALTED_BEEF, "Raw Salted Beef");
        translator.name(SALTED_PORKCHOP, "Raw Salted Porkchop");
        translator.name(SALTED_MUTTON, "Raw Salted Mutton");
        translator.name(SALTED_CHICKEN, "Raw Salted Chicken");

        translator.name(COOKED_SALTED_BEEF, "Salted Steak");
        translator.name(COOKED_SALTED_PORKCHOP, "Cooked Salted Porkchop");
        translator.name(COOKED_SALTED_MUTTON, "Cooked Salted Mutton");
        translator.name(COOKED_SALTED_CHICKEN, "Cooked Salted Chicken");
    }


    // MODELS
    // =============================================

    public static void models(ItemModels models) {
        models.generated(ASPEN_BOAT);
        models.generated(BEECH_BOAT);
        models.generated(MAPLE_BOAT);
        models.generated(REDWOOD_BOAT);
        models.generated(DEAD_WOOD_BOAT);

        models.generated(ASPEN_CHEST_BOAT);
        models.generated(BEECH_CHEST_BOAT);
        models.generated(MAPLE_CHEST_BOAT);
        models.generated(REDWOOD_CHEST_BOAT);
        models.generated(DEAD_WOOD_CHEST_BOAT);

        models.generated(SALT);
        models.generated(PINCH_OF_SALT);
        models.generated(ROCKSALT_CHUNK);

        models.saltedItem(SALTED_BEEF, Items.BEEF);
        models.saltedItem(SALTED_PORKCHOP, Items.PORKCHOP);
        models.saltedItem(SALTED_MUTTON, Items.MUTTON);
        models.saltedItem(SALTED_CHICKEN, Items.CHICKEN);

        models.saltedItem(COOKED_SALTED_BEEF, Items.COOKED_BEEF);
        models.saltedItem(COOKED_SALTED_PORKCHOP, Items.COOKED_PORKCHOP);
        models.saltedItem(COOKED_SALTED_MUTTON, Items.COOKED_MUTTON);
        models.saltedItem(COOKED_SALTED_CHICKEN, Items.COOKED_CHICKEN);
    }


    // FACTORIES
    // =============================================

    private static ItemFactory item() {
        return Item::new;
    }

    private static ItemFactory saltItem() {
        return SaltItem::new;
    }

    private static ItemFactory saltedItem(Item baseItem, int plusNutrition, float plusSaturation) {
        var baseFood = baseItem.components().get(DataComponents.FOOD);
        var newFood = baseFood == null ? null : new FoodProperties(
                baseFood.nutrition() + plusNutrition,
                baseFood.saturation() + plusSaturation,
                baseFood.canAlwaysEat()
        );

        var baseConsumable = baseItem.components().get(DataComponents.CONSUMABLE);

        return props -> {
            if (newFood != null) {
                props.component(DataComponents.FOOD, newFood);
            }
            if (baseConsumable != null) {
                props.component(DataComponents.CONSUMABLE, baseConsumable);
            }
            return new SaltedItem(baseItem, props);
        };
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

    @SuppressWarnings("deprecation")
    private static Item register(Block copyName, ItemFactory factory, Item.Properties properties) {
        var blockKey = copyName.builtInRegistryHolder().key();
        var itemKey = ResourceKey.create(Registries.ITEM, blockKey.identifier());

        return register(itemKey, factory, properties);
    }

    private static Item register(String name, ItemFactory factory) {
        return register(name, factory, new Item.Properties());
    }

    private static Item register(String name, ItemFactory factory, Item.Properties properties) {
        var key = Smptg.key(Registries.ITEM, name);
        return register(key, factory, properties);
    }

    private static Item register(ResourceKey<Item> key, ItemFactory factory) {
        return register(key, factory, new Item.Properties());
    }

    private static Item register(ResourceKey<Item> key, ItemFactory factory, Item.Properties properties) {
        var item = factory.create(properties.setId(key));
        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }
}
