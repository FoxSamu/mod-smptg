package net.foxboi.salted.common.item;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.jspecify.annotations.NonNull;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

/**
 * A functional interface that creates an {@link Item} from {@link Item.Properties}. Used in {@link ModItems}.
 */
public interface ItemFactory extends BiFunction<ResourceKey<Item>, Item.Properties, Item> {
    /**
     * Instantiates an {@link Item}.
     *
     * @param properties The {@link Item.Properties} object to pass to the item. You may modify this to set additional
     *                   properties via the factory.
     * @return The created {@link Item}.
     */
    Item create(Item.Properties properties);

    @Override
    default Item apply(ResourceKey<Item> key, Item.Properties properties) {
        return create(properties.setId(key));
    }
}
