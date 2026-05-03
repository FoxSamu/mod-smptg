package net.foxboi.salted.common.block;

import java.util.List;
import java.util.function.Supplier;

import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.ColorRegistry;
import net.foxboi.salted.common.misc.Translator;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

/**
 * SMPTG's collection of {@link Block}s. Each {@link Block} instance is provided here in a static field.
 */
@SuppressWarnings("unused")
public record ModBlocks() {
    // IMPORTANT NOTES WHEN ADDING BLOCKS
    // - Register block types in init()
    // - Register blocks to relevant Fabric registries in init() if needed
    // - Go through all the configuration methods below and supply the correct configuration for the block
    // - Go through all the configurations in ModBlockData (under the data source set) as well
    // - All configuration methods reside above the factory methods
    // - Add a block item for each block that should be placeable in ModItems


    // BLOCKS
    // =============================================

    // Woods

    public static final Block ASPEN_LOG = register("aspen_log", rotatedPillar(), logProps(MapColor.SAND, MapColor.QUARTZ, SoundType.WOOD));
    public static final Block ASPEN_WOOD = register("aspen_wood", rotatedPillar(), logProps(MapColor.QUARTZ, SoundType.WOOD));
    public static final Block STRIPPED_ASPEN_LOG = register("stripped_aspen_log", rotatedPillar(), logProps(MapColor.SAND, SoundType.WOOD));
    public static final Block STRIPPED_ASPEN_WOOD = register("stripped_aspen_wood", rotatedPillar(), logProps(MapColor.SAND, SoundType.WOOD));
    public static final Block ASPEN_LEAVES = register("aspen_leaves", leaves(0.01f), leavesProps(MapColor.PLANT, SoundType.GRASS));
    public static final Block ASPEN_PLANKS = register("aspen_planks", block(), planksProps(MapColor.QUARTZ, SoundType.WOOD));
    public static final Block ASPEN_SLAB = register("aspen_slab", slab(), props(ASPEN_PLANKS));
    public static final Block ASPEN_STAIRS = register("aspen_stairs", stairs(), props(ASPEN_PLANKS));
    public static final Block ASPEN_FENCE = register("aspen_fence", fence(), props(ASPEN_PLANKS));
    public static final Block ASPEN_FENCE_GATE = register("aspen_fence_gate", fenceGate(ModWoodTypes.ASPEN), props(ASPEN_PLANKS));
    public static final Block ASPEN_PRESSURE_PLATE = register("aspen_pressure_plate", pressurePlate(ModBlockSetTypes.ASPEN), pressurePlateProps(ASPEN_PLANKS));
    public static final Block ASPEN_BUTTON = register("aspen_button", button(ModBlockSetTypes.ASPEN, 30), buttonProps());
    public static final Block ASPEN_DOOR = register("aspen_door", door(ModBlockSetTypes.ASPEN), doorProps(ASPEN_PLANKS));
    public static final Block ASPEN_TRAPDOOR = register("aspen_trapdoor", trapdoor(ModBlockSetTypes.ASPEN), trapdoorProps(ASPEN_PLANKS));
    public static final Block ASPEN_SHELF = register("aspen_shelf", shelf(), shelfProps(ASPEN_PLANKS));
    public static final Block ASPEN_SIGN = register("aspen_sign", standingSign(ModWoodTypes.ASPEN), signProps(ASPEN_PLANKS));
    public static final Block ASPEN_WALL_SIGN = register("aspen_wall_sign", wallSign(ModWoodTypes.ASPEN), wallSignProps(ASPEN_PLANKS));
    public static final Block ASPEN_HANGING_SIGN = register("aspen_hanging_sign", ceilingHangingSign(ModWoodTypes.ASPEN), signProps(ASPEN_PLANKS));
    public static final Block ASPEN_WALL_HANGING_SIGN = register("aspen_wall_hanging_sign", wallHangingSign(ModWoodTypes.ASPEN), wallSignProps(ASPEN_PLANKS));

    public static final Block BEECH_LOG = register("beech_log", rotatedPillar(), logProps(MapColor.WOOD, MapColor.WOOD, SoundType.WOOD));
    public static final Block BEECH_WOOD = register("beech_wood", rotatedPillar(), logProps(MapColor.WOOD, SoundType.WOOD));
    public static final Block STRIPPED_BEECH_LOG = register("stripped_beech_log", rotatedPillar(), logProps(MapColor.WOOD, SoundType.WOOD));
    public static final Block STRIPPED_BEECH_WOOD = register("stripped_beech_wood", rotatedPillar(), logProps(MapColor.WOOD, SoundType.WOOD));
    public static final Block BEECH_LEAVES = register("beech_leaves", leaves(0.01f), leavesProps(MapColor.PLANT, SoundType.GRASS));
    public static final Block BEECH_PLANKS = register("beech_planks", block(), planksProps(MapColor.WOOD, SoundType.WOOD));
    public static final Block BEECH_SLAB = register("beech_slab", slab(), props(BEECH_PLANKS));
    public static final Block BEECH_STAIRS = register("beech_stairs", stairs(), props(BEECH_PLANKS));
    public static final Block BEECH_FENCE = register("beech_fence", fence(), props(BEECH_PLANKS));
    public static final Block BEECH_FENCE_GATE = register("beech_fence_gate", fenceGate(ModWoodTypes.BEECH), props(BEECH_PLANKS));
    public static final Block BEECH_PRESSURE_PLATE = register("beech_pressure_plate", pressurePlate(ModBlockSetTypes.BEECH), pressurePlateProps(BEECH_PLANKS));
    public static final Block BEECH_BUTTON = register("beech_button", button(ModBlockSetTypes.BEECH, 30), buttonProps());
    public static final Block BEECH_DOOR = register("beech_door", door(ModBlockSetTypes.BEECH), doorProps(BEECH_PLANKS));
    public static final Block BEECH_TRAPDOOR = register("beech_trapdoor", trapdoor(ModBlockSetTypes.BEECH), trapdoorProps(BEECH_PLANKS));
    public static final Block BEECH_SHELF = register("beech_shelf", shelf(), shelfProps(BEECH_PLANKS));
    public static final Block BEECH_SIGN = register("beech_sign", standingSign(ModWoodTypes.BEECH), signProps(BEECH_PLANKS));
    public static final Block BEECH_WALL_SIGN = register("beech_wall_sign", wallSign(ModWoodTypes.BEECH), wallSignProps(BEECH_PLANKS));
    public static final Block BEECH_HANGING_SIGN = register("beech_hanging_sign", ceilingHangingSign(ModWoodTypes.BEECH), signProps(BEECH_PLANKS));
    public static final Block BEECH_WALL_HANGING_SIGN = register("beech_wall_hanging_sign", wallHangingSign(ModWoodTypes.BEECH), wallSignProps(BEECH_PLANKS));

    public static final Block MAPLE_LOG = register("maple_log", rotatedPillar(), logProps(MapColor.WOOD, MapColor.WOOD, SoundType.WOOD));
    public static final Block MAPLE_WOOD = register("maple_wood", rotatedPillar(), logProps(MapColor.WOOD, SoundType.WOOD));
    public static final Block STRIPPED_MAPLE_LOG = register("stripped_maple_log", rotatedPillar(), logProps(MapColor.WOOD, SoundType.WOOD));
    public static final Block STRIPPED_MAPLE_WOOD = register("stripped_maple_wood", rotatedPillar(), logProps(MapColor.WOOD, SoundType.WOOD));
    public static final Block RED_MAPLE_LEAVES = register("red_maple_leaves", leaves(0.01f), leavesProps(MapColor.TERRACOTTA_RED, SoundType.GRASS));
    public static final Block ORANGE_MAPLE_LEAVES = register("orange_maple_leaves", leaves(0.01f), leavesProps(MapColor.TERRACOTTA_ORANGE, SoundType.GRASS));
    public static final Block YELLOW_MAPLE_LEAVES = register("yellow_maple_leaves", leaves(0.01f), leavesProps(MapColor.TERRACOTTA_YELLOW, SoundType.GRASS));
    public static final Block MAPLE_PLANKS = register("maple_planks", block(), planksProps(MapColor.WOOD, SoundType.WOOD));
    public static final Block MAPLE_SLAB = register("maple_slab", slab(), props(MAPLE_PLANKS));
    public static final Block MAPLE_STAIRS = register("maple_stairs", stairs(), props(MAPLE_PLANKS));
    public static final Block MAPLE_FENCE = register("maple_fence", fence(), props(MAPLE_PLANKS));
    public static final Block MAPLE_FENCE_GATE = register("maple_fence_gate", fenceGate(ModWoodTypes.MAPLE), props(MAPLE_PLANKS));
    public static final Block MAPLE_PRESSURE_PLATE = register("maple_pressure_plate", pressurePlate(ModBlockSetTypes.MAPLE), pressurePlateProps(MAPLE_PLANKS));
    public static final Block MAPLE_BUTTON = register("maple_button", button(ModBlockSetTypes.MAPLE, 30), buttonProps());
    public static final Block MAPLE_DOOR = register("maple_door", door(ModBlockSetTypes.MAPLE), doorProps(MAPLE_PLANKS));
    public static final Block MAPLE_TRAPDOOR = register("maple_trapdoor", trapdoor(ModBlockSetTypes.MAPLE), trapdoorProps(MAPLE_PLANKS));
    public static final Block MAPLE_SHELF = register("maple_shelf", shelf(), shelfProps(MAPLE_PLANKS));
    public static final Block MAPLE_SIGN = register("maple_sign", standingSign(ModWoodTypes.MAPLE), signProps(MAPLE_PLANKS));
    public static final Block MAPLE_WALL_SIGN = register("maple_wall_sign", wallSign(ModWoodTypes.MAPLE), wallSignProps(MAPLE_PLANKS));
    public static final Block MAPLE_HANGING_SIGN = register("maple_hanging_sign", ceilingHangingSign(ModWoodTypes.MAPLE), signProps(MAPLE_PLANKS));
    public static final Block MAPLE_WALL_HANGING_SIGN = register("maple_wall_hanging_sign", wallHangingSign(ModWoodTypes.MAPLE), wallSignProps(MAPLE_PLANKS));

    public static final Block REDWOOD_LOG = register("redwood_log", rotatedPillar(), logProps(MapColor.TERRACOTTA_RED, MapColor.TERRACOTTA_RED, SoundType.WOOD));
    public static final Block REDWOOD_WOOD = register("redwood_wood", rotatedPillar(), logProps(MapColor.TERRACOTTA_RED, SoundType.WOOD));
    public static final Block STRIPPED_REDWOOD_LOG = register("stripped_redwood_log", rotatedPillar(), logProps(MapColor.TERRACOTTA_RED, SoundType.WOOD));
    public static final Block STRIPPED_REDWOOD_WOOD = register("stripped_redwood_wood", rotatedPillar(), logProps(MapColor.TERRACOTTA_RED, SoundType.WOOD));
    public static final Block REDWOOD_LEAVES = register("redwood_leaves", leaves(0.01f), leavesProps(MapColor.PLANT, SoundType.GRASS));
    public static final Block REDWOOD_PLANKS = register("redwood_planks", block(), planksProps(MapColor.TERRACOTTA_RED, SoundType.WOOD));
    public static final Block REDWOOD_SLAB = register("redwood_slab", slab(), props(REDWOOD_PLANKS));
    public static final Block REDWOOD_STAIRS = register("redwood_stairs", stairs(), props(REDWOOD_PLANKS));
    public static final Block REDWOOD_FENCE = register("redwood_fence", fence(), props(REDWOOD_PLANKS));
    public static final Block REDWOOD_FENCE_GATE = register("redwood_fence_gate", fenceGate(ModWoodTypes.REDWOOD), props(REDWOOD_PLANKS));
    public static final Block REDWOOD_PRESSURE_PLATE = register("redwood_pressure_plate", pressurePlate(ModBlockSetTypes.REDWOOD), pressurePlateProps(REDWOOD_PLANKS));
    public static final Block REDWOOD_BUTTON = register("redwood_button", button(ModBlockSetTypes.REDWOOD, 30), buttonProps());
    public static final Block REDWOOD_DOOR = register("redwood_door", door(ModBlockSetTypes.REDWOOD), doorProps(REDWOOD_PLANKS));
    public static final Block REDWOOD_TRAPDOOR = register("redwood_trapdoor", trapdoor(ModBlockSetTypes.REDWOOD), trapdoorProps(REDWOOD_PLANKS));
    public static final Block REDWOOD_SHELF = register("redwood_shelf", shelf(), shelfProps(REDWOOD_PLANKS));
    public static final Block REDWOOD_SIGN = register("redwood_sign", standingSign(ModWoodTypes.REDWOOD), signProps(REDWOOD_PLANKS));
    public static final Block REDWOOD_WALL_SIGN = register("redwood_wall_sign", wallSign(ModWoodTypes.REDWOOD), wallSignProps(REDWOOD_PLANKS));
    public static final Block REDWOOD_HANGING_SIGN = register("redwood_hanging_sign", ceilingHangingSign(ModWoodTypes.REDWOOD), signProps(REDWOOD_PLANKS));
    public static final Block REDWOOD_WALL_HANGING_SIGN = register("redwood_wall_hanging_sign", wallHangingSign(ModWoodTypes.REDWOOD), wallSignProps(REDWOOD_PLANKS));

    public static final Block DEAD_LOG = register("dead_log", rotatedPillar(), logProps(MapColor.TERRACOTTA_LIGHT_GRAY, MapColor.TERRACOTTA_BROWN, SoundType.CHERRY_WOOD).strength(1f));
    public static final Block DEAD_WOOD = register("dead_wood", rotatedPillar(), logProps(MapColor.TERRACOTTA_BROWN, SoundType.CHERRY_WOOD).strength(1f));
    public static final Block STRIPPED_DEAD_LOG = register("stripped_dead_log", rotatedPillar(), logProps(MapColor.TERRACOTTA_LIGHT_GRAY, SoundType.CHERRY_WOOD).strength(1f));
    public static final Block STRIPPED_DEAD_WOOD = register("stripped_dead_wood", rotatedPillar(), logProps(MapColor.TERRACOTTA_LIGHT_GRAY, SoundType.CHERRY_WOOD).strength(1f));
    public static final Block DEAD_LEAVES = register("dead_leaves", leaves(0.05f), leavesProps(MapColor.TERRACOTTA_LIGHT_GRAY, SoundType.AZALEA_LEAVES));
    public static final Block DEAD_WOOD_PLANKS = register("dead_wood_planks", block(), planksProps(MapColor.TERRACOTTA_BROWN, SoundType.CHERRY_WOOD).strength(1f, 1.5f));
    public static final Block DEAD_WOOD_SLAB = register("dead_wood_slab", slab(), props(DEAD_WOOD_PLANKS));
    public static final Block DEAD_WOOD_STAIRS = register("dead_wood_stairs", stairs(), props(DEAD_WOOD_PLANKS));
    public static final Block DEAD_WOOD_FENCE = register("dead_wood_fence", fence(), props(DEAD_WOOD_PLANKS));
    public static final Block DEAD_WOOD_FENCE_GATE = register("dead_wood_fence_gate", fenceGate(ModWoodTypes.DEAD_WOOD), props(DEAD_WOOD_PLANKS));
    public static final Block DEAD_WOOD_PRESSURE_PLATE = register("dead_wood_pressure_plate", pressurePlate(ModBlockSetTypes.DEAD_WOOD), pressurePlateProps(DEAD_WOOD_PLANKS).strength(0.25f));
    public static final Block DEAD_WOOD_BUTTON = register("dead_wood_button", button(ModBlockSetTypes.DEAD_WOOD, 30), buttonProps().strength(0.25f));
    public static final Block DEAD_WOOD_DOOR = register("dead_wood_door", door(ModBlockSetTypes.DEAD_WOOD), doorProps(DEAD_WOOD_PLANKS).strength(1.5f));
    public static final Block DEAD_WOOD_TRAPDOOR = register("dead_wood_trapdoor", trapdoor(ModBlockSetTypes.DEAD_WOOD), trapdoorProps(DEAD_WOOD_PLANKS).strength(1.5f));
    public static final Block DEAD_WOOD_SHELF = register("dead_wood_shelf", shelf(), shelfProps(DEAD_WOOD_PLANKS).strength(1f, 1.5f));
    public static final Block DEAD_WOOD_SIGN = register("dead_wood_sign", standingSign(ModWoodTypes.DEAD_WOOD), signProps(DEAD_WOOD_PLANKS).strength(0.5f));
    public static final Block DEAD_WOOD_WALL_SIGN = register("dead_wood_wall_sign", wallSign(ModWoodTypes.DEAD_WOOD), wallSignProps(DEAD_WOOD_PLANKS).strength(0.5f));
    public static final Block DEAD_WOOD_HANGING_SIGN = register("dead_wood_hanging_sign", ceilingHangingSign(ModWoodTypes.DEAD_WOOD), signProps(DEAD_WOOD_PLANKS).strength(0.5f));
    public static final Block DEAD_WOOD_WALL_HANGING_SIGN = register("dead_wood_wall_hanging_sign", wallHangingSign(ModWoodTypes.DEAD_WOOD), wallSignProps(DEAD_WOOD_PLANKS).strength(0.5f));


    // Ash

    public static final Block BURNED_STEM = register("burned_stem", rotatedPillar(), logProps(MapColor.COLOR_BLACK, MapColor.COLOR_BLACK, SoundType.STEM).strength(1f));
    public static final Block BURNED_HYPHAE = register("burned_hyphae", rotatedPillar(), logProps(MapColor.COLOR_BLACK, SoundType.STEM).strength(1f));

    public static final Block ASH_LAYER = register("ash_layer", multilayer(), ashLayerProps());
    public static final Block ASH_BLOCK = register("ash_block", falling(0x111217), props(Blocks.DIRT).sound(SoundType.WART_BLOCK).mapColor(MapColor.COLOR_BLACK));

    public static final Block PACKED_ASH = register("packed_ash", block(), props(Blocks.NETHERRACK).mapColor(MapColor.COLOR_BLACK));
    public static final Block ASH_BRICKS = register("ash_bricks", block(), props(PACKED_ASH).sound(SoundType.NETHER_BRICKS));
    public static final Block ASH_BRICK_SLAB = register("ash_brick_slab", slab(), props(ASH_BRICKS));
    public static final Block ASH_BRICK_STAIRS = register("ash_brick_stairs", stairs(), props(ASH_BRICKS));

    public static final Block ASHCREEP = register("ashcreep", ashcreep(), props(Blocks.POPPY).sound(SoundType.NETHER_SPROUTS).mapColor(MapColor.COLOR_GRAY));
    public static final Block ASHVINE = register("ashvine", ashvine(), props(Blocks.POPPY).sound(SoundType.WEEPING_VINES).mapColor(MapColor.COLOR_GRAY));


    // Soils

    public static final Block MOSSY_DIRT = register("mossy_dirt", grassBlock(), props(Blocks.GRASS_BLOCK).sound(SoundType.MOSS));


    // Plants

    public static final Block CLOVERS = register("clovers", clovers(), props(Blocks.PINK_PETALS).replaceable());
    public static final Block GRASS_SPROUTS = register("grass_sprouts", grassSprouts(), props(Blocks.SHORT_GRASS).sound(SoundType.MOSS_CARPET));
    public static final Block BARLEY = register("barley", grassyPlant(ModBlocks::tallBarley), props(Blocks.POPPY));
    public static final Block TALL_BARLEY = register("tall_barley", tallGrassyPlant(), props(Blocks.ROSE_BUSH));
    public static final Block CATTAIL = register("cattail", grassyPlant(ModBlocks::tallCattail), props(Blocks.POPPY));
    public static final Block TALL_CATTAIL = register("tall_cattail", partiallyWaterloggableTallGrassyPlant(), props(Blocks.ROSE_BUSH));
    public static final Block LAVENDER = register("lavender", grassyPlant(ModBlocks::tallLavender), props(Blocks.POPPY));
    public static final Block TALL_LAVENDER = register("tall_lavender", tallGrassyPlant(), props(Blocks.ROSE_BUSH));
    public static final Block CAVE_GRASS = register("cave_grass", caveGrass(), props(Blocks.SHORT_GRASS).sound(SoundType.MOSS_CARPET).mapColor(MapColor.SAND));
    public static final Block DRIPMOSS = register("dripmoss", dripmoss(), props(Blocks.POPPY).sound(SoundType.MOSS_CARPET).mapColor(MapColor.SAND));
    public static final Block PATCHMOSS = register("patchmoss", multifacePlant(), patchmossProps());
    public static final Block GLOBE_THISTLE = register("globe_thistle", tallGrassyPlant(), props(Blocks.ROSE_BUSH));

    public static final Block SHELF_FUNGUS = register("shelf_fungus", shelfFungus(), props(Blocks.POPPY).mapColor(MapColor.SAND).offsetType(BlockBehaviour.OffsetType.XYZ));


    // Salt

    public static final Block SALT_BLOCK = register("salt_block", falling(0xFFFFFFFF), props(Blocks.SAND).mapColor(DyeColor.WHITE));
    public static final Block SALT_CRUST = register("salt_crust", block(), props(Blocks.DIRT).sound(SoundType.BASALT).mapColor(DyeColor.WHITE));

    public static final Block ROCKSALT = register("rocksalt", block(), props(Blocks.SANDSTONE).mapColor(DyeColor.WHITE));
    public static final Block ROCKSALT_SLAB = register("rocksalt_slab", slab(), props(ROCKSALT));
    public static final Block ROCKSALT_STAIRS = register("rocksalt_stairs", stairs(), props(ROCKSALT));
    public static final Block ROCKSALT_WALL = register("rocksalt_wall", wall(), props(ROCKSALT));

    public static final Block ROCKSALT_BRICKS = register("rocksalt_bricks", block(), props(ROCKSALT));
    public static final Block ROCKSALT_BRICK_SLAB = register("rocksalt_brick_slab", slab(), props(ROCKSALT));
    public static final Block ROCKSALT_BRICK_STAIRS = register("rocksalt_brick_stairs", stairs(), props(ROCKSALT));
    public static final Block ROCKSALT_BRICK_WALL = register("rocksalt_brick_wall", wall(), props(ROCKSALT));

    public static final Block SALT_CRYSTAL = register("salt_crystal", saltCrystal(), props(Blocks.AMETHYST_CLUSTER).lightLevel(state -> 0).strength(0.8F).mapColor(DyeColor.WHITE).sound(SoundType.CALCITE).noCollision());

    public static final Block SALT_ORE = register("salt_ore", xpDropping(1, 3), props(Blocks.COAL_ORE));
    public static final Block DEEPSLATE_SALT_ORE = register("deepslate_salt_ore", xpDropping(1, 3), props(Blocks.DEEPSLATE_COAL_ORE));
    public static final Block NETHER_SALT_ORE = register("nether_salt_ore", xpDropping(2, 5), props(Blocks.NETHER_QUARTZ_ORE));


    // INITIALISATION
    // =============================================

    public static void init() {
        // Register block types
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("stair"), SimpleStairBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("salt_crystal"), SaltCrystalBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("short_plant"), ShortPlantBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("hanging_short_plant"), HangingShortPlantBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("tall_plant"), TallPlantBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("partially_waterloggable_tall_plant"), PartiallyWaterloggableTallPlantBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("column_plant"), ColumnPlantBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("hanging_column_plant"), HangingColumnPlantBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("dripmoss"), DripmossBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("ash_layer"), MultilayerBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("diagonally_attachable"), DiagonallyAttachableBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("shelf_fungus"), ShelfFungusBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("segmented_flowers"), SegmentedFlowersBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("segmented_clovers"), SegmentedCloversBlock.CODEC);
        Registry.register(BuiltInRegistries.BLOCK_TYPE, Smptg.id("multiface_plant"), MultifacePlantBlock.CODEC);

        // Setup supported blocks
        BlockEntityType.SHELF.addValidBlock(ASPEN_SHELF);
        BlockEntityType.SHELF.addValidBlock(BEECH_SHELF);
        BlockEntityType.SHELF.addValidBlock(MAPLE_SHELF);
        BlockEntityType.SHELF.addValidBlock(REDWOOD_SHELF);
        BlockEntityType.SHELF.addValidBlock(DEAD_WOOD_SHELF);

        BlockEntityType.SIGN.addValidBlock(ASPEN_SIGN);
        BlockEntityType.SIGN.addValidBlock(ASPEN_WALL_SIGN);
        BlockEntityType.SIGN.addValidBlock(BEECH_SIGN);
        BlockEntityType.SIGN.addValidBlock(BEECH_WALL_SIGN);
        BlockEntityType.SIGN.addValidBlock(MAPLE_SIGN);
        BlockEntityType.SIGN.addValidBlock(MAPLE_WALL_SIGN);
        BlockEntityType.SIGN.addValidBlock(REDWOOD_SIGN);
        BlockEntityType.SIGN.addValidBlock(REDWOOD_WALL_SIGN);
        BlockEntityType.SIGN.addValidBlock(DEAD_WOOD_SIGN);
        BlockEntityType.SIGN.addValidBlock(DEAD_WOOD_WALL_SIGN);

        BlockEntityType.HANGING_SIGN.addValidBlock(ASPEN_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addValidBlock(ASPEN_WALL_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addValidBlock(BEECH_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addValidBlock(BEECH_WALL_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addValidBlock(MAPLE_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addValidBlock(MAPLE_WALL_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addValidBlock(REDWOOD_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addValidBlock(REDWOOD_WALL_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addValidBlock(DEAD_WOOD_HANGING_SIGN);
        BlockEntityType.HANGING_SIGN.addValidBlock(DEAD_WOOD_WALL_HANGING_SIGN);

        // Setup log stripping
        StrippableBlockRegistry.register(ASPEN_LOG, STRIPPED_ASPEN_LOG);
        StrippableBlockRegistry.register(ASPEN_WOOD, STRIPPED_ASPEN_WOOD);
        StrippableBlockRegistry.register(BEECH_LOG, STRIPPED_BEECH_LOG);
        StrippableBlockRegistry.register(BEECH_WOOD, STRIPPED_BEECH_WOOD);
        StrippableBlockRegistry.register(MAPLE_LOG, STRIPPED_MAPLE_LOG);
        StrippableBlockRegistry.register(MAPLE_WOOD, STRIPPED_MAPLE_WOOD);
        StrippableBlockRegistry.register(REDWOOD_LOG, STRIPPED_REDWOOD_LOG);
        StrippableBlockRegistry.register(REDWOOD_WOOD, STRIPPED_REDWOOD_WOOD);
        StrippableBlockRegistry.register(DEAD_LOG, STRIPPED_DEAD_LOG);
        StrippableBlockRegistry.register(DEAD_WOOD, STRIPPED_DEAD_WOOD);

        // TODO Composting, Fuel, Burning, etc.
    }



    // =====================================================================================================
    //   CONFIGURATION METHODS
    // =====================================================================================================



    // SHEARING
    // =============================================

    @SuppressWarnings("deprecation")
    public static void addShearsRules(List<Tool.Rule> rules) {
        // Method called from mixin to add custom shears logic

        var blockRegistry = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);

        rules.add(Tool.Rule.overrideSpeed(
                HolderSet.direct(PATCHMOSS.builtInRegistryHolder()),
                2
        ));
    }



    // TRANSLATION
    // =============================================

    public static void translate(Translator<Block> translator) {
        translator.name(ASPEN_LOG, "Aspen Log");
        translator.name(ASPEN_WOOD, "Aspen Wood");
        translator.name(STRIPPED_ASPEN_LOG, "Stripped Aspen Log");
        translator.name(STRIPPED_ASPEN_WOOD, "Stripped Aspen Wood");
        translator.name(ASPEN_LEAVES, "Aspen Leaves");
        translator.name(ASPEN_PLANKS, "Aspen Planks");
        translator.name(ASPEN_SLAB, "Aspen Slab");
        translator.name(ASPEN_STAIRS, "Aspen Stairs");
        translator.name(ASPEN_FENCE, "Aspen Fence");
        translator.name(ASPEN_FENCE_GATE, "Aspen Fence Gate");
        translator.name(ASPEN_PRESSURE_PLATE, "Aspen Pressure Plate");
        translator.name(ASPEN_BUTTON, "Aspen Button");
        translator.name(ASPEN_DOOR, "Aspen Door");
        translator.name(ASPEN_TRAPDOOR, "Aspen Trapdoor");
        translator.name(ASPEN_SHELF, "Aspen Shelf");
        translator.name(ASPEN_SIGN, "Aspen Sign");
        translator.name(ASPEN_HANGING_SIGN, "Aspen Hanging Sign");

        translator.name(BEECH_LOG, "Beech Log");
        translator.name(BEECH_WOOD, "Beech Wood");
        translator.name(STRIPPED_BEECH_LOG, "Stripped Beech Log");
        translator.name(STRIPPED_BEECH_WOOD, "Stripped Beech Wood");
        translator.name(BEECH_LEAVES, "Beech Leaves");
        translator.name(BEECH_PLANKS, "Beech Planks");
        translator.name(BEECH_SLAB, "Beech Slab");
        translator.name(BEECH_STAIRS, "Beech Stairs");
        translator.name(BEECH_FENCE, "Beech Fence");
        translator.name(BEECH_FENCE_GATE, "Beech Fence Gate");
        translator.name(BEECH_PRESSURE_PLATE, "Beech Pressure Plate");
        translator.name(BEECH_BUTTON, "Beech Button");
        translator.name(BEECH_DOOR, "Beech Door");
        translator.name(BEECH_TRAPDOOR, "Beech Trapdoor");
        translator.name(BEECH_SHELF, "Beech Shelf");
        translator.name(BEECH_SIGN, "Beech Sign");
        translator.name(BEECH_HANGING_SIGN, "Beech Hanging Sign");

        translator.name(MAPLE_LOG, "Maple Log");
        translator.name(MAPLE_WOOD, "Maple Wood");
        translator.name(STRIPPED_MAPLE_LOG, "Stripped Maple Log");
        translator.name(STRIPPED_MAPLE_WOOD, "Stripped Maple Wood");
        translator.name(RED_MAPLE_LEAVES, "Red Maple Leaves");
        translator.name(ORANGE_MAPLE_LEAVES, "Orange Maple Leaves");
        translator.name(YELLOW_MAPLE_LEAVES, "Yellow Maple Leaves");
        translator.name(MAPLE_PLANKS, "Maple Planks");
        translator.name(MAPLE_SLAB, "Maple Slab");
        translator.name(MAPLE_STAIRS, "Maple Stairs");
        translator.name(MAPLE_FENCE, "Maple Fence");
        translator.name(MAPLE_FENCE_GATE, "Maple Fence Gate");
        translator.name(MAPLE_PRESSURE_PLATE, "Maple Pressure Plate");
        translator.name(MAPLE_BUTTON, "Maple Button");
        translator.name(MAPLE_DOOR, "Maple Door");
        translator.name(MAPLE_TRAPDOOR, "Maple Trapdoor");
        translator.name(MAPLE_SHELF, "Maple Shelf");
        translator.name(MAPLE_SIGN, "Maple Sign");
        translator.name(MAPLE_HANGING_SIGN, "Maple Hanging Sign");

        translator.name(REDWOOD_LOG, "Redwood Log");
        translator.name(REDWOOD_WOOD, "Redwood Wood");
        translator.name(STRIPPED_REDWOOD_LOG, "Stripped Redwood Log");
        translator.name(STRIPPED_REDWOOD_WOOD, "Stripped Redwood Wood");
        translator.name(REDWOOD_LEAVES, "Redwood Leaves");
        translator.name(REDWOOD_PLANKS, "Redwood Planks");
        translator.name(REDWOOD_SLAB, "Redwood Slab");
        translator.name(REDWOOD_STAIRS, "Redwood Stairs");
        translator.name(REDWOOD_FENCE, "Redwood Fence");
        translator.name(REDWOOD_FENCE_GATE, "Redwood Fence Gate");
        translator.name(REDWOOD_PRESSURE_PLATE, "Redwood Pressure Plate");
        translator.name(REDWOOD_BUTTON, "Redwood Button");
        translator.name(REDWOOD_DOOR, "Redwood Door");
        translator.name(REDWOOD_TRAPDOOR, "Redwood Trapdoor");
        translator.name(REDWOOD_SHELF, "Redwood Shelf");
        translator.name(REDWOOD_SIGN, "Redwood Sign");
        translator.name(REDWOOD_HANGING_SIGN, "Redwood Hanging Sign");

        translator.name(DEAD_LOG, "Dead Log");
        translator.name(DEAD_WOOD, "Dead Wood");
        translator.name(STRIPPED_DEAD_LOG, "Stripped Dead Log");
        translator.name(STRIPPED_DEAD_WOOD, "Stripped Dead Wood");
        translator.name(DEAD_LEAVES, "Dead Leaves");
        translator.name(DEAD_WOOD_PLANKS, "Dead Wood Planks");
        translator.name(DEAD_WOOD_SLAB, "Dead Wood Slab");
        translator.name(DEAD_WOOD_STAIRS, "Dead Wood Stairs");
        translator.name(DEAD_WOOD_FENCE, "Dead Wood Fence");
        translator.name(DEAD_WOOD_FENCE_GATE, "Dead Wood Fence Gate");
        translator.name(DEAD_WOOD_PRESSURE_PLATE, "Dead Wood Pressure Plate");
        translator.name(DEAD_WOOD_BUTTON, "Dead Wood Button");
        translator.name(DEAD_WOOD_DOOR, "Dead Wood Door");
        translator.name(DEAD_WOOD_TRAPDOOR, "Dead Wood Trapdoor");
        translator.name(DEAD_WOOD_SHELF, "Dead Wood Shelf");
        translator.name(DEAD_WOOD_SIGN, "Dead Wood Sign");
        translator.name(DEAD_WOOD_HANGING_SIGN, "Dead Wood Hanging Sign");

        // Ash
        translator.name(BURNED_STEM, "Burned Stem");
        translator.name(BURNED_HYPHAE, "Burned Hyphae");

        translator.name(ASH_LAYER, "Ash Layer");
        translator.name(ASH_BLOCK, "Block of Ash");
        translator.name(PACKED_ASH, "Packed Ash");
        translator.name(ASH_BRICKS, "Ash Bricks");
        translator.name(ASH_BRICK_SLAB, "Ash Brick Slab");
        translator.name(ASH_BRICK_STAIRS, "Ash Brick Stairs");
        translator.name(ASHVINE, "Ashvine");
        translator.name(ASHCREEP, "Ashcreep");

        // Soils
        translator.name(MOSSY_DIRT, "Mossy Dirt");

        // Plants
        translator.name(CLOVERS, "Clovers");
        translator.name(GRASS_SPROUTS, "Grass Sprouts");
        translator.name(BARLEY, "Barley");
        translator.name(TALL_BARLEY, "Tall Barley");
        translator.name(CATTAIL, "Cattail");
        translator.name(TALL_CATTAIL, "Tall Cattail");
        translator.name(LAVENDER, "Lavender");
        translator.name(TALL_LAVENDER, "Tall Lavender");
        translator.name(CAVE_GRASS, "Cave Grass");
        translator.name(DRIPMOSS, "Dripmoss");
        translator.name(PATCHMOSS, "Patchmoss");
        translator.name(GLOBE_THISTLE, "Globe Thistle");

        translator.name(SHELF_FUNGUS, "Shelf Fungus");

        // Salt
        translator.name(SALT_BLOCK, "Block of Salt");
        translator.name(SALT_CRUST, "Salt Crust");

        translator.name(ROCKSALT, "Rocksalt");
        translator.name(ROCKSALT_SLAB, "Rocksalt Slab");
        translator.name(ROCKSALT_STAIRS, "Rocksalt Stairs");
        translator.name(ROCKSALT_WALL, "Rocksalt Wall");

        translator.name(ROCKSALT_BRICKS, "Rocksalt Bricks");
        translator.name(ROCKSALT_BRICK_SLAB, "Rocksalt Brick Slab");
        translator.name(ROCKSALT_BRICK_STAIRS, "Rocksalt Brick Stairs");
        translator.name(ROCKSALT_BRICK_WALL, "Rocksalt Brick Wall");

        translator.name(SALT_CRYSTAL, "Salt Crystal");

        translator.name(SALT_ORE, "Salt Ore");
        translator.name(DEEPSLATE_SALT_ORE, "Deepslate Salt Ore");
        translator.name(NETHER_SALT_ORE, "Nether Salt Ore");
    }



    // BLOCK COLORS
    // =============================================

    public static void colors(ColorRegistry colors) {
        colors.goldgreenFoliage(ASPEN_LEAVES, true);
        colors.redFoliage(RED_MAPLE_LEAVES, true);
        colors.goldenFoliage(ORANGE_MAPLE_LEAVES, true);
        colors.yellowFoliage(YELLOW_MAPLE_LEAVES, true);
        colors.solid(REDWOOD_LEAVES, 0x215931, true);
        colors.foliage(BEECH_LEAVES, true);
        colors.dryFoliage(DEAD_LEAVES, true);

        colors.grass(CLOVERS, true);
        colors.grass(GRASS_SPROUTS, true);
        colors.grass(CATTAIL, true);
        colors.grass(TALL_CATTAIL, true);
        colors.grass(LAVENDER, true);
        colors.grass(TALL_LAVENDER, true);
    }



    // ========================================================================================
    // Thought you were done? HAH NO
    // All block data now resides in the data source set, under ModBlockData.
    // Be sure to fill in block data there as well to properly generate resources.
    //
    // Also don't forget to add a block item in ModItems!
    // ========================================================================================



    // =====================================================================================================
    //   FACTORY METHODS
    // =====================================================================================================


    // GETTERS
    // =============================================

    private static BlockState tallCattail() {
        return TALL_CATTAIL.defaultBlockState();
    }

    private static BlockState tallBarley() {
        return TALL_BARLEY.defaultBlockState();
    }

    private static BlockState tallLavender() {
        return TALL_LAVENDER.defaultBlockState();
    }


    // FACTORIES
    // =============================================

    private static BlockFactory block() {
        return Block::new;
    }

    private static BlockFactory rotatedPillar() {
        return RotatedPillarBlock::new;
    }

    private static BlockFactory grassBlock() {
        return MossyDirtBlock::new;
    }

    private static BlockFactory grassyPlant() {
        return props -> new ShortPlantBlock(
                PlantConfig.of()
                        .canGrowOn(PlantConfig.GROW_ON_DIRT_SAND)
                        .size(14, 14),
                props
        );
    }

    private static BlockFactory grassyPlant(Supplier<BlockState> tallVersion) {
        return props -> new ShortPlantBlock(
                PlantConfig.of()
                        .canGrowOn(PlantConfig.GROW_ON_DIRT_SAND)
                        .bonemealBehavior(BonemealBehaviors.growIntoTallPlant(tallVersion))
                        .size(14, 14),
                props
        );
    }

    private static BlockFactory tallGrassyPlant() {
        return props -> new TallPlantBlock(
                PlantConfig.of()
                        .canGrowOn(PlantConfig.GROW_ON_DIRT_SAND),
                props
        );
    }

    private static BlockFactory partiallyWaterloggableTallGrassyPlant() {
        return props -> new PartiallyWaterloggableTallPlantBlock(
                PlantConfig.of()
                        .canGrowOn(PlantConfig.GROW_ON_DIRT_SAND),
                props
        );
    }

    private static BlockFactory clovers() {
        return props -> new SegmentedCloversBlock(
                PlantConfig.of()
                        .size(16, 3)
                        .bonemealBehavior(BonemealBehaviors.growSegmentedPlant()),
                props
        );
    }

    private static BlockFactory shelfFungus() {
        return ShelfFungusBlock::new;
    }

    private static BlockFactory dripmoss() {
        return DripmossBlock::new;
    }

    private static BlockFactory multifacePlant() {
        return MultifacePlantBlock::new;
    }

    private static BlockFactory grassSprouts() {
        return props -> new ShortPlantBlock(
                PlantConfig.of()
                        .canGrowOn(PlantConfig.GROW_ON_DIRT_SAND)
                        .size(14, 4)
                        .bonemealBehavior(BonemealBehaviors.growIntoShortPlant(Blocks.SHORT_GRASS::defaultBlockState)),
                props
        );
    }

    private static BlockFactory caveGrass() {
        return props -> new ShortPlantBlock(
                PlantConfig.of()
                        .canGrowOn(PlantConfig.GROW_ON_DIRT_SAND_STONE)
                        .size(14, 4),
                props
        );
    }

    private static BlockFactory ashcreep() {
        return props -> new ShortPlantBlock(
                PlantConfig.of()
                        .canGrowOn(PlantConfig.GROW_ON_ASH)
                        .size(14, 4),
                props
        );
    }

    private static BlockFactory ashvine() {
        return props -> new HangingColumnPlantBlock(
                PlantConfig.of()
                        .canGrowOn(PlantConfig.GROW_ON_STURDY_FACE)
                        .size(14, 13)
                        .bonemealBehavior(BonemealBehaviors.growColumnPlant(1, 4)),
                props
        );
    }

    private static BlockFactory saltCrystal() {
        return SaltCrystalBlock::new;
    }

    private static BlockFactory leaves(float particleChance) {
        return props -> new TintedParticleLeavesBlock(particleChance, props);
    }

    private static BlockFactory slab() {
        return SlabBlock::new;
    }

    private static BlockFactory stairs() {
        return SimpleStairBlock::new;
    }

    private static BlockFactory wall() {
        return WallBlock::new;
    }

    private static BlockFactory fence() {
        return FenceBlock::new;
    }

    private static BlockFactory fenceGate(WoodType type) {
        return props -> new FenceGateBlock(type, props);
    }

    private static BlockFactory pressurePlate(BlockSetType type) {
        return props -> new PressurePlateBlock(type, props);
    }

    private static BlockFactory button(BlockSetType type, int pressTicks) {
        return props -> new ButtonBlock(type, pressTicks, props);
    }

    private static BlockFactory door(BlockSetType type) {
        return props -> new DoorBlock(type, props);
    }

    private static BlockFactory trapdoor(BlockSetType type) {
        return props -> new TrapDoorBlock(type, props);
    }

    private static BlockFactory shelf() {
        return ShelfBlock::new;
    }

    private static BlockFactory wallSign(WoodType type) {
        return props -> new WallSignBlock(type, props);
    }

    private static BlockFactory standingSign(WoodType type) {
        return props -> new StandingSignBlock(type, props);
    }

    private static BlockFactory ceilingHangingSign(WoodType type) {
        return props -> new CeilingHangingSignBlock(type, props);
    }

    private static BlockFactory wallHangingSign(WoodType type) {
        return props -> new WallHangingSignBlock(type, props);
    }

    private static BlockFactory falling(int color) {
        return props -> new ColoredFallingBlock(new ColorRGBA(color), props);
    }

    private static BlockFactory multilayer() {
        return MultilayerBlock::new;
    }

    private static BlockFactory xpDropping(IntProvider xpRange) {
        return props -> new DropExperienceBlock(xpRange, props);
    }

    private static BlockFactory xpDropping(int xp) {
        return props -> new DropExperienceBlock(ConstantInt.of(xp), props);
    }

    private static BlockFactory xpDropping(int minXp, int maxXp) {
        return props -> new DropExperienceBlock(UniformInt.of(minXp, maxXp), props);
    }


    // PROPERTIES
    // =============================================

    private static BlockBehaviour.Properties props() {
        return BlockBehaviour.Properties.of();
    }

    private static BlockBehaviour.Properties props(Block block) {
        return BlockBehaviour.Properties.ofFullCopy(block);
    }


    private static BlockBehaviour.Properties logProps(MapColor topColor, MapColor sideColor, SoundType sound) {
        return props()
                .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2f)
                .sound(sound)
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties logProps(MapColor color, SoundType sound) {
        return props()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2f)
                .sound(sound)
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties planksProps(MapColor color, SoundType sound) {
        return props()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .strength(2f, 3f)
                .sound(sound)
                .ignitedByLava();
    }

    public static BlockBehaviour.Properties buttonProps() {
        return props()
                .noCollision()
                .strength(0.5F)
                .pushReaction(PushReaction.DESTROY);
    }

    public static BlockBehaviour.Properties pressurePlateProps(Block base) {
        return props()
                .mapColor(base.defaultMapColor())
                .forceSolidOn()
                .instrument(NoteBlockInstrument.BASS)
                .noCollision()
                .strength(0.5F)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY);
    }

    public static BlockBehaviour.Properties doorProps(Block base) {
        return props()
                .mapColor(base.defaultMapColor())
                .instrument(NoteBlockInstrument.BASS)
                .strength(3.0F)
                .noOcclusion()
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY);
    }

    public static BlockBehaviour.Properties trapdoorProps(Block base) {
        return props()
                .mapColor(base.defaultMapColor())
                .instrument(NoteBlockInstrument.BASS)
                .strength(3.0F)
                .noOcclusion()
                .isValidSpawn(Blocks::never)
                .ignitedByLava();
    }

    public static BlockBehaviour.Properties shelfProps(Block base) {
        return props()
                .mapColor(base.defaultMapColor())
                .instrument(NoteBlockInstrument.BASS)
                .sound(SoundType.SHELF)
                .ignitedByLava()
                .strength(2.0F, 3.0F);
    }

    public static BlockBehaviour.Properties signProps(Block base) {
        return props()
                .mapColor(base.defaultMapColor())
                .forceSolidOn()
                .instrument(NoteBlockInstrument.BASS)
                .noCollision()
                .strength(1.0F)
                .ignitedByLava();
    }

    private static BlockBehaviour.Properties wallVariantProps(Block block, boolean copyDescription) {
        BlockBehaviour.Properties base = block.properties();
        BlockBehaviour.Properties props = props().overrideLootTable(block.getLootTable());
        if (copyDescription) {
            props = props.overrideDescription(block.getDescriptionId());
        }

        return props;
    }

    private static BlockBehaviour.Properties wallSignProps(Block base) {
        return wallVariantProps(base, true)
                .mapColor(base.defaultMapColor())
                .forceSolidOn()
                .instrument(NoteBlockInstrument.BASS)
                .noCollision()
                .strength(1.0F)
                .ignitedByLava();
    }

    public static BlockBehaviour.Properties leavesProps(MapColor color, SoundType sound) {
        return BlockBehaviour.Properties.of()
                .mapColor(color)
                .strength(0.2f)
                .randomTicks()
                .sound(sound)
                .noOcclusion()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isSuffocating(Blocks::never)
                .isViewBlocking(Blocks::never)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
                .isRedstoneConductor(Blocks::never);
    }

    public static BlockBehaviour.Properties ashLayerProps() {
        return props()
                .mapColor(MapColor.COLOR_BLACK)
                .replaceable()
                .randomTicks()
                .strength(0.1F)
                .sound(SoundType.WART_BLOCK)
                .isViewBlocking((state, level, pos) -> state.getValue(MultilayerBlock.LAYERS) >= MultilayerBlock.MAX_HEIGHT)
                .pushReaction(PushReaction.DESTROY);
    }

    public static BlockBehaviour.Properties patchmossProps() {
        return props()
                .mapColor(MapColor.GRASS)
                .replaceable()
                .noCollision()
                .strength(0.2F)
                .sound(SoundType.MOSS_CARPET)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY);
    }


    // REGISTRY
    // =============================================

    private static Block register(String name, BlockFactory factory, BlockBehaviour.Properties properties) {
        var key = Smptg.key(Registries.BLOCK, name);
        return register(key, factory, properties);
    }

    private static Block register(ResourceKey<Block> key, BlockFactory factory, BlockBehaviour.Properties properties) {
        var block = factory.create(properties.setId(key));
        return Registry.register(BuiltInRegistries.BLOCK, key, block);
    }
}
