package net.foxboi.salted.common.block;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;

public record ModSoundTypes() {
    public static final SoundType GRASSY_LIMESTONE = new SoundType(
            1f, 1f,
            SoundEvents.TUFF_BREAK,
            SoundEvents.GRASS_STEP,
            SoundEvents.TUFF_PLACE,
            SoundEvents.TUFF_HIT,
            SoundEvents.TUFF_FALL
    );

    public static final SoundType GRASSY_PEAT = new SoundType(
            1f, 1f,
            SoundEvents.ROOTED_DIRT_BREAK,
            SoundEvents.GRASS_STEP,
            SoundEvents.ROOTED_DIRT_PLACE,
            SoundEvents.ROOTED_DIRT_HIT,
            SoundEvents.ROOTED_DIRT_FALL
    );

    public static final SoundType MOSSY_PEAT = new SoundType(
            1f, 1f,
            SoundEvents.ROOTED_DIRT_BREAK,
            SoundEvents.MOSS_STEP,
            SoundEvents.ROOTED_DIRT_PLACE,
            SoundEvents.ROOTED_DIRT_HIT,
            SoundEvents.ROOTED_DIRT_FALL
    );
}
