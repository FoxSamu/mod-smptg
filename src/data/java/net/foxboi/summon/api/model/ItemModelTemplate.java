package net.foxboi.summon.api.model;

import net.minecraft.world.item.Item;

public interface ItemModelTemplate {
    Model create(Item item, String suffix);

    default Model create(Item item) {
        return create(item, "");
    }
}
