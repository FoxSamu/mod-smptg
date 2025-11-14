package net.foxboi.salted.mixin.datagen;

import java.util.concurrent.CompletableFuture;

import net.foxboi.salted.data.DataRunner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Inject(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;getBackendDescription()Ljava/lang/String;")
    )
	private void onInit(CallbackInfo info) {
		if (DataRunner.shouldRunAndStop()) {
            DataRunner.run();
			System.exit(0);
		}
	}

    @Inject(
            method = "<init>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/ReloadableResourceManager;createReload(Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Ljava/util/List;)Lnet/minecraft/server/packs/resources/ReloadInstance;")
    )
    private void onReloadPacksInit(GameConfig config, CallbackInfo ci) {
        if (DataRunner.shouldRunOnResourceReload()) {
            DataRunner.run();
        }
    }

    @Inject(
            method = "reloadResourcePacks(ZLnet/minecraft/client/Minecraft$GameLoadCookie;)Ljava/util/concurrent/CompletableFuture;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/ReloadableResourceManager;createReload(Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Ljava/util/List;)Lnet/minecraft/server/packs/resources/ReloadInstance;")
    )
    private void onReloadPacks(boolean bl, @Nullable @Coerce Object cookie, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        if (DataRunner.shouldRunOnResourceReload()) {
            DataRunner.run();
        }
    }
}