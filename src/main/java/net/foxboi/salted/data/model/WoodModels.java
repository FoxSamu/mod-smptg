package net.foxboi.salted.data.model;

import net.minecraft.world.level.block.Block;

public interface WoodModels {
    WoodModels wood(Block block);
    WoodModels log(Block block);
    WoodModels logWithHorizontal(Block block);
    WoodModels logUVLocked(Block block);
}
