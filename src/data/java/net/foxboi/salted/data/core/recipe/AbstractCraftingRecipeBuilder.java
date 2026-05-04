package net.foxboi.salted.data.core.recipe;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;

public abstract class AbstractCraftingRecipeBuilder<B extends AbstractCraftingRecipeBuilder<B>> extends AbstractRecipeBuilder<B> {
    protected final ItemStackTemplate result;

    protected String group = "";
    protected CraftingBookCategory category = CraftingBookCategory.BUILDING;

    public AbstractCraftingRecipeBuilder(HolderGetter<Item> items, ItemStackTemplate result) {
        super(items, RecipeBuilder.getDefaultRecipeId(result));
        this.result = result;
    }

    public B group(String group) {
        this.group = group;
        return self();
    }

    public B category(CraftingBookCategory category) {
        this.category = category;
        return self();
    }

    @Override
    protected Recipe<?> createRecipe(Recipe.CommonInfo commonInfo) {
        return createRecipe(commonInfo, new CraftingRecipe.CraftingBookInfo(category, group));
    }

    protected abstract Recipe<?> createRecipe(Recipe.CommonInfo commonInfo, CraftingRecipe.CraftingBookInfo craftingBookInfo);
}
