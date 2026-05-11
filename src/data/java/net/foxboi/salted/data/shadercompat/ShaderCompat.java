package net.foxboi.salted.data.shadercompat;

import net.minecraft.world.level.block.Block;

public interface ShaderCompat {
    void leaves(Block block);
    void grass(Block block);
    void plant(Block block);
    void tallPlant(Block block);
    void hangingPlant(Block block);

    void coalOre(Block block);
    void copperOre(Block block);
    void ironOre(Block block);
    void goldOre(Block block);
    void diamondOre(Block block);
    void emeraldOre(Block block);
    void redstoneOre(Block block);
    void lapisOre(Block block);
}
