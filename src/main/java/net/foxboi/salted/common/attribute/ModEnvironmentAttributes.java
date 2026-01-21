package net.foxboi.salted.common.attribute;

import net.foxboi.salted.common.Smptg;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.attribute.EnvironmentAttribute;

public record ModEnvironmentAttributes() {
    public static void init() {
    }

    private static <V> EnvironmentAttribute<V> register(String id, EnvironmentAttribute.Builder<V> builder) {
        var attr = builder.build();
        Registry.register(BuiltInRegistries.ENVIRONMENT_ATTRIBUTE, Smptg.id(id), attr);
        return attr;
    }
}
