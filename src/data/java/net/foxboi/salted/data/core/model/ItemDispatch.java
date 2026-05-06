package net.foxboi.salted.data.core.model;

import java.util.Collection;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public interface ItemDispatch {
    void save(ItemDispatchSink sink);

    static SimpleItemDispatch of(ItemLike item, ItemModel model) {
        return model.dispatch(item);
    }

    static SimpleItemDispatch of(ItemLike item, Model model) {
        return ItemModel.model(model)
                .dispatch(item);
    }

    static SimpleItemDispatch of(ItemLike item, Model model, Collection<? extends ItemTint> tints) {
        return ItemModel.model(model)
                .withTint(tints)
                .dispatch(item);
    }

    static SimpleItemDispatch of(ItemLike item, Model model, ItemTint... tints) {
        return ItemModel.model(model)
                .withTint(tints)
                .dispatch(item);
    }

    static SimpleItemDispatch of(ItemLike item, Model model, ItemTint tint) {
        return ItemModel.model(model)
                .withTint(tint)
                .dispatch(item);
    }
}
