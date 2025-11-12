package net.foxboi.salted.common.color;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ColorResolver;

import java.util.Map;

public interface BiomeColorLevelInj {
    int smptg$computeBlockTintUncached(BlockPos pos, ColorResolver resolver);
    int smptg$getBlockTint(BlockPos pos, ResourceLocation color);
    void smptg$reloadColors(Map<ResourceLocation, BiomeColor> map);
}
