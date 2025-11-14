package net.foxboi.salted.common.misc.cache;

public interface Cache<O> {
    O instance();
    <V> V get(CacheKey<O, V> key);


    @SuppressWarnings("unchecked")
    static <O> Cache<O> of(O object) {
        if (object instanceof CacheHolderInj<?> cache) {
            return (Cache<O>) cache.smptg$getCache();
        }

        return new NonCache<>(object);
    }
}
