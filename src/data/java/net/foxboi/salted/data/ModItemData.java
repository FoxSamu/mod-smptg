package net.foxboi.salted.data;

import net.foxboi.salted.data.model.ItemModels;

import static net.foxboi.salted.common.item.ModItems.*;

public record ModItemData() {


    // MODELS
    // =============================================

    public static void models(ItemModels models) {
        // Blocks declare what their items look like so for regular block
        // items you don't typically need to declare an item model here.

        models.generated(ASPEN_BOAT);
        models.generated(BEECH_BOAT);
        models.generated(MAPLE_BOAT);
        models.generated(REDWOOD_BOAT);
        models.generated(DEAD_WOOD_BOAT);

        models.generated(ASPEN_CHEST_BOAT);
        models.generated(BEECH_CHEST_BOAT);
        models.generated(MAPLE_CHEST_BOAT);
        models.generated(REDWOOD_CHEST_BOAT);
        models.generated(DEAD_WOOD_CHEST_BOAT);
    }
}
