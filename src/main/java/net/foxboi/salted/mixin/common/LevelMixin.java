package net.foxboi.salted.mixin.common;

import net.foxboi.salted.common.block.DiagonallyAttachableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
@Debug(export = true)
public class LevelMixin {
    @Inject(
            method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/state/BlockState;updateIndirectNeighbourShapes(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;II)V",
                    ordinal = 1,
                    shift = At.Shift.AFTER
            )
    )
    private void onUpdateNeighbors(BlockPos pos, BlockState state, int flags, int maxRecursion, CallbackInfoReturnable<Boolean> cir) {
        var recursiveFlags = flags & ~(Block.UPDATE_NEIGHBORS | Block.UPDATE_SUPPRESS_DROPS);

        DiagonallyAttachableBlock.updateDiagonallyAttachedBlocks((Level) (Object) this, pos, state, recursiveFlags, maxRecursion - 1);
    }
}
