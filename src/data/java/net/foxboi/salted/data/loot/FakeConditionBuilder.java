package net.foxboi.salted.data.loot;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public record FakeConditionBuilder(LootItemCondition condition) implements LootItemCondition.Builder {
    @Override
    public LootItemCondition build() {
        return condition;
    }
}
