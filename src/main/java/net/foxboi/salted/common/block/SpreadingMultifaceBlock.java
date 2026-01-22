package net.foxboi.salted.common.block;

import net.minecraft.world.level.block.MultifaceSpreader;

/**
 * A block that acts like a {@link net.minecraft.world.level.block.MultifaceBlock MultifaceBlock} but does not inherit
 * from  {@link net.minecraft.world.level.block.MultifaceBlock MultifaceBlock}. This is necessary since vanilla's
 * multiface block implementation comes with some restrictions that we cannot circumvent. For example, SMPTG has
 * patchmoss, a multiface block that is not waterloggable. However, vanilla's multiface block implementation forces
 * waterloggability, breaking a whole bunch of things.
 * <p>
 * Luckily, vanilla's {@link MultifaceSpreader} does not actually care what type of multiface block it is given. It only
 * cares about some custom growth logic that we provide which we can just implement for our custom multiface blocks.
 * </p>
 */
public interface SpreadingMultifaceBlock {
    /**
     * Returns the {@link MultifaceSpreader} to grow this multiface block.
     */
    MultifaceSpreader getSpreader();
}
