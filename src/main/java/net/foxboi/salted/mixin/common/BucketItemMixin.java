package net.foxboi.salted.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public abstract class BucketItemMixin {
    @Shadow
    @Final
    private Fluid content;

    @Shadow
    protected abstract void playEmptySound(LivingEntity entity, LevelAccessor level, BlockPos pos);

    @Inject(
            method = "emptyContents",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/LiquidBlockContainer;placeLiquid(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)Z"),
            cancellable = true
    )
    private void onPlaceLiquid(LivingEntity entity, Level level, BlockPos pos, BlockHitResult hit, CallbackInfoReturnable<Boolean> cir) {
        var state = level.getBlockState(pos);
        var block = state.getBlock();
        var lbc = (LiquidBlockContainer) block;

        if (!lbc.placeLiquid(level, pos, state, ((FlowingFluid) content).getSource(false))) {
            cir.setReturnValue(false);
        } else {
            playEmptySound(entity, level, pos);
            cir.setReturnValue(true);
        }
    }
}
