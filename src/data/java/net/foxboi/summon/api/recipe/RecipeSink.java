package net.foxboi.summon.api.recipe;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;

public interface RecipeSink {
	void accept(ResourceKey<Recipe<?>> id, Recipe<?> recipe, AdvancementHolder advancement);
}
