package net.foxboi.salted.data.model;

import net.foxboi.salted.common.misc.ItemTint;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

/**
 * A server-safe interface to which {@link net.foxboi.salted.common.item.ModItems} supplies item model data to the
 * data generator.
 */
public interface ItemModels {
    /**
     * Creates a basic flat item.
     */
    void generated(Item item);

    /**
     * Creates a basic flat item with a tint.
     */
    void generated(Item item, ItemTint tint);

    /**
     * Creates a basic flat item with a tint.
     */
    default void generated(Item item, int tint) {
        generated(item, ItemTint.constant(tint));
    }

    /**
     * Creates a basic flat item with a tint.
     */
    default void generated(Item item, Identifier tint) {
        generated(item, ItemTint.biomeColor(tint));
    }

    /**
     * Creates a flat item with a salt pinch overlay, using the base item as base texture.
     */
    void saltedItem(Item item, Item base);
}
