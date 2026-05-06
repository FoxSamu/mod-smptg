package net.foxboi.summon.api.tags;

import net.minecraft.resources.Identifier;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public record TagEntry(Identifier name, boolean tag, boolean optional) {
    public JsonElement toJson() {
        var nameString = new JsonPrimitive(tag ? "#" + name : "" + name);

        if (optional) {
            var entry = new JsonObject();
            entry.add("id", nameString);
            entry.addProperty("required", false);
            return entry;
        } else {
            return nameString;
        }
    }
}
