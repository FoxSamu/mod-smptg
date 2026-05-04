package net.foxboi.salted.common.attribute;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.reg.GameRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.attribute.AttributeType;
import net.minecraft.world.attribute.EnvironmentAttribute;

public record ModEnvironmentAttributes() {
    private static final GameRegistry<EnvironmentAttribute<?>> REGISTRY = Smptg.REGISTRAR.game(Registries.ENVIRONMENT_ATTRIBUTE);

    public static void init() {
    }

    private static <V> EnvironmentAttribute<V> register(String id, EnvironmentAttribute.Builder<V> builder) {
        return REGISTRY.register(id, builder.build());
    }
}
