package net.foxboi.salted.data.model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.ConditionBuilder;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SegmentableBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.foxboi.salted.common.block.*;
import net.foxboi.salted.common.misc.DiagonalDirection;
import net.foxboi.salted.data.ItemTint;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

@Environment(EnvType.CLIENT)
public final class BlockModelsImpl implements BlockModels {
    private static final PropertyDispatch<VariantMutator> ROTATIONS_COLUMN_WITH_FACING = PropertyDispatch.modify(BlockStateProperties.FACING)
            .select(Direction.DOWN, X_ROT_180)
            .select(Direction.UP, NOP)
            .select(Direction.NORTH, X_ROT_90)
            .select(Direction.SOUTH, X_ROT_90.then(Y_ROT_180))
            .select(Direction.WEST, X_ROT_90.then(Y_ROT_270))
            .select(Direction.EAST, X_ROT_90.then(Y_ROT_90));

    private static final List<ModelTemplate> MULTILAYER_MODELS = List.of(
            ModModelTemplates.LAYER_HEIGHT2,
            ModModelTemplates.LAYER_HEIGHT4,
            ModModelTemplates.LAYER_HEIGHT6,
            ModModelTemplates.LAYER_HEIGHT8,
            ModModelTemplates.LAYER_HEIGHT10,
            ModModelTemplates.LAYER_HEIGHT12,
            ModModelTemplates.LAYER_HEIGHT14,
            ModModelTemplates.LAYER_HEIGHT16
    );

    private static final DiagonalDirection[] ALIGNED_DIRECTIONS = {
            DiagonalDirection.NORTH,
            DiagonalDirection.EAST,
            DiagonalDirection.SOUTH,
            DiagonalDirection.WEST
    };

    private static final DiagonalDirection[] DIAGONAL_DIRECTIONS = {
            DiagonalDirection.NORTH_WEST,
            DiagonalDirection.NORTH_EAST,
            DiagonalDirection.SOUTH_EAST,
            DiagonalDirection.SOUTH_WEST
    };

    private static final VariantMutator[] SHELF_FUNGUS_ROTATIONS = {
            Y_ROT_180,
            Y_ROT_270,
            NOP,
            Y_ROT_90
    };

    private static final Map<Block, Material> SNOWY_SIDE_TEXTURES = Map.of(
            Blocks.DIRT, new Material(Identifier.withDefaultNamespace("block/grass_block_snow"))
    );

    private static final Function<ConditionBuilder, ConditionBuilder> FLAT_SEGMENT_1_SEGMENT_CONDITION = condition -> condition.term(BlockStateProperties.SEGMENT_AMOUNT, 1);
    private static final Function<ConditionBuilder, ConditionBuilder> FLAT_SEGMENT_2_SEGMENT_CONDITION = condition -> condition.term(BlockStateProperties.SEGMENT_AMOUNT, 2, 3);
    private static final Function<ConditionBuilder, ConditionBuilder> FLAT_SEGMENT_3_SEGMENT_CONDITION = condition -> condition.term(BlockStateProperties.SEGMENT_AMOUNT, 3);
    private static final Function<ConditionBuilder, ConditionBuilder> FLAT_SEGMENT_4_SEGMENT_CONDITION = condition -> condition.term(BlockStateProperties.SEGMENT_AMOUNT, 4);

    private final BlockModelGenerators gen;

    public BlockModelsImpl(BlockModelGenerators gen) {
        this.gen = gen;
    }

    /**
     * Creates a simple cube block with the same texture on all sides.
     */
    public void cube(Block block) {
        gen.createTrivialCube(block);
    }

    /**
     * Creates a simple cube block with the same texture on all sides, that is randomly rotated (like dirt).
     */
    public void randomlyRotatedCube(Block block) {
        gen.createRotatedVariantBlock(block);
    }

    /**
     * Creates a simple cube block with the same texture on all sides, that is randomly mirrored (like stone).
     */
    public void randomlyMirroredCube(Block block) {
        gen.createRotatedMirroredVariantBlock(block);
    }


    private void crossPlant(Block block, BlockStyle type, ItemTintSource... tints) {
        createCrossBlock(block, type);
        gen.itemModelOutput.accept(block.asItem(), ItemModelUtils.tintedModel(type.createItemModel(gen, block), tints));
    }

    /**
     * Creates an untinted cross plant.
     */
    public void crossPlant(Block block) {
        crossPlant(block, BlockStyle.NONE);
    }

    /**
     * Creates a tinted cross plant.
     */
    public void tintedCrossPlant(Block block, ItemTint tint) {
        crossPlant(block, BlockStyle.TINTED, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates a tinted cross plant with an untinted overlay.
     */
    public void layeredCrossPlant(Block block, ItemTint tint) {
        crossPlant(block, BlockStyle.LAYERED, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates an untinted cross plant with an emissive overlay.
     */
    public void emissiveCrossPlant(Block block) {
        crossPlant(block, BlockStyle.EMISSIVE);
    }

    @Override
    public void glowingCrossPlant(Block block) {
        crossPlant(block, BlockStyle.GLOWING);
    }

    @Override
    public void tintedGlowingCrossPlant(Block block, ItemTint tint) {
        crossPlant(block, BlockStyle.TINTED_GLOWING, ModelProvider.createTintSource(tint));
    }

    private void tallCrossPlant(Block block, BlockStyle type, ItemTintSource... tints) {
        createDoublePlant(block, type);
        gen.itemModelOutput.accept(block.asItem(), ItemModelUtils.tintedModel(type.createItemModel(gen, block, "_top"), tints));
    }

    /**
     * Creates an untinted double-block cross plant.
     */
    public void tallCrossPlant(Block block) {
        tallCrossPlant(block, BlockStyle.NONE);
    }

    /**
     * Creates a tinted double-block cross plant.
     */
    public void tallTintedCrossPlant(Block block, ItemTint tint) {
        tallCrossPlant(block, BlockStyle.TINTED, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates a tinted double-block cross plant with untinted overlay.
     */
    public void tallLayeredCrossPlant(Block block, ItemTint tint) {
        tallCrossPlant(block, BlockStyle.LAYERED, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates an untinted double-block cross plant with emissive overlay.
     */
    public void tallEmissiveCrossPlant(Block block) {
        tallCrossPlant(block, BlockStyle.EMISSIVE);
    }

    @Override
    public void tallGlowingCrossPlant(Block block) {
        tallCrossPlant(block, BlockStyle.GLOWING);
    }

    @Override
    public void tallTintedGlowingCrossPlant(Block block, ItemTint tint) {
        tallCrossPlant(block, BlockStyle.TINTED_GLOWING, ModelProvider.createTintSource(tint));
    }


    private void columnCrossPlant(Block block, BlockStyle type, ItemTintSource... tints) {
        createColumnPlant(block, type);
        gen.itemModelOutput.accept(block.asItem(), ItemModelUtils.tintedModel(type.createItemModel(gen, block, "_end"), tints));
    }

    /**
     * Creates an untinted double-block cross plant.
     */
    public void columnCrossPlant(Block block) {
        columnCrossPlant(block, BlockStyle.NONE);
    }

    /**
     * Creates a tinted double-block cross plant.
     */
    public void columnTintedCrossPlant(Block block, ItemTint tint) {
        columnCrossPlant(block, BlockStyle.TINTED, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates a tinted double-block cross plant with untinted overlay.
     */
    public void columnLayeredCrossPlant(Block block, ItemTint tint) {
        columnCrossPlant(block, BlockStyle.LAYERED, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates an untinted double-block cross plant with emissive overlay.
     */
    public void columnEmissiveCrossPlant(Block block) {
        columnCrossPlant(block, BlockStyle.EMISSIVE);
    }

    @Override
    public void columnGlowingCrossPlant(Block block) {
        columnCrossPlant(block, BlockStyle.GLOWING);
    }

    @Override
    public void columnTintedGlowingCrossPlant(Block block, ItemTint tint) {
        columnCrossPlant(block, BlockStyle.TINTED_GLOWING, ModelProvider.createTintSource(tint));
    }


    /**
     * Creates an untinted flower bed.
     */
    public void flowerBed(Block block) {
        var segment1 = plainVariant(TexturedModel.FLOWERBED_1.create(block, gen.modelOutput));
        var segment2 = plainVariant(TexturedModel.FLOWERBED_2.create(block, gen.modelOutput));
        var segment3 = plainVariant(TexturedModel.FLOWERBED_3.create(block, gen.modelOutput));
        var segment4 = plainVariant(TexturedModel.FLOWERBED_4.create(block, gen.modelOutput));

        var property = block instanceof SegmentableBlock sb
                ? sb.getSegmentAmountProperty()
                : BlockStateProperties.FLOWER_AMOUNT;

        gen.createSegmentedBlock(
                block,
                segment1, it -> it,
                segment2, it -> it.term(property, 2, 3, 4),
                segment3, it -> it.term(property, 3, 4),
                segment4, it -> it.term(property, 4)
        );

        gen.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(gen.createFlatItemModel(block.asItem())));
    }

    /**
     * Creates a tinted flower bed.
     */
    public void tintedFlowerBed(Block block, ItemTint tint) {
        var segment1 = plainVariant(ModTexturedModels.TINTED_FLOWERBED_1.create(block, gen.modelOutput));
        var segment2 = plainVariant(ModTexturedModels.TINTED_FLOWERBED_2.create(block, gen.modelOutput));
        var segment3 = plainVariant(ModTexturedModels.TINTED_FLOWERBED_3.create(block, gen.modelOutput));
        var segment4 = plainVariant(ModTexturedModels.TINTED_FLOWERBED_4.create(block, gen.modelOutput));

        var property = block instanceof SegmentableBlock sb
                ? sb.getSegmentAmountProperty()
                : BlockStateProperties.FLOWER_AMOUNT;

        gen.createSegmentedBlock(
                block,
                segment1, it -> it,
                segment2, it -> it.term(property, 2, 3, 4),
                segment3, it -> it.term(property, 3, 4),
                segment4, it -> it.term(property, 4)
        );

        gen.itemModelOutput.accept(block.asItem(), ItemModelUtils.tintedModel(gen.createFlatItemModel(block.asItem()), ModelProvider.createTintSource(tint)));
    }


    private void flatSegmentedPlant(Block block, BlockStyle type, ItemTintSource... tints) {
        createFlatSegmentedPlant(block, type);
        gen.itemModelOutput.accept(block.asItem(), ItemModelUtils.tintedModel(type.createItemModel(gen, block), tints));
    }

    @Override
    public void flatSegmentedPlant(Block block) {
        flatSegmentedPlant(block, BlockStyle.NONE);
    }

    @Override
    public void tintedFlatSegmentedPlant(Block block, ItemTint tint) {
        flatSegmentedPlant(block, BlockStyle.TINTED, ModelProvider.createTintSource(tint));
    }

    @Override
    public void layeredFlatSegmentedPlant(Block block, ItemTint tint) {
        flatSegmentedPlant(block, BlockStyle.LAYERED, ModelProvider.createTintSource(tint));
    }

    @Override
    public void emissiveFlatSegmentedPlant(Block block) {
        flatSegmentedPlant(block, BlockStyle.EMISSIVE);
    }

    @Override
    public void glowingFlatSegmentedPlant(Block block) {
        flatSegmentedPlant(block, BlockStyle.GLOWING);
    }

    @Override
    public void tintedGlowingFlatSegmentedPlant(Block block, ItemTint tint) {
        flatSegmentedPlant(block, BlockStyle.TINTED_GLOWING, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates an untinted multiface block.
     */
    public void multiface(Block block) {
        // createMultiface does not generate a block model, doing this generates one using our flat model template
        ModModelTemplates.FLAT.create(block, TextureMapping.defaultTexture(block), gen.modelOutput);

        gen.createMultiface(block);
    }

    /**
     * Creates a tinted multiface block.
     */
    public void tintedMultiface(Block block, ItemTint tint) {
        // createMultiface does not generate a block model, doing this generates one using our flat model template
        ModModelTemplates.FLAT_TINTED.create(block, TextureMapping.defaultTexture(block), gen.modelOutput);

        gen.createMultifaceBlockStates(block);
        gen.registerSimpleTintedItemModel(block, gen.createFlatItemModelWithBlockTexture(block.asItem(), block), ModelProvider.createTintSource(tint));
    }

    /**
     * Creates a shelf fungus.
     */
    public void shelfFungus(Block block) {
        var disp = PropertyDispatch.initial(DiagonallyAttachableBlock.FACING);

        var aligned1 = plainModel(createAlignedShelfFungus(block, "_1"));
        var aligned2 = plainModel(createAlignedShelfFungus(block, "_2"));
        var aligned3 = plainModel(createAlignedShelfFungus(block, "_3"));
        var aligned = variants(aligned1, aligned2, aligned3);

        var diagonal1 = plainModel(createDiagonalShelfFungus(block, "_1"));
        var diagonal2 = plainModel(createDiagonalShelfFungus(block, "_2"));
        var diagonal3 = plainModel(createDiagonalShelfFungus(block, "_3"));
        var diagonal = variants(diagonal1, diagonal2, diagonal3);

        for (int i = 0; i < 4; i++) {
            disp.select(ALIGNED_DIRECTIONS[i], aligned.with(SHELF_FUNGUS_ROTATIONS[i]));
            disp.select(DIAGONAL_DIRECTIONS[i], diagonal.with(SHELF_FUNGUS_ROTATIONS[i]));
        }

        gen.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(disp)
        );

        gen.registerSimpleItemModel(block, gen.createFlatItemModel(block.asItem()));
    }

    /**
     * Creates a leaves model using the maple leaves texture.
     */
    public void mapleLeaves(Block block, ItemTint tint) {
        createMapleLeaves(block, TexturedModel.LEAVES, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates an untinted leaves model.
     */
    public void leaves(Block block) {
        gen.createTrivialBlock(block, TexturedModel.LEAVES);
    }

    /**
     * Creates a tinted leaves model.
     */
    public void tintedLeaves(Block block, ItemTint tint) {
        createTintedLeaves(block, TexturedModel.LEAVES, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates an untinted covered block model (like grass blocks and podzol).
     */
    public void coveredBlock(Block block, Block base) {
        createCoveredBlock(block, base, false);
    }

    /**
     * Creates an untinted covered block model (like grass blocks and podzol).
     */
    public void randomlyRotatedCoveredBlock(Block block, Block base) {
        createCoveredBlock(block, base, true);
    }

    /**
     * Creates a model provider for block families.
     */
    public FamilyModels family(Block block) {
        var model = FamilyModelsImpl.CUSTOM_MODELS.getOrDefault(block, TexturedModel.CUBE.get(block));
        return new FamilyModelsImpl(gen, model.getMapping()).fullBlock(block, model.getTemplate());
    }

    public WoodModels wood(Block block) {
        return new WoodModelsImpl(gen, TextureMapping.logColumn(block));
    }

    /**
     * Creates an multilayer model.
     */
    public void multilayer(Block block, Block fullBlock) {
        var disp = PropertyDispatch.initial(MultilayerBlock.LAYERS);
        var mapping = TextureMapping.defaultTexture(fullBlock);

        var models = MULTILAYER_MODELS.stream()
                .map(it -> it.create(block, mapping, gen.modelOutput))
                .toList();

        for (int i = 0; i < MultilayerBlock.MAX_HEIGHT; i++) {
            var height = i + 1;
            disp.select(height, plainVariant(models.get(i)));
        }

        gen.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(disp));
        gen.itemModelOutput.accept(block.asItem(), ItemModelUtils.plainModel(models.getFirst()));
    }


    // HELPERS
    // ==========================================

    private void createTintedLeaves(Block block, TexturedModel.Provider provider, ItemTintSource tint) {
        var model = provider.create(block, gen.modelOutput);
        gen.blockStateOutput.accept(createSimpleBlock(block, plainVariant(model)));
        gen.registerSimpleTintedItemModel(block, model, tint);
    }

    private void createCoveredBlock(Block block, Block base, boolean randomlyRotate) {
        var topTexture = TextureMapping.getBlockTexture(block, "_top");
        var sideTexture = TextureMapping.getBlockTexture(block, "_side");
        var bottomTexture = TextureMapping.getBlockTexture(base);

        var baseMapping = new TextureMapping()
                .put(TextureSlot.BOTTOM, bottomTexture)
                .put(TextureSlot.SIDE, sideTexture)
                .put(TextureSlot.TOP, topTexture);

        var baseModel = plainModel(ModelTemplates.CUBE_BOTTOM_TOP.create(block, baseMapping, gen.modelOutput));
        var baseVariant = randomlyRotate ? createRotatedVariants(baseModel) : variant(baseModel);

        if (block.getStateDefinition().getProperties().contains(BlockStateProperties.SNOWY) && SNOWY_SIDE_TEXTURES.containsKey(base)) {
            var snowySideTexture = SNOWY_SIDE_TEXTURES.get(base);

            var snowyMapping = new TextureMapping()
                    .put(TextureSlot.BOTTOM, bottomTexture)
                    .put(TextureSlot.SIDE, snowySideTexture)
                    .put(TextureSlot.TOP, topTexture);

            var snowyModel = plainModel(ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(block, "_snowy", snowyMapping, gen.modelOutput));
            var snowyVariant = randomlyRotate ? createRotatedVariants(snowyModel) : variant(snowyModel);

            var dispatch = PropertyDispatch.initial(BlockStateProperties.SNOWY)
                    .select(false, baseVariant)
                    .select(true, snowyVariant);

            gen.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(dispatch));
        } else {
            gen.blockStateOutput.accept(createSimpleBlock(block, baseVariant));
        }
    }


    private void createMapleLeaves(Block block, TexturedModel.Provider provider, ItemTintSource tint) {
        var model = provider
                .updateTexture(it -> it.put(TextureSlot.ALL, Materials.of("block/maple_leaves")))
                .create(block, gen.modelOutput);

        gen.blockStateOutput.accept(createSimpleBlock(block, plainVariant(model)));
        gen.registerSimpleTintedItemModel(block, model, tint);
    }

    private void createCrossBlock(Block block, BlockStyle type) {
        var plant = plainVariant(createVariant(block, type.getCross(), type::getCrossTextureMapping));
        gen.blockStateOutput.accept(createSimpleBlock(block, plant));
    }

    private void createDoublePlant(Block block, BlockStyle type) {
        var top = plainVariant(createVariant(block, "_top", type.getCross(), type::getCrossTextureMapping));
        var bottom = plainVariant(createVariant(block, "_bottom", type.getCross(), type::getCrossTextureMapping));
        gen.createDoubleBlock(block, top, bottom);
    }

    private void createColumnPlant(Block block, BlockStyle type) {
        var end = plainVariant(createVariant(block, "_end", type.getCross(), type::getCrossTextureMapping));
        var base = plainVariant(createVariant(block, type.getCross(), type::getCrossTextureMapping));
        createColumnBlock(block, end, base);
    }

    private void createFlatSegmentedPlant(Block block, BlockStyle type) {
        var model1 = plainVariant(createVariant(block, type.getFlatSegment1(), type::getDefaultTextureMapping));
        var model2 = plainVariant(createVariant(block, type.getFlatSegment2(), type::getDefaultTextureMapping));
        var model3 = plainVariant(createVariant(block, type.getFlatSegment3(), type::getDefaultTextureMapping));
        var model4 = plainVariant(createVariant(block, type.getFlatSegment4(), type::getDefaultTextureMapping));

        gen.createSegmentedBlock(
                block,
                model1, FLAT_SEGMENT_1_SEGMENT_CONDITION,
                model2, FLAT_SEGMENT_2_SEGMENT_CONDITION,
                model3, FLAT_SEGMENT_3_SEGMENT_CONDITION,
                model4, FLAT_SEGMENT_4_SEGMENT_CONDITION
        );
    }

    private void createColumnBlock(Block block, MultiVariant end, MultiVariant base) {
        gen.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block).with(
                        PropertyDispatch.initial(AbstractColumnPlantBlock.SHAPE)
                                .select(ColumnPlantShape.GROWING, end)
                                .select(ColumnPlantShape.PERMANENT, end)
                                .select(ColumnPlantShape.BODY, base)
                )
        );
    }

    private Identifier createVariant(Block block, ModelTemplate template, Function<Material, TextureMapping> textureMapper) {
        return template.create(block, textureMapper.apply(TextureMapping.getBlockTexture(block)), gen.modelOutput);
    }

    private Identifier createVariant(Block block, String suffix, ModelTemplate template, Function<Material, TextureMapping> textureMapper) {
        return template.createWithSuffix(block, suffix, textureMapper.apply(TextureMapping.getBlockTexture(block, suffix)), gen.modelOutput);
    }

    private TextureMapping createShelfFungusMapping(Material texture) {
        return new TextureMapping().put(TextureSlot.END, texture);
    }

    private Identifier createAlignedShelfFungus(Block block, String suffix) {
        return ModModelTemplates.SHELF_FUNGUS.createWithSuffix(block, suffix, createShelfFungusMapping(TextureMapping.getBlockTexture(block, suffix)), gen.modelOutput);
    }

    private Identifier createDiagonalShelfFungus(Block block, String suffix) {
        return ModModelTemplates.SHELF_FUNGUS_DIAGONAL.createWithSuffix(block, "_diagonal" + suffix, createShelfFungusMapping(TextureMapping.getBlockTexture(block, "_diagonal" + suffix)), gen.modelOutput);
    }
}
