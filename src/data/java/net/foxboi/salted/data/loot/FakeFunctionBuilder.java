package net.foxboi.salted.data.loot;

import net.minecraft.world.level.storage.loot.functions.LootItemFunction;

public record FakeFunctionBuilder(LootItemFunction function) implements LootItemFunction.Builder {
    @Override
    public LootItemFunction build() {
        return function;
    }
}
