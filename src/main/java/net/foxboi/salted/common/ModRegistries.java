package net.foxboi.salted.common;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.foxboi.salted.common.levelgen.surface.BiomeSurfaceOverride;
import net.foxboi.salted.common.levelgen.surface.BiomeSurfaceOverrides;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public record ModRegistries() {
    public static final ResourceKey<Registry<BiomeSurfaceOverrides>> BIOME_SURFACE_OVERRIDES = Smptg.reg("biome_surface_overrides");

    public static void init() {
        DynamicRegistries.register(BIOME_SURFACE_OVERRIDES, BiomeSurfaceOverrides.DIRECT_CODEC);
    }
}
