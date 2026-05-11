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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;

import com.mojang.serialization.MapCodec;

public abstract class AbstractSpreadingBlock extends SnowyBlock {
    private final ResourceKey<Block> baseBlock;

    public AbstractSpreadingBlock(Properties properties, ResourceKey<Block> baseBlock) {
        super(properties);
        this.baseBlock = baseBlock;
    }

    @Override
    protected abstract MapCodec<? extends AbstractSpreadingBlock> codec();

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

    public static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
        var above = pos.above();
        return canStayAlive(state, level, pos) && !level.getFluidState(above).is(FluidTags.WATER);
    }

    protected abstract BlockState getSpreadState(ServerLevel level, BlockPos pos);

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        var blocks = level.registryAccess().lookupOrThrow(Registries.BLOCK);
        var baseBlock = blocks.getOptional(this.baseBlock);

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

            var spreadState = getSpreadState(level, testPos);
            if (spreadState != null && canPropagate(spreadState, level, testPos)) {
                level.setBlockAndUpdate(testPos, spreadState);
            }
        }
    }
}
