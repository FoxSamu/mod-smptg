package net.foxboi.salted.data.model;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import static net.minecraft.client.data.models.model.TextureMapping.*;

public enum BlockStyle {
    NONE(
            ModelTemplates.CROSS,
            ModelTemplates.FLOWER_POT_CROSS,
            ModModelTemplates.FLAT_SEGMENT_1,
            ModModelTemplates.FLAT_SEGMENT_2,
            ModModelTemplates.FLAT_SEGMENT_3,
            ModModelTemplates.FLAT_SEGMENT_4
    ),
    TINTED(
            ModelTemplates.TINTED_CROSS,
            ModelTemplates.TINTED_FLOWER_POT_CROSS,
            ModModelTemplates.TINTED_FLAT_SEGMENT_1,
            ModModelTemplates.TINTED_FLAT_SEGMENT_2,
            ModModelTemplates.TINTED_FLAT_SEGMENT_3,
            ModModelTemplates.TINTED_FLAT_SEGMENT_4
    ),
    LAYERED(
            ModModelTemplates.LAYERED_CROSS,
            null, // TODO
            ModModelTemplates.LAYERED_FLAT_SEGMENT_1,
            ModModelTemplates.LAYERED_FLAT_SEGMENT_2,
            ModModelTemplates.LAYERED_FLAT_SEGMENT_3,
            ModModelTemplates.LAYERED_FLAT_SEGMENT_4
    ),
    EMISSIVE(
            ModelTemplates.CROSS_EMISSIVE,
            ModelTemplates.FLOWER_POT_CROSS_EMISSIVE,
            ModModelTemplates.EMISSIVE_FLAT_SEGMENT_1,
            ModModelTemplates.EMISSIVE_FLAT_SEGMENT_2,
            ModModelTemplates.EMISSIVE_FLAT_SEGMENT_3,
            ModModelTemplates.EMISSIVE_FLAT_SEGMENT_4
    ),
    GLOWING(
            ModModelTemplates.GLOWING_CROSS,
            null, // TODO
            ModModelTemplates.GLOWING_FLAT_SEGMENT_1,
            ModModelTemplates.GLOWING_FLAT_SEGMENT_2,
            ModModelTemplates.GLOWING_FLAT_SEGMENT_3,
            ModModelTemplates.GLOWING_FLAT_SEGMENT_4
    ),
    TINTED_GLOWING(
            ModModelTemplates.TINTED_GLOWING_CROSS,
            null, // TODO
            ModModelTemplates.TINTED_GLOWING_FLAT_SEGMENT_1,
            ModModelTemplates.TINTED_GLOWING_FLAT_SEGMENT_2,
            ModModelTemplates.TINTED_GLOWING_FLAT_SEGMENT_3,
            ModModelTemplates.TINTED_GLOWING_FLAT_SEGMENT_4
    );

    private final ModelTemplate cross;
    private final ModelTemplate flowerPotCross;
    private final ModelTemplate flatSegment1;
    private final ModelTemplate flatSegment2;
    private final ModelTemplate flatSegment3;
    private final ModelTemplate flatSegment4;

    BlockStyle(ModelTemplate cross, ModelTemplate crossPotTemplate, ModelTemplate flatSegment1, ModelTemplate flatSegment2, ModelTemplate flatSegment3, ModelTemplate flatSegment4) {
        this.cross = cross;
        this.flowerPotCross = crossPotTemplate;
        this.flatSegment1 = flatSegment1;
        this.flatSegment2 = flatSegment2;
        this.flatSegment3 = flatSegment3;
        this.flatSegment4 = flatSegment4;
    }

    public ModelTemplate getCross() {
        return cross;
    }

    public ModelTemplate flowerPotCross() {
        return flowerPotCross;
    }

    public ModelTemplate getFlatSegment1() {
        return flatSegment1;
    }

    public ModelTemplate getFlatSegment2() {
        return flatSegment2;
    }

    public ModelTemplate getFlatSegment3() {
        return flatSegment3;
    }

    public ModelTemplate getFlatSegment4() {
        return flatSegment4;
    }

    public Identifier createItemModel(BlockModelGenerators gen, Block block) {
        return createItemModel(gen, block, "");
    }

    public Identifier createItemModel(BlockModelGenerators gen, Block block, String baseSuffix) {
        var item = block.asItem();

        return switch (this) {
            case EMISSIVE -> ModelTemplates.TWO_LAYERED_ITEM.create(
                    ModelLocationUtils.getModelLocation(item),
                    layered(getBlockTexture(block, baseSuffix), getBlockTexture(block, baseSuffix + "_emissive")),
                    gen.modelOutput
            );

            case LAYERED -> ModelTemplates.TWO_LAYERED_ITEM.create(
                    ModelLocationUtils.getModelLocation(item),
                    layered(getBlockTexture(block, baseSuffix), getBlockTexture(block, baseSuffix + "_overlay")),
                    gen.modelOutput
            );

            default -> ModelTemplates.FLAT_ITEM.create(
                    ModelLocationUtils.getModelLocation(item),
                    layer0(getBlockTexture(block, baseSuffix)),
                    gen.modelOutput
            );
        };
    }

    public TextureMapping getCrossTextureMapping(Material texture) {
        return switch (this) {
            case TINTED, NONE, GLOWING, TINTED_GLOWING -> cross(texture);
            case LAYERED -> ModTextureMappings.tintedWithOverlay(texture);
            case EMISSIVE -> ModTextureMappings.crossEmissive(texture);
        };
    }

    public TextureMapping getPlantTextureMapping(Material texture) {
        return switch (this) {
            case TINTED, NONE, GLOWING, TINTED_GLOWING -> plant(texture);
            case LAYERED -> ModTextureMappings.tintedWithOverlay(texture);
            case EMISSIVE -> ModTextureMappings.plantEmissive(texture);
        };
    }

    public TextureMapping getDefaultTextureMapping(Material texture) {
        return switch (this) {
            case TINTED, NONE, GLOWING, TINTED_GLOWING -> defaultTexture(texture);
            case LAYERED -> ModTextureMappings.tintedWithOverlay(texture);
            case EMISSIVE -> ModTextureMappings.textureEmissive(texture);
        };
    }
}