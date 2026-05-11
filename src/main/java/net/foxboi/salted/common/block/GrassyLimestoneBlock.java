package net.foxboi.salted.common.block;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockIds;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;

import com.mojang.serialization.MapCodec;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.BonemealSpreadingLogic;

public class GrassyLimestoneBlock extends SnowyBlock implements BonemealableBlock {
	public static final MapCodec<GrassyLimestoneBlock> CODEC = simpleCodec(GrassyLimestoneBlock::new);

	@Override
	public MapCodec<GrassyLimestoneBlock> codec() {
		return CODEC;
	}

	public GrassyLimestoneBlock(Properties properties) {
		super(properties);
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
        BonemealSpreadingLogic.grassAndFlowers(level, rng, pos);
	}

	@Override
	public Type getType() {
		return Type.NEIGHBOR_SPREADER;
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canStayAlive(state, level, pos)) {
            level.setBlockAndUpdate(pos, ModBlocks.LIMESTONE.defaultBlockState());
        }
    }

	public static boolean canStayAlive(BlockState state, LevelReader level, BlockPos pos) {
		var above = pos.above();
		var aboveState = level.getBlockState(above);

		if (aboveState.is(Blocks.SNOW) && aboveState.getValue(SnowLayerBlock.LAYERS) == 1) {
			return true;
		}

        if (aboveState.getFluidState().isFull()) {
            return false;
        }

        var lightBlockInto = LightEngine.getLightBlockInto(state, aboveState, Direction.UP, aboveState.getLightDampening());
        return lightBlockInto < 15;
    }
}
