package net.foxboi.salted.mixin.common;

import net.minecraft.server.players.PlayerList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(
            method = "reloadResources",
            at = @At("HEAD")
    )
    private void beforeSyncResources(CallbackInfo ci) {

    }
}
