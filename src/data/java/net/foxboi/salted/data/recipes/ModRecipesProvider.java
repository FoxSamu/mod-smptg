package net.foxboi.salted.data.recipes;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.item.ModItemTags;
import net.foxboi.salted.common.item.ModItems;
import net.foxboi.summon.api.recipe.*;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture, Smptg.ID);
    }

    @Override
    public void setup(HolderLookup.Provider lookups, RecipeSink sink) {
        var stonecutting = new StonecuttingGraph();

        stonecutting.add(ModItems.LIMESTONE, ModItems.POLISHED_LIMESTONE);
        stonecutting.add(ModItems.POLISHED_LIMESTONE, ModItems.LIMESTONE_BRICKS);
        stonecutting.add(ModItems.LIMESTONE_BRICKS, ModItems.LIMESTONE_TILES);

        stonecutting.add(ModItems.LIMESTONE, ModItems.LIMESTONE_STAIRS);
        stonecutting.add(ModItems.LIMESTONE, ModItems.LIMESTONE_SLAB, 2);
        stonecutting.add(ModItems.LIMESTONE, ModItems.LIMESTONE_WALL);

        stonecutting.add(ModItems.POLISHED_LIMESTONE, ModItems.POLISHED_LIMESTONE_STAIRS);
        stonecutting.add(ModItems.POLISHED_LIMESTONE, ModItems.POLISHED_LIMESTONE_SLAB, 2);
        stonecutting.add(ModItems.POLISHED_LIMESTONE, ModItems.POLISHED_LIMESTONE_WALL);

        stonecutting.add(ModItems.LIMESTONE_BRICKS, ModItems.LIMESTONE_BRICK_STAIRS);
        stonecutting.add(ModItems.LIMESTONE_BRICKS, ModItems.LIMESTONE_BRICK_SLAB, 2);
        stonecutting.add(ModItems.LIMESTONE_BRICKS, ModItems.LIMESTONE_BRICK_WALL);

        stonecutting.add(ModItems.LIMESTONE_TILES, ModItems.LIMESTONE_TILE_STAIRS);
        stonecutting.add(ModItems.LIMESTONE_TILES, ModItems.LIMESTONE_TILE_SLAB, 2);
        stonecutting.add(ModItems.LIMESTONE_TILES, ModItems.LIMESTONE_TILE_WALL);

        stonecutting.add(ModItems.CRACKED_LIMESTONE_BRICKS, ModItems.CRACKED_LIMESTONE_BRICK_STAIRS);
        stonecutting.add(ModItems.CRACKED_LIMESTONE_BRICKS, ModItems.CRACKED_LIMESTONE_BRICK_SLAB, 2);
        stonecutting.add(ModItems.CRACKED_LIMESTONE_BRICKS, ModItems.CRACKED_LIMESTONE_BRICK_WALL);

        stonecutting.buildTable().forEach(e -> {
            stonecutting(e.to(), e.amount())
                    .input(e.from())
                    .unlockedBy(e.from())
                    .save(sink, Smptg.id(itemName(e.to()) + "_from_" + itemName(e.from()) + "_stonecutting"));
        });

        buildBricks(ModItems.LIMESTONE, ModItems.POLISHED_LIMESTONE).save(sink);
        buildBricks(ModItems.POLISHED_LIMESTONE, ModItems.LIMESTONE_BRICKS).save(sink);
        buildBricks(ModItems.LIMESTONE_BRICKS, ModItems.LIMESTONE_TILES).save(sink);
        buildSmelt(ModItems.LIMESTONE_BRICKS, 0.1f, ModItems.CRACKED_LIMESTONE_BRICKS).save(sink);

        buildStairs(ModItems.LIMESTONE, ModItems.LIMESTONE_STAIRS).save(sink);
        buildSlab(ModItems.LIMESTONE, ModItems.LIMESTONE_SLAB).save(sink);
        buildWall(ModItems.LIMESTONE, ModItems.LIMESTONE_WALL).save(sink);

        buildStairs(ModItems.POLISHED_LIMESTONE, ModItems.POLISHED_LIMESTONE_STAIRS).save(sink);
        buildSlab(ModItems.POLISHED_LIMESTONE, ModItems.POLISHED_LIMESTONE_SLAB).save(sink);
        buildWall(ModItems.POLISHED_LIMESTONE, ModItems.POLISHED_LIMESTONE_WALL).save(sink);

        buildStairs(ModItems.LIMESTONE_BRICKS, ModItems.LIMESTONE_BRICK_STAIRS).save(sink);
        buildSlab(ModItems.LIMESTONE_BRICKS, ModItems.LIMESTONE_BRICK_SLAB).save(sink);
        buildWall(ModItems.LIMESTONE_BRICKS, ModItems.LIMESTONE_BRICK_WALL).save(sink);

        buildStairs(ModItems.LIMESTONE_TILES, ModItems.LIMESTONE_TILE_STAIRS).save(sink);
        buildSlab(ModItems.LIMESTONE_TILES, ModItems.LIMESTONE_TILE_SLAB).save(sink);
        buildWall(ModItems.LIMESTONE_TILES, ModItems.LIMESTONE_TILE_WALL).save(sink);

        buildStairs(ModItems.CRACKED_LIMESTONE_BRICKS, ModItems.CRACKED_LIMESTONE_BRICK_STAIRS).save(sink);
        buildSlab(ModItems.CRACKED_LIMESTONE_BRICKS, ModItems.CRACKED_LIMESTONE_BRICK_SLAB).save(sink);
        buildWall(ModItems.CRACKED_LIMESTONE_BRICKS, ModItems.CRACKED_LIMESTONE_BRICK_WALL).save(sink);

        buildBrazier(ModItems.POLISHED_LIMESTONE, ItemTags.COALS, ModItems.LIMESTONE_BRAZIER).save(sink);
        buildBrazier(ModItems.POLISHED_LIMESTONE, ItemTags.SOUL_FIRE_BASE_BLOCKS, ModItems.SOUL_LIMESTONE_BRAZIER).save(sink);

        buildWood(ModItems.ASPEN_LOG, ModItems.ASPEN_WOOD).group("bark").save(sink);
        buildWood(ModItems.STRIPPED_ASPEN_LOG, ModItems.STRIPPED_ASPEN_WOOD).group("bark").save(sink);
        buildPlanks(ModItemTags.ASPEN_LOGS, ModItems.ASPEN_PLANKS).group("planks").save(sink);
        buildSlab(ModItems.ASPEN_PLANKS, ModItems.ASPEN_SLAB).group("wooden_slab").save(sink);
        buildStairs(ModItems.ASPEN_PLANKS, ModItems.ASPEN_STAIRS).group("wooden_stairs").save(sink);
        buildFence(ModItems.ASPEN_PLANKS, ModItems.ASPEN_FENCE).group("wooden_fence").save(sink);
        buildFenceGate(ModItems.ASPEN_PLANKS, ModItems.ASPEN_FENCE_GATE).group("wooden_fence_gate").save(sink);
        buildPressurePlate(ModItems.ASPEN_PLANKS, ModItems.ASPEN_PRESSURE_PLATE).group("wooden_pressue_plate").save(sink);
        buildButton(ModItems.ASPEN_PLANKS, ModItems.ASPEN_BUTTON).group("wooden_button").save(sink);
        buildWoodenDoor(ModItems.ASPEN_PLANKS, ModItems.ASPEN_DOOR).group("wooden_door").save(sink);
        buildWoodenTrapdoor(ModItems.ASPEN_PLANKS, ModItems.ASPEN_TRAPDOOR).group("wooden_trapdoor").save(sink);
        buildShelf(ModItems.STRIPPED_ASPEN_LOG, ModItems.ASPEN_SHELF).group("shelf").save(sink);
        buildSign(ModItems.ASPEN_PLANKS, ModItems.ASPEN_SIGN).group("wooden_sign").save(sink);
        buildHangingSign(ModItems.STRIPPED_ASPEN_LOG, ModItems.ASPEN_HANGING_SIGN).group("hanging_sign").save(sink);

        buildWood(ModItems.BEECH_LOG, ModItems.BEECH_WOOD).group("bark").save(sink);
        buildWood(ModItems.STRIPPED_BEECH_LOG, ModItems.STRIPPED_BEECH_WOOD).group("bark").save(sink);
        buildPlanks(ModItemTags.BEECH_LOGS, ModItems.BEECH_PLANKS).group("planks").save(sink);
        buildSlab(ModItems.BEECH_PLANKS, ModItems.BEECH_SLAB).group("wooden_slab").save(sink);
        buildStairs(ModItems.BEECH_PLANKS, ModItems.BEECH_STAIRS).group("wooden_stairs").save(sink);
        buildFence(ModItems.BEECH_PLANKS, ModItems.BEECH_FENCE).group("wooden_fence").save(sink);
        buildFenceGate(ModItems.BEECH_PLANKS, ModItems.BEECH_FENCE_GATE).group("wooden_fence_gate").save(sink);
        buildPressurePlate(ModItems.BEECH_PLANKS, ModItems.BEECH_PRESSURE_PLATE).group("wooden_pressue_plate").save(sink);
        buildButton(ModItems.BEECH_PLANKS, ModItems.BEECH_BUTTON).group("wooden_button").save(sink);
        buildWoodenDoor(ModItems.BEECH_PLANKS, ModItems.BEECH_DOOR).group("wooden_door").save(sink);
        buildWoodenTrapdoor(ModItems.BEECH_PLANKS, ModItems.BEECH_TRAPDOOR).group("wooden_trapdoor").save(sink);
        buildShelf(ModItems.STRIPPED_BEECH_LOG, ModItems.BEECH_SHELF).group("shelf").save(sink);
        buildSign(ModItems.BEECH_PLANKS, ModItems.BEECH_SIGN).group("wooden_sign").save(sink);
        buildHangingSign(ModItems.STRIPPED_BEECH_LOG, ModItems.BEECH_HANGING_SIGN).group("hanging_sign").save(sink);

        buildWood(ModItems.MAPLE_LOG, ModItems.MAPLE_WOOD).group("bark").save(sink);
        buildWood(ModItems.STRIPPED_MAPLE_LOG, ModItems.STRIPPED_MAPLE_WOOD).group("bark").save(sink);
        buildPlanks(ModItemTags.MAPLE_LOGS, ModItems.MAPLE_PLANKS).group("planks").save(sink);
        buildSlab(ModItems.MAPLE_PLANKS, ModItems.MAPLE_SLAB).group("wooden_slab").save(sink);
        buildStairs(ModItems.MAPLE_PLANKS, ModItems.MAPLE_STAIRS).group("wooden_stairs").save(sink);
        buildFence(ModItems.MAPLE_PLANKS, ModItems.MAPLE_FENCE).group("wooden_fence").save(sink);
        buildFenceGate(ModItems.MAPLE_PLANKS, ModItems.MAPLE_FENCE_GATE).group("wooden_fence_gate").save(sink);
        buildPressurePlate(ModItems.MAPLE_PLANKS, ModItems.MAPLE_PRESSURE_PLATE).group("wooden_pressue_plate").save(sink);
        buildButton(ModItems.MAPLE_PLANKS, ModItems.MAPLE_BUTTON).group("wooden_button").save(sink);
        buildWoodenDoor(ModItems.MAPLE_PLANKS, ModItems.MAPLE_DOOR).group("wooden_door").save(sink);
        buildWoodenTrapdoor(ModItems.MAPLE_PLANKS, ModItems.MAPLE_TRAPDOOR).group("wooden_trapdoor").save(sink);
        buildShelf(ModItems.STRIPPED_MAPLE_LOG, ModItems.MAPLE_SHELF).group("shelf").save(sink);
        buildSign(ModItems.MAPLE_PLANKS, ModItems.MAPLE_SIGN).group("wooden_sign").save(sink);
        buildHangingSign(ModItems.STRIPPED_MAPLE_LOG, ModItems.MAPLE_HANGING_SIGN).group("hanging_sign").save(sink);

        buildWood(ModItems.REDWOOD_LOG, ModItems.REDWOOD_WOOD).group("bark").save(sink);
        buildWood(ModItems.STRIPPED_REDWOOD_LOG, ModItems.STRIPPED_REDWOOD_WOOD).group("bark").save(sink);
        buildPlanks(ModItemTags.REDWOOD_LOGS, ModItems.REDWOOD_PLANKS).group("planks").save(sink);
        buildSlab(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_SLAB).group("wooden_slab").save(sink);
        buildStairs(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_STAIRS).group("wooden_stairs").save(sink);
        buildFence(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_FENCE).group("wooden_fence").save(sink);
        buildFenceGate(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_FENCE_GATE).group("wooden_fence_gate").save(sink);
        buildPressurePlate(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_PRESSURE_PLATE).group("wooden_pressue_plate").save(sink);
        buildButton(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_BUTTON).group("wooden_button").save(sink);
        buildWoodenDoor(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_DOOR).group("wooden_door").save(sink);
        buildWoodenTrapdoor(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_TRAPDOOR).group("wooden_trapdoor").save(sink);
        buildShelf(ModItems.STRIPPED_REDWOOD_LOG, ModItems.REDWOOD_SHELF).group("shelf").save(sink);
        buildSign(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_SIGN).group("wooden_sign").save(sink);
        buildHangingSign(ModItems.STRIPPED_REDWOOD_LOG, ModItems.REDWOOD_HANGING_SIGN).group("hanging_sign").save(sink);

        buildWood(ModItems.DEAD_LOG, ModItems.DEAD_WOOD).group("bark").save(sink);
        buildWood(ModItems.STRIPPED_DEAD_LOG, ModItems.STRIPPED_DEAD_WOOD).group("bark").save(sink);
        buildPlanks(ModItemTags.DEAD_LOGS, ModItems.DEAD_WOOD_PLANKS).group("planks").save(sink);
        buildSlab(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_SLAB).group("wooden_slab").save(sink);
        buildStairs(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_STAIRS).group("wooden_stairs").save(sink);
        buildFence(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_FENCE).group("wooden_fence").save(sink);
        buildFenceGate(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_FENCE_GATE).group("wooden_fence_gate").save(sink);
        buildPressurePlate(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_PRESSURE_PLATE).group("wooden_pressue_plate").save(sink);
        buildButton(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_BUTTON).group("wooden_button").save(sink);
        buildWoodenDoor(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_DOOR).group("wooden_door").save(sink);
        buildWoodenTrapdoor(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_TRAPDOOR).group("wooden_trapdoor").save(sink);
        buildShelf(ModItems.STRIPPED_DEAD_LOG, ModItems.DEAD_WOOD_SHELF).group("shelf").save(sink);
        buildSign(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_SIGN).group("wooden_sign").save(sink);
        buildHangingSign(ModItems.STRIPPED_DEAD_LOG, ModItems.DEAD_WOOD_HANGING_SIGN).group("hanging_sign").save(sink);

        buildWetten(ModItems.DRIED_PEAT, ModItems.PEAT).save(sink);
        buildSmelt(ModItems.PEAT, 0.1f, ModItems.DRIED_PEAT).save(sink);
        buildCoarseSubstrate(ModItems.PEAT, ModItems.COARSE_PEAT).save(sink);
    }

    private ShapedRecipeBuilder buildSlab(ItemLike input, ItemLike output) {
        return shaped(output, 6)
                .category(CraftingBookCategory.BUILDING)
                .pattern("###")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildStairs(ItemLike input, ItemLike output) {
        return shaped(output, 4)
                .category(CraftingBookCategory.BUILDING)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildWall(ItemLike input, ItemLike output) {
        return shaped(output, 6)
                .category(CraftingBookCategory.MISC)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildFence(ItemLike input, ItemLike output) {
        return shaped(output, 3)
                .category(CraftingBookCategory.MISC)
                .pattern("#/#")
                .pattern("#/#")
                .define('#', input)
                .define('/', Items.STICK)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildFenceGate(ItemLike input, ItemLike output) {
        return shaped(output, 1)
                .category(CraftingBookCategory.REDSTONE)
                .pattern("/#/")
                .pattern("/#/")
                .define('#', input)
                .define('/', Items.STICK)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildPressurePlate(ItemLike input, ItemLike output) {
        return shaped(output, 1)
                .category(CraftingBookCategory.REDSTONE)
                .pattern("##")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapelessRecipeBuilder buildButton(ItemLike input, ItemLike output) {
        return shapeless(output, 1)
                .category(CraftingBookCategory.REDSTONE)
                .requires(input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildWoodenDoor(ItemLike input, ItemLike output) {
        return shaped(output, 3)
                .category(CraftingBookCategory.REDSTONE)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildWoodenTrapdoor(ItemLike input, ItemLike output) {
        return shaped(output, 2)
                .category(CraftingBookCategory.REDSTONE)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildShelf(ItemLike input, ItemLike output) {
        return shaped(output, 6)
                .category(CraftingBookCategory.MISC)
                .pattern("###")
                .pattern("   ")
                .pattern("###")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildSign(ItemLike input, ItemLike output) {
        return shaped(output, 3)
                .category(CraftingBookCategory.MISC)
                .pattern("###")
                .pattern("###")
                .pattern(" / ")
                .define('#', input)
                .define('/', Items.STICK)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildHangingSign(ItemLike input, ItemLike output) {
        return shaped(output, 6)
                .category(CraftingBookCategory.MISC)
                .pattern("| |")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .define('|', Items.IRON_CHAIN)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildBoat(ItemLike input, ItemLike output) {
        return shaped(output, 1)
                .category(CraftingBookCategory.MISC)
                .pattern("# #")
                .pattern("###")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapelessRecipeBuilder buildChestBoat(ItemLike input, ItemLike output) {
        return shapeless(output, 1)
                .category(CraftingBookCategory.MISC)
                .requires(input)
                .requires(Items.CHEST)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildBrazier(ItemLike input, TagKey<Item> fuel, ItemLike output) {
        return shaped(output)
                .category(CraftingBookCategory.MISC)
                .pattern("F")
                .pattern("#")
                .define('F', fuel)
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildBricks(ItemLike input, ItemLike output) {
        return shaped(output, 4)
                .category(CraftingBookCategory.BUILDING)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildWood(ItemLike input, ItemLike output) {
        return shaped(output, 3)
                .category(CraftingBookCategory.BUILDING)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildCompact(ItemLike input, ItemLike output) {
        return shaped(output)
                .category(CraftingBookCategory.MISC)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapelessRecipeBuilder buildExpand(ItemLike input, ItemLike output) {
        return shapeless(output, 9)
                .category(CraftingBookCategory.MISC)
                .requires(input)
                .unlockedBy(input);
    }

    private ShapelessRecipeBuilder buildPlanks(TagKey<Item> input, ItemLike output) {
        return shapeless(output, 4)
                .category(CraftingBookCategory.BUILDING)
                .requires(input)
                .unlockedBy(input);
    }

    private ShapelessRecipeBuilder buildConvert(ItemLike input, ItemLike output, int amount) {
        return shapeless(output, amount)
                .category(CraftingBookCategory.MISC)
                .requires(input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildCoarseSubstrate(ItemLike input, ItemLike output) {
        return shaped(output, 4)
                .category(CraftingBookCategory.MISC)
                .pattern("#G")
                .pattern("G#")
                .define('#', input)
                .define('G', Items.GRAVEL)
                .unlockedBy(input);
    }

    private ShapelessRecipeBuilder buildWetten(ItemLike input, ItemLike output) {
        return shapeless(output, 1)
                .category(CraftingBookCategory.MISC)
                .requires(Items.WATER_BUCKET)
                .requires(input)
                .unlockedBy(input);
    }

    private CookingRecipeBuilder buildSmelt(ItemLike input, float xp, ItemLike output) {
        return smelting(output)
                .experience(xp)
                .category(CookingBookCategory.BLOCKS)
                .input(input)
                .unlockedBy(input);
    }

    @Override
    public String getName() {
        return "Recipes";
    }
}
