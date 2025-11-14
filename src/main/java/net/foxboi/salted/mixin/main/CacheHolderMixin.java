package net.foxboi.salted.mixin.main;

import net.foxboi.salted.common.misc.cache.Cache;
import net.foxboi.salted.common.misc.cache.CacheHolderInj;
import net.foxboi.salted.common.misc.cache.SimpleCache;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("rawtypes")
@Mixin({
        // Simply add classes that need a cache here
        Biome.class
})
public class CacheHolderMixin implements CacheHolderInj {

    @Unique
    private final SimpleCache cache = new SimpleCache<>(this);

    @Override
    public Cache smptg$getCache() {
        return cache;
    }
}
