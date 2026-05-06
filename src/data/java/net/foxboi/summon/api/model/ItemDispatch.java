package net.foxboi.summon.api.model;

import java.util.Collection;

import net.minecraft.world.level.ItemLike;

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
