package net.foxboi.salted.data.model;

import java.util.Optional;

import net.foxboi.salted.common.Smptg;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureSlot;

public class ModModelTemplates {
    public static final ModelTemplate TINTED_FLOWERBED_1 = create("tinted_flowerbed_1", "_1", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate TINTED_FLOWERBED_2 = create("tinted_flowerbed_2", "_2", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate TINTED_FLOWERBED_3 = create("tinted_flowerbed_3", "_3", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate TINTED_FLOWERBED_4 = create("tinted_flowerbed_4", "_4", TextureSlot.FLOWERBED, TextureSlot.STEM);
    public static final ModelTemplate LAYERED_CROSS = create("layered_cross", ModTextureSlots.UNTINTED, ModTextureSlots.TINTED);

    public static final ModelTemplate LAYER_HEIGHT2 = create("layer_height2", "_height2", TextureSlot.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT4 = create("layer_height4", "_height4", TextureSlot.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT6 = create("layer_height6", "_height6", TextureSlot.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT8 = create("layer_height8", "_height8", TextureSlot.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT10 = create("layer_height10", "_height10", TextureSlot.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT12 = create("layer_height12", "_height12", TextureSlot.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT14 = create("layer_height14", "_height14", TextureSlot.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT16 = create("layer_height16", "_height16", TextureSlot.TEXTURE);

    public static final ModelTemplate SHELF_FUNGUS = create("shelf_fungus", TextureSlot.TOP, TextureSlot.BOTTOM);
    public static final ModelTemplate SHELF_FUNGUS_DIAGONAL = create("shelf_fungus_diagonal", TextureSlot.TOP, TextureSlot.BOTTOM);

    public static final ModelTemplate FLAT = create("flat", TextureSlot.TEXTURE);
    public static final ModelTemplate FLAT_TINTED = create("flat_tinted", TextureSlot.TEXTURE);

    private static ModelTemplate create(TextureSlot... slots) {
        return new ModelTemplate(Optional.empty(), Optional.empty(), slots);
    }

    private static ModelTemplate create(String model, TextureSlot... slots) {
        return new ModelTemplate(Optional.of(Smptg.id("block/" + model)), Optional.empty(), slots);
    }

    private static ModelTemplate create(String model, String suffix, TextureSlot... slots) {
        return new ModelTemplate(Optional.of(Smptg.id("block/" + model)), Optional.of(suffix), slots);
    }

    private static ModelTemplate createItem(String model, TextureSlot... slots) {
        return new ModelTemplate(Optional.of(Smptg.id("item/" + model)), Optional.empty(), slots);
    }

    private static ModelTemplate createItem(String model, String suffix, TextureSlot... slots) {
        return new ModelTemplate(Optional.of(Smptg.id("item/" + model)), Optional.of(suffix), slots);
    }
}
