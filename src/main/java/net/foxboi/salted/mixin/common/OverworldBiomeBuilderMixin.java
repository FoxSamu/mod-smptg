package net.foxboi.salted.mixin.common;

import java.util.function.Consumer;

import com.mojang.datafixers.util.Pair;
import net.foxboi.salted.common.levelgen.biome.source.ModifiedOverworldBiomeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OverworldBiomeBuilder.class)
public class OverworldBiomeBuilderMixin {
    @Inject(
            method = "addBiomes",
            at = @At("HEAD"),
            cancellable = true
    )

    private void onAddBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> output, CallbackInfo ci) {
        new ModifiedOverworldBiomeBuilder().addBiomes(output);
        ci.cancel();
    }
}
