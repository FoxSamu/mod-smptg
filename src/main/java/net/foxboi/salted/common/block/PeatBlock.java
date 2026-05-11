package net.foxboi.salted.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.serialization.MapCodec;

public class PeatBlock extends Block {
    public static final MapCodec<PeatBlock> CODEC = simpleCodec(PeatBlock::new);

    public PeatBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (PeatUtil.isBurning(level, pos) || PeatUtil.isNearLava(level, pos)) {
            level.setBlockAndUpdate(pos, ModBlocks.DRIED_PEAT.defaultBlockState());
        }
    }
}
