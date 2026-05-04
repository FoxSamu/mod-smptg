package net.foxboi.salted.data.core.recipe;

import net.minecraft.core.HolderGetter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;

public class SingleItemRecipeBuilder extends AbstractSingleItemRecipeBuilder<SingleItemRecipeBuilder> {
    private final SingleItemRecipe.Factory<?> factory;

    public SingleItemRecipeBuilder(SingleItemRecipe.Factory<?> factory, HolderGetter<Item> items, ItemStackTemplate result) {
        super(items, result);
        this.factory = factory;
    }

    public static SingleItemRecipeBuilder stonecutting(HolderGetter<Item> items, ItemStackTemplate result) {
        return new SingleItemRecipeBuilder(StonecutterRecipe::new, items, result);
    }

    protected Recipe<?> createRecipe(Recipe.CommonInfo commonInfo, Ingredient ingredient) {
        return factory.create(commonInfo, ingredient, result);
    }
}
