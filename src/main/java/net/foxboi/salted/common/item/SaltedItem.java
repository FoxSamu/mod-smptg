package net.foxboi.salted.common.item;

import net.minecraft.world.item.Item;

public class SaltedItem extends Item {
    private final Item baseItem;

    public SaltedItem(Item baseItem, Properties properties) {
        super(properties);
        this.baseItem = baseItem;
    }

    public Item getBaseItem() {
        return baseItem;
    }
}
