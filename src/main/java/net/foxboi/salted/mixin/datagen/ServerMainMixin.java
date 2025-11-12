package net.foxboi.salted.mixin.datagen;

import net.foxboi.salted.data.DataRunner;
import net.minecraft.server.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class ServerMainMixin {
	@Inject(
            method = "main",
            at = @At(value = "NEW", target = "net/minecraft/server/dedicated/DedicatedServerSettings"),
            cancellable = true
    )
	private static void onMain(String[] args, CallbackInfo info) {
		if (DataRunner.shouldRunAndStop()) {
			DataRunner.run();
			info.cancel();
		}
	}
}
