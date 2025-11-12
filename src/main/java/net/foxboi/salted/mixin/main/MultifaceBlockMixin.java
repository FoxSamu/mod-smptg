package net.foxboi.salted.mixin.main;

import java.util.Set;

import net.foxboi.salted.common.block.AbstractMultifaceBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultifaceBlock.class)
public class MultifaceBlockMixin {
    @Inject(
            method = "availableFaces",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void getAvailableFaces(BlockState state, CallbackInfoReturnable<Set<Direction>> cir) {
        if (state.getBlock() instanceof AbstractMultifaceBlock) {
            cir.setReturnValue(AbstractMultifaceBlock.availableFaces(state));
        }
    }
}
