package net.foxboi.salted.common.misc;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

public interface ColorRegistry {
    @Deprecated
    void color(Block block, Identifier colorId);

    void foliage(Block block, boolean colorParticles, int indices);
    void dryFoliage(Block block, boolean colorParticles, int indices);
    void water(Block block, boolean colorParticles, int indices);
    void grass(Block block, boolean colorParticles, int indices);

    void darkRedFoliage(Block block, boolean colorParticles, int indices);
    void redFoliage(Block block, boolean colorParticles, int indices);
    void goldenFoliage(Block block, boolean colorParticles, int indices);
    void goldgreenFoliage(Block block, boolean colorParticles, int indices);
    void yellowFoliage(Block block, boolean colorParticles, int indices);

    void solid(Block block, int rgb, boolean colorParticles, int indices);
}
