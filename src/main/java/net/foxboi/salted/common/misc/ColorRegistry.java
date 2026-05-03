package net.foxboi.salted.common.misc;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public interface ColorRegistry {
    @Deprecated
    void color(Block block, Identifier colorId);

    void foliage(Block block, boolean colorParticles);
    void dryFoliage(Block block, boolean colorParticles);
    void water(Block block, boolean colorParticles);
    void grass(Block block, boolean colorParticles);

    void darkRedFoliage(Block block, boolean colorParticles);
    void redFoliage(Block block, boolean colorParticles);
    void goldenFoliage(Block block, boolean colorParticles);
    void goldgreenFoliage(Block block, boolean colorParticles);
    void yellowFoliage(Block block, boolean colorParticles);

    void solid(Block block, int rgb, boolean colorParticles);
}
