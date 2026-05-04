package net.foxboi.salted.common.misc.reg;

import net.minecraft.resources.ResourceKey;

public interface Definition<T> {
    T create(ResourceKey<T> key, DefinitionContext context);

    static <T> Definition<T> of(T object) {
        return (_, _) -> object;
    }
}
