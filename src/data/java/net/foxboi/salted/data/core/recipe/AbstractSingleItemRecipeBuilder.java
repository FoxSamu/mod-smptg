package net.foxboi.salted.data.core.recipe;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.ItemLike;

public abstract class AbstractSingleItemRecipeBuilder<B extends AbstractSingleItemRecipeBuilder<B>> extends AbstractRecipeBuilder<B> {
    protected final ItemStackTemplate result;

    private Ingredient ingredient;

    public AbstractSingleItemRecipeBuilder(HolderGetter<Item> items, ItemStackTemplate result) {
        super(items, RecipeBuilder.getDefaultRecipeId(result));
        this.result = result;
    }

    public B input(Ingredient input) {
        this.ingredient = input;
        return self();
    }

    public B input(TagKey<Item> input) {
        return input(Ingredient.of(items.getOrThrow(input)));
    }

    public B input(ItemLike input) {
        return input(Ingredient.of(input));
    }

    @Override
    public void save(RecipeSink output, ResourceKey<Recipe<?>> location) {
        if (ingredient == null) {
            throw new RuntimeException("Ingredient not defined for SingleItemRecipe " + location.identifier());
        }

        super.save(output, location);
    }

    @Override
    protected Recipe<?> createRecipe(Recipe.CommonInfo commonInfo) {
        return createRecipe(commonInfo, ingredient);
    }

    protected abstract Recipe<?> createRecipe(Recipe.CommonInfo commonInfo, Ingredient ingredient);
}
