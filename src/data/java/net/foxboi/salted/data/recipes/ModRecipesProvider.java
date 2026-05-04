package net.foxboi.salted.data.recipes;

import java.util.concurrent.CompletableFuture;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.item.ModItemTags;
import net.foxboi.salted.common.item.ModItems;
import net.foxboi.salted.data.core.recipe.RecipeProvider;
import net.foxboi.salted.data.core.recipe.RecipeSink;
import net.foxboi.salted.data.core.recipe.ShapedRecipeBuilder;
import net.foxboi.salted.data.core.recipe.ShapelessRecipeBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.level.ItemLike;

public class ModRecipesProvider extends RecipeProvider {
    public ModRecipesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture, Smptg.ID);
    }

    @Override
    public void setup(HolderLookup.Provider lookups, RecipeSink sink) {
        // TODO Stonecutting

        buildWood(ModItems.ASPEN_LOG, ModItems.ASPEN_WOOD).save(sink);
        buildWood(ModItems.STRIPPED_ASPEN_LOG, ModItems.STRIPPED_ASPEN_WOOD).save(sink);
        buildPlanks(ModItemTags.ASPEN_LOGS, ModItems.ASPEN_PLANKS).save(sink);
        buildSlab(ModItems.ASPEN_PLANKS, ModItems.ASPEN_SLAB).save(sink);
        buildStairs(ModItems.ASPEN_PLANKS, ModItems.ASPEN_STAIRS).save(sink);
        buildFence(ModItems.ASPEN_PLANKS, ModItems.ASPEN_FENCE).save(sink);
        buildFenceGate(ModItems.ASPEN_PLANKS, ModItems.ASPEN_FENCE_GATE).save(sink);
        buildPressurePlate(ModItems.ASPEN_PLANKS, ModItems.ASPEN_PRESSURE_PLATE).save(sink);
        buildButton(ModItems.ASPEN_PLANKS, ModItems.ASPEN_BUTTON).save(sink);
        buildWoodenDoor(ModItems.ASPEN_PLANKS, ModItems.ASPEN_DOOR).save(sink);
        buildWoodenTrapdoor(ModItems.ASPEN_PLANKS, ModItems.ASPEN_TRAPDOOR).save(sink);
        buildShelf(ModItems.STRIPPED_ASPEN_LOG, ModItems.ASPEN_SHELF).save(sink);
        buildSign(ModItems.ASPEN_PLANKS, ModItems.ASPEN_SIGN).save(sink);
        buildHangingSign(ModItems.STRIPPED_ASPEN_LOG, ModItems.ASPEN_HANGING_SIGN).save(sink);

        buildWood(ModItems.BEECH_LOG, ModItems.BEECH_WOOD).save(sink);
        buildWood(ModItems.STRIPPED_BEECH_LOG, ModItems.STRIPPED_BEECH_WOOD).save(sink);
        buildPlanks(ModItemTags.BEECH_LOGS, ModItems.BEECH_PLANKS).save(sink);
        buildSlab(ModItems.BEECH_PLANKS, ModItems.BEECH_SLAB).save(sink);
        buildStairs(ModItems.BEECH_PLANKS, ModItems.BEECH_STAIRS).save(sink);
        buildFence(ModItems.BEECH_PLANKS, ModItems.BEECH_FENCE).save(sink);
        buildFenceGate(ModItems.BEECH_PLANKS, ModItems.BEECH_FENCE_GATE).save(sink);
        buildPressurePlate(ModItems.BEECH_PLANKS, ModItems.BEECH_PRESSURE_PLATE).save(sink);
        buildButton(ModItems.BEECH_PLANKS, ModItems.BEECH_BUTTON).save(sink);
        buildWoodenDoor(ModItems.BEECH_PLANKS, ModItems.BEECH_DOOR).save(sink);
        buildWoodenTrapdoor(ModItems.BEECH_PLANKS, ModItems.BEECH_TRAPDOOR).save(sink);
        buildShelf(ModItems.STRIPPED_BEECH_LOG, ModItems.BEECH_SHELF).save(sink);
        buildSign(ModItems.BEECH_PLANKS, ModItems.BEECH_SIGN).save(sink);
        buildHangingSign(ModItems.STRIPPED_BEECH_LOG, ModItems.BEECH_HANGING_SIGN).save(sink);

        buildWood(ModItems.MAPLE_LOG, ModItems.MAPLE_WOOD).save(sink);
        buildWood(ModItems.STRIPPED_MAPLE_LOG, ModItems.STRIPPED_MAPLE_WOOD).save(sink);
        buildPlanks(ModItemTags.MAPLE_LOGS, ModItems.MAPLE_PLANKS).save(sink);
        buildSlab(ModItems.MAPLE_PLANKS, ModItems.MAPLE_SLAB).save(sink);
        buildStairs(ModItems.MAPLE_PLANKS, ModItems.MAPLE_STAIRS).save(sink);
        buildFence(ModItems.MAPLE_PLANKS, ModItems.MAPLE_FENCE).save(sink);
        buildFenceGate(ModItems.MAPLE_PLANKS, ModItems.MAPLE_FENCE_GATE).save(sink);
        buildPressurePlate(ModItems.MAPLE_PLANKS, ModItems.MAPLE_PRESSURE_PLATE).save(sink);
        buildButton(ModItems.MAPLE_PLANKS, ModItems.MAPLE_BUTTON).save(sink);
        buildWoodenDoor(ModItems.MAPLE_PLANKS, ModItems.MAPLE_DOOR).save(sink);
        buildWoodenTrapdoor(ModItems.MAPLE_PLANKS, ModItems.MAPLE_TRAPDOOR).save(sink);
        buildShelf(ModItems.STRIPPED_MAPLE_LOG, ModItems.MAPLE_SHELF).save(sink);
        buildSign(ModItems.MAPLE_PLANKS, ModItems.MAPLE_SIGN).save(sink);
        buildHangingSign(ModItems.STRIPPED_MAPLE_LOG, ModItems.MAPLE_HANGING_SIGN).save(sink);

        buildWood(ModItems.REDWOOD_LOG, ModItems.REDWOOD_WOOD).save(sink);
        buildWood(ModItems.STRIPPED_REDWOOD_LOG, ModItems.STRIPPED_REDWOOD_WOOD).save(sink);
        buildPlanks(ModItemTags.REDWOOD_LOGS, ModItems.REDWOOD_PLANKS).save(sink);
        buildSlab(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_SLAB).save(sink);
        buildStairs(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_STAIRS).save(sink);
        buildFence(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_FENCE).save(sink);
        buildFenceGate(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_FENCE_GATE).save(sink);
        buildPressurePlate(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_PRESSURE_PLATE).save(sink);
        buildButton(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_BUTTON).save(sink);
        buildWoodenDoor(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_DOOR).save(sink);
        buildWoodenTrapdoor(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_TRAPDOOR).save(sink);
        buildShelf(ModItems.STRIPPED_REDWOOD_LOG, ModItems.REDWOOD_SHELF).save(sink);
        buildSign(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_SIGN).save(sink);
        buildHangingSign(ModItems.STRIPPED_REDWOOD_LOG, ModItems.REDWOOD_HANGING_SIGN).save(sink);

        buildWood(ModItems.DEAD_LOG, ModItems.DEAD_WOOD).save(sink);
        buildWood(ModItems.STRIPPED_DEAD_LOG, ModItems.STRIPPED_DEAD_WOOD).save(sink);
        buildPlanks(ModItemTags.DEAD_LOGS, ModItems.DEAD_WOOD_PLANKS).save(sink);
        buildSlab(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_SLAB).save(sink);
        buildStairs(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_STAIRS).save(sink);
        buildFence(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_FENCE).save(sink);
        buildFenceGate(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_FENCE_GATE).save(sink);
        buildPressurePlate(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_PRESSURE_PLATE).save(sink);
        buildButton(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_BUTTON).save(sink);
        buildWoodenDoor(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_DOOR).save(sink);
        buildWoodenTrapdoor(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_TRAPDOOR).save(sink);
        buildShelf(ModItems.STRIPPED_DEAD_LOG, ModItems.DEAD_WOOD_SHELF).save(sink);
        buildSign(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_SIGN).save(sink);
        buildHangingSign(ModItems.STRIPPED_DEAD_LOG, ModItems.DEAD_WOOD_HANGING_SIGN).save(sink);
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
                .category(CraftingBookCategory.BUILDING)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildFence(ItemLike input, ItemLike output) {
        return shaped(output, 3)
                .category(CraftingBookCategory.BUILDING)
                .pattern("#/#")
                .pattern("#/#")
                .define('#', input)
                .define('/', Items.STICK)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildFenceGate(ItemLike input, ItemLike output) {
        return shaped(output, 1)
                .category(CraftingBookCategory.BUILDING)
                .pattern("/#/")
                .pattern("/#/")
                .define('#', input)
                .define('/', Items.STICK)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildPressurePlate(ItemLike input, ItemLike output) {
        return shaped(output, 1)
                .category(CraftingBookCategory.BUILDING)
                .pattern("##")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapelessRecipeBuilder buildButton(ItemLike input, ItemLike output) {
        return shapeless(output, 1)
                .category(CraftingBookCategory.BUILDING)
                .requires(input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildWoodenDoor(ItemLike input, ItemLike output) {
        return shaped(output, 3)
                .category(CraftingBookCategory.BUILDING)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildWoodenTrapdoor(ItemLike input, ItemLike output) {
        return shaped(output, 2)
                .category(CraftingBookCategory.BUILDING)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildShelf(ItemLike input, ItemLike output) {
        return shaped(output, 6)
                .category(CraftingBookCategory.BUILDING)
                .pattern("###")
                .pattern("   ")
                .pattern("###")
                .define('#', input)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildSign(ItemLike input, ItemLike output) {
        return shaped(output, 3)
                .category(CraftingBookCategory.BUILDING)
                .pattern("###")
                .pattern("###")
                .pattern(" / ")
                .define('#', input)
                .define('/', Items.STICK)
                .unlockedBy(input);
    }

    private ShapedRecipeBuilder buildHangingSign(ItemLike input, ItemLike output) {
        return shaped(output, 6)
                .category(CraftingBookCategory.BUILDING)
                .pattern("| |")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .define('|', Items.IRON_CHAIN)
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

    @Override
    public String getName() {
        return "Recipes";
    }
}
