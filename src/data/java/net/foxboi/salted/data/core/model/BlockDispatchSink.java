package net.foxboi.salted.data.core.model;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.world.level.block.Block;

import com.google.gson.JsonElement;

public interface BlockDispatchSink {
    void save(Block block, Function<ModelSink, JsonElement> json);
}
