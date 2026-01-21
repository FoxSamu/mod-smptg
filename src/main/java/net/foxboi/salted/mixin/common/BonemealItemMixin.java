package net.foxboi.salted.mixin.common;

import net.foxboi.salted.common.misc.Misc;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BonemealItemMixin {
    @Inject(
            method = "growWaterPlant",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getRandom()Lnet/minecraft/util/RandomSource;"),
            cancellable = true
    )
    private static void onGrowWaterPlant(ItemStack item, Level abstractLevel, BlockPos pos, Direction clickedFace, CallbackInfoReturnable<Boolean> cir) {
        var level = (ServerLevel) abstractLevel;

        if (Misc.tryModifiedUnderwaterBonemealEffect(level, level.getRandom(), pos, clickedFace)) {
            cir.setReturnValue(true);
        }
    }
}
