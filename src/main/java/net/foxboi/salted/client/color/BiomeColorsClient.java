package net.foxboi.salted.client.color;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.color.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BiomeColorsClient {
    public static final Map<Identifier, BiomeColor> COLORS = new HashMap<>();
    public static final Map<Identifier, Colormap> COLORMAPS = new HashMap<>();

    public static void init() {
        ItemTintSources.ID_MAPPER.put(Smptg.id("biome_color"), BiomeColorTint.CODEC);
    }

    public static void reloadColors(Map<Identifier, BiomeColor> map) {
        COLORS.clear();
        COLORS.putAll(map);

        var mc = Minecraft.getInstance();
        if (mc.level != null) {
            ((BiomeColorLevelInj) mc.level).smptg$reloadColors(BiomeColorsClient.COLORS);
        }
    }

    public static void reloadColormaps(Map<Identifier, Colormap> map) {
        COLORMAPS.clear();
        COLORMAPS.putAll(map);

        // Delegate them to common class
        BiomeColors.COLORMAPS.clear();
        BiomeColors.COLORMAPS.putAll(map);

        var mc = Minecraft.getInstance();
        if (mc.level != null) {
            mc.level.clearTintCaches();
        }
    }

    public static int sampleItem(Identifier name) {
        return COLORS.getOrDefault(name, ErrorColor.INSTANCE).sampleItem();
    }
}
