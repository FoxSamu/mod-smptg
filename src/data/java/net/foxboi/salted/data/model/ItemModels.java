package net.foxboi.salted.data.model;

import net.minecraft.world.item.Item;

import net.foxboi.summon.api.model.*;

public class ItemModels {
    private final ItemDispatchSink items;

    public ItemModels(ItemDispatchSink items) {
        this.items = items;
    }

    public void generated(Item item, ItemTint... tints) {
        var model = Model.generated(item);
        saveItem(item, model, tints);
    }

    public void generated(Item item, int tint) {
        generated(item, ItemTint.constant(tint));
    }

    public void handheld(Item item, ItemTint... tints) {
        var model = Model.handheld(item);
        saveItem(item, model, tints);
    }

    public void handheld(Item item, int tint) {
        handheld(item, ItemTint.constant(tint));
    }

    public void rod(Item item, ItemTint... tints) {
        var model = Model.rod(item);
        saveItem(item, model, tints);
    }

    public void rod(Item item, int tint) {
        rod(item, ItemTint.constant(tint));
    }


    private void saveItem(Item item, Model model, ItemTint... tints) {
        ItemDispatch.of(item, ItemModel.model(model).withTint(tints)).save(items);
    }
}
