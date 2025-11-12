package net.foxboi.salted.data.shadercompat;

import net.minecraft.world.level.block.Block;

public interface ShaderCompat {
    void leaves(Block block);
    void grass(Block block);
    void plant(Block block);
    void tallPlant(Block block);
    void hangingPlant(Block block);
}
