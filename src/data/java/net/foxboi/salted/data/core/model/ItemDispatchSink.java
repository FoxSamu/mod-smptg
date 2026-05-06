package net.foxboi.salted.data.core.model;

import java.util.function.Function;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import com.google.gson.JsonElement;

public interface ItemDispatchSink {
    void save(Item item, Function<ModelSink, JsonElement> json);
}
