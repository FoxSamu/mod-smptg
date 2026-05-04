package net.foxboi.salted.common.misc.reg;

import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

/**
 * A registry for game objects. The registry accepts objects directly, which are registered to a {@link Registry} from
 * {@link BuiltInRegistries} later.
 */
public class GameRegistry<T> extends ModRegistry<T, T> {
    GameRegistry(String namespace, ResourceKey<Registry<T>> registryKey) {
        super(namespace, registryKey);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ResourceKey<T> keyOf(T object) {
        var lookup = reverseLookup(object);

        if (lookup == null) {
            // Try built in registry instead
            var builtinReg = (Registry<T>) BuiltInRegistries.REGISTRY.getValue((ResourceKey) getRegistryKey());
            return builtinReg == null ? null : builtinReg.getResourceKey(object).orElse(null);
        }

        return lookup;
    }

    public <O extends T> O register(ResourceKey<T> key, O object) {
        registerEntry(key, object);
        return object;
    }

    public <O extends T> O register(Identifier id, O object) {
        return register(ResourceKey.create(getRegistryKey(), id), object);
    }

    public <O extends T> O register(String name, O object) {
        return register(Identifier.fromNamespaceAndPath(getNamespace(), name), object);
    }

    public <O extends T> O register(ResourceKey<T> key, Function<ResourceKey<T>, O> factory) {
        return register(key, factory.apply(key));
    }

    public <O extends T> O register(Identifier id, Function<ResourceKey<T>, O> factory) {
        return register(ResourceKey.create(getRegistryKey(), id), factory);
    }

    public <O extends T> O register(String name, Function<ResourceKey<T>, O> factory) {
        return register(Identifier.fromNamespaceAndPath(getNamespace(), name), factory);
    }

    public <O extends T, U> O register(ResourceKey<T> key, BiFunction<ResourceKey<T>, U, O> factory, U properties) {
        return register(key, factory.apply(key, properties));
    }

    public <O extends T, U> O register(Identifier id, BiFunction<ResourceKey<T>, U, O> factory, U properties) {
        return register(ResourceKey.create(getRegistryKey(), id), factory, properties);
    }

    public <O extends T, U> O register(String name, BiFunction<ResourceKey<T>, U, O> factory, U properties) {
        return register(Identifier.fromNamespaceAndPath(getNamespace(), name), factory, properties);
    }
}
