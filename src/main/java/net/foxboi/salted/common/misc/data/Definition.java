package net.foxboi.salted.common.misc.data;

import net.minecraft.resources.ResourceKey;

public interface Definition<T> {
    T create(ResourceKey<T> key, DefinitionContext context);
}
