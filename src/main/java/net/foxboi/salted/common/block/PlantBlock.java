package net.foxboi.salted.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

/**
 * A plant block. This interface is implemented on different blocks that provide plant logic. It is an interface so that
 * plant block classes can extend any vanilla block class whilst also providing the {@link PlantBlock} logic.
 * <p>
 * SMPTG provides a lot of plants, so to ensure easy customisability, a plant blocks does not only have the default
 * set of block properties ({@link net.minecraft.world.level.block.Block.Properties Block.Properties}) but also has
 * an extra set of properties from {@link PlantConfig}. This config provides common information about the plant, mostly
 * independent of the plant's logic. The plant config includes things like it's hitbox size and what blocks it can grow
 * on.
 * </p>
 * <p>
 * Note that if you implement a {@link PlantBlock}, bonemealing logic is defined by {@link PlantConfig} and delegated
 * automatically as {@link PlantBlock} implements {@link BonemealableBlock}. Hence, you don't need to implement this
 * logic in your block implementation. However, if your plant block implementation comes with custom growing logic, then
 * it should be defined in {@link BonemealBehaviors}.
 * </p>
 */
public interface PlantBlock extends BonemealableBlock {
    /**
     * Get's this plant block's {@link PlantConfig}.
     */
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
