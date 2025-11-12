package net.foxboi.salted.data.recipes;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.item.ModItemTags;
import net.foxboi.salted.common.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class RecipesProvider extends RecipeProvider {
    protected RecipesProvider(HolderLookup.Provider lookup, RecipeOutput output) {
        super(lookup, output);
    }

    @Override
    public void buildRecipes() {
        // TODO Stonecutting

        buildConvert(ModItems.ROCKSALT_CHUNK, ModItems.SALT, 2).save(output, Smptg.sid("salt_from_chunk"));
        buildConvert(ModItems.SALT_CRUST, ModItems.SALT, 12).save(output, Smptg.sid("salt_from_crust"));

        buildExpand(ModItems.SALT, ModItems.PINCH_OF_SALT).save(output, Smptg.sid("pinch_from_salt"));
        buildCompact(ModItems.PINCH_OF_SALT, ModItems.SALT).save(output, Smptg.sid("salt_from_pinch"));

        buildExpand(ModItems.ROCKSALT, ModItems.ROCKSALT_CHUNK).save(output, Smptg.sid("chunks_from_rocksalt"));
        buildCompact(ModItems.ROCKSALT_CHUNK, ModItems.ROCKSALT).save(output, Smptg.sid("rocksalt_from_chunks"));

        buildExpand(ModItems.SALT_BLOCK, ModItems.SALT).save(output, Smptg.sid("salt_from_block"));
        buildCompact(ModItems.SALT, ModItems.SALT_BLOCK).save(output, Smptg.sid("block_from_salt"));

        buildBricks(ModItems.ROCKSALT, ModItems.ROCKSALT_BRICKS).save(output);

        buildSlab(ModItems.ROCKSALT, ModItems.ROCKSALT_SLAB).save(output);
        buildStairs(ModItems.ROCKSALT, ModItems.ROCKSALT_STAIRS).save(output);
        buildWall(ModItems.ROCKSALT, ModItems.ROCKSALT_WALL).save(output);

        buildSlab(ModItems.ROCKSALT_BRICKS, ModItems.ROCKSALT_BRICK_SLAB).save(output);
        buildStairs(ModItems.ROCKSALT_BRICKS, ModItems.ROCKSALT_BRICK_STAIRS).save(output);
        buildWall(ModItems.ROCKSALT_BRICKS, ModItems.ROCKSALT_BRICK_WALL).save(output);

        buildWood(ModItems.ASPEN_LOG, ModItems.ASPEN_WOOD).save(output);
        buildWood(ModItems.STRIPPED_ASPEN_LOG, ModItems.STRIPPED_ASPEN_WOOD).save(output);
        buildPlanks(ModItemTags.ASPEN_LOGS, ModItems.ASPEN_PLANKS).save(output);
        buildSlab(ModItems.ASPEN_PLANKS, ModItems.ASPEN_SLAB).save(output);
        buildStairs(ModItems.ASPEN_PLANKS, ModItems.ASPEN_STAIRS).save(output);
        buildFence(ModItems.ASPEN_PLANKS, ModItems.ASPEN_FENCE).save(output);
        buildFenceGate(ModItems.ASPEN_PLANKS, ModItems.ASPEN_FENCE_GATE).save(output);
        buildPressurePlate(ModItems.ASPEN_PLANKS, ModItems.ASPEN_PRESSURE_PLATE).save(output);
        buildButton(ModItems.ASPEN_PLANKS, ModItems.ASPEN_BUTTON).save(output);
        buildWoodenDoor(ModItems.ASPEN_PLANKS, ModItems.ASPEN_DOOR).save(output);
        buildWoodenTrapdoor(ModItems.ASPEN_PLANKS, ModItems.ASPEN_TRAPDOOR).save(output);
        buildShelf(ModItems.STRIPPED_ASPEN_LOG, ModItems.ASPEN_SHELF).save(output);
        buildSign(ModItems.ASPEN_PLANKS, ModItems.ASPEN_SIGN).save(output);
        buildHangingSign(ModItems.STRIPPED_ASPEN_LOG, ModItems.ASPEN_HANGING_SIGN).save(output);

        buildWood(ModItems.BEECH_LOG, ModItems.BEECH_WOOD).save(output);
        buildWood(ModItems.STRIPPED_BEECH_LOG, ModItems.STRIPPED_BEECH_WOOD).save(output);
        buildPlanks(ModItemTags.BEECH_LOGS, ModItems.BEECH_PLANKS).save(output);
        buildSlab(ModItems.BEECH_PLANKS, ModItems.BEECH_SLAB).save(output);
        buildStairs(ModItems.BEECH_PLANKS, ModItems.BEECH_STAIRS).save(output);
        buildFence(ModItems.BEECH_PLANKS, ModItems.BEECH_FENCE).save(output);
        buildFenceGate(ModItems.BEECH_PLANKS, ModItems.BEECH_FENCE_GATE).save(output);
        buildPressurePlate(ModItems.BEECH_PLANKS, ModItems.BEECH_PRESSURE_PLATE).save(output);
        buildButton(ModItems.BEECH_PLANKS, ModItems.BEECH_BUTTON).save(output);
        buildWoodenDoor(ModItems.BEECH_PLANKS, ModItems.BEECH_DOOR).save(output);
        buildWoodenTrapdoor(ModItems.BEECH_PLANKS, ModItems.BEECH_TRAPDOOR).save(output);
        buildShelf(ModItems.STRIPPED_BEECH_LOG, ModItems.BEECH_SHELF).save(output);
        buildSign(ModItems.BEECH_PLANKS, ModItems.BEECH_SIGN).save(output);
        buildHangingSign(ModItems.STRIPPED_BEECH_LOG, ModItems.BEECH_HANGING_SIGN).save(output);

        buildWood(ModItems.MAPLE_LOG, ModItems.MAPLE_WOOD).save(output);
        buildWood(ModItems.STRIPPED_MAPLE_LOG, ModItems.STRIPPED_MAPLE_WOOD).save(output);
        buildPlanks(ModItemTags.MAPLE_LOGS, ModItems.MAPLE_PLANKS).save(output);
        buildSlab(ModItems.MAPLE_PLANKS, ModItems.MAPLE_SLAB).save(output);
        buildStairs(ModItems.MAPLE_PLANKS, ModItems.MAPLE_STAIRS).save(output);
        buildFence(ModItems.MAPLE_PLANKS, ModItems.MAPLE_FENCE).save(output);
        buildFenceGate(ModItems.MAPLE_PLANKS, ModItems.MAPLE_FENCE_GATE).save(output);
        buildPressurePlate(ModItems.MAPLE_PLANKS, ModItems.MAPLE_PRESSURE_PLATE).save(output);
        buildButton(ModItems.MAPLE_PLANKS, ModItems.MAPLE_BUTTON).save(output);
        buildWoodenDoor(ModItems.MAPLE_PLANKS, ModItems.MAPLE_DOOR).save(output);
        buildWoodenTrapdoor(ModItems.MAPLE_PLANKS, ModItems.MAPLE_TRAPDOOR).save(output);
        buildShelf(ModItems.STRIPPED_MAPLE_LOG, ModItems.MAPLE_SHELF).save(output);
        buildSign(ModItems.MAPLE_PLANKS, ModItems.MAPLE_SIGN).save(output);
        buildHangingSign(ModItems.STRIPPED_MAPLE_LOG, ModItems.MAPLE_HANGING_SIGN).save(output);

        buildWood(ModItems.REDWOOD_LOG, ModItems.REDWOOD_WOOD).save(output);
        buildWood(ModItems.STRIPPED_REDWOOD_LOG, ModItems.STRIPPED_REDWOOD_WOOD).save(output);
        buildPlanks(ModItemTags.REDWOOD_LOGS, ModItems.REDWOOD_PLANKS).save(output);
        buildSlab(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_SLAB).save(output);
        buildStairs(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_STAIRS).save(output);
        buildFence(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_FENCE).save(output);
        buildFenceGate(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_FENCE_GATE).save(output);
        buildPressurePlate(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_PRESSURE_PLATE).save(output);
        buildButton(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_BUTTON).save(output);
        buildWoodenDoor(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_DOOR).save(output);
        buildWoodenTrapdoor(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_TRAPDOOR).save(output);
        buildShelf(ModItems.STRIPPED_REDWOOD_LOG, ModItems.REDWOOD_SHELF).save(output);
        buildSign(ModItems.REDWOOD_PLANKS, ModItems.REDWOOD_SIGN).save(output);
        buildHangingSign(ModItems.STRIPPED_REDWOOD_LOG, ModItems.REDWOOD_HANGING_SIGN).save(output);

        buildWood(ModItems.DEAD_LOG, ModItems.DEAD_WOOD).save(output);
        buildWood(ModItems.STRIPPED_DEAD_LOG, ModItems.STRIPPED_DEAD_WOOD).save(output);
        buildPlanks(ModItemTags.DEAD_LOGS, ModItems.DEAD_WOOD_PLANKS).save(output);
        buildSlab(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_SLAB).save(output);
        buildStairs(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_STAIRS).save(output);
        buildFence(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_FENCE).save(output);
        buildFenceGate(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_FENCE_GATE).save(output);
        buildPressurePlate(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_PRESSURE_PLATE).save(output);
        buildButton(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_BUTTON).save(output);
        buildWoodenDoor(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_DOOR).save(output);
        buildWoodenTrapdoor(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_TRAPDOOR).save(output);
        buildShelf(ModItems.STRIPPED_DEAD_LOG, ModItems.DEAD_WOOD_SHELF).save(output);
        buildSign(ModItems.DEAD_WOOD_PLANKS, ModItems.DEAD_WOOD_SIGN).save(output);
        buildHangingSign(ModItems.STRIPPED_DEAD_LOG, ModItems.DEAD_WOOD_HANGING_SIGN).save(output);
    }

    private RecipeBuilder buildSlab(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("###")
                .define('#', input)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildStairs(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .define('#', input)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildWall(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.BUILDING_BLOCKS, output, 6)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildFence(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("#/#")
                .pattern("#/#")
                .define('#', input)
                .define('/', Items.STICK)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildFenceGate(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.REDSTONE, output, 1)
                .pattern("/#/")
                .pattern("/#/")
                .define('#', input)
                .define('/', Items.STICK)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildPressurePlate(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.REDSTONE, output, 1)
                .pattern("##")
                .define('#', input)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildButton(ItemLike input, ItemLike output) {
        return shapeless(RecipeCategory.REDSTONE, output, 1)
                .requires(input)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildWoodenDoor(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.REDSTONE, output, 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildWoodenTrapdoor(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.REDSTONE, output, 2)
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildShelf(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.REDSTONE, output, 6)
                .pattern("###")
                .pattern("   ")
                .pattern("###")
                .define('#', input)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildSign(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.REDSTONE, output, 3)
                .pattern("###")
                .pattern("###")
                .pattern(" / ")
                .define('#', input)
                .define('/', Items.STICK)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildHangingSign(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.REDSTONE, output, 6)
                .pattern("| |")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .define('|', Items.IRON_CHAIN)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildBricks(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildWood(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.BUILDING_BLOCKS, output, 3)
                .pattern("##")
                .pattern("##")
                .define('#', input)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildCompact(ItemLike input, ItemLike output) {
        return shaped(RecipeCategory.MISC, output)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', input)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildExpand(ItemLike input, ItemLike output) {
        return shapeless(RecipeCategory.MISC, output, 9)
                .requires(input)
                .unlockedBy(getHasName(input), has(input));
    }

    private RecipeBuilder buildPlanks(TagKey<Item> input, ItemLike output) {
        return shapeless(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .requires(input)
                .unlockedBy("has_log", has(input));
    }

    private RecipeBuilder buildConvert(ItemLike input, ItemLike output, int amount) {
        return shapeless(RecipeCategory.MISC, output, amount)
                .requires(input)
                .unlockedBy(getHasName(input), has(input));
    }

    public static FabricDataGenerator.Pack.RegistryDependentFactory<FabricRecipeProvider> factory() {
        return (output, regs) -> new FabricRecipeProvider(output, regs) {
            @Override
            protected RecipeProvider createRecipeProvider(HolderLookup.Provider lookup, RecipeOutput output) {
                return new RecipesProvider(lookup, output);
            }

            @Override
            public String getName() {
                return "Recipes";
            }
        };
    }
}
