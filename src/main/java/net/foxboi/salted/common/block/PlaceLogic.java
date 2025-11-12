package net.foxboi.salted.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.MossyCarpetBlock;
import net.minecraft.world.level.block.state.BlockState;

public interface PlaceLogic {
    boolean placeAt(LevelAccessor level, BlockPos pos, BlockState state, RandomSource rng, int setFlags);

    static boolean place(LevelAccessor level, BlockPos pos, BlockState state, RandomSource rng, int setFlags) {
        var block = state.getBlock();
        return switch (block) {
            case PlaceLogic logic -> logic.placeAt(level, pos, state, rng, setFlags);

            case DoublePlantBlock ignored -> {
                if (!level.isEmptyBlock(pos.above())) {
                    yield false;
                }

                DoublePlantBlock.placeAt(level, state, pos, setFlags);
                yield true;
            }

            case MossyCarpetBlock ignored -> {
                MossyCarpetBlock.placeAt(level, pos, rng, setFlags);
                yield true;
            }

            default -> level.setBlock(pos, state, setFlags);
        };
    }

    static boolean place(LevelAccessor level, BlockPos pos, BlockState state, int setFlags) {
        return place(level, pos, state, level.getRandom(), setFlags);
    }
}
