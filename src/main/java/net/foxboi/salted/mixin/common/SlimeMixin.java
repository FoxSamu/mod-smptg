package net.foxboi.salted.mixin.common;

import net.foxboi.salted.common.misc.Misc;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slime.class)
public class SlimeMixin {
    @Unique
    private int saltDamageCooldown = 0;

    @SuppressWarnings("deprecation")
    @Inject(
            method = "tick",
            at = @At("RETURN")
    )
    private void onTick(CallbackInfo ci) {
        var self = (Slime) (Object) (this);

        var inSalt = Misc.isInDamagingSalt(self);
        if (inSalt) {
            if (saltDamageCooldown <= 0) {
                saltDamageCooldown = 10;

                self.hurt(self.damageSources().dryOut(), 2f);
            } else {
                saltDamageCooldown --;
            }
        } else {
            saltDamageCooldown = 0;
        }
    }

    @Inject(
            method = "addAdditionalSaveData",
            at = @At("RETURN")
    )
    private void onSave(ValueOutput out, CallbackInfo ci) {
        out.putInt("salted:saltDamageCooldown", saltDamageCooldown);
    }

    @Inject(
            method = "readAdditionalSaveData",
            at = @At("RETURN")
    )
    private void onLoad(ValueInput in, CallbackInfo ci) {
        saltDamageCooldown = in.getIntOr("salted:saltDamageCooldown", 0);
    }
}
