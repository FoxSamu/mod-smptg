package net.foxboi.salted.common.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;

public interface Definition<T> {
    T create(ResourceKey<T> key, DefinitionContext context);
}
