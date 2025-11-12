package net.foxboi.salted.common.util;

import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.LegacyRandomSource;

public class SaltChunks {

    public static boolean isSaltChunk(WorldGenLevel level, int x, int z) {
        var seed = level.getSeed();
        var rng = level.getRandom().fork();

        rng.setSeed(seed);
        rng.consumeCount(847);

        rng = rng.forkPositional().at(x * 16, 0, z * 16);

        return rng.nextInt(10) == 3;
    }
}
