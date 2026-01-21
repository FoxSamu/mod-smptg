package net.foxboi.salted.mixin.common;

import net.foxboi.salted.common.block.AbstractMultifaceBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(
            method = "updateOrDestroy(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;II)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void tryUpdateOrDestroyMultiface(BlockState oldState, BlockState newState, LevelAccessor level, BlockPos pos, int setFlags, int recursion, CallbackInfo ci) {
        var oldBlock = oldState.getBlock();
        if (oldBlock instanceof AbstractMultifaceBlock || oldBlock instanceof MultifaceBlock) {
            AbstractMultifaceBlock.destroyFaces(level, pos, oldState, newState, setFlags, recursion);
            ci.cancel();
        }
    }
}
