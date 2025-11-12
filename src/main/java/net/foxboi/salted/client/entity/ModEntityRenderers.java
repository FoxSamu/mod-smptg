package net.foxboi.salted.client.entity;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.foxboi.salted.common.entity.ModEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractBoat;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;

public class ModEntityRenderers {
    public static void init() {
        registerBoat(ModEntityTypes.ASPEN_BOAT, ModModelLayers.ASPEN_BOAT);
        registerBoat(ModEntityTypes.ASPEN_CHEST_BOAT, ModModelLayers.ASPEN_CHEST_BOAT);
        registerBoat(ModEntityTypes.BEECH_BOAT, ModModelLayers.BEECH_BOAT);
        registerBoat(ModEntityTypes.BEECH_CHEST_BOAT, ModModelLayers.BEECH_CHEST_BOAT);
        registerBoat(ModEntityTypes.MAPLE_BOAT, ModModelLayers.MAPLE_BOAT);
        registerBoat(ModEntityTypes.MAPLE_CHEST_BOAT, ModModelLayers.MAPLE_CHEST_BOAT);
        registerBoat(ModEntityTypes.REDWOOD_BOAT, ModModelLayers.REDWOOD_BOAT);
        registerBoat(ModEntityTypes.REDWOOD_CHEST_BOAT, ModModelLayers.REDWOOD_CHEST_BOAT);
        registerBoat(ModEntityTypes.DEAD_WOOD_BOAT, ModModelLayers.DEAD_WOOD_BOAT);
        registerBoat(ModEntityTypes.DEAD_WOOD_CHEST_BOAT, ModModelLayers.DEAD_WOOD_CHEST_BOAT);
    }

    private static void registerBoat(EntityType<? extends AbstractBoat> type, ModelLayerLocation layer) {
        EntityRenderers.register(type, context -> new BoatRenderer(context, layer));
    }

    private static void registerRaft(EntityType<? extends AbstractBoat> type, ModelLayerLocation layer) {
        EntityRenderers.register(type, context -> new BoatRenderer(context, layer));
    }
}
