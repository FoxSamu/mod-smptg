package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.BonemealSpreadingLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.references.BlockIds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SpreadingSnowyBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MossyDirtBlock extends SpreadingSnowyBlock implements BonemealableBlock {
	public static final MapCodec<MossyDirtBlock> CODEC = simpleCodec(MossyDirtBlock::new);

	@Override
	public MapCodec<MossyDirtBlock> codec() {
		return CODEC;
	}

	public MossyDirtBlock(BlockBehaviour.Properties properties) {
		super(properties, BlockIds.DIRT);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return level.getBlockState(pos.above()).isAir();
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource rng, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource rng, BlockPos pos, BlockState state) {
        BonemealSpreadingLogic.grassAndMoss(level, rng, pos);
	}

	@Override
	public BonemealableBlock.Type getType() {
		return BonemealableBlock.Type.NEIGHBOR_SPREADER;
	}
}
