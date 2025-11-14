package net.foxboi.salted.mixin.main;

import java.util.Optional;

import net.foxboi.salted.common.misc.biome.BiomeModificationContextEffectsContextInj;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net/fabricmc/fabric/impl/biome/modification/BiomeModificationContextImpl$EffectsContextImpl")
public class BiomeModificationContextEffectsContextMixin implements BiomeModificationContextEffectsContextInj {

    @Shadow
    @Final
    private BiomeSpecialEffects effects;

    @Override
    public void smptg$setDryFoliageColor(Optional<Integer> color) {
        effects.dryFoliageColorOverride = color;
    }
}
