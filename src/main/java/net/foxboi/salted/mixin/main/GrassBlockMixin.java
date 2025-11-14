package net.foxboi.salted.mixin.main;

import net.foxboi.salted.common.misc.BonemealSpreadingLogic;
import net.foxboi.salted.common.misc.Misc;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GrassBlock.class)
public class GrassBlockMixin {
    @Inject(
            method = "performBonemeal",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onPerformBonemeal(ServerLevel level, RandomSource rng, BlockPos pos, BlockState state, CallbackInfo ci) {
        if (Misc.tryModifiedGrassBlockBonemealEffect(level, rng, pos)) {
            ci.cancel();
        }
    }

    @Redirect(
            method = "performBonemeal",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0)
    )
    private boolean checkIsGrassBlock(BlockState instance, Block block) {
        return BonemealSpreadingLogic.GRASS_VALID_BLOCKS.contains(instance.getBlock());
    }
}
