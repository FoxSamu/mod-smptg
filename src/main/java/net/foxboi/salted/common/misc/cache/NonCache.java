package net.foxboi.salted.common.misc.cache;

public record NonCache<O>(O instance) implements Cache<O> {
    @Override
    public <V> V get(CacheKey<O, V> key) {
        return key.getGetter().apply(instance);
    }
}
