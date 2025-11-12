package net.foxboi.salted.common.util;

import java.util.ArrayList;
import java.util.HashSet;

import net.foxboi.salted.common.block.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class SaltMelting {
    public static boolean isNextToSalt(LevelReader level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            if (level.getBlockState(pos.relative(dir)).is(ModBlockTags.GROWS_SALT_MORE_LIKELY)) {
                return true;
            }
            if (level.getBlockState(pos.relative(dir)).is(ModBlockTags.GROWS_SALT)) {
                return true;
            }
        }

        return false;
    }

    public static void meltFromSalt(Level level, BlockPos pos, RandomSource rng) {
        var toVisit = new ArrayList<BlockPos>();
        var visted = new HashSet<BlockPos>();
        var iter = rng.nextInt(24, 32);

        toVisit.add(pos);

        for (int i = 0; i < iter; i ++) {
            if (toVisit.isEmpty())
                return;

            if (rng.nextInt(4) == 0) {
                pos = toVisit.get(rng.nextInt(toVisit.size()));
            } else {
                pos = toVisit.removeFirst();
            }

            var state = level.getBlockState(pos);
            if (state.is(Blocks.SNOW)) {
                Block.dropResources(state, level, pos);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
            } else if (state.is(Blocks.ICE)) {
                level.setBlock(pos, Blocks.WATER.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
            }

            visted.add(pos);

            for (Direction dir : Direction.allShuffled(rng)) {
                var relative = pos.relative(dir);
                if (!visted.contains(relative)) {
                    toVisit.add(relative);
                }
            }
        }
    }
}
