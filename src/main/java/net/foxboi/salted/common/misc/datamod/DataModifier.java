package net.foxboi.salted.common.misc.datamod;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;

public interface DataModifier<T> {
    T modify(ResourceKey<T> key, T original, HolderGetter.Provider lookup);
}
