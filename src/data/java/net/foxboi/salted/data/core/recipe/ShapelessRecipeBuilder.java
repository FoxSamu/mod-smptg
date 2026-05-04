package net.foxboi.salted.data.core.recipe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.HolderGetter;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;

public class ShapelessRecipeBuilder extends AbstractCraftingRecipeBuilder<ShapelessRecipeBuilder> {
    private final List<Ingredient> ingredients = new ArrayList<>();

    public ShapelessRecipeBuilder(final HolderGetter<Item> items, final ItemStackTemplate result) {
        super(items, result);
    }

    public ShapelessRecipeBuilder requires(TagKey<Item> tag) {
        return requires(Ingredient.of(items.getOrThrow(tag)));
    }

    public ShapelessRecipeBuilder requires(ItemLike item) {
        return requires(item, 1);
    }

    public ShapelessRecipeBuilder requires(ItemLike item, int count) {
        for (int i = 0; i < count; i++) {
            requires(Ingredient.of(item));
        }

        return this;
    }

    public ShapelessRecipeBuilder requires(Ingredient ingredient) {
        ingredients.add(ingredient);
        return this;
    }

    public ShapelessRecipeBuilder requires(Ingredient ingredient, int count) {
        for (int i = 0; i < count; i++) {
            requires(ingredient);
        }

        return this;
    }

    @Override
    protected Recipe<?> createRecipe(Recipe.CommonInfo commonInfo, CraftingRecipe.CraftingBookInfo craftingBookInfo) {
        return new ShapelessRecipe(commonInfo, craftingBookInfo, result, ingredients);
    }
}
