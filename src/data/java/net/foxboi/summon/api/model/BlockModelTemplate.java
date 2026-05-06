package net.foxboi.summon.api.model;

import net.minecraft.world.level.block.Block;

public interface BlockModelTemplate {
    Model create(Block block, String suffix);

    default Model create(Block block) {
        return create(block, "");
    }
}
