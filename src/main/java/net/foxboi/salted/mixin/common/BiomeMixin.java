package net.foxboi.salted.mixin.common;

import net.foxboi.salted.common.misc.biome.color.BiomeInj;
import net.foxboi.salted.common.misc.Misc;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin implements BiomeInj {
    @Shadow
    @Final
    private Biome.ClimateSettings climateSettings;

    @Inject(
            method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onShouldFreeze(LevelReader level, BlockPos pos, boolean freezeAnywhere, CallbackInfoReturnable<Boolean> cir) {
        if (Misc.isNextToSalt(level, pos)) {
            cir.setReturnValue(false);
        }
    }
    @Inject(
            method = "shouldSnow",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onShouldSnow(LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (Misc.isNextToSalt(level, pos)) {
            cir.setReturnValue(false);
        }
    }

    @Override
    public double smptg$temperature() {
        return climateSettings.temperature();
    }

    @Override
    public double smptg$downfall() {
        return climateSettings.downfall();
    }
}
