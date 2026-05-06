package net.foxboi.summon.api.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public non-sealed abstract class DataTagSet<T> extends TagSet<T, TagBuilder<T>> {
    public DataTagSet(TagProvider provider, ResourceKey<Registry<T>> registry) {
        super(provider, registry, TagBuilder::new);
    }
}
