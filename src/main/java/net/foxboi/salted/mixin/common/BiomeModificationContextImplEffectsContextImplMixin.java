package net.foxboi.salted.mixin.common;

import net.foxboi.salted.common.misc.biome.color.BiomeFoliageColorExtension;
import net.foxboi.salted.common.misc.biome.color.BiomeSpecialEffectsInj;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net.fabricmc.fabric.impl.biome.modification.BiomeModificationContextImpl$EffectsContextImpl")
public class BiomeModificationContextImplEffectsContextImplMixin implements BiomeSpecialEffectsInj {
    @Shadow
    @Final
    private BiomeSpecialEffects effects;

    @Override
    public BiomeFoliageColorExtension smptg$getFoliageColorExtension() {
        return ((BiomeSpecialEffectsInj) (Object) effects).smptg$getFoliageColorExtension();
    }

    @Override
    public void smptg$setFoliageColorExtension(BiomeFoliageColorExtension extension) {
        ((BiomeSpecialEffectsInj) (Object) effects).smptg$setFoliageColorExtension(extension);
    }
}
