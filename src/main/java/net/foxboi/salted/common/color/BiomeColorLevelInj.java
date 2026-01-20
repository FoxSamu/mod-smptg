package net.foxboi.salted.common.color;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ColorResolver;

import java.util.Map;

public interface BiomeColorLevelInj {
    int smptg$computeBlockTintUncached(BlockPos pos, ColorResolver resolver);
    int smptg$getBlockTint(BlockPos pos, Identifier color);
    void smptg$reloadColors(Map<Identifier, BiomeColor> map);
}
