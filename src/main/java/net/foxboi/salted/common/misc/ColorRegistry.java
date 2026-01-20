package net.foxboi.salted.common.misc;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public interface ColorRegistry {
    void color(Block block, Identifier colorId);
    void foliage(Block block);
    void dryFoliage(Block block);
    void water(Block block);
    void grass(Block block);
    void solid(Block block, int rgb);
}
