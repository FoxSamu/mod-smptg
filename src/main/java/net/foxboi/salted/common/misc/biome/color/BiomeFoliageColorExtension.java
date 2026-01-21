package net.foxboi.salted.common.misc.biome.color;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.foxboi.salted.common.Smptg;
import net.minecraft.util.ExtraCodecs;

public record BiomeFoliageColorExtension(
        Optional<Integer> darkRedFoliageColorOverride,
        Optional<Integer> redFoliageColorOverride,
        Optional<Integer> goldenFoliageColorOverride,
        Optional<Integer> yellowFoliageColorOverride
) {
    public static final BiomeFoliageColorExtension DEFAULT = new BiomeFoliageColorExtension(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
    );

    public static final Codec<BiomeFoliageColorExtension> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ExtraCodecs.STRING_RGB_COLOR.optionalFieldOf(Smptg.sid("dark_red_foliage_color_override")).forGetter(BiomeFoliageColorExtension::darkRedFoliageColorOverride),
            ExtraCodecs.STRING_RGB_COLOR.optionalFieldOf(Smptg.sid("red_foliage_color_override")).forGetter(BiomeFoliageColorExtension::redFoliageColorOverride),
            ExtraCodecs.STRING_RGB_COLOR.optionalFieldOf(Smptg.sid("golden_foliage_color_override")).forGetter(BiomeFoliageColorExtension::goldenFoliageColorOverride),
            ExtraCodecs.STRING_RGB_COLOR.optionalFieldOf(Smptg.sid("yellow_foliage_color_override")).forGetter(BiomeFoliageColorExtension::yellowFoliageColorOverride)
    ).apply(inst, BiomeFoliageColorExtension::new));
}
