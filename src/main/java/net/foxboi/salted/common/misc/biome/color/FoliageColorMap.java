package net.foxboi.salted.common.misc.biome.color;

import java.util.Optional;
import java.util.function.Function;

import net.minecraft.util.ARGB;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.biome.Biome;

public class FoliageColorMap implements ColorResolver {
    public static final int DAR_RED_DEFAULT = 0xFF8B2A3A;
    public static final int RED_DEFAULT = 0xFFE12E22;
    public static final int GOLDEN_DEFAULT = 0xFFD87800;
    public static final int YELLOW_DEFAULT = 0xFFECBD10;

    public static final int GOLDGREEN_DEFAULT = ARGB.srgbLerp(0.65f, YELLOW_DEFAULT, FoliageColor.FOLIAGE_DEFAULT);

    public static final FoliageColorMap DARK_RED = new FoliageColorMap(DAR_RED_DEFAULT, false, BiomeFoliageColorExtension::darkRedFoliageColorOverride);
    public static final FoliageColorMap RED = new FoliageColorMap(RED_DEFAULT, false, BiomeFoliageColorExtension::redFoliageColorOverride);
    public static final FoliageColorMap GOLDEN = new FoliageColorMap(GOLDEN_DEFAULT, false, BiomeFoliageColorExtension::goldenFoliageColorOverride);
    public static final FoliageColorMap YELLOW = new FoliageColorMap(YELLOW_DEFAULT, false, BiomeFoliageColorExtension::yellowFoliageColorOverride);


    private final int fallbackColor;
    private final boolean useColormapForDefaultColor;
    private final Function<BiomeFoliageColorExtension, Optional<Integer>> override;

    private ColorMap colorMap;

    public FoliageColorMap(int fallbackColor, boolean useColormapForDefaultColor, Function<BiomeFoliageColorExtension, Optional<Integer>> override) {
        this.fallbackColor = fallbackColor;
        this.useColormapForDefaultColor = useColormapForDefaultColor;
        this.override = override;
    }

    public void setColorMap(ColorMap colorMap) {
        this.colorMap = colorMap;
    }

    private Optional<Integer> getOverride(Biome biome) {
        var sfx = biome.getSpecialEffects();
        var ext = ((BiomeSpecialEffectsInj) (Object) sfx).smptg$getFoliageColorExtension();
        return override.apply(ext);
    }

    public int sample(Biome biome, double tmp, double df) {
        var override = getOverride(biome);
        if (override.isPresent()) {
            return override.get();
        }

        if (colorMap == null) {
            return fallbackColor;
        }

        return colorMap.get(tmp, df);
    }

    public int getDefault() {
        return useColormapForDefaultColor && colorMap != null
                ? colorMap.getDefaultColor()
                : fallbackColor;
    }

    @Override
    public int getColor(Biome biome, double x, double z) {
        var tmp = ((BiomeInj) (Object) biome).smptg$temperature();
        var df = ((BiomeInj) (Object) biome).smptg$downfall();
        return sample(biome, tmp, df);
    }
}
