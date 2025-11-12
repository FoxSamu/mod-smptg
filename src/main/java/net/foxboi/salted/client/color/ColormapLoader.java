package net.foxboi.salted.client.color;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleResourceReloader;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.color.Colormap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ColormapLoader extends SimpleResourceReloader<Map<ResourceLocation, Colormap>> {
    public static final ResourceLocation ID = Smptg.id("colormaps");

    public static final FileToIdConverter NAMER = new FileToIdConverter("textures/colormap", ".png");

    public static final Logger LOGGER = LogManager.getLogger(Smptg.ID);

    private static Colormap load(ResourceLocation id, Resource res) {
        try (var reader = res.open(); NativeImage img = NativeImage.read(reader)) {
            @SuppressWarnings("deprecation")
            var array = img.makePixelArray();
            return new Colormap(img.getWidth(), img.getHeight(), array);
        } catch (Exception exc) {
            LOGGER.error("Failed to load biome color {}", id);
            return new Colormap(1, 1, new int[] {0xFFFF00FF});
        }
    }

    @Override
    protected Map<ResourceLocation, Colormap> prepare(SharedState state) {
        var map = NAMER.listMatchingResources(state.resourceManager());
        var result = new HashMap<ResourceLocation, Colormap>();
        map.forEach((path, r) -> {
            var name = NAMER.fileToId(path);
            result.put(name, load(name, r));
        });

        LOGGER.info("Loaded {} colourmaps", result.size());
        return result;
    }

    @Override
    protected void apply(Map<ResourceLocation, Colormap> prepared, SharedState state) {
        BiomeColorsClient.reloadColormaps(prepared);
    }
}
