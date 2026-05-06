package net.foxboi.summon.api.registry;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;

class RegistryOutput<T> implements BootstrapContext<T> {
    private final HolderLookup.Provider lookups;
    private final HolderOwner<T> owner;
    private final ResourceKey<? extends Registry<T>> registry;
    private final Codec<T> elementCodec;

    private final Map<ResourceKey<T>, T> resources = new HashMap<>();

    public RegistryOutput(HolderLookup.Provider lookups, HolderOwner<T> owner, ResourceKey<? extends Registry<T>> registry, Codec<T> elementCodec) {
        this.lookups = lookups;
        this.owner = owner;
        this.registry = registry;
        this.elementCodec = elementCodec;
    }

    public HolderOwner<T> getOwner() {
        return owner;
    }

    public ResourceKey<? extends Registry<T>> getRegistryKey() {
        return registry;
    }

    public Codec<T> getElementCodec() {
        return elementCodec;
    }

    public Map<ResourceKey<T>, T> getResources() {
        return resources;
    }

    public static <T> RegistryOutput<T> of(HolderLookup.Provider lookups, RegistryDataLoader.RegistryData<T> reg) {
        return new RegistryOutput<>(lookups, lookups.lookupOrThrow(reg.key()), reg.key(), reg.elementCodec());
    }

    @Override
    public Holder.Reference<T> register(ResourceKey<T> key, T value, Lifecycle lifecycle) {
        if (resources.containsKey(key)) {
            throw new IllegalArgumentException("Duplicate registration for key: " + key.identifier());
        }

        resources.put(key, value);
        return Holder.Reference.createStandAlone(owner, key);
    }

    @Override
    public <S> HolderGetter<S> lookup(ResourceKey<? extends Registry<? extends S>> key) {
        return lookups.lookupOrThrow(key);
    }
}
