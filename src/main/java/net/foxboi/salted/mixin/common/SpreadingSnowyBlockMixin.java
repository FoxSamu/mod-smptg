package net.foxboi.salted.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyBlock;
import net.minecraft.world.level.block.state.BlockState;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.foxboi.salted.common.block.GrowthSpreadingUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpreadingSnowyBlock.class)
public abstract class SpreadingSnowyBlockMixin {
    @Shadow
    private static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
        throw new AssertionError();
    }

    @ModifyExpressionValue(
            method = "randomTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z")
    )
    private boolean spreadToNonDirtBlocks(boolean original, BlockState state, ServerLevel level, BlockPos pos, RandomSource random, @Local(name = "testPos") BlockPos testPos) {
        var self = (Block) (Object) this;

        if (self == Blocks.GRASS_BLOCK) {
            var spreadState = GrowthSpreadingUtil.getGrassSpreadState(level, testPos);
            if (spreadState != null && canPropagate(spreadState, level, testPos)) {
                level.setBlockAndUpdate(testPos, spreadState);
                return false; // We are targeting an if condition, returning false will skip the code inside that as we have just overridden that
            }
        }

        if (self == Blocks.MYCELIUM) {
            var spreadState = GrowthSpreadingUtil.getMyceliumSpreadState(level, testPos);
            if (spreadState != null && canPropagate(spreadState, level, testPos)) {
                level.setBlockAndUpdate(testPos, spreadState);
                return false; // We are targeting an if condition, returning false will skip the code inside that as we have just overridden that
            }
        }

        return original;
    }
}
