package net.foxboi.salted.common.misc.reg;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import java.util.function.BiConsumer;

/**
 * A registry for datapack data. The registry accepts {@link Definition}s which are evaluated by data generation, and
 * returns {@link ResourceKey}s for the registered elements.
 */
public class DataRegistry<T> extends ModRegistry<T, Definition<T>> {
    DataRegistry(String namespace, ResourceKey<Registry<T>> registryKey) {
        super(namespace, registryKey);
    }

    public ResourceKey<T> register(ResourceKey<T> key, Definition<T> definition) {
        registerEntry(key, definition);
        return key;
    }

    public ResourceKey<T> register(Identifier id, Definition<T> definition) {
        return register(ResourceKey.create(getRegistryKey(), id), definition);
    }

    public ResourceKey<T> register(String name, Definition<T> definition) {
        return register(Identifier.fromNamespaceAndPath(getNamespace(), name), definition);
    }

    public ResourceKey<T> register(ResourceKey<T> key, T object) {
        return register(key, Definition.of(object));
    }

    public ResourceKey<T> register(Identifier id, T object) {
        return register(id, Definition.of(object));
    }

    public ResourceKey<T> register(String name, T object) {
        return register(name, Definition.of(object));
    }
}
