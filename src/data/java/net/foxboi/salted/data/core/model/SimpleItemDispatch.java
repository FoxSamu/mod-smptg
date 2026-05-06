package net.foxboi.salted.data.core.model;

import net.minecraft.world.item.Item;

public final class SimpleItemDispatch implements ItemDispatch {
    private final Item item;
    private final ItemModel model;

    private boolean handAnimationOnSwap = true;
    private boolean oversizedInGui = false;
    private float swapAnimationScale = 1f;

    public SimpleItemDispatch(Item item, ItemModel model) {
        this.item = item;
        this.model = model;
    }

    @Override
    public void save(ItemDispatchSink sink) {
        sink.save(item, modelSink -> ModelJson.createItemDispatch(model.toJson(modelSink), handAnimationOnSwap, oversizedInGui, swapAnimationScale));
    }

    public Item item() {
        return item;
    }

    public ItemModel model() {
        return model;
    }

    public boolean handAnimationOnSwap() {
        return handAnimationOnSwap;
    }

    public boolean oversizedInGui() {
        return oversizedInGui;
    }

    public float swapAnimationScale() {
        return swapAnimationScale;
    }

    public SimpleItemDispatch handAnimationOnSwap(boolean handAnimationOnSwap) {
        this.handAnimationOnSwap = handAnimationOnSwap;
        return this;
    }

    public SimpleItemDispatch oversizedInGui(boolean oversizedInGui) {
        this.oversizedInGui = oversizedInGui;
        return this;
    }

    public SimpleItemDispatch swapAnimationScale(float swapAnimationScale) {
        this.swapAnimationScale = swapAnimationScale;
        return this;
    }
}
