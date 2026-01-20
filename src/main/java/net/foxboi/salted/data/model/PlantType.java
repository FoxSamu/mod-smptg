package net.foxboi.salted.data.model;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import static net.minecraft.client.data.models.model.TextureMapping.*;

public enum PlantType {
    TINTED(ModelTemplates.TINTED_CROSS, ModelTemplates.TINTED_FLOWER_POT_CROSS),
    NOT_TINTED(ModelTemplates.CROSS, ModelTemplates.FLOWER_POT_CROSS),
    LAYERED(ModModelTemplates.LAYERED_CROSS, null),
    EMISSIVE_NOT_TINTED(ModelTemplates.CROSS_EMISSIVE, ModelTemplates.FLOWER_POT_CROSS_EMISSIVE);

    private final ModelTemplate cross;
    private final ModelTemplate flowerPotCross;

    PlantType(ModelTemplate cross, ModelTemplate crossPotTemplate) {
        this.cross = cross;
        this.flowerPotCross = crossPotTemplate;
    }

    public ModelTemplate getCross() {
        return cross;
    }

    public ModelTemplate flowerPotCross() {
        return flowerPotCross;
    }

    public Identifier createItemModel(BlockModelGenerators gen, Block block) {
        return createItemModel(gen, block, "");
    }

    public Identifier createItemModel(BlockModelGenerators gen, Block block, String baseSuffix) {
        var item = block.asItem();

        return switch (this) {
            case EMISSIVE_NOT_TINTED -> ModelTemplates.TWO_LAYERED_ITEM.create(
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

    public TextureMapping getTextureMapping(Block block) {
        return switch (this) {
            case TINTED, NOT_TINTED -> cross(block);
            case LAYERED -> ModTextureMappings.tintedWithOverlay(block);
            case EMISSIVE_NOT_TINTED -> crossEmissive(block);
        };
    }

    public TextureMapping getTextureMapping(Identifier texture) {
        return switch (this) {
            case TINTED, NOT_TINTED -> cross(texture);
            case LAYERED -> ModTextureMappings.tintedWithOverlay(texture);
            case EMISSIVE_NOT_TINTED -> ModTextureMappings.crossEmissive(texture);
        };
    }

    public TextureMapping getPlantTextureMapping(Block block) {
        return switch (this) {
            case TINTED, NOT_TINTED -> plant(block);
            case LAYERED -> ModTextureMappings.tintedWithOverlay(block);
            case EMISSIVE_NOT_TINTED -> plantEmissive(block);
        };
    }
}