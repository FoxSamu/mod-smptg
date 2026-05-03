package net.foxboi.salted.client.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;

import net.foxboi.salted.common.Smptg;
import net.minecraft.client.model.object.boat.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.Identifier;

public class ModModelLayers {
    private static final Set<ModelLayerLocation> ALL_MODELS = new LinkedHashSet<>();

    public static final ModelLayerLocation ASPEN_BOAT = register("boat/aspen");
    public static final ModelLayerLocation BEECH_BOAT = register("boat/beech");
    public static final ModelLayerLocation MAPLE_BOAT = register("boat/maple");
    public static final ModelLayerLocation REDWOOD_BOAT = register("boat/redwood");
    public static final ModelLayerLocation DEAD_WOOD_BOAT = register("boat/dead_wood");

    public static final ModelLayerLocation ASPEN_CHEST_BOAT = register("chest_boat/aspen");
    public static final ModelLayerLocation BEECH_CHEST_BOAT = register("chest_boat/beech");
    public static final ModelLayerLocation MAPLE_CHEST_BOAT = register("chest_boat/maple");
    public static final ModelLayerLocation REDWOOD_CHEST_BOAT = register("chest_boat/redwood");
    public static final ModelLayerLocation DEAD_WOOD_CHEST_BOAT = register("chest_boat/dead_wood");

    public static void init() {
        ModelLayerRegistry.registerModelLayer(ASPEN_BOAT, BoatModel::createBoatModel);
        ModelLayerRegistry.registerModelLayer(BEECH_BOAT, BoatModel::createBoatModel);
        ModelLayerRegistry.registerModelLayer(MAPLE_BOAT, BoatModel::createBoatModel);
        ModelLayerRegistry.registerModelLayer(REDWOOD_BOAT, BoatModel::createBoatModel);
        ModelLayerRegistry.registerModelLayer(DEAD_WOOD_BOAT, BoatModel::createBoatModel);

        ModelLayerRegistry.registerModelLayer(ASPEN_CHEST_BOAT, BoatModel::createChestBoatModel);
        ModelLayerRegistry.registerModelLayer(BEECH_CHEST_BOAT, BoatModel::createChestBoatModel);
        ModelLayerRegistry.registerModelLayer(MAPLE_CHEST_BOAT, BoatModel::createChestBoatModel);
        ModelLayerRegistry.registerModelLayer(REDWOOD_CHEST_BOAT, BoatModel::createChestBoatModel);
        ModelLayerRegistry.registerModelLayer(DEAD_WOOD_CHEST_BOAT, BoatModel::createChestBoatModel);
    }

    private static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(String name, String layer) {
        var location = createLocation(name, layer);
        if (!ALL_MODELS.add(location)) {
            throw new IllegalStateException("Duplicate registration for " + location);
        } else {
            return location;
        }
    }

    private static ModelLayerLocation createLocation(String name, String layer) {
        return new ModelLayerLocation(Smptg.id(name), layer);
    }
}
