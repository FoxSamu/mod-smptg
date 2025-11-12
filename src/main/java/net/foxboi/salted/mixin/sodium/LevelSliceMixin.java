package net.foxboi.salted.mixin.sodium;

import java.util.Map;

import net.caffeinemc.mods.sodium.client.world.LevelSlice;
import net.foxboi.salted.common.color.BiomeColor;
import net.foxboi.salted.common.color.BiomeColorLevelInj;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ColorResolver;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LevelSlice.class)
public class LevelSliceMixin implements BiomeColorLevelInj {
    @Shadow
    @Final
    private ClientLevel level;

    @Override
    public int smptg$computeBlockTintUncached(BlockPos pos, ColorResolver resolver) {
        return ((BiomeColorLevelInj) level).smptg$computeBlockTintUncached(pos, resolver);
    }

    @Override
    public int smptg$getBlockTint(BlockPos pos, ResourceLocation color) {
        return ((BiomeColorLevelInj) level).smptg$getBlockTint(pos, color);
    }

    @Override
    public void smptg$reloadColors(Map<ResourceLocation, BiomeColor> map) {
        ((BiomeColorLevelInj) level).smptg$reloadColors(map);
    }

    @Inject(
            method = "getBlockTint",
            at = @At("HEAD"),
            cancellable = true
    )
    private void injGetBlockTint(BlockPos pos, ColorResolver resolver, CallbackInfoReturnable<Integer> cir) {
        // Redirect to use our block tint system
        // TODO this doesn't currently benefit from Sodium's optimisations
        if (resolver == BiomeColors.WATER_COLOR_RESOLVER)
            cir.setReturnValue(smptg$getBlockTint(pos, net.foxboi.salted.common.color.BiomeColors.WATER));
        if (resolver == BiomeColors.GRASS_COLOR_RESOLVER)
            cir.setReturnValue(smptg$getBlockTint(pos, net.foxboi.salted.common.color.BiomeColors.GRASS));
        if (resolver == BiomeColors.FOLIAGE_COLOR_RESOLVER)
            cir.setReturnValue(smptg$getBlockTint(pos, net.foxboi.salted.common.color.BiomeColors.FOLIAGE));
        if (resolver == BiomeColors.DRY_FOLIAGE_COLOR_RESOLVER)
            cir.setReturnValue(smptg$getBlockTint(pos, net.foxboi.salted.common.color.BiomeColors.DRY_FOLIAGE));
    }
}
