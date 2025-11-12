package net.foxboi.salted.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public interface PlantBlock extends BonemealableBlock {
    PlantConfig getPlantConfig();

    default boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return getPlantConfig().bonemealBehavior().isValidBonemealTarget(level, pos, state);
    }

    default boolean isBonemealSuccess(Level level, RandomSource rng, BlockPos pos, BlockState state) {
        return getPlantConfig().bonemealBehavior().isBonemealSuccess(level, rng, pos, state);
    }

    default void performBonemeal(ServerLevel level, RandomSource rng, BlockPos pos, BlockState state) {
        getPlantConfig().bonemealBehavior().performBonemeal(level, rng, pos, state);
    }
}
