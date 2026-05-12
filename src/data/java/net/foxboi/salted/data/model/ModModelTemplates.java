package net.foxboi.salted.data.model;

import net.foxboi.salted.common.Smptg;
import net.foxboi.summon.api.model.ModelTemplate;
import net.foxboi.summon.api.model.TextureKey;

public class ModModelTemplates {
    public static final ModelTemplate FLOWERBED_1 = ModelTemplates.FLOWERBED_1;
    public static final ModelTemplate FLOWERBED_2 = ModelTemplates.FLOWERBED_2;
    public static final ModelTemplate FLOWERBED_3 = ModelTemplates.FLOWERBED_3;
    public static final ModelTemplate FLOWERBED_4 = ModelTemplates.FLOWERBED_4;

    public static final ModelTemplate TINTED_FLOWERBED_1 = block("tinted_flowerbed_1", "_1", TextureKeys.FLOWERBED, TextureKeys.STEM);
    public static final ModelTemplate TINTED_FLOWERBED_2 = block("tinted_flowerbed_2", "_2", TextureKeys.FLOWERBED, TextureKeys.STEM);
    public static final ModelTemplate TINTED_FLOWERBED_3 = block("tinted_flowerbed_3", "_3", TextureKeys.FLOWERBED, TextureKeys.STEM);
    public static final ModelTemplate TINTED_FLOWERBED_4 = block("tinted_flowerbed_4", "_4", TextureKeys.FLOWERBED, TextureKeys.STEM);

    public static final ModelTemplate LAYERED_FLOWERBED_1 = block("layered_flowerbed_1", "_1", TextureKeys.FLOWERBED, ModTextureKeys.OVERLAY, TextureKeys.STEM);
    public static final ModelTemplate LAYERED_FLOWERBED_2 = block("layered_flowerbed_2", "_2", TextureKeys.FLOWERBED, ModTextureKeys.OVERLAY, TextureKeys.STEM);
    public static final ModelTemplate LAYERED_FLOWERBED_3 = block("layered_flowerbed_3", "_3", TextureKeys.FLOWERBED, ModTextureKeys.OVERLAY, TextureKeys.STEM);
    public static final ModelTemplate LAYERED_FLOWERBED_4 = block("layered_flowerbed_4", "_4", TextureKeys.FLOWERBED, ModTextureKeys.OVERLAY, TextureKeys.STEM);

    public static final ModelTemplate EMISSIVE_FLOWERBED_1 = block("emissive_flowerbed_1", "_1", TextureKeys.FLOWERBED, ModTextureKeys.EMISSIVE, TextureKeys.STEM);
    public static final ModelTemplate EMISSIVE_FLOWERBED_2 = block("emissive_flowerbed_2", "_2", TextureKeys.FLOWERBED, ModTextureKeys.EMISSIVE, TextureKeys.STEM);
    public static final ModelTemplate EMISSIVE_FLOWERBED_3 = block("emissive_flowerbed_3", "_3", TextureKeys.FLOWERBED, ModTextureKeys.EMISSIVE, TextureKeys.STEM);
    public static final ModelTemplate EMISSIVE_FLOWERBED_4 = block("emissive_flowerbed_4", "_4", TextureKeys.FLOWERBED, ModTextureKeys.EMISSIVE, TextureKeys.STEM);

    public static final ModelTemplate GLOWING_FLOWERBED_1 = block("glowing_flowerbed_1", "_1", TextureKeys.FLOWERBED, TextureKeys.STEM);
    public static final ModelTemplate GLOWING_FLOWERBED_2 = block("glowing_flowerbed_2", "_2", TextureKeys.FLOWERBED, TextureKeys.STEM);
    public static final ModelTemplate GLOWING_FLOWERBED_3 = block("glowing_flowerbed_3", "_3", TextureKeys.FLOWERBED, TextureKeys.STEM);
    public static final ModelTemplate GLOWING_FLOWERBED_4 = block("glowing_flowerbed_4", "_4", TextureKeys.FLOWERBED, TextureKeys.STEM);

    public static final ModelTemplate TINTED_GLOWING_FLOWERBED_1 = block("tinted_glowing_flowerbed_1", "_1", TextureKeys.FLOWERBED, TextureKeys.STEM);
    public static final ModelTemplate TINTED_GLOWING_FLOWERBED_2 = block("tinted_glowing_flowerbed_2", "_2", TextureKeys.FLOWERBED, TextureKeys.STEM);
    public static final ModelTemplate TINTED_GLOWING_FLOWERBED_3 = block("tinted_glowing_flowerbed_3", "_3", TextureKeys.FLOWERBED, TextureKeys.STEM);
    public static final ModelTemplate TINTED_GLOWING_FLOWERBED_4 = block("tinted_glowing_flowerbed_4", "_4", TextureKeys.FLOWERBED, TextureKeys.STEM);

    public static final ModelTemplate CROSS = ModelTemplates.CROSS;
    public static final ModelTemplate TINTED_CROSS = ModelTemplates.TINTED_CROSS;
    public static final ModelTemplate EMISSIVE_CROSS = ModelTemplates.CROSS_EMISSIVE;
    public static final ModelTemplate LAYERED_CROSS = block("layered_cross", TextureKeys.CROSS, ModTextureKeys.OVERLAY);
    public static final ModelTemplate GLOWING_CROSS = block("glowing_cross", TextureKeys.CROSS);
    public static final ModelTemplate TINTED_GLOWING_CROSS = block("tinted_glowing_cross", TextureKeys.CROSS);

    public static final ModelTemplate FLAT = block("flat", TextureKeys.TEXTURE);
    public static final ModelTemplate TINTED_FLAT = block("tinted_flat", TextureKeys.TEXTURE);
    public static final ModelTemplate LAYERED_FLAT = block("layered_flat", TextureKeys.TEXTURE, ModTextureKeys.OVERLAY);
    public static final ModelTemplate EMISSIVE_FLAT = block("emissive_flat", TextureKeys.TEXTURE, ModTextureKeys.EMISSIVE);
    public static final ModelTemplate GLOWING_FLAT = block("glowing_flat", TextureKeys.TEXTURE);
    public static final ModelTemplate TINTED_GLOWING_FLAT = block("tinted_glowing_flat", TextureKeys.TEXTURE);

    public static final ModelTemplate FLAT_SEGMENT_1 = block("flat_segment_1", "_1", TextureKeys.TEXTURE);
    public static final ModelTemplate FLAT_SEGMENT_2 = block("flat_segment_2", "_2", TextureKeys.TEXTURE);
    public static final ModelTemplate FLAT_SEGMENT_3 = block("flat_segment_3", "_3", TextureKeys.TEXTURE);
    public static final ModelTemplate FLAT_SEGMENT_4 = block("flat_segment_4", "_4", TextureKeys.TEXTURE);

    public static final ModelTemplate TINTED_FLAT_SEGMENT_1 = block("tinted_flat_segment_1", "_1", TextureKeys.TEXTURE);
    public static final ModelTemplate TINTED_FLAT_SEGMENT_2 = block("tinted_flat_segment_2", "_2", TextureKeys.TEXTURE);
    public static final ModelTemplate TINTED_FLAT_SEGMENT_3 = block("tinted_flat_segment_3", "_3", TextureKeys.TEXTURE);
    public static final ModelTemplate TINTED_FLAT_SEGMENT_4 = block("tinted_flat_segment_4", "_4", TextureKeys.TEXTURE);

    public static final ModelTemplate LAYERED_FLAT_SEGMENT_1 = block("layered_flat_segment_1", "_1", TextureKeys.TEXTURE, ModTextureKeys.OVERLAY);
    public static final ModelTemplate LAYERED_FLAT_SEGMENT_2 = block("layered_flat_segment_2", "_2", TextureKeys.TEXTURE, ModTextureKeys.OVERLAY);
    public static final ModelTemplate LAYERED_FLAT_SEGMENT_3 = block("layered_flat_segment_3", "_3", TextureKeys.TEXTURE, ModTextureKeys.OVERLAY);
    public static final ModelTemplate LAYERED_FLAT_SEGMENT_4 = block("layered_flat_segment_4", "_4", TextureKeys.TEXTURE, ModTextureKeys.OVERLAY);

    public static final ModelTemplate EMISSIVE_FLAT_SEGMENT_1 = block("emissive_flat_segment_1", "_1", TextureKeys.TEXTURE, ModTextureKeys.EMISSIVE);
    public static final ModelTemplate EMISSIVE_FLAT_SEGMENT_2 = block("emissive_flat_segment_2", "_2", TextureKeys.TEXTURE, ModTextureKeys.EMISSIVE);
    public static final ModelTemplate EMISSIVE_FLAT_SEGMENT_3 = block("emissive_flat_segment_3", "_3", TextureKeys.TEXTURE, ModTextureKeys.EMISSIVE);
    public static final ModelTemplate EMISSIVE_FLAT_SEGMENT_4 = block("emissive_flat_segment_4", "_4", TextureKeys.TEXTURE, ModTextureKeys.EMISSIVE);

    public static final ModelTemplate GLOWING_FLAT_SEGMENT_1 = block("glowing_flat_segment_1", "_1", TextureKeys.TEXTURE);
    public static final ModelTemplate GLOWING_FLAT_SEGMENT_2 = block("glowing_flat_segment_2", "_2", TextureKeys.TEXTURE);
    public static final ModelTemplate GLOWING_FLAT_SEGMENT_3 = block("glowing_flat_segment_3", "_3", TextureKeys.TEXTURE);
    public static final ModelTemplate GLOWING_FLAT_SEGMENT_4 = block("glowing_flat_segment_4", "_4", TextureKeys.TEXTURE);

    public static final ModelTemplate TINTED_GLOWING_FLAT_SEGMENT_1 = block("tinted_glowing_flat_segment_1", "_1", TextureKeys.TEXTURE);
    public static final ModelTemplate TINTED_GLOWING_FLAT_SEGMENT_2 = block("tinted_glowing_flat_segment_2", "_2", TextureKeys.TEXTURE);
    public static final ModelTemplate TINTED_GLOWING_FLAT_SEGMENT_3 = block("tinted_glowing_flat_segment_3", "_3", TextureKeys.TEXTURE);
    public static final ModelTemplate TINTED_GLOWING_FLAT_SEGMENT_4 = block("tinted_glowing_flat_segment_4", "_4", TextureKeys.TEXTURE);

    public static final ModelTemplate SHRUB_1 = block("shrub_1", "_1", ModTextureKeys.BASE, ModTextureKeys.SIDE, ModTextureKeys.MIDDLE);
    public static final ModelTemplate SHRUB_2 = block("shrub_2", "_2", ModTextureKeys.BASE, ModTextureKeys.SIDE, ModTextureKeys.MIDDLE);
    public static final ModelTemplate SHRUB_3 = block("shrub_3", "_3", ModTextureKeys.BASE, ModTextureKeys.SIDE, ModTextureKeys.MIDDLE);
    public static final ModelTemplate SHRUB_4 = block("shrub_4", "_4", ModTextureKeys.BASE, ModTextureKeys.SIDE, ModTextureKeys.MIDDLE);

    public static final ModelTemplate EMISSIVE_LEAVES = block("emissive_leaves", TextureKeys.ALL, ModTextureKeys.EMISSIVE);
    public static final ModelTemplate LAYERED_LEAVES = block("layered_leaves", TextureKeys.ALL, ModTextureKeys.OVERLAY);
    public static final ModelTemplate GLOWING_LEAVES = block("glowing_leaves", TextureKeys.ALL);

    public static final ModelTemplate LAYER_HEIGHT2 = block("layer_height2", "_height2", TextureKeys.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT4 = block("layer_height4", "_height4", TextureKeys.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT6 = block("layer_height6", "_height6", TextureKeys.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT8 = block("layer_height8", "_height8", TextureKeys.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT10 = block("layer_height10", "_height10", TextureKeys.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT12 = block("layer_height12", "_height12", TextureKeys.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT14 = block("layer_height14", "_height14", TextureKeys.TEXTURE);
    public static final ModelTemplate LAYER_HEIGHT16 = block("layer_height16", "_height16", TextureKeys.TEXTURE);

    public static final ModelTemplate SHELF_FUNGUS = block("shelf_fungus", TextureKeys.TOP, TextureKeys.BOTTOM);
    public static final ModelTemplate SHELF_FUNGUS_DIAGONAL = block("shelf_fungus_diagonal", TextureKeys.TOP, TextureKeys.BOTTOM);

    public static final ModelTemplate GRASSY_SOIL = block("grassy_soil", TextureKeys.TOP, TextureKeys.BOTTOM, TextureKeys.SIDE, ModTextureKeys.OVERLAY);
    public static final ModelTemplate GRASSY_SOIL_R90 = block("grassy_soil_r90", "_r90", TextureKeys.TOP, TextureKeys.BOTTOM, TextureKeys.SIDE, ModTextureKeys.OVERLAY);
    public static final ModelTemplate GRASSY_SOIL_R180 = block("grassy_soil_r180", "_r180", TextureKeys.TOP, TextureKeys.BOTTOM, TextureKeys.SIDE, ModTextureKeys.OVERLAY);
    public static final ModelTemplate GRASSY_SOIL_R270 = block("grassy_soil_r270", "_r270", TextureKeys.TOP, TextureKeys.BOTTOM, TextureKeys.SIDE, ModTextureKeys.OVERLAY);

    public static final ModelTemplate OVERGROWN_SOIL = block("overgrown_soil", TextureKeys.TOP, TextureKeys.BOTTOM, TextureKeys.SIDE);
    public static final ModelTemplate OVERGROWN_SOIL_R90 = block("overgrown_soil_r90", "_r90", TextureKeys.TOP, TextureKeys.BOTTOM, TextureKeys.SIDE);
    public static final ModelTemplate OVERGROWN_SOIL_R180 = block("overgrown_soil_r180", "_r180", TextureKeys.TOP, TextureKeys.BOTTOM, TextureKeys.SIDE);
    public static final ModelTemplate OVERGROWN_SOIL_R270 = block("overgrown_soil_r270", "_r270", TextureKeys.TOP, TextureKeys.BOTTOM, TextureKeys.SIDE);

    public static final ModelTemplate BRAZIER_UNLIT = block("brazier_unlit", "_unlit", TextureKeys.TOP, TextureKeys.BOTTOM, TextureKeys.SIDE);
    public static final ModelTemplate BRAZIER_LIT = block("brazier_lit", "_lit", TextureKeys.TOP, TextureKeys.BOTTOM, TextureKeys.SIDE, TextureKeys.FIRE);
    public static final ModelTemplate BRAZIER_FRAMED_UNLIT = block("brazier_framed_unlit", "_framed_unlit", TextureKeys.TOP, TextureKeys.BOTTOM, TextureKeys.SIDE, ModTextureKeys.FRAME);
    public static final ModelTemplate BRAZIER_FRAMED_LIT = block("brazier_framed_lit", "_framed_lit", TextureKeys.TOP, TextureKeys.BOTTOM, TextureKeys.SIDE, ModTextureKeys.FRAME, TextureKeys.FIRE);


    private static ModelTemplate block(String id, TextureKey... keys) {
        return ModelTemplate.of(Smptg.id("block/" + id), keys);
    }

    private static ModelTemplate block(String id, String suffix, TextureKey... keys) {
        return ModelTemplate.of(Smptg.id("block/" + id), suffix, keys);
    }

    private static ModelTemplate item(String id, TextureKey... keys) {
        return ModelTemplate.of(Smptg.id("item/" + id), keys);
    }

    private static ModelTemplate item(String id, String suffix, TextureKey... keys) {
        return ModelTemplate.of(Smptg.id("item/" + id), suffix, keys);
    }
}
