package net.foxboi.salted.client;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorResolverRegistry;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;

import net.foxboi.salted.client.entity.ModEntityRenderers;
import net.foxboi.salted.client.entity.ModModelLayers;
import net.foxboi.salted.client.misc.ColorMapLoader;
import net.foxboi.salted.client.misc.FabricColorRegistry;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.misc.biome.color.FoliageColorMap;

public class SmptgClient extends Smptg implements ClientModInitializer {
    public static SmptgClient get() {
        if (Smptg.get() instanceof SmptgClient client) {
            return client;
        } else {
            throw new RuntimeException("Cannot access SmptgClient on dedicated server");
        }
    }

    @Override
    public void onInitializeClient() {
        init();
    }

    @Override
    public void init() {
        super.init();

        // Register block render data
        ModBlocks.colors(new FabricColorRegistry());

        // Register entity renderers
        ModModelLayers.init();
        ModEntityRenderers.init();

        // Register resource reloaders
        var clientLoader = ResourceLoader.get(PackType.CLIENT_RESOURCES);
        clientLoader.registerReloadListener(ColorMapLoader.DARK_RED_FOLIAGE.id(), ColorMapLoader.DARK_RED_FOLIAGE);
        clientLoader.registerReloadListener(ColorMapLoader.RED_FOLIAGE.id(), ColorMapLoader.RED_FOLIAGE);
        clientLoader.registerReloadListener(ColorMapLoader.GOLDEN_FOLIAGE.id(), ColorMapLoader.GOLDEN_FOLIAGE);
        clientLoader.registerReloadListener(ColorMapLoader.YELLOW_FOLIAGE.id(), ColorMapLoader.YELLOW_FOLIAGE);

        ColorResolverRegistry.register(FoliageColorMap.DARK_RED);
        ColorResolverRegistry.register(FoliageColorMap.RED);
        ColorResolverRegistry.register(FoliageColorMap.GOLDEN);
        ColorResolverRegistry.register(FoliageColorMap.YELLOW);
    }
}
