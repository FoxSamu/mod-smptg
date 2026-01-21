package net.foxboi.salted.mixin.common;

import com.mojang.serialization.Codec;
import net.foxboi.salted.common.misc.biome.color.BiomeFoliageColorExtension;
import net.foxboi.salted.common.misc.biome.color.BiomeSpecialEffectsInj;
import net.foxboi.salted.common.misc.CodecExtension;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.spongepowered.asm.mixin.*;

@Mixin(BiomeSpecialEffects.class)
public class BiomeSpecialEffectsMixin implements BiomeSpecialEffectsInj {
    @Shadow
    @Final
    @Mutable
    public static Codec<BiomeSpecialEffects> CODEC;

    @Unique
    private BiomeFoliageColorExtension foliageColorExtension = BiomeFoliageColorExtension.DEFAULT;

    static {
        CODEC = new CodecExtension<>(
                CODEC,
                BiomeFoliageColorExtension.CODEC,
                (sfx, ext) -> ((BiomeSpecialEffectsInj) (Object) sfx).smptg$setFoliageColorExtension(ext),
                sfx -> ((BiomeSpecialEffectsInj) (Object) sfx).smptg$getFoliageColorExtension()
        );
    }

    @Override
    public BiomeFoliageColorExtension smptg$getFoliageColorExtension() {
        return foliageColorExtension;
    }

    @Override
    public void smptg$setFoliageColorExtension(BiomeFoliageColorExtension extension) {
        foliageColorExtension = extension;
    }
}
