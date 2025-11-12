package net.foxboi.salted.common.util;

import net.minecraft.world.level.block.Block;

public interface LayerRegistry {
    void solid(Block block);
    void cutout(Block block);
    void cutoutMipped(Block block);
    void translucent(Block block);
}
