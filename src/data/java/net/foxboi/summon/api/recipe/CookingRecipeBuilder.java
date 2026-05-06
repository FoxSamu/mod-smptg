package net.foxboi.summon.api.recipe;

import net.minecraft.core.HolderGetter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;

public class CookingRecipeBuilder extends AbstractSingleItemRecipeBuilder<CookingRecipeBuilder> {
    private float experience = 0f;
    private int cookingTime = 200;

    protected String group = "";
    private CookingBookCategory category = CookingBookCategory.BLOCKS;

    private final AbstractCookingRecipe.Factory<?> factory;

    public CookingRecipeBuilder(AbstractCookingRecipe.Factory<?> factory, HolderGetter<Item> items, ItemStackTemplate result) {
        super(items, result);
        this.factory = factory;
    }

    public static CookingRecipeBuilder smelting(HolderGetter<Item> items, ItemStackTemplate result) {
        return new CookingRecipeBuilder(SmeltingRecipe::new, items, result);
    }

    public static CookingRecipeBuilder blasting(HolderGetter<Item> items, ItemStackTemplate result) {
        return new CookingRecipeBuilder(BlastingRecipe::new, items, result)
                .cookingTime(100);
    }

    public static CookingRecipeBuilder smoking(HolderGetter<Item> items, ItemStackTemplate result) {
        return new CookingRecipeBuilder(SmokingRecipe::new, items, result)
                .cookingTime(100);
    }

    public static CookingRecipeBuilder campfireCooking(HolderGetter<Item> items, ItemStackTemplate result) {
        return new CookingRecipeBuilder(CampfireCookingRecipe::new, items, result)
                .cookingTime(600);
    }

    public CookingRecipeBuilder experience(float experience) {
        this.experience = experience;
        return this;
    }

    public CookingRecipeBuilder cookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
        return this;
    }

    public CookingRecipeBuilder group(String group) {
        this.group = group;
        return this;
    }

    public CookingRecipeBuilder category(CookingBookCategory category) {
        this.category = category;
        return this;
    }

    @Override
    protected Recipe<?> createRecipe(Recipe.CommonInfo commonInfo, Ingredient ingredient) {
        return factory.create(
                commonInfo,
                new AbstractCookingRecipe.CookingBookInfo(category, group),
                ingredient,
                result,
                experience,
                cookingTime
        );
    }
}