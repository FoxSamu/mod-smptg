package net.foxboi.salted.mixin.common;

import com.llamalad7.mixinextras.sugar.Local;
import net.foxboi.salted.common.block.PlaceLogic;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleBlockFeature.class)
public class SimpleBlockFeatureMixin {
    @Inject(
            method = "place",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;", ordinal = 0),
            cancellable = true
    )
    private void redirectSetBlock(FeaturePlaceContext<SimpleBlockConfiguration> ctx, CallbackInfoReturnable<Boolean> cir, @Local BlockState state) {
        if (state.getBlock() instanceof PlaceLogic spl) {
            if (!spl.placeAt(ctx.level(), ctx.origin(), state, ctx.random(), 2)) {
                cir.setReturnValue(false);
                return;
            }

            // Schedule tick now if configured, since we're cancelling the method after this
            if (ctx.config().scheduleTick()) {
                ctx.level().scheduleTick(ctx.origin(), ctx.level().getBlockState(ctx.origin()).getBlock(), 1);
            }

            cir.setReturnValue(true);
        }
    }
}
