package net.foxboi.salted.data;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.biome.color.FoliageColorMap;
import net.foxboi.salted.data.loot.BlockDrops;
import net.foxboi.salted.data.model.BlockModels;
import net.foxboi.salted.data.shadercompat.ShaderCompat;
import net.foxboi.salted.data.tags.ToolTags;
import net.minecraft.world.level.DryFoliageColor;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Blocks;

import static net.foxboi.salted.common.block.ModBlocks.*;

public record ModBlockData() {

    // MODELS
    // =============================================

    public static void models(BlockModels models) {
        models.multilayer(ASH_LAYER, ASH_BLOCK);
        models.randomlyRotatedCube(ASH_BLOCK);
        models.cube(PACKED_ASH);

        models.randomlyRotatedCoveredBlock(MOSSY_DIRT, Blocks.DIRT);

        models.tintedFlowerBed(CLOVERS, ItemTint.grass());
        models.tintedCrossPlant(GRASS_SPROUTS, ItemTint.grass());
        models.crossPlant(BARLEY);
        models.tallCrossPlant(TALL_BARLEY);
        models.tallCrossPlant(GLOBE_THISTLE);
        models.layeredCrossPlant(CATTAIL, ItemTint.grass());
        models.tallLayeredCrossPlant(TALL_CATTAIL, ItemTint.grass());
        models.layeredCrossPlant(LAVENDER, ItemTint.grass());
        models.tallLayeredCrossPlant(TALL_LAVENDER, ItemTint.grass());
        models.crossPlant(CAVE_GRASS);
        models.columnCrossPlant(DRIPMOSS);
        models.crossPlant(ASHCREEP);
        models.columnCrossPlant(ASHVINE);
        models.multiface(PATCHMOSS);
        models.shelfFungus(SHELF_FUNGUS);
        models.emissiveCrossPlant(EMBERGRASS);
        models.emissiveCrossPlant(EMBERWEED);
        models.glowingFlatSegmentedPlant(EMBERS);

        models.wood(BURNED_STEM)
                .logWithHorizontal(BURNED_STEM)
                .wood(BURNED_HYPHAE);

        models.family(ASH_BRICKS)
                .slab(ASH_BRICK_SLAB)
                .stairs(ASH_BRICK_STAIRS);

        models.family(ASPEN_PLANKS)
                .log(ASPEN_LOG)
                .wood(ASPEN_WOOD)
                .strippedLog(STRIPPED_ASPEN_LOG)
                .strippedWood(STRIPPED_ASPEN_WOOD)
                .slab(ASPEN_SLAB)
                .stairs(ASPEN_STAIRS)
                .fence(ASPEN_FENCE)
                .fenceGate(ASPEN_FENCE_GATE)
                .pressurePlate(ASPEN_PRESSURE_PLATE)
                .button(ASPEN_BUTTON)
                .door(ASPEN_DOOR)
                .trapdoor(ASPEN_TRAPDOOR)
                .shelf(ASPEN_SHELF)
                .sign(ASPEN_SIGN, ASPEN_WALL_SIGN)
                .hangingSign(ASPEN_HANGING_SIGN, ASPEN_WALL_HANGING_SIGN);

        models.family(BEECH_PLANKS)
                .log(BEECH_LOG)
                .wood(BEECH_WOOD)
                .strippedLog(STRIPPED_BEECH_LOG)
                .strippedWood(STRIPPED_BEECH_WOOD)
                .slab(BEECH_SLAB)
                .stairs(BEECH_STAIRS)
                .fence(BEECH_FENCE)
                .fenceGate(BEECH_FENCE_GATE)
                .pressurePlate(BEECH_PRESSURE_PLATE)
                .button(BEECH_BUTTON)
                .door(BEECH_DOOR)
                .trapdoor(BEECH_TRAPDOOR)
                .shelf(BEECH_SHELF)
                .sign(BEECH_SIGN, BEECH_WALL_SIGN)
                .hangingSign(BEECH_HANGING_SIGN, BEECH_WALL_HANGING_SIGN);

        models.family(MAPLE_PLANKS)
                .log(MAPLE_LOG)
                .wood(MAPLE_WOOD)
                .strippedLog(STRIPPED_MAPLE_LOG)
                .strippedWood(STRIPPED_MAPLE_WOOD)
                .slab(MAPLE_SLAB)
                .stairs(MAPLE_STAIRS)
                .fence(MAPLE_FENCE)
                .fenceGate(MAPLE_FENCE_GATE)
                .pressurePlate(MAPLE_PRESSURE_PLATE)
                .button(MAPLE_BUTTON)
                .door(MAPLE_DOOR)
                .trapdoor(MAPLE_TRAPDOOR)
                .shelf(MAPLE_SHELF)
                .sign(MAPLE_SIGN, MAPLE_WALL_SIGN)
                .hangingSign(MAPLE_HANGING_SIGN, MAPLE_WALL_HANGING_SIGN);

        models.family(REDWOOD_PLANKS)
                .log(REDWOOD_LOG)
                .wood(REDWOOD_WOOD)
                .strippedLog(STRIPPED_REDWOOD_LOG)
                .strippedWood(STRIPPED_REDWOOD_WOOD)
                .slab(REDWOOD_SLAB)
                .stairs(REDWOOD_STAIRS)
                .fence(REDWOOD_FENCE)
                .fenceGate(REDWOOD_FENCE_GATE)
                .pressurePlate(REDWOOD_PRESSURE_PLATE)
                .button(REDWOOD_BUTTON)
                .door(REDWOOD_DOOR)
                .trapdoor(REDWOOD_TRAPDOOR)
                .shelf(REDWOOD_SHELF)
                .sign(REDWOOD_SIGN, REDWOOD_WALL_SIGN)
                .hangingSign(REDWOOD_HANGING_SIGN, REDWOOD_WALL_HANGING_SIGN);

        models.family(DEAD_WOOD_PLANKS)
                .log(DEAD_LOG)
                .wood(DEAD_WOOD)
                .strippedLog(STRIPPED_DEAD_LOG)
                .strippedWood(STRIPPED_DEAD_WOOD)
                .slab(DEAD_WOOD_SLAB)
                .stairs(DEAD_WOOD_STAIRS)
                .fence(DEAD_WOOD_FENCE)
                .fenceGate(DEAD_WOOD_FENCE_GATE)
                .pressurePlate(DEAD_WOOD_PRESSURE_PLATE)
                .button(DEAD_WOOD_BUTTON)
                .door(DEAD_WOOD_DOOR)
                .trapdoor(DEAD_WOOD_TRAPDOOR)
                .shelf(DEAD_WOOD_SHELF)
                .sign(DEAD_WOOD_SIGN, DEAD_WOOD_WALL_SIGN)
                .hangingSign(DEAD_WOOD_HANGING_SIGN, DEAD_WOOD_WALL_HANGING_SIGN);

        models.tintedLeaves(ASPEN_LEAVES, FoliageColorMap.GOLDGREEN_DEFAULT);
        models.tintedLeaves(BEECH_LEAVES, FoliageColor.FOLIAGE_DEFAULT);
        models.mapleLeaves(RED_MAPLE_LEAVES, FoliageColorMap.RED_DEFAULT);
        models.mapleLeaves(ORANGE_MAPLE_LEAVES, FoliageColorMap.GOLDEN_DEFAULT);
        models.mapleLeaves(YELLOW_MAPLE_LEAVES, FoliageColorMap.YELLOW_DEFAULT);
        models.tintedLeaves(REDWOOD_LEAVES, 0x215931);
        models.tintedLeaves(DEAD_LEAVES, DryFoliageColor.FOLIAGE_DRY_DEFAULT);
    }


    // TOOLS
    // =============================================

    public static void tools(ToolTags tools) {
        tools.axe(ASPEN_LOG);
        tools.axe(ASPEN_WOOD);
        tools.axe(STRIPPED_ASPEN_LOG);
        tools.axe(STRIPPED_ASPEN_WOOD);
        tools.hoe(ASPEN_LEAVES);
        tools.axe(ASPEN_PLANKS);
        tools.axe(ASPEN_SLAB);
        tools.axe(ASPEN_STAIRS);
        tools.axe(ASPEN_FENCE);
        tools.axe(ASPEN_FENCE_GATE);
        tools.axe(ASPEN_PRESSURE_PLATE);
        tools.axe(ASPEN_BUTTON);
        tools.axe(ASPEN_DOOR);
        tools.axe(ASPEN_TRAPDOOR);
        tools.axe(ASPEN_SHELF);
        tools.axe(ASPEN_SIGN);
        tools.axe(ASPEN_WALL_SIGN);
        tools.axe(ASPEN_HANGING_SIGN);
        tools.axe(ASPEN_WALL_HANGING_SIGN);

        tools.axe(BEECH_LOG);
        tools.axe(BEECH_WOOD);
        tools.axe(STRIPPED_BEECH_LOG);
        tools.axe(STRIPPED_BEECH_WOOD);
        tools.hoe(BEECH_LEAVES);
        tools.axe(BEECH_PLANKS);
        tools.axe(BEECH_SLAB);
        tools.axe(BEECH_STAIRS);
        tools.axe(BEECH_FENCE);
        tools.axe(BEECH_FENCE_GATE);
        tools.axe(BEECH_PRESSURE_PLATE);
        tools.axe(BEECH_BUTTON);
        tools.axe(BEECH_DOOR);
        tools.axe(BEECH_TRAPDOOR);
        tools.axe(BEECH_SHELF);
        tools.axe(BEECH_SIGN);
        tools.axe(BEECH_WALL_SIGN);
        tools.axe(BEECH_HANGING_SIGN);
        tools.axe(BEECH_WALL_HANGING_SIGN);

        tools.axe(MAPLE_LOG);
        tools.axe(MAPLE_WOOD);
        tools.axe(STRIPPED_MAPLE_LOG);
        tools.axe(STRIPPED_MAPLE_WOOD);
        tools.hoe(RED_MAPLE_LEAVES);
        tools.hoe(ORANGE_MAPLE_LEAVES);
        tools.hoe(YELLOW_MAPLE_LEAVES);
        tools.axe(MAPLE_PLANKS);
        tools.axe(MAPLE_SLAB);
        tools.axe(MAPLE_STAIRS);
        tools.axe(MAPLE_FENCE);
        tools.axe(MAPLE_FENCE_GATE);
        tools.axe(MAPLE_PRESSURE_PLATE);
        tools.axe(MAPLE_BUTTON);
        tools.axe(MAPLE_DOOR);
        tools.axe(MAPLE_TRAPDOOR);
        tools.axe(MAPLE_SHELF);
        tools.axe(MAPLE_SIGN);
        tools.axe(MAPLE_WALL_SIGN);
        tools.axe(MAPLE_HANGING_SIGN);
        tools.axe(MAPLE_WALL_HANGING_SIGN);

        tools.axe(REDWOOD_LOG);
        tools.axe(REDWOOD_WOOD);
        tools.axe(STRIPPED_REDWOOD_LOG);
        tools.axe(STRIPPED_REDWOOD_WOOD);
        tools.hoe(REDWOOD_LEAVES);
        tools.axe(REDWOOD_PLANKS);
        tools.axe(REDWOOD_SLAB);
        tools.axe(REDWOOD_STAIRS);
        tools.axe(REDWOOD_FENCE);
        tools.axe(REDWOOD_FENCE_GATE);
        tools.axe(REDWOOD_PRESSURE_PLATE);
        tools.axe(REDWOOD_BUTTON);
        tools.axe(REDWOOD_DOOR);
        tools.axe(REDWOOD_TRAPDOOR);
        tools.axe(REDWOOD_SHELF);
        tools.axe(REDWOOD_SIGN);
        tools.axe(REDWOOD_WALL_SIGN);
        tools.axe(REDWOOD_HANGING_SIGN);
        tools.axe(REDWOOD_WALL_HANGING_SIGN);

        tools.axe(DEAD_LOG);
        tools.axe(DEAD_WOOD);
        tools.axe(STRIPPED_DEAD_LOG);
        tools.axe(STRIPPED_DEAD_WOOD);
        tools.hoe(DEAD_LEAVES);
        tools.axe(DEAD_WOOD_PLANKS);
        tools.axe(DEAD_WOOD_SLAB);
        tools.axe(DEAD_WOOD_STAIRS);
        tools.axe(DEAD_WOOD_FENCE);
        tools.axe(DEAD_WOOD_FENCE_GATE);
        tools.axe(DEAD_WOOD_PRESSURE_PLATE);
        tools.axe(DEAD_WOOD_BUTTON);
        tools.axe(DEAD_WOOD_DOOR);
        tools.axe(DEAD_WOOD_TRAPDOOR);
        tools.axe(DEAD_WOOD_SHELF);
        tools.axe(DEAD_WOOD_SIGN);
        tools.axe(DEAD_WOOD_WALL_SIGN);
        tools.axe(DEAD_WOOD_HANGING_SIGN);
        tools.axe(DEAD_WOOD_WALL_HANGING_SIGN);

        tools.axe(BURNED_STEM);
        tools.axe(BURNED_HYPHAE);
        tools.shovel(ASH_LAYER);
        tools.shovel(ASH_BLOCK);
        tools.pickaxe(PACKED_ASH);
        tools.pickaxe(ASH_BRICKS);
        tools.pickaxe(ASH_BRICK_SLAB);
        tools.pickaxe(ASH_BRICK_STAIRS);

        tools.shovel(MOSSY_DIRT);
    }


    // DROPS
    // =============================================

    public static void drops(BlockDrops drops) {
        // TODO Leaves

        drops.self(ASPEN_LOG);
        drops.self(ASPEN_WOOD);
        drops.self(STRIPPED_ASPEN_LOG);
        drops.self(STRIPPED_ASPEN_WOOD);
        drops.self(ASPEN_PLANKS);
        drops.slab(ASPEN_SLAB);
        drops.self(ASPEN_STAIRS);
        drops.self(ASPEN_FENCE);
        drops.self(ASPEN_FENCE_GATE);
        drops.self(ASPEN_PRESSURE_PLATE);
        drops.self(ASPEN_BUTTON);
        drops.door(ASPEN_DOOR);
        drops.self(ASPEN_TRAPDOOR);
        drops.self(ASPEN_SHELF);
        drops.self(ASPEN_SIGN);
        drops.self(ASPEN_HANGING_SIGN);

        drops.self(BEECH_LOG);
        drops.self(BEECH_WOOD);
        drops.self(STRIPPED_BEECH_LOG);
        drops.self(STRIPPED_BEECH_WOOD);
        drops.self(BEECH_PLANKS);
        drops.slab(BEECH_SLAB);
        drops.self(BEECH_STAIRS);
        drops.self(BEECH_FENCE);
        drops.self(BEECH_FENCE_GATE);
        drops.self(BEECH_PRESSURE_PLATE);
        drops.self(BEECH_BUTTON);
        drops.door(BEECH_DOOR);
        drops.self(BEECH_TRAPDOOR);
        drops.self(BEECH_SHELF);
        drops.self(BEECH_SIGN);
        drops.self(BEECH_HANGING_SIGN);

        drops.self(MAPLE_LOG);
        drops.self(MAPLE_WOOD);
        drops.self(STRIPPED_MAPLE_LOG);
        drops.self(STRIPPED_MAPLE_WOOD);
        drops.self(MAPLE_PLANKS);
        drops.slab(MAPLE_SLAB);
        drops.self(MAPLE_STAIRS);
        drops.self(MAPLE_FENCE);
        drops.self(MAPLE_FENCE_GATE);
        drops.self(MAPLE_PRESSURE_PLATE);
        drops.self(MAPLE_BUTTON);
        drops.door(MAPLE_DOOR);
        drops.self(MAPLE_TRAPDOOR);
        drops.self(MAPLE_SHELF);
        drops.self(MAPLE_SIGN);
        drops.self(MAPLE_HANGING_SIGN);

        drops.self(REDWOOD_LOG);
        drops.self(REDWOOD_WOOD);
        drops.self(STRIPPED_REDWOOD_LOG);
        drops.self(STRIPPED_REDWOOD_WOOD);
        drops.self(REDWOOD_PLANKS);
        drops.slab(REDWOOD_SLAB);
        drops.self(REDWOOD_STAIRS);
        drops.self(REDWOOD_FENCE);
        drops.self(REDWOOD_FENCE_GATE);
        drops.self(REDWOOD_PRESSURE_PLATE);
        drops.self(REDWOOD_BUTTON);
        drops.door(REDWOOD_DOOR);
        drops.self(REDWOOD_TRAPDOOR);
        drops.self(REDWOOD_SHELF);
        drops.self(REDWOOD_SIGN);
        drops.self(REDWOOD_HANGING_SIGN);

        drops.self(DEAD_LOG);
        drops.self(DEAD_WOOD);
        drops.self(STRIPPED_DEAD_LOG);
        drops.self(STRIPPED_DEAD_WOOD);
        drops.self(DEAD_WOOD_PLANKS);
        drops.slab(DEAD_WOOD_SLAB);
        drops.self(DEAD_WOOD_STAIRS);
        drops.self(DEAD_WOOD_FENCE);
        drops.self(DEAD_WOOD_FENCE_GATE);
        drops.self(DEAD_WOOD_PRESSURE_PLATE);
        drops.self(DEAD_WOOD_BUTTON);
        drops.door(DEAD_WOOD_DOOR);
        drops.self(DEAD_WOOD_TRAPDOOR);
        drops.self(DEAD_WOOD_SHELF);
        drops.self(DEAD_WOOD_SIGN);
        drops.self(DEAD_WOOD_HANGING_SIGN);

        drops.self(BURNED_STEM);
        drops.self(BURNED_HYPHAE);
        drops.self(ASH_LAYER);
        drops.self(ASH_BLOCK);
        drops.self(PACKED_ASH);
        drops.self(ASH_BRICKS);
        drops.slab(ASH_BRICK_SLAB);
        drops.self(ASH_BRICK_STAIRS);
        drops.self(ASHVINE);
        drops.selfIfShears(ASHCREEP);
        drops.selfIfShears(EMBERGRASS);
        drops.selfIfShears(EMBERWEED);
        drops.segmented(EMBERS);


        drops.selfIfSilkTouch(MOSSY_DIRT, Blocks.DIRT);

        drops.segmented(CLOVERS);
        drops.selfIfShears(GRASS_SPROUTS);
        drops.barley(BARLEY);
        drops.tallBarley(TALL_BARLEY, BARLEY);
        drops.self(CATTAIL);
        drops.selfIfLower(TALL_CATTAIL);
        drops.self(LAVENDER);
        drops.selfIfLower(TALL_LAVENDER);
        drops.selfIfShears(CAVE_GRASS);
        drops.self(DRIPMOSS);
        drops.multiface(PATCHMOSS);
        drops.self(SHELF_FUNGUS);
        drops.selfIfLower(GLOBE_THISTLE);
    }


    // SHADER COMPAT
    // =============================================

    public static void shadercompat(ShaderCompat compat) {
        compat.leaves(ASPEN_LEAVES);
        compat.leaves(RED_MAPLE_LEAVES);
        compat.leaves(ORANGE_MAPLE_LEAVES);
        compat.leaves(YELLOW_MAPLE_LEAVES);
        compat.leaves(REDWOOD_LEAVES);
        compat.leaves(BEECH_LEAVES);
        compat.leaves(DEAD_LEAVES);

        compat.plant(CLOVERS);
        compat.plant(GRASS_SPROUTS);
        compat.plant(CATTAIL);
        compat.plant(LAVENDER);
        compat.plant(BARLEY);
        compat.plant(CAVE_GRASS);
        compat.plant(ASHCREEP);
        compat.plant(EMBERWEED);
        compat.plant(EMBERGRASS);

        compat.tallPlant(TALL_CATTAIL);
        compat.tallPlant(TALL_LAVENDER);
        compat.tallPlant(TALL_BARLEY);
        compat.tallPlant(GLOBE_THISTLE);

        compat.hangingPlant(ASHVINE);
        compat.hangingPlant(DRIPMOSS);
    }
}
