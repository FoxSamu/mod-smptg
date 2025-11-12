package net.foxboi.salted.common.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public interface ColorRegistry {
    void color(Block block, ResourceLocation colorId);
    void foliage(Block block);
    void dryFoliage(Block block);
    void water(Block block);
    void grass(Block block);
    void solid(Block block, int rgb);
}
