package net.foxboi.summon.api.tags;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public non-sealed abstract class LookupTagSet<T> extends TagSet<T, LookupTagBuilder<T>> {
    public LookupTagSet(TagProvider provider, ResourceKey<Registry<T>> registry, Function<T, ResourceKey<T>> idLookup) {
        super(provider, registry, (pv, key) -> new LookupTagBuilder<>(pv, key, idLookup));
    }

    @SuppressWarnings("unchecked")
    public LookupTagSet(TagProvider provider, Registry<T> registry) {
        this(
                provider,
                (ResourceKey<Registry<T>>) registry.key(),
                elem -> registry.getResourceKey(elem).orElseThrow(() -> new RuntimeException("Cannot add unregistered element to tag"))
        );
    }
}
