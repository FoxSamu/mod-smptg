package net.foxboi.salted.data.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.world.level.block.Block;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

@Environment(EnvType.CLIENT)
public class WoodModelsImpl implements WoodModels {
    private final BlockModelGenerators gen;
    private final TextureMapping logMapping;

    public WoodModelsImpl(BlockModelGenerators gen, TextureMapping logMapping) {
        this.gen = gen;
        this.logMapping = logMapping;
    }

    @Override
    public WoodModelsImpl wood(Block block) {
        var mapping = logMapping.copyAndUpdate(TextureSlot.END, logMapping.get(TextureSlot.SIDE));
        var model = ModelTemplates.CUBE_COLUMN.create(block, mapping, gen.modelOutput);

        gen.blockStateOutput.accept(createAxisAlignedPillarBlock(block, plainVariant(model)));
        gen.registerSimpleItemModel(block, model);
        return this;
    }

    @Override
    public WoodModelsImpl log(Block block) {
        var model = ModelTemplates.CUBE_COLUMN.create(block, logMapping, gen.modelOutput);

        gen.blockStateOutput.accept(createAxisAlignedPillarBlock(block, plainVariant(model)));
        gen.registerSimpleItemModel(block, model);
        return this;
    }

    @Override
    public WoodModelsImpl logWithHorizontal(Block block) {
        var model = ModelTemplates.CUBE_COLUMN.create(block, logMapping, gen.modelOutput);
        var horizModel = ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(block, logMapping, gen.modelOutput);

        gen.blockStateOutput.accept(createRotatedPillarWithHorizontalVariant(block, plainVariant(model), plainVariant(horizModel)));
        gen.registerSimpleItemModel(block, model);
        return this;
    }

    @Override
    public WoodModelsImpl logUVLocked(Block block) {
        gen.blockStateOutput.accept(createPillarBlockUVLocked(block, logMapping, gen.modelOutput));
        gen.registerSimpleItemModel(block, ModelTemplates.CUBE_COLUMN.create(block, logMapping, gen.modelOutput));
        return this;
    }
}