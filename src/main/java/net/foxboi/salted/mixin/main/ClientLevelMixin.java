package net.foxboi.salted.mixin.main;

import net.foxboi.salted.client.color.BiomeColorLoader;
import net.foxboi.salted.client.color.BiomeColorsClient;
import net.foxboi.salted.common.color.BiomeColor;
import net.foxboi.salted.common.color.BiomeColorLevelInj;
import net.foxboi.salted.common.color.ErrorColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.Util;
import net.minecraft.client.color.block.BlockTintCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin implements BiomeColorLevelInj {
    @Shadow
    public abstract int calculateBlockTint(BlockPos pos, ColorResolver resolver);

    @Unique
    private final Map<ResourceLocation, BlockTintCache> biomeColourCache = Util.make(new HashMap<>(), it -> {
        reloadColours(BiomeColorsClient.COLORS, it);
    });

    @Unique
    private final Set<ResourceLocation> reportedMissing = new HashSet<>();

    @Override
    public int smptg$computeBlockTintUncached(BlockPos pos, ColorResolver resolver) {
        return calculateBlockTint(pos, resolver);
    }

    @Override
    public int smptg$getBlockTint(BlockPos pos, ResourceLocation color) {
        var cache = biomeColourCache.get(color);
        if (cache == null) {
            if (reportedMissing.add(color)) {
                BiomeColorLoader.LOGGER.warn("Biome color {} not found", color);
            }
            return ErrorColor.INSTANCE.sample((Level) (Object) this, pos);
        }
        return cache.getColor(pos) | 0xFF000000;
    }

    @Override
    public void smptg$reloadColors(Map<ResourceLocation, BiomeColor> map) {
        reloadColours(map, biomeColourCache);
        reportedMissing.clear();
    }

    @Unique
    private void reloadColours(Map<ResourceLocation, BiomeColor> map, Map<ResourceLocation, BlockTintCache> caches) {
        caches.clear();
        map.forEach((k, v) -> {
            caches.put(k, new BlockTintCache(pos -> v.sample((Level) (Object) this, pos)));
        });
    }

    @Inject(
            method = "getBlockTint",
            at = @At("HEAD"),
            cancellable = true
    )
    private void injGetBlockTint(BlockPos pos, ColorResolver resolver, CallbackInfoReturnable<Integer> cir) {
        // Redirect to use our block tint system
        if (resolver == BiomeColors.WATER_COLOR_RESOLVER)
            cir.setReturnValue(smptg$getBlockTint(pos, net.foxboi.salted.common.color.BiomeColors.WATER));
        if (resolver == BiomeColors.GRASS_COLOR_RESOLVER)
            cir.setReturnValue(smptg$getBlockTint(pos, net.foxboi.salted.common.color.BiomeColors.GRASS));
        if (resolver == BiomeColors.FOLIAGE_COLOR_RESOLVER)
            cir.setReturnValue(smptg$getBlockTint(pos, net.foxboi.salted.common.color.BiomeColors.FOLIAGE));
        if (resolver == BiomeColors.DRY_FOLIAGE_COLOR_RESOLVER)
            cir.setReturnValue(smptg$getBlockTint(pos, net.foxboi.salted.common.color.BiomeColors.DRY_FOLIAGE));
    }


    @Inject(
            method = "onChunkLoaded",
            at = @At("RETURN")
    )
    private void injOnChunkLoaded(ChunkPos pos, CallbackInfo info) {
        biomeColourCache.forEach((k, v) -> v.invalidateForChunk(pos.x, pos.z));
    }

    @Inject(
            method = "clearTintCaches",
            at = @At("RETURN")
    )
    private void injClearTintCaches(CallbackInfo info) {
        biomeColourCache.forEach((k, v) -> v.invalidateAll());
    }
}
