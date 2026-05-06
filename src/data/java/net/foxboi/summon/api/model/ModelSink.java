package net.foxboi.summon.api.model;

import java.util.function.Supplier;

import net.minecraft.resources.Identifier;

import com.google.gson.JsonElement;

public interface ModelSink {
    boolean hasModel(Identifier name);
    boolean save(Identifier name, Supplier<JsonElement> json);

    default void requireSave(Identifier name, Supplier<JsonElement> json) {
        if (!save(name, json)) {
            throw new RuntimeException("Duplicate model: " + name);
        }
    }
}
