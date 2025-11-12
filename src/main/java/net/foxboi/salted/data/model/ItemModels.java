package net.foxboi.salted.data.model;

import net.foxboi.salted.client.color.BiomeColorTint;
import net.foxboi.salted.common.Smptg;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public final class ItemModels {
    private final ItemModelGenerators gen;

    public ItemModels(ItemModelGenerators gen) {
        this.gen = gen;
    }

    public void generated(Item item) {
        gen.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }

    public void generated(Item item, int tint) {
        gen.itemModelOutput.accept(item, ItemModelUtils.tintedModel(
                gen.createFlatItemModel(item, ModelTemplates.FLAT_ITEM),
                ItemModelUtils.constantTint(0xFF000000 | tint)
        ));
    }

    public void generated(Item item, ResourceLocation colorProvider) {
        gen.itemModelOutput.accept(item, ItemModelUtils.tintedModel(
                gen.createFlatItemModel(item, ModelTemplates.FLAT_ITEM),
                new BiomeColorTint(colorProvider)
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
