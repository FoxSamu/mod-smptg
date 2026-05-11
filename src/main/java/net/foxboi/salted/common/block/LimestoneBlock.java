package net.foxboi.salted.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LimestoneBlock extends Block implements BonemealableBlock {
    public LimestoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return canPropagate(state, level, pos) && isNextToGrass(level, pos);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.setBlockAndUpdate(pos, ModBlocks.GRASSY_LIMESTONE.defaultBlockState());
    }

    private static boolean isNextToGrass(LevelReader level, BlockPos pos) {
        var mpos = pos.mutable();
        for (int x = -1; x <= 1; x ++) {
            for (int z = -1; z <= 1; z ++) {
                if (x == 0 && z == 0) {
                    continue; // Cannot spread purely vertically
                }

                for (int y = -2; y <= 2; y ++) {
                    mpos.setWithOffset(pos, x, y, z);

                    var nearbyState = level.getBlockState(mpos);
                    if (canGrowFrom(nearbyState) && canPropagate(nearbyState, level, mpos)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
        return GrassyLimestoneBlock.canStayAlive(state, level, pos) && !level.getFluidState(pos.above()).is(FluidTags.WATER);
    }

    private static boolean canGrowFrom(BlockState state) {
        return state.is(ModBlockTags.GRASS_SPREAD_SOURCE);
    }
}
