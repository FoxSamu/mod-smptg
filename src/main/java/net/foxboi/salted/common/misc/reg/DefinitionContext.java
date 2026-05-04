package net.foxboi.salted.common.misc.reg;

import java.util.Optional;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public interface DefinitionContext {
    <A> HolderGetter<A> lookupOrThrow(ResourceKey<Registry<A>> registry);
    <A> Optional<? extends HolderGetter<A>> lookup(ResourceKey<Registry<A>> registry);

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
