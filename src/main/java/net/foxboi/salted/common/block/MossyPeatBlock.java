package net.foxboi.salted.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SpreadingSnowyBlock;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.serialization.MapCodec;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.BonemealSpreadingLogic;

public class MossyPeatBlock extends AbstractSpreadingBlock implements BonemealableBlock {
	public static final MapCodec<MossyPeatBlock> CODEC = simpleCodec(MossyPeatBlock::new);

	public MossyPeatBlock(Properties properties) {
		super(properties, Smptg.key(Registries.BLOCK, "peat"));
	}

	@Override
	public MapCodec<MossyPeatBlock> codec() {
		return CODEC;
	}

	@Override
	protected BlockState getSpreadState(ServerLevel level, BlockPos pos) {
		return GrowthSpreadingUtil.getMossSpreadState(level, pos);
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
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (PeatUtil.isBurning(level, pos) || PeatUtil.isNearLava(level, pos)) {
			level.setBlockAndUpdate(pos, ModBlocks.DRIED_PEAT.defaultBlockState());
			return;
		}

		super.randomTick(state, level, pos, random);
	}

	@Override
	public Type getType() {
		return Type.NEIGHBOR_SPREADER;
	}
}
