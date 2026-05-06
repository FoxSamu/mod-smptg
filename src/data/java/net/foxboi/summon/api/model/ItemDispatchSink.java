package net.foxboi.summon.api.model;

import java.util.function.Function;

import net.minecraft.world.item.Item;

import com.google.gson.JsonElement;

public interface ItemDispatchSink {
    void save(Item item, Function<ModelSink, JsonElement> json);
}
