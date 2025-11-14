package net.foxboi.salted.common.misc.cache;

import java.util.function.Function;

import net.minecraft.core.Holder;

public class CacheKey<O, V> {
    private final Function<O, V> getter;

    public CacheKey(Function<O, V> getter) {
        this.getter = getter;
    }

    public Function<O, V> getGetter() {
        return getter;
    }

    public V get(O object) {
        return Cache.of(object).get(this);
    }

    public V get(Holder<O> object) {
        return get(object.value());
    }

    public static <O, V> CacheKey<O, V> of(Function<O, V> getter) {
        return new CacheKey<>(getter);
    }
}
