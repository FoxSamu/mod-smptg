package net.foxboi.salted.common.misc.reg;

import java.util.Optional;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

import net.minecraft.core.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

@SuppressWarnings("unchecked")
public interface DefinitionContext {
    <A> HolderGetter<A> lookupOrThrow(ResourceKey<Registry<A>> registry);

    <A> Optional<? extends HolderGetter<A>> lookup(ResourceKey<Registry<A>> registry);

    default <A> Holder<A> getOrThrow(ResourceKey<A> key) {
        return lookupOrThrow(key.registryKey()).getOrThrow(key);
    }

    default <A> Optional<Holder<A>> get(ResourceKey<A> key) {
        return lookup(key.registryKey()).flatMap(it -> it.get(key));
    }

    default <A> HolderSet<A> getOrThrow(TagKey<A> key) {
        return lookupOrThrow((ResourceKey<Registry<A>>) key.registry()).getOrThrow(key);
    }

    default <A> Optional<HolderSet<A>> get(TagKey<A> key) {
        return lookup((ResourceKey<Registry<A>>) key.registry()).flatMap(it -> it.get(key));
    }

    static DefinitionContext fromHolderLookupProvider(HolderLookup.Provider provider) {
        return new DefinitionContext() {
            @Override
            public <A> HolderGetter<A> lookupOrThrow(ResourceKey<Registry<A>> registry) {
                return provider.lookupOrThrow(registry);
            }

            @Override
            public <A> Optional<? extends HolderGetter<A>> lookup(ResourceKey<Registry<A>> registry) {
                return provider.lookup(registry);
            }
        };
    }

    static DefinitionContext fromBootstrapContext(BootstrapContext<?> provider) {
        return new DefinitionContext() {
            @Override
            public <A> HolderGetter<A> lookupOrThrow(ResourceKey<Registry<A>> registry) {
                return provider.lookup(registry);
            }

            @Override
            public <A> Optional<? extends HolderGetter<A>> lookup(ResourceKey<Registry<A>> registry) {
                return Optional.of(provider.lookup(registry)); // No way not to throw an exception here unfortunately
            }
        };
    }

    static DefinitionContext fromGeneratorEntries(FabricDynamicRegistryProvider.Entries entries) {
        return fromHolderLookupProvider(entries.getLookups());
    }
}
