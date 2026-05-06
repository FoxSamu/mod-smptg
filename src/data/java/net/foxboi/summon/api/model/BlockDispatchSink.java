package net.foxboi.summon.api.model;

import java.util.function.Function;

import net.minecraft.world.level.block.Block;

import com.google.gson.JsonElement;

public interface BlockDispatchSink {
    void save(Block block, Function<ModelSink, JsonElement> json);
}
