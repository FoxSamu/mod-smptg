package net.foxboi.salted.data.model;

import java.util.stream.IntStream;

import net.minecraft.resources.Identifier;

import net.foxboi.summon.api.model.Model;
import net.foxboi.summon.api.model.ModelTemplate;
import net.foxboi.summon.api.model.TextureKey;

public class ModelTemplates {
    // Blocks

    public static final ModelTemplate PARTICLE_ONLY = particleOnly();

    public static final ModelTemplate CUBE = block(
            "cube",
            TextureKeys.PARTICLE,
            TextureKeys.NORTH,
            TextureKeys.SOUTH,
            TextureKeys.EAST,
            TextureKeys.WEST,
            TextureKeys.UP,
            TextureKeys.DOWN
    );

    public static final ModelTemplate CUBE_DIRECTIONAL = block(
            "cube_directional",
            TextureKeys.PARTICLE,
            TextureKeys.NORTH,
            TextureKeys.SOUTH,
            TextureKeys.EAST,
            TextureKeys.WEST,
            TextureKeys.UP,
            TextureKeys.DOWN
    );

    public static final ModelTemplate CUBE_ALL = block(
            "cube_all",
            TextureKeys.ALL
    );

    public static final ModelTemplate CUBE_ALL_INNER_FACES = block(
            "cube_all_inner_faces",
            TextureKeys.ALL
    );

    public static final ModelTemplate CUBE_MIRRORED_ALL = block(
            "cube_mirrored_all", "_mirrored",
            TextureKeys.ALL
    );

    public static final ModelTemplate CUBE_NORTH_WEST_MIRRORED_ALL = block(
            "cube_north_west_mirrored_all", "_north_west_mirrored",
            TextureKeys.ALL
    );

    public static final ModelTemplate CUBE_COLUMN_UV_LOCKED_X = block(
            "cube_column_uv_locked_x", "_x",
            TextureKeys.END,
            TextureKeys.SIDE
    );

    public static final ModelTemplate CUBE_COLUMN_UV_LOCKED_Y = block(
            "cube_column_uv_locked_y", "_y",
            TextureKeys.END,
            TextureKeys.SIDE
    );

    public static final ModelTemplate CUBE_COLUMN_UV_LOCKED_Z = block(
            "cube_column_uv_locked_z", "_z",
            TextureKeys.END,
            TextureKeys.SIDE
    );

    public static final ModelTemplate CUBE_COLUMN = block(
            "cube_column",
            TextureKeys.END,
            TextureKeys.SIDE
    );

    public static final ModelTemplate CUBE_COLUMN_HORIZONTAL = block(
            "cube_column_horizontal", "_horizontal",
            TextureKeys.END,
            TextureKeys.SIDE
    );

    public static final ModelTemplate CUBE_COLUMN_MIRRORED = block(
            "cube_column_mirrored", "_mirrored",
            TextureKeys.END,
            TextureKeys.SIDE
    );

    public static final ModelTemplate CUBE_TOP = block(
            "cube_top",
            TextureKeys.TOP,
            TextureKeys.SIDE
    );

    public static final ModelTemplate CUBE_BOTTOM_TOP = block(
            "cube_bottom_top",
            TextureKeys.TOP,
            TextureKeys.BOTTOM,
            TextureKeys.SIDE
    );

    public static final ModelTemplate CUBE_BOTTOM_TOP_INNER_FACES = block(
            "cube_bottom_top_inner_faces",
            TextureKeys.TOP,
            TextureKeys.BOTTOM,
            TextureKeys.SIDE
    );

    public static final ModelTemplate CUBE_ORIENTABLE = block(
            "orientable",
            TextureKeys.TOP,
            TextureKeys.FRONT,
            TextureKeys.SIDE
    );

    public static final ModelTemplate CUBE_ORIENTABLE_TOP_BOTTOM = block(
            "orientable_with_bottom",
            TextureKeys.TOP,
            TextureKeys.BOTTOM,
            TextureKeys.SIDE,
            TextureKeys.FRONT
    );

    public static final ModelTemplate CUBE_ORIENTABLE_VERTICAL = block(
            "orientable_vertical", "_vertical",
            TextureKeys.FRONT,
            TextureKeys.SIDE
    );

    public static final ModelTemplate BUTTON = block(
            "button",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate BUTTON_PRESSED = block(
            "button_pressed", "_pressed",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate BUTTON_INVENTORY = block(
            "button_inventory", "_inventory",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate DOOR_BOTTOM_LEFT = block(
            "door_bottom_left", "_bottom_left",
            TextureKeys.TOP,
            TextureKeys.BOTTOM
    );

    public static final ModelTemplate DOOR_BOTTOM_LEFT_OPEN = block(
            "door_bottom_left_open", "_bottom_left_open",
            TextureKeys.TOP,
            TextureKeys.BOTTOM
    );

    public static final ModelTemplate DOOR_BOTTOM_RIGHT = block(
            "door_bottom_right", "_bottom_right",
            TextureKeys.TOP,
            TextureKeys.BOTTOM
    );

    public static final ModelTemplate DOOR_BOTTOM_RIGHT_OPEN = block(
            "door_bottom_right_open", "_bottom_right_open",
            TextureKeys.TOP,
            TextureKeys.BOTTOM
    );

    public static final ModelTemplate DOOR_TOP_LEFT = block(
            "door_top_left", "_top_left",
            TextureKeys.TOP,
            TextureKeys.BOTTOM
    );

    public static final ModelTemplate DOOR_TOP_LEFT_OPEN = block(
            "door_top_left_open", "_top_left_open",
            TextureKeys.TOP,
            TextureKeys.BOTTOM
    );

    public static final ModelTemplate DOOR_TOP_RIGHT = block(
            "door_top_right", "_top_right",
            TextureKeys.TOP,
            TextureKeys.BOTTOM
    );

    public static final ModelTemplate DOOR_TOP_RIGHT_OPEN = block(
            "door_top_right_open", "_top_right_open",
            TextureKeys.TOP,
            TextureKeys.BOTTOM
    );

    public static final ModelTemplate CUSTOM_FENCE_POST = block(
            "custom_fence_post", "_post",
            TextureKeys.TEXTURE,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate CUSTOM_FENCE_SIDE_NORTH = block(
            "custom_fence_side_north", "_side_north",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate CUSTOM_FENCE_SIDE_EAST = block(
            "custom_fence_side_east", "_side_east",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate CUSTOM_FENCE_SIDE_SOUTH = block(
            "custom_fence_side_south", "_side_south",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate CUSTOM_FENCE_SIDE_WEST = block(
            "custom_fence_side_west", "_side_west",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate CUSTOM_FENCE_INVENTORY = block(
            "custom_fence_inventory", "_inventory",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate FENCE_POST = block(
            "fence_post", "_post",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate FENCE_SIDE = block(
            "fence_side", "_side",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate FENCE_INVENTORY = block(
            "fence_inventory", "_inventory",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate WALL_POST = block(
            "template_wall_post", "_post",
            TextureKeys.WALL
    );

    public static final ModelTemplate WALL_LOW_SIDE = block(
            "template_wall_side", "_side",
            TextureKeys.WALL
    );

    public static final ModelTemplate WALL_TALL_SIDE = block(
            "template_wall_side_tall", "_side_tall",
            TextureKeys.WALL
    );

    public static final ModelTemplate WALL_INVENTORY = block(
            "wall_inventory", "_inventory",
            TextureKeys.WALL
    );

    public static final ModelTemplate CUSTOM_FENCE_GATE_CLOSED = block(
            "template_custom_fence_gate",
            TextureKeys.TEXTURE,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate CUSTOM_FENCE_GATE_OPEN = block(
            "template_custom_fence_gate_open", "_open",
            TextureKeys.TEXTURE,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate CUSTOM_FENCE_GATE_WALL_CLOSED = block(
            "template_custom_fence_gate_wall", "_wall",
            TextureKeys.TEXTURE,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate CUSTOM_FENCE_GATE_WALL_OPEN = block(
            "template_custom_fence_gate_wall_open", "_wall_open",
            TextureKeys.TEXTURE,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate FENCE_GATE_CLOSED = block(
            "template_fence_gate",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate FENCE_GATE_OPEN = block(
            "template_fence_gate_open", "_open",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate FENCE_GATE_WALL_CLOSED = block(
            "template_fence_gate_wall", "_wall",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate FENCE_GATE_WALL_OPEN = block(
            "template_fence_gate_wall_open", "_wall_open",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate PRESSURE_PLATE_UP = block(
            "pressure_plate_up",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate PRESSURE_PLATE_DOWN = block(
            "pressure_plate_down", "_down",
            TextureKeys.TEXTURE
    );


    public static final ModelTemplate SLAB_BOTTOM = block(
            "slab",
            TextureKeys.BOTTOM,
            TextureKeys.TOP,
            TextureKeys.SIDE
    );

    public static final ModelTemplate SLAB_TOP = block(
            "slab_top",
            "_top",
            TextureKeys.BOTTOM,
            TextureKeys.TOP,
            TextureKeys.SIDE
    );

    public static final ModelTemplate LEAVES = block(
            "leaves",
            TextureKeys.ALL
    );

    public static final ModelTemplate STAIRS_STRAIGHT = block(
            "stairs",
            TextureKeys.BOTTOM,
            TextureKeys.TOP,
            TextureKeys.SIDE
    );

    public static final ModelTemplate STAIRS_INNER = block(
            "inner_stairs", "_inner",
            TextureKeys.BOTTOM,
            TextureKeys.TOP,
            TextureKeys.SIDE
    );

    public static final ModelTemplate STAIRS_OUTER = block(
            "outer_stairs", "_outer",
            TextureKeys.BOTTOM,
            TextureKeys.TOP,
            TextureKeys.SIDE
    );

    public static final ModelTemplate TRAPDOOR_TOP = block(
            "template_trapdoor_top", "_top",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate TRAPDOOR_BOTTOM = block(
            "template_trapdoor_bottom", "_bottom",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate TRAPDOOR_OPEN = block(
            "template_trapdoor_open", "_open",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate ORIENTABLE_TRAPDOOR_TOP = block(
            "template_orientable_trapdoor_top", "_top",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate ORIENTABLE_TRAPDOOR_BOTTOM = block(
            "template_orientable_trapdoor_bottom", "_bottom",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate ORIENTABLE_TRAPDOOR_OPEN = block(
            "template_orientable_trapdoor_open", "_open",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate POINTED_DRIPSTONE = block(
            "pointed_dripstone",
            TextureKeys.CROSS
    );

    public static final ModelTemplate CROSS = block(
            "cross",
            TextureKeys.CROSS
    );

    public static final ModelTemplate TINTED_CROSS = block(
            "tinted_cross",
            TextureKeys.CROSS
    );

    public static final ModelTemplate CROSS_EMISSIVE = block(
            "cross_emissive",
            TextureKeys.CROSS,
            TextureKeys.CROSS_EMISSIVE
    );

    public static final ModelTemplate FLOWER_POT_CROSS = block(
            "flower_pot_cross",
            TextureKeys.PLANT
    );

    public static final ModelTemplate TINTED_FLOWER_POT_CROSS = block(
            "tinted_flower_pot_cross",
            TextureKeys.PLANT
    );

    public static final ModelTemplate FLOWER_POT_CROSS_EMISSIVE = block(
            "flower_pot_cross_emissive",
            TextureKeys.PLANT,
            TextureKeys.CROSS_EMISSIVE
    );

    public static final ModelTemplate RAIL_FLAT = block(
            "rail_flat",
            TextureKeys.RAIL
    );

    public static final ModelTemplate RAIL_CURVED = block(
            "rail_curved", "_corner",
            TextureKeys.RAIL
    );

    public static final ModelTemplate RAIL_RAISED_NE = block(
            "template_rail_raised_ne", "_raised_ne",
            TextureKeys.RAIL
    );

    public static final ModelTemplate RAIL_RAISED_SW = block(
            "template_rail_raised_sw", "_raised_sw",
            TextureKeys.RAIL
    );

    public static final ModelTemplate CARPET = block(
            "carpet",
            TextureKeys.WOOL
    );

    public static final ModelTemplate MOSSY_CARPET_SIDE = block(
            "mossy_carpet_side",
            TextureKeys.SIDE
    );

    public static final ModelTemplate FLOWERBED_1 = block(
            "flowerbed_1", "_1",
            TextureKeys.FLOWERBED,
            TextureKeys.STEM
    );

    public static final ModelTemplate FLOWERBED_2 = block(
            "flowerbed_2", "_2",
            TextureKeys.FLOWERBED,
            TextureKeys.STEM
    );

    public static final ModelTemplate FLOWERBED_3 = block(
            "flowerbed_3", "_3",
            TextureKeys.FLOWERBED,
            TextureKeys.STEM
    );

    public static final ModelTemplate FLOWERBED_4 = block(
            "flowerbed_4", "_4",
            TextureKeys.FLOWERBED,
            TextureKeys.STEM
    );

    public static final ModelTemplate LEAF_LITTER_1 = block(
            "template_leaf_litter_1", "_1",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate LEAF_LITTER_2 = block(
            "template_leaf_litter_2", "_2",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate LEAF_LITTER_3 = block(
            "template_leaf_litter_3", "_3",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate LEAF_LITTER_4 = block(
            "template_leaf_litter_4", "_4",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate CORAL_FAN = block(
            "coral_fan",
            TextureKeys.FAN
    );

    public static final ModelTemplate CORAL_WALL_FAN = block(
            "coral_wall_fan",
            TextureKeys.FAN
    );

    public static final ModelTemplate GLAZED_TERRACOTTA = block(
            "template_glazed_terracotta",
            TextureKeys.PATTERN
    );

    public static final ModelTemplate CHORUS_FLOWER = block(
            "template_chorus_flower",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate DAYLIGHT_DETECTOR = block(
            "template_daylight_detector",
            TextureKeys.TOP,
            TextureKeys.SIDE
    );

    public static final ModelTemplate STAINED_GLASS_PANE_NOSIDE = block(
            "template_glass_pane_noside", "_noside",
            TextureKeys.PANE
    );

    public static final ModelTemplate STAINED_GLASS_PANE_NOSIDE_ALT = block(
            "template_glass_pane_noside_alt", "_noside_alt",
            TextureKeys.PANE
    );

    public static final ModelTemplate STAINED_GLASS_PANE_POST = block(
            "template_glass_pane_post", "_post",
            TextureKeys.PANE,
            TextureKeys.EDGE
    );

    public static final ModelTemplate STAINED_GLASS_PANE_SIDE = block(
            "template_glass_pane_side", "_side",
            TextureKeys.PANE,
            TextureKeys.EDGE
    );

    public static final ModelTemplate STAINED_GLASS_PANE_SIDE_ALT = block(
            "template_glass_pane_side_alt", "_side_alt",
            TextureKeys.PANE,
            TextureKeys.EDGE
    );

    public static final ModelTemplate COMMAND_BLOCK = block(
            "template_command_block",
            TextureKeys.FRONT,
            TextureKeys.BACK,
            TextureKeys.SIDE
    );

    public static final ModelTemplate CHISELED_BOOKSHELF_SLOT_TOP_LEFT = block(
            "template_chiseled_bookshelf_slot_top_left",
            "_slot_top_left",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate CHISELED_BOOKSHELF_SLOT_TOP_MID = block(
            "template_chiseled_bookshelf_slot_top_mid",
            "_slot_top_mid",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate CHISELED_BOOKSHELF_SLOT_TOP_RIGHT = block(
            "template_chiseled_bookshelf_slot_top_right",
            "_slot_top_right",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate CHISELED_BOOKSHELF_SLOT_BOTTOM_LEFT = block(
            "template_chiseled_bookshelf_slot_bottom_left",
            "_slot_bottom_left",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate CHISELED_BOOKSHELF_SLOT_BOTTOM_MID = block(
            "template_chiseled_bookshelf_slot_bottom_mid",
            "_slot_bottom_mid",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate CHISELED_BOOKSHELF_SLOT_BOTTOM_RIGHT = block(
            "template_chiseled_bookshelf_slot_bottom_right",
            "_slot_bottom_right",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate SHELF_BODY = block(
            "template_shelf_body",
            TextureKeys.ALL,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate SHELF_INVENTORY = block(
            "template_shelf_inventory",
            "_inventory",
            TextureKeys.ALL,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate SHELF_UNPOWERED = block(
            "template_shelf_unpowered",
            "_unpowered",
            TextureKeys.ALL,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate SHELF_UNCONNECTED = block(
            "template_shelf_unconnected",
            "_unconnected",
            TextureKeys.ALL,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate SHELF_LEFT = block(
            "template_shelf_left",
            "_left",
            TextureKeys.ALL,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate SHELF_CENTER = block(
            "template_shelf_center",
            "_center",
            TextureKeys.ALL,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate SHELF_RIGHT = block(
            "template_shelf_right",
            "_right",
            TextureKeys.ALL,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate ANVIL = block(
            "template_anvil",
            TextureKeys.TOP
    );

    public static final ModelTemplate[] STEMS = IntStream.range(0, 8)
            .mapToObj(i -> block("stem_growth" + i, "_stage" + i, TextureKeys.STEM))
            .toArray(ModelTemplate[]::new);

    public static final ModelTemplate ATTACHED_STEM = block(
            "stem_fruit",
            TextureKeys.STEM,
            TextureKeys.UPPER_STEM
    );

    public static final ModelTemplate CROP = block(
            "crop",
            TextureKeys.CROP
    );

    public static final ModelTemplate FARMLAND = block(
            "template_farmland",
            TextureKeys.DIRT,
            TextureKeys.TOP
    );

    public static final ModelTemplate FIRE_FLOOR = block(
            "template_fire_floor",
            TextureKeys.FIRE
    );

    public static final ModelTemplate FIRE_SIDE = block(
            "template_fire_side",
            TextureKeys.FIRE
    );

    public static final ModelTemplate FIRE_SIDE_ALT = block(
            "template_fire_side_alt",
            TextureKeys.FIRE
    );

    public static final ModelTemplate FIRE_UP = block(
            "template_fire_up",
            TextureKeys.FIRE
    );

    public static final ModelTemplate FIRE_UP_ALT = block(
            "template_fire_up_alt",
            TextureKeys.FIRE
    );

    public static final ModelTemplate CAMPFIRE = block(
            "template_campfire",
            TextureKeys.FIRE,
            TextureKeys.LIT_LOG
    );

    public static final ModelTemplate LANTERN = block(
            "template_lantern",
            TextureKeys.LANTERN
    );

    public static final ModelTemplate HANGING_LANTERN = block(
            "template_hanging_lantern", "_hanging",
            TextureKeys.LANTERN
    );

    public static final ModelTemplate CHAIN = block(
            "template_chain",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate BARS_CAP = block(
            "template_bars_cap", "_cap",
            TextureKeys.BARS,
            TextureKeys.EDGE
    );

    public static final ModelTemplate BARS_CAP_ALT = block(
            "template_bars_cap_alt", "_cap_alt",
            TextureKeys.BARS,
            TextureKeys.EDGE
    );

    public static final ModelTemplate BARS_POST = block(
            "template_bars_post", "_post",
            TextureKeys.BARS,
            TextureKeys.EDGE
    );

    public static final ModelTemplate BARS_POST_ENDS = block(
            "template_bars_post_ends", "_post_ends",
            TextureKeys.BARS,
            TextureKeys.EDGE
    );

    public static final ModelTemplate BARS_POST_SIDE = block(
            "template_bars_side", "_side",
            TextureKeys.BARS,
            TextureKeys.EDGE
    );

    public static final ModelTemplate BARS_POST_SIDE_ALT = block(
            "template_bars_side_alt", "_side_alt",
            TextureKeys.BARS,
            TextureKeys.EDGE
    );

    public static final ModelTemplate TORCH = block(
            "template_torch",
            TextureKeys.TORCH
    );

    public static final ModelTemplate TORCH_UNLIT = block(
            "template_torch_unlit",
            TextureKeys.TORCH
    );

    public static final ModelTemplate WALL_TORCH = block(
            "template_torch_wall",
            TextureKeys.TORCH
    );

    public static final ModelTemplate WALL_TORCH_UNLIT = block(
            "template_torch_wall_unlit",
            TextureKeys.TORCH
    );

    public static final ModelTemplate REDSTONE_TORCH = block(
            "template_redstone_torch",
            TextureKeys.TORCH
    );

    public static final ModelTemplate REDSTONE_WALL_TORCH = block(
            "template_redstone_torch_wall",
            TextureKeys.TORCH
    );

    public static final ModelTemplate PISTON = block(
            "template_piston",
            TextureKeys.PLATFORM,
            TextureKeys.BOTTOM,
            TextureKeys.SIDE
    );

    public static final ModelTemplate PISTON_HEAD = block(
            "template_piston_head",
            TextureKeys.PLATFORM,
            TextureKeys.SIDE,
            TextureKeys.UNSTICKY
    );

    public static final ModelTemplate PISTON_HEAD_SHORT = block(
            "template_piston_head_short",
            TextureKeys.PLATFORM,
            TextureKeys.SIDE,
            TextureKeys.UNSTICKY
    );

    public static final ModelTemplate SEAGRASS = block(
            "template_seagrass",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate TURTLE_EGG = block(
            "template_turtle_egg",
            TextureKeys.ALL
    );

    public static final ModelTemplate DRIED_GHAST = block(
            "dried_ghast",
            TextureKeys.PARTICLE,
            TextureKeys.TOP,
            TextureKeys.BOTTOM,
            TextureKeys.NORTH,
            TextureKeys.SOUTH,
            TextureKeys.EAST,
            TextureKeys.WEST,
            TextureKeys.TENTACLES
    );

    public static final ModelTemplate TWO_TURTLE_EGGS = block(
            "template_two_turtle_eggs",
            TextureKeys.ALL
    );

    public static final ModelTemplate THREE_TURTLE_EGGS = block(
            "template_three_turtle_eggs",
            TextureKeys.ALL
    );

    public static final ModelTemplate FOUR_TURTLE_EGGS = block(
            "template_four_turtle_eggs",
            TextureKeys.ALL
    );

    public static final ModelTemplate SINGLE_FACE = block(
            "template_single_face",
            TextureKeys.TEXTURE
    );

    public static final ModelTemplate CAULDRON_LEVEL1 = block(
            "template_cauldron_level1",
            TextureKeys.CONTENT,
            TextureKeys.INSIDE,
            TextureKeys.PARTICLE,
            TextureKeys.TOP,
            TextureKeys.BOTTOM,
            TextureKeys.SIDE
    );

    public static final ModelTemplate CAULDRON_LEVEL2 = block(
            "template_cauldron_level2",
            TextureKeys.CONTENT,
            TextureKeys.INSIDE,
            TextureKeys.PARTICLE,
            TextureKeys.TOP,
            TextureKeys.BOTTOM,
            TextureKeys.SIDE
    );

    public static final ModelTemplate CAULDRON_FULL = block(
            "template_cauldron_full",
            TextureKeys.CONTENT,
            TextureKeys.INSIDE,
            TextureKeys.PARTICLE,
            TextureKeys.TOP,
            TextureKeys.BOTTOM,
            TextureKeys.SIDE
    );

    public static final ModelTemplate AZALEA = block(
            "template_azalea",
            TextureKeys.TOP,
            TextureKeys.SIDE
    );

    public static final ModelTemplate POTTED_AZALEA = block(
            "template_potted_azalea_bush",
            TextureKeys.PLANT,
            TextureKeys.TOP,
            TextureKeys.SIDE
    );

    public static final ModelTemplate POTTED_FLOWERING_AZALEA = block(
            "template_potted_azalea_bush",
            TextureKeys.PLANT,
            TextureKeys.TOP,
            TextureKeys.SIDE
    );

    public static final ModelTemplate SNIFFER_EGG = block(
            "sniffer_egg",
            TextureKeys.TOP,
            TextureKeys.BOTTOM,
            TextureKeys.NORTH,
            TextureKeys.SOUTH,
            TextureKeys.EAST,
            TextureKeys.WEST
    );

    public static final ModelTemplate CANDLE = block(
            "template_candle",
            TextureKeys.ALL,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate TWO_CANDLES = block(
            "template_two_candles",
            TextureKeys.ALL,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate THREE_CANDLES = block(
            "template_three_candles",
            TextureKeys.ALL,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate FOUR_CANDLES = block(
            "template_four_candles",
            TextureKeys.ALL,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate CANDLE_CAKE = block(
            "template_cake_with_candle",
            TextureKeys.CANDLE,
            TextureKeys.BOTTOM,
            TextureKeys.SIDE,
            TextureKeys.TOP,
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate SCULK_SHRIEKER = block(
            "template_sculk_shrieker",
            TextureKeys.BOTTOM,
            TextureKeys.SIDE,
            TextureKeys.TOP,
            TextureKeys.PARTICLE,
            TextureKeys.INNER_TOP
    );

    public static final ModelTemplate VAULT = block(
            "template_vault",
            TextureKeys.TOP,
            TextureKeys.BOTTOM,
            TextureKeys.SIDE,
            TextureKeys.FRONT
    );

    public static final ModelTemplate LIGHTNING_ROD = block(
            "template_lightning_rod",
            TextureKeys.TEXTURE
    );


    // Items

    public static final ModelTemplate FLAT_ITEM = item(
            "generated",
            TextureKeys.LAYER0
    );

    public static final ModelTemplate MUSIC_DISC = item(
            "template_music_disc",
            TextureKeys.LAYER0
    );

    public static final ModelTemplate FLAT_HANDHELD_ITEM = item(
            "handheld",
            TextureKeys.LAYER0
    );

    public static final ModelTemplate FLAT_HANDHELD_ROD_ITEM = item(
            "handheld_rod",
            TextureKeys.LAYER0
    );

    public static final ModelTemplate FLAT_HANDHELD_MACE_ITEM = item(
            "handheld_mace",
            TextureKeys.LAYER0
    );

    public static final ModelTemplate TWO_LAYERED_ITEM = item(
            "generated",
            TextureKeys.LAYER0,
            TextureKeys.LAYER1
    );

    public static final ModelTemplate THREE_LAYERED_ITEM = item(
            "generated",
            TextureKeys.LAYER0,
            TextureKeys.LAYER1,
            TextureKeys.LAYER2
    );

    public static final ModelTemplate SHULKER_BOX_INVENTORY = item(
            "template_shulker_box",
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate BED_INVENTORY = item(
            "template_bed",
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate CHEST_INVENTORY = item(
            "template_chest",
            TextureKeys.PARTICLE
    );

    public static final ModelTemplate BUNDLE_OPEN_FRONT_INVENTORY = item(
            "template_bundle_open_front", "_open_front",
            TextureKeys.LAYER0
    );

    public static final ModelTemplate BUNDLE_OPEN_BACK_INVENTORY = item(
            "template_bundle_open_back", "_open_back",
            TextureKeys.LAYER0
    );

    public static final ModelTemplate BOW = item(
            "bow",
            TextureKeys.LAYER0
    );

    public static final ModelTemplate CROSSBOW = item(
            "crossbow",
            TextureKeys.LAYER0
    );

    public static final ModelTemplate SPEAR_IN_HAND = item(
            "spear_in_hand", "_in_hand",
            TextureKeys.LAYER0
    );

    private static ModelTemplate particleOnly() {
        return (id, map) -> Model.particleOnly(id, map.get(TextureKeys.PARTICLE));
    }

    private static ModelTemplate block(String id, TextureKey... slots) {
        return ModelTemplate.of(Identifier.withDefaultNamespace("block/" + id), slots);
    }

    private static ModelTemplate block(String id, String suffix, TextureKey... slots) {
        return ModelTemplate.of(Identifier.withDefaultNamespace("block/" + id), suffix, slots);
    }

    private static ModelTemplate item(String id, TextureKey... slots) {
        return ModelTemplate.of(Identifier.withDefaultNamespace("item/" + id), slots);
    }

    private static ModelTemplate item(String id, String suffix, TextureKey... slots) {
        return ModelTemplate.of(Identifier.withDefaultNamespace("item/" + id), suffix, slots);
    }
}
