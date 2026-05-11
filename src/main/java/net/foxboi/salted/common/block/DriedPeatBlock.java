package net.foxboi.salted.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.serialization.MapCodec;

public class DriedPeatBlock extends Block {
    public static final MapCodec<PeatBlock> CODEC = simpleCodec(PeatBlock::new);

    public DriedPeatBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!PeatUtil.isBurning(level, pos) && PeatUtil.isNearWater(level, pos)) {
            level.setBlockAndUpdate(pos, ModBlocks.PEAT.defaultBlockState());
        }
    }
}
