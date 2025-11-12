package net.foxboi.salted.common.item;

import net.minecraft.world.item.Item;

/**
 * A functional interface that creates an {@link Item} from {@link Item.Properties}. Used in {@link ModItems}.
 */
public interface ItemFactory {
    /**
     * Instantiates an {@link Item}.
     *
     * @param properties The {@link Item.Properties} object to pass to the item. You may modify this to set additional
     *                   properties via the factory.
     * @return The created {@link Item}.
     */
    Item create(Item.Properties properties);
}
