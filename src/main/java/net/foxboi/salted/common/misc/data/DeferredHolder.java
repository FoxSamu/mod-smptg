package net.foxboi.salted.common.misc.data;

import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;

/**
 * A {@link Holder}, but lookup is deferred to a later moment.
 * @param <T>
 */
public interface DeferredHolder<T> {
    Optional<Holder<T>> resolve(DefinitionContext provider);
    Holder<T> resolveOrThrow(DefinitionContext provider);

    static <T> DeferredHolder<T> direct(T value) {
        return new Direct<>(Holder.direct(value));
    }

    static <T> DeferredHolder<T> direct(Holder<T> value) {
        return new Direct<>(value);
    }

    static <T> DeferredHolder<T> reference(ResourceKey<T> value) {
        return new Reference<>(value);
    }

    record Direct<T>(Holder<T> value) implements DeferredHolder<T> {
        @Override
        public Optional<Holder<T>> resolve(DefinitionContext provider) {
            return Optional.of(value);
        }

        @Override
        public Holder<T> resolveOrThrow(DefinitionContext provider) {
            return value;
        }
    }

    record Reference<T>(ResourceKey<T> key) implements DeferredHolder<T> {
        @Override
        public Optional<Holder<T>> resolve(DefinitionContext provider) {
            return provider.lookup(key.registryKey()).flatMap(registry -> registry.get(key));
        }

        @Override
        public Holder<T> resolveOrThrow(DefinitionContext provider) {
            return provider.lookupOrThrow(key.registryKey()).getOrThrow(key);
        }
    }
}
