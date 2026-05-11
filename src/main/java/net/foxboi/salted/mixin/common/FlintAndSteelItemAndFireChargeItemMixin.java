package net.foxboi.salted.mixin.common;

import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.context.UseOnContext;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.foxboi.salted.common.block.BrazierBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({ FlintAndSteelItem.class, FireChargeItem.class })
public class FlintAndSteelItemAndFireChargeItemMixin {
    @ModifyExpressionValue(
            method = "useOn",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/CampfireBlock;canLight(Lnet/minecraft/world/level/block/state/BlockState;)Z")
    )
    private boolean onUseOn(boolean original, UseOnContext context) {
        // The item checks if there is any block that can be lit, and then tries to set the 'lit' property
        // of that. We can just hook there and add our braziers to the lightable blocks.
        // Since both fire charge and flint and steal do this the same way it can save us a mixin.
        return original || BrazierBlock.canLight(context.getLevel().getBlockState(context.getClickedPos()));
    }
}
