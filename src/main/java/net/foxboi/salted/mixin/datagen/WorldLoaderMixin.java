package net.foxboi.salted.mixin.datagen;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import net.foxboi.salted.data.DataRunner;
import net.minecraft.server.WorldLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldLoader.class)
public class WorldLoaderMixin {
    @Inject(
            method = "load",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/WorldLoader$PackConfig;createResourceManager()Lcom/mojang/datafixers/util/Pair;")
    )
    private static void onReloadResources(WorldLoader.InitConfig initConfig, WorldLoader.WorldDataSupplier<?> worldDataSupplier, WorldLoader.ResultFactory<?, ?> resultFactory, Executor executor, Executor executor2, CallbackInfoReturnable<CompletableFuture<?>> cir) {
        if (DataRunner.shouldRunOnResourceReload()) {
            DataRunner.run();
        }
    }
}
