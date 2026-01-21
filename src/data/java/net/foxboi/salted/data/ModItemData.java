package net.foxboi.salted.data;

import net.foxboi.salted.data.model.ItemModels;
import net.minecraft.world.item.Items;

import static net.foxboi.salted.common.item.ModItems.*;

public record ModItemData() {


    // MODELS
    // =============================================

    public static void models(ItemModels models) {
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

        models.generated(SALT);
        models.generated(PINCH_OF_SALT);
        models.generated(ROCKSALT_CHUNK);

        models.saltedItem(SALTED_BEEF, Items.BEEF);
        models.saltedItem(SALTED_PORKCHOP, Items.PORKCHOP);
        models.saltedItem(SALTED_MUTTON, Items.MUTTON);
        models.saltedItem(SALTED_CHICKEN, Items.CHICKEN);

        models.saltedItem(COOKED_SALTED_BEEF, Items.COOKED_BEEF);
        models.saltedItem(COOKED_SALTED_PORKCHOP, Items.COOKED_PORKCHOP);
        models.saltedItem(COOKED_SALTED_MUTTON, Items.COOKED_MUTTON);
        models.saltedItem(COOKED_SALTED_CHICKEN, Items.COOKED_CHICKEN);
    }
}
