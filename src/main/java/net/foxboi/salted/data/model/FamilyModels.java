package net.foxboi.salted.data.model;

import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

public interface FamilyModels {
    FamilyModels donateModelTo(Block to, Block from);

    FamilyModels log(Block block);
    FamilyModels wood(Block block);
    FamilyModels strippedLog(Block block);
    FamilyModels strippedWood(Block block);

    FamilyModels slab(Block block);
    FamilyModels stairs(Block block);

    FamilyModels door(Block block);
    FamilyModels trapdoor(Block block);
    FamilyModels shelf(Block block);

    FamilyModels button(Block block);
    FamilyModels pressurePlate(Block block);

    FamilyModels wall(Block block);
    FamilyModels customFence(Block block);
    FamilyModels fence(Block block);
    FamilyModels customFenceGate(Block block);
    FamilyModels fenceGate(Block block);

    FamilyModels sign(Block block, Block wallBlock);
    FamilyModels hangingSign(Block block, Block wallBlock);

    FamilyModels generateFor(BlockFamily family);
}
