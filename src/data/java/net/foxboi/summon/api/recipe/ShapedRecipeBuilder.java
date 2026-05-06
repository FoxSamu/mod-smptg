package net.foxboi.summon.api.recipe;

import it.unimi.dsi.fastutil.chars.Char2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.HolderGetter;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;

public class ShapedRecipeBuilder extends AbstractCraftingRecipeBuilder<ShapedRecipeBuilder> {
    private final List<String> rows = new ArrayList<>();
    private final Char2ObjectMap<Ingredient> key = new Char2ObjectLinkedOpenHashMap<>();

    public ShapedRecipeBuilder(HolderGetter<Item> items, ItemStackTemplate result) {
        super(items, result);
    }

    public ShapedRecipeBuilder define(char symbol, TagKey<Item> tag) {
        return define(symbol, Ingredient.of(items.getOrThrow(tag)));
    }

    public ShapedRecipeBuilder define(char symbol, ItemLike item) {
        return define(symbol, Ingredient.of(item));
    }

    public ShapedRecipeBuilder define(char symbol, Ingredient ingredient) {
        if (key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        }

        if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        }

        key.put(symbol, ingredient);
        return this;
    }

    public ShapedRecipeBuilder pattern(String row) {
        if (!rows.isEmpty() && row.length() != rows.getFirst().length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        }

        rows.add(row);
        return this;
    }

    @Override
    protected Recipe<?> createRecipe(Recipe.CommonInfo commonInfo, CraftingRecipe.CraftingBookInfo craftingBookInfo) {
        return new ShapedRecipe(commonInfo, craftingBookInfo, ShapedRecipePattern.of(key, rows), result);
    }
}
