package net.foxboi.salted.data.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.data.ItemTint;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.world.item.Item;

@Environment(EnvType.CLIENT)
public final class ItemModelsImpl implements ItemModels {
    private final ItemModelGenerators gen;

    public ItemModelsImpl(ItemModelGenerators gen) {
        this.gen = gen;
    }

    public void generated(Item item) {
        gen.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    @Override
    public void generated(Item item, ItemTint tint) {
        gen.itemModelOutput.accept(item, ItemModelUtils.tintedModel(
                gen.createFlatItemModel(item, ModelTemplates.FLAT_ITEM),
                ModelProvider.createTintSource(tint)
        ));
    }

    public void saltedItem(Item item, Item base) {
        var model = ModelTemplates.TWO_LAYERED_ITEM.create(
                ModelLocationUtils.getModelLocation(item),
                TextureMapping.layered(
                        TextureMapping.getItemTexture(base),
                        Smptg.id("item/salted_overlay")
                ),
                gen.modelOutput
        );
        gen.itemModelOutput.accept(item, ItemModelUtils.plainModel(model));
    }
}
