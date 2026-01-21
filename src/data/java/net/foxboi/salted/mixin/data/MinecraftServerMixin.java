package net.foxboi.salted.mixin.data;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import net.foxboi.salted.data.DataRunner;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(
            method = "reloadResources",
            at = @At(value = "HEAD")
    )
    private static void onReloadResources(Collection<String> packs, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        if (DataRunner.shouldRunOnResourceReload()) {
            DataRunner.run();
        }
    }
}
