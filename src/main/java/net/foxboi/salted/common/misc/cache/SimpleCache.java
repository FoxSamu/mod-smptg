package net.foxboi.salted.common.misc.cache;

import java.util.HashMap;
import java.util.Map;

public class SimpleCache<O> implements Cache<O> {
    private final O instance;
    private final Map<CacheKey<O, ?>, Object> cache = new HashMap<>();

    public SimpleCache(O instance) {
        this.instance = instance;
    }

    @Override
    public O instance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <V> V get(CacheKey<O, V> key) {
        return (V) cache.computeIfAbsent(key, it -> key.getGetter().apply(instance));
    }
}
