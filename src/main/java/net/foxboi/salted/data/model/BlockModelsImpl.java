package net.foxboi.salted.data.model;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.block.AbstractColumnPlantBlock;
import net.foxboi.salted.common.block.DiagonallyAttachableBlock;
import net.foxboi.salted.common.block.MultilayerBlock;
import net.foxboi.salted.common.block.SaltCrystalBlock;
import net.foxboi.salted.common.misc.DiagonalDirection;
import net.foxboi.salted.common.misc.ItemTint;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SegmentableBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

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

    private static final Map<Block, ResourceLocation> SNOWY_SIDE_TEXTURES = Map.of(
            Blocks.DIRT, ResourceLocation.withDefaultNamespace("block/grass_block_snow")
    );

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


    private void crossPlant(Block block, PlantType type, ItemTintSource... tints) {
        createCrossBlock(block, type);
        gen.itemModelOutput.accept(block.asItem(), ItemModelUtils.tintedModel(type.createItemModel(gen, block), tints));
    }

    /**
     * Creates an untinted cross plant.
     */
    public void crossPlant(Block block) {
        crossPlant(block, PlantType.NOT_TINTED);
    }

    /**
     * Creates a tinted cross plant.
     */
    public void tintedCrossPlant(Block block, ItemTint tint) {
        crossPlant(block, PlantType.TINTED, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates a tinted cross plant with an untinted overlay.
     */
    public void layeredCrossPlant(Block block, ItemTint tint) {
        crossPlant(block, PlantType.LAYERED, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates an untinted cross plant with an emissive overlay.
     */
    public void emissiveCrossPlant(Block block) {
        crossPlant(block, PlantType.EMISSIVE_NOT_TINTED);
    }

    private void tallCrossPlant(Block block, PlantType type, ItemTintSource... tints) {
        createDoublePlant(block, type);
        gen.itemModelOutput.accept(block.asItem(), ItemModelUtils.tintedModel(type.createItemModel(gen, block, "_top"), tints));
    }

    /**
     * Creates an untinted double-block cross plant.
     */
    public void tallCrossPlant(Block block) {
        tallCrossPlant(block, PlantType.NOT_TINTED);
    }

    /**
     * Creates a tinted double-block cross plant.
     */
    public void tallTintedCrossPlant(Block block, ItemTint tint) {
        tallCrossPlant(block, PlantType.TINTED, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates a tinted double-block cross plant with untinted overlay.
     */
    public void tallLayeredCrossPlant(Block block, ItemTint tint) {
        tallCrossPlant(block, PlantType.LAYERED, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates an untinted double-block cross plant with emissive overlay.
     */
    public void tallEmissiveCrossPlant(Block block) {
        tallCrossPlant(block, PlantType.EMISSIVE_NOT_TINTED);
    }


    private void columnCrossPlant(Block block, PlantType type, ItemTintSource... tints) {
        createColumnPlant(block, type);
        gen.itemModelOutput.accept(block.asItem(), ItemModelUtils.tintedModel(type.createItemModel(gen, block, "_end"), tints));
    }

    /**
     * Creates an untinted double-block cross plant.
     */
    public void columnCrossPlant(Block block) {
        columnCrossPlant(block, PlantType.NOT_TINTED);
    }

    /**
     * Creates a tinted double-block cross plant.
     */
    public void columnTintedCrossPlant(Block block, ItemTint tint) {
        columnCrossPlant(block, PlantType.TINTED, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates a tinted double-block cross plant with untinted overlay.
     */
    public void columnLayeredCrossPlant(Block block, ItemTint tint) {
        columnCrossPlant(block, PlantType.LAYERED, ModelProvider.createTintSource(tint));
    }

    /**
     * Creates an untinted double-block cross plant with emissive overlay.
     */
    public void columnEmissiveCrossPlant(Block block) {
        columnCrossPlant(block, PlantType.EMISSIVE_NOT_TINTED);
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
     * Creates a salt crystal model.
     */
    public void saltCrystal(Block block) {
        var disp = PropertyDispatch.initial(SaltCrystalBlock.AGE);

        for (int i = 0; i < 8; i++) {
            var stage = i + 1;
            var name = TextureMapping.getBlockTexture(block, "_stage_" + stage);
            var variant = plainVariant(ModelTemplates.CROSS.create(name, TextureMapping.cross(name), gen.modelOutput));
            disp.select(stage, variant);
        }

        gen.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block)
                        .with(disp)
                        .with(ROTATIONS_COLUMN_WITH_FACING)
        );
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
                .updateTexture(it -> it.put(TextureSlot.ALL, Smptg.id("block/maple_leaves")))
                .create(block, gen.modelOutput);

        gen.blockStateOutput.accept(createSimpleBlock(block, plainVariant(model)));
        gen.registerSimpleTintedItemModel(block, model, tint);
    }

    private void createCrossBlock(Block block, PlantType type) {
        var plant = plainVariant(createVariant(block, type.getCross(), type::getTextureMapping));
        gen.blockStateOutput.accept(createSimpleBlock(block, plant));
    }

    private void createDoublePlant(Block block, PlantType type) {
        var top = plainVariant(createVariant(block, "_top", type.getCross(), type::getTextureMapping));
        var bottom = plainVariant(createVariant(block, "_bottom", type.getCross(), type::getTextureMapping));
        gen.createDoubleBlock(block, top, bottom);
    }

    private void createColumnPlant(Block block, PlantType type) {
        var end = plainVariant(createVariant(block, "_end", type.getCross(), type::getTextureMapping));
        var base = plainVariant(createVariant(block, type.getCross(), type::getTextureMapping));
        createColumnBlock(block, end, base);
    }

    private void createColumnBlock(Block block, MultiVariant end, MultiVariant base) {
        gen.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block).with(
                        PropertyDispatch.initial(AbstractColumnPlantBlock.END)
                                .select(true, end)
                                .select(false, base)
                )
        );
    }

    private ResourceLocation createVariant(Block block, ModelTemplate template, Function<ResourceLocation, TextureMapping> textureMapper) {
        return template.create(block, textureMapper.apply(TextureMapping.getBlockTexture(block)), gen.modelOutput);
    }

    private ResourceLocation createVariant(Block block, String suffix, ModelTemplate template, Function<ResourceLocation, TextureMapping> textureMapper) {
        return template.createWithSuffix(block, suffix, textureMapper.apply(TextureMapping.getBlockTexture(block, suffix)), gen.modelOutput);
    }

    private TextureMapping createShelfFungusMapping(ResourceLocation texture) {
        return new TextureMapping().put(TextureSlot.END, texture);
    }

    private ResourceLocation createAlignedShelfFungus(Block block, String suffix) {
        return ModModelTemplates.SHELF_FUNGUS.createWithSuffix(block, suffix, createShelfFungusMapping(TextureMapping.getBlockTexture(block, suffix)), gen.modelOutput);
    }

    private ResourceLocation createDiagonalShelfFungus(Block block, String suffix) {
        return ModModelTemplates.SHELF_FUNGUS_DIAGONAL.createWithSuffix(block, "_diagonal" + suffix, createShelfFungusMapping(TextureMapping.getBlockTexture(block, "_diagonal" + suffix)), gen.modelOutput);
    }
}
