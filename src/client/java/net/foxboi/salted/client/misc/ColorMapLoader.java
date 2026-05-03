package net.foxboi.salted.client.misc;

import com.mojang.blaze3d.platform.NativeImage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleReloadListener;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.biome.color.ColorMap;
import net.foxboi.salted.common.misc.biome.color.FoliageColorMap;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class ColorMapLoader extends SimpleReloadListener<ColorMap> {
    public static final ColorMapLoader DARK_RED_FOLIAGE = new ColorMapLoader(Smptg.id("dark_red_foliage"), FoliageColorMap.DARK_RED);
    public static final ColorMapLoader RED_FOLIAGE = new ColorMapLoader(Smptg.id("red_foliage"), FoliageColorMap.RED);
    public static final ColorMapLoader GOLDEN_FOLIAGE = new ColorMapLoader(Smptg.id("golden_foliage"), FoliageColorMap.GOLDEN);
    public static final ColorMapLoader YELLOW_FOLIAGE = new ColorMapLoader(Smptg.id("yellow_foliage"), FoliageColorMap.YELLOW);

    public static final FileToIdConverter NAMER = new FileToIdConverter("textures/colormap", ".png");

    public static final Logger LOGGER = LogManager.getLogger(Smptg.ID);

    private final Identifier colorMapName;
    private final FoliageColorMap foliageColorMap;

    public ColorMapLoader(Identifier colorMapName, FoliageColorMap foliageColorMap) {
        this.colorMapName = colorMapName;
        this.foliageColorMap = foliageColorMap;
    }

    public Identifier id() {
        return colorMapName.withPrefix("colormap/");
    }

    @Override
    protected ColorMap prepare(SharedState state) {
        var id = NAMER.idToFile(colorMapName);

        try (var reader = state.resourceManager().getResourceOrThrow(id).open(); var img = NativeImage.read(reader)) {
            @SuppressWarnings("deprecation")
            var array = img.makePixelArray();
            return new ColorMap(img.getWidth(), img.getHeight(), array);
        } catch (Exception exc) {
            LOGGER.error("Failed to load color map {}", id);
            return null;
        }
    }

    @Override
    protected void apply(ColorMap prepared, SharedState state) {
        foliageColorMap.setColorMap(prepared);
    }
}
