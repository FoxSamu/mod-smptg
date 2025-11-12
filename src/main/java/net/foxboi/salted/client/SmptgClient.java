package net.foxboi.salted.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.foxboi.salted.client.color.BiomeColorLoader;
import net.foxboi.salted.client.color.BiomeColorsClient;
import net.foxboi.salted.client.color.ColormapLoader;
import net.foxboi.salted.client.entity.ModModelLayers;
import net.foxboi.salted.client.entity.ModEntityRenderers;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.block.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackType;

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
        ModBlocks.layers(new FabricLayerRegistry());
        ModBlocks.colors(new FabricColorRegistry());

        BiomeColorsClient.init();

        // Register entity renderers
        ModModelLayers.init();
        ModEntityRenderers.init();

        // Register resource reloaders
        var clientLoader = ResourceLoader.get(PackType.CLIENT_RESOURCES);
        clientLoader.registerReloader(BiomeColorLoader.ID, new BiomeColorLoader());
        clientLoader.registerReloader(ColormapLoader.ID, new ColormapLoader());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBiomeBlendRadius() {
        return Minecraft.getInstance().options.biomeBlendRadius().get();
    }
}
