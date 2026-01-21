package net.foxboi.salted.common.attribute;

import net.foxboi.salted.common.Smptg;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.AttributeType;

public record ModAttributeTypes() {
    public static void init() {
    }

    private static <V> AttributeType<V> register(String id, AttributeType<V> type) {
        Registry.register(BuiltInRegistries.ATTRIBUTE_TYPE, Smptg.id(id), type);
        return type;
    }
}
