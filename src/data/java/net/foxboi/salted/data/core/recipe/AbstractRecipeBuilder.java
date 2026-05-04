package net.foxboi.salted.data.core.recipe;

import java.util.Arrays;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

public abstract class AbstractRecipeBuilder<B extends AbstractRecipeBuilder<B>> {
    protected final HolderGetter<Item> items;

    protected final RecipeAdvancementBuilder advancement = new RecipeAdvancementBuilder();

    protected boolean notification = true;

    private ResourceKey<Recipe<?>> defaultId;

    public AbstractRecipeBuilder(HolderGetter<Item> items, ResourceKey<Recipe<?>> defaultId) {
        this.items = items;
        this.defaultId = defaultId;
    }

    @SuppressWarnings("unchecked")
    protected B self() {
        return (B) this;
    }

    public B unlockedBy(String name, Criterion<?> criterion) {
        advancement.unlockedBy(name, criterion);
        return self();
    }

    public B unlockedBy(String name, ItemPredicate.Builder... itemPredicates) {
        return unlockedBy(
                name,
                RecipeProvider.inventoryTrigger(itemPredicates)
        );
    }

    public B unlockedBy(String name, ItemLike... items) {
        return unlockedBy(
                name,
                Arrays.stream(items)
                        .map(it -> ItemPredicate.Builder.item().of(this.items, it))
                        .toArray(ItemPredicate.Builder[]::new)
        );
    }

    public B unlockedBy(ItemLike item) {
        return unlockedBy(
                RecipeProvider.hasItemName(item),
                ItemPredicate.Builder.item().of(items, item)
        );
    }

    public B unlockedBy(ItemLike item, int minAmount) {
        return unlockedBy(
                RecipeProvider.hasItemName(item),
                ItemPredicate.Builder.item().of(items, item).withCount(MinMaxBounds.Ints.atLeast(minAmount))
        );
    }

    public B unlockedBy(TagKey<Item> tag) {
        return unlockedBy(
                RecipeProvider.hasTagName(tag),
                ItemPredicate.Builder.item().of(items, tag)
        );
    }

    public B unlockedBy(TagKey<Item> tag, int minAmount) {
        return unlockedBy(
                RecipeProvider.hasTagName(tag),
                ItemPredicate.Builder.item().of(items, tag).withCount(MinMaxBounds.Ints.atLeast(minAmount))
        );
    }

    public B notification(boolean notification) {
        this.notification = notification;
        return self();
    }

    public B defaultId(ResourceKey<Recipe<?>> defaultId) {
        this.defaultId = defaultId;
        return self();
    }

    public B defaultId(Identifier defaultId) {
        return defaultId(ResourceKey.create(Registries.RECIPE, defaultId));
    }

    public B namespace(String namespace) {
        return defaultId(ResourceKey.create(defaultId.registryKey(), Identifier.fromNamespaceAndPath(namespace, defaultId.identifier().getPath())));
    }

    public void save(RecipeSink output) {
        save(output, defaultId);
    }

    public void save(RecipeSink output, Identifier id) {
        save(output, ResourceKey.create(Registries.RECIPE, id));
    }

    public void save(RecipeSink output, ResourceKey<Recipe<?>> location) {
        var recipe = createRecipe(new Recipe.CommonInfo(notification));
        output.accept(location, recipe, advancement.build(location));
    }

    protected abstract Recipe<?> createRecipe(Recipe.CommonInfo commonInfo);
}
