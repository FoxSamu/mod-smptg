package net.foxboi.salted.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.MossyCarpetBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Allows for custom placing logic used by {@link net.minecraft.world.level.levelgen.feature.SimpleBlockFeature}.
 * This feature has some hardcoded logic for certain blocks, like double blocks, to ensure that it is placed correctly.
 * Using a mixin, this interface is also checked for by {@code SimpleBlockFeature}, calling the {@link #placeAt} method
 * to provide placement logic and allowing modded blocks to implement custom placement logic in a more soft-coded way.
 */
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
