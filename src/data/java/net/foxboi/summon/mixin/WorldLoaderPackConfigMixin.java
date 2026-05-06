package net.foxboi.summon.mixin;

import net.minecraft.server.WorldLoader;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.world.level.WorldDataConfiguration;

import net.foxboi.summon.impl.Summon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import oshi.util.tuples.Pair;

@Mixin(WorldLoader.PackConfig.class)
public class WorldLoaderPackConfigMixin {
    @Inject(
            method = "createResourceManager",
            at = @At(value = "HEAD")
    )
    private static void onReloadResources(CallbackInfoReturnable<Pair<WorldDataConfiguration, CloseableResourceManager>> cir) {
        Summon.runLive();
    }
}
