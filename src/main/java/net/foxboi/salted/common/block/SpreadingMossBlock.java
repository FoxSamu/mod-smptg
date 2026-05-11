package net.foxboi.salted.common.block;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SpreadingSnowyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;

import com.mojang.serialization.MapCodec;

public abstract class SpreadingMossBlock extends SpreadingSnowyBlock {
    private final ResourceKey<Block> baseBlock;

    public SpreadingMossBlock(Properties properties, ResourceKey<Block> baseBlock) {
        super(properties, baseBlock);
        this.baseBlock = baseBlock;
    }

    private static boolean canStayAlive(final BlockState state, final LevelReader level, final BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);
        if (aboveState.is(Blocks.SNOW) && (Integer) aboveState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        }
        if (aboveState.getFluidState().isFull()) {
            return false;
        }

        var lightBlockInto = LightEngine.getLightBlockInto(state, aboveState, Direction.UP, aboveState.getLightDampening());
        return lightBlockInto < 15;
    }

    private static boolean canPropagate(final BlockState state, final LevelReader level, final BlockPos pos) {
        var above = pos.above();
        return canStayAlive(state, level, pos) && !level.getFluidState(above).is(FluidTags.WATER);
    }

    @Override
    protected void randomTick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        Registry<Block> blocks = level.registryAccess().lookupOrThrow(Registries.BLOCK);
        Optional<Block> baseBlock = blocks.getOptional(this.baseBlock);
        if (baseBlock.isEmpty()) {
            return;
        }

        if (!canStayAlive(state, level, pos)) {
            level.setBlockAndUpdate(pos, baseBlock.get().defaultBlockState());
            return;
        }

        if (level.getMaxLocalRawBrightness(pos.above()) < 9) {
            return;
        }

        for (var i = 0; i < 4; i++) {
            var testPos = pos.offset(
                    random.nextInt(3) - 1,
                    random.nextInt(5) - 3,
                    random.nextInt(3) - 1
            );

            var spreadState = GrowthSpreadingUtil.getMossSpreadState(level, testPos);
            if (spreadState != null && canPropagate(spreadState, level, testPos)) {
                level.setBlockAndUpdate(testPos, spreadState);
            }
        }
    }
}
