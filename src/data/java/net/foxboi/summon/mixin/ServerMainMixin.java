package net.foxboi.summon.mixin;

import net.minecraft.server.Main;

import net.foxboi.summon.impl.Summon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class ServerMainMixin {
	@Inject(method = "main", at = @At(value = "NEW", target = "net/minecraft/server/dedicated/DedicatedServerSettings"))
	private static void main(String[] args, CallbackInfo info) {
		Summon.runNormal();
	}
}
