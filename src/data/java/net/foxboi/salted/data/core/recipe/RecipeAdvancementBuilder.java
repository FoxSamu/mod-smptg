package net.foxboi.salted.data.core.recipe;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.advancements.*;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeAdvancementBuilder {
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    public void unlockedBy(String name, Criterion<?> criterion) {
        criteria.put(name, criterion);
    }

    @SuppressWarnings("removal")
    private Advancement.Builder advancement() {
        return Advancement.Builder.recipeAdvancement().parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT);
    }

    public AdvancementHolder build(ResourceKey<Recipe<?>> id) {
        if (criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id.identifier());
        }

        var advancement = advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);

        criteria.forEach(advancement::addCriterion);
        return advancement.build(id.identifier().withPrefix("recipes/"));
    }
}
