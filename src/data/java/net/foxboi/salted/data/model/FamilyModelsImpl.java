package net.foxboi.salted.data.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import com.google.common.collect.ImmutableMap;

public class FamilyModelsImpl implements FamilyModels {
    interface StateFactory {
        BlockModelDefinitionGenerator create(Block block, Variant variant, TextureMapping texture, BiConsumer<Identifier, ModelInstance> biConsumer);
    }

    static final Map<Block, StateFactory> FULL_BLOCK_CUSTOM = Map.of(
            Blocks.STONE, BlockModelGenerators::createMirroredCubeGenerator,
            Blocks.DEEPSLATE, BlockModelGenerators::createMirroredColumnGenerator,
            Blocks.MUD_BRICKS, BlockModelGenerators::createNorthWestMirroredCubeGenerator
    );

    static final Set<Block> NON_ORIENTABLE_TRAPDOORS = Set.of(
            Blocks.OAK_TRAPDOOR,
            Blocks.DARK_OAK_TRAPDOOR,
            Blocks.IRON_TRAPDOOR
    );

    static final Set<Block> UV_LOCKED_LOGS = Set.of(
            Blocks.CHERRY_LOG,
            Blocks.STRIPPED_CHERRY_LOG,
            Blocks.BAMBOO_BLOCK,
            Blocks.STRIPPED_BAMBOO_BLOCK
    );

    static final Set<Block> NON_HORIZONTAL_LOGS = Set.of(
            Blocks.CRIMSON_STEM,
            Blocks.CRIMSON_HYPHAE,
            Blocks.WARPED_STEM,
            Blocks.WARPED_HYPHAE
    );

    static final Map<Block, TexturedModel> CUSTOM_MODELS = ImmutableMap.<Block, TexturedModel>builder()
            .put(Blocks.SANDSTONE, TexturedModel.TOP_BOTTOM_WITH_WALL.get(Blocks.SANDSTONE))
            .put(Blocks.RED_SANDSTONE, TexturedModel.TOP_BOTTOM_WITH_WALL.get(Blocks.RED_SANDSTONE))
            .put(Blocks.SMOOTH_SANDSTONE, TexturedModel.createAllSame(TextureMapping.getBlockTexture(Blocks.SANDSTONE, "_top")))
            .put(Blocks.SMOOTH_RED_SANDSTONE, TexturedModel.createAllSame(TextureMapping.getBlockTexture(Blocks.RED_SANDSTONE, "_top")))
            .put(Blocks.CUT_SANDSTONE, TexturedModel.COLUMN.get(Blocks.SANDSTONE).updateTextures(map -> map.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CUT_SANDSTONE))))
            .put(Blocks.CUT_RED_SANDSTONE, TexturedModel.COLUMN.get(Blocks.RED_SANDSTONE).updateTextures(map -> map.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CUT_RED_SANDSTONE))))
            .put(Blocks.QUARTZ_BLOCK, TexturedModel.COLUMN.get(Blocks.QUARTZ_BLOCK))
            .put(Blocks.SMOOTH_QUARTZ, TexturedModel.createAllSame(TextureMapping.getBlockTexture(Blocks.QUARTZ_BLOCK, "_bottom")))
            .put(Blocks.BLACKSTONE, TexturedModel.COLUMN_WITH_WALL.get(Blocks.BLACKSTONE))
            .put(Blocks.DEEPSLATE, TexturedModel.COLUMN_WITH_WALL.get(Blocks.DEEPSLATE))
            .put(Blocks.CHISELED_QUARTZ_BLOCK, TexturedModel.COLUMN.get(Blocks.CHISELED_QUARTZ_BLOCK).updateTextures(textureMapping -> textureMapping.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CHISELED_QUARTZ_BLOCK))))
            .put(Blocks.CHISELED_SANDSTONE, TexturedModel.COLUMN.get(Blocks.CHISELED_SANDSTONE).updateTextures(map -> map.put(TextureSlot.END, TextureMapping.getBlockTexture(Blocks.SANDSTONE, "_top")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CHISELED_SANDSTONE))))
            .put(Blocks.CHISELED_RED_SANDSTONE, TexturedModel.COLUMN.get(Blocks.CHISELED_RED_SANDSTONE).updateTextures(map -> map.put(TextureSlot.END, TextureMapping.getBlockTexture(Blocks.RED_SANDSTONE, "_top")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(Blocks.CHISELED_RED_SANDSTONE))))
            .put(Blocks.CHISELED_TUFF_BRICKS, TexturedModel.COLUMN_WITH_WALL.get(Blocks.CHISELED_TUFF_BRICKS))
            .put(Blocks.CHISELED_TUFF, TexturedModel.COLUMN_WITH_WALL.get(Blocks.CHISELED_TUFF))
            .build();

    static final Map<BlockFamily.Variant, BiConsumer<FamilyModelsImpl, Block>> FAMILY_SHAPES = ImmutableMap.<BlockFamily.Variant, BiConsumer<FamilyModelsImpl, Block>>builder()
            .put(BlockFamily.Variant.BUTTON, FamilyModelsImpl::button)
            .put(BlockFamily.Variant.DOOR, FamilyModelsImpl::door)
            .put(BlockFamily.Variant.CHISELED, FamilyModelsImpl::fullBlockVariant)
            .put(BlockFamily.Variant.CRACKED, FamilyModelsImpl::fullBlockVariant)
            .put(BlockFamily.Variant.CUSTOM_FENCE, FamilyModelsImpl::customFence)
            .put(BlockFamily.Variant.FENCE, FamilyModelsImpl::fence)
            .put(BlockFamily.Variant.CUSTOM_FENCE_GATE, FamilyModelsImpl::customFenceGate)
            .put(BlockFamily.Variant.FENCE_GATE, FamilyModelsImpl::fenceGate)
            .put(BlockFamily.Variant.SIGN, FamilyModelsImpl::sign)
            .put(BlockFamily.Variant.SLAB, FamilyModelsImpl::slab)
            .put(BlockFamily.Variant.STAIRS, FamilyModelsImpl::stairs)
            .put(BlockFamily.Variant.PRESSURE_PLATE, FamilyModelsImpl::pressurePlate)
            .put(BlockFamily.Variant.TRAPDOOR, FamilyModelsImpl::trapdoor)
            .put(BlockFamily.Variant.WALL, FamilyModelsImpl::wall)
            .build();

    private final BlockModelGenerators gen;
    private final TextureMapping mapping;

    private final Map<ModelTemplate, Identifier> models = new HashMap<>();
    private final Set<Block> skipModels = new HashSet<>();

    private BlockFamily family;
    private Variant fullBlock;

    private BlockModelGenerators.WoodProvider woodProvider;
    private BlockModelGenerators.WoodProvider strippedWoodProvider;
    private Block strippedLog;

    public FamilyModelsImpl(BlockModelGenerators gen, TextureMapping mapping) {
        this.gen = gen;
        this.mapping = mapping;
    }

    public FamilyModelsImpl fullBlock(Block block, ModelTemplate template) {
        fullBlock = BlockModelGenerators.plainModel(template.create(block, mapping, gen.modelOutput));

        if (FULL_BLOCK_CUSTOM.containsKey(block)) {
            gen.blockStateOutput.accept(FULL_BLOCK_CUSTOM.get(block).create(block, fullBlock, mapping, gen.modelOutput));
        } else {
            gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, BlockModelGenerators.variant(fullBlock)));
        }

        return this;
    }

    @Override
    public FamilyModelsImpl log(Block log) {
        woodProvider = gen.woodProvider(log);

        if (UV_LOCKED_LOGS.contains(log)) {
            woodProvider.logUVLocked(log);
        } else if (NON_HORIZONTAL_LOGS.contains(log)) {
            woodProvider.log(log);
        } else {
            woodProvider.logWithHorizontal(log);
        }

        return this;
    }

    @Override
    public FamilyModelsImpl wood(Block wood) {
        if (woodProvider == null) {
            throw new IllegalStateException("Log not generated yet");
        }

        woodProvider.wood(wood);

        return this;
    }

    @Override
    public FamilyModelsImpl strippedLog(Block log) {
        strippedWoodProvider = gen.woodProvider(log);

        if (UV_LOCKED_LOGS.contains(log)) {
            strippedWoodProvider.logUVLocked(log);
        } else if (NON_HORIZONTAL_LOGS.contains(log)) {
            strippedWoodProvider.log(log);
        } else {
            strippedWoodProvider.logWithHorizontal(log);
        }

        strippedLog = log;

        return this;
    }

    @Override
    public FamilyModelsImpl strippedWood(Block wood) {
        if (strippedWoodProvider == null) {
            throw new IllegalStateException("Stripped log not generated yet");
        }

        strippedWoodProvider.wood(wood);

        return this;
    }

    @Override
    public FamilyModelsImpl donateModelTo(Block to, Block from) {
        var toId = ModelLocationUtils.getModelLocation(to);
        gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(from, BlockModelGenerators.plainVariant(toId)));
        gen.itemModelOutput.copy(to.asItem(), from.asItem());

        skipModels.add(from);
        return this;
    }

    @Override
    public FamilyModelsImpl button(Block block) {
        var unpressed = BlockModelGenerators.plainVariant(ModelTemplates.BUTTON.create(block, mapping, gen.modelOutput));
        var pressed = BlockModelGenerators.plainVariant(ModelTemplates.BUTTON_PRESSED.create(block, mapping, gen.modelOutput));
        gen.blockStateOutput.accept(BlockModelGenerators.createButton(block, unpressed, pressed));

        var inventory = ModelTemplates.BUTTON_INVENTORY.create(block, mapping, gen.modelOutput);
        gen.registerSimpleItemModel(block, inventory);

        return this;
    }

    @Override
    public FamilyModelsImpl wall(Block block) {
        var post = BlockModelGenerators.plainVariant(ModelTemplates.WALL_POST.create(block, mapping, gen.modelOutput));
        var low = BlockModelGenerators.plainVariant(ModelTemplates.WALL_LOW_SIDE.create(block, mapping, gen.modelOutput));
        var tall = BlockModelGenerators.plainVariant(ModelTemplates.WALL_TALL_SIDE.create(block, mapping, gen.modelOutput));
        gen.blockStateOutput.accept(BlockModelGenerators.createWall(block, post, low, tall));

        var inventory = ModelTemplates.WALL_INVENTORY.create(block, mapping, gen.modelOutput);
        gen.registerSimpleItemModel(block, inventory);

        return this;
    }

    @Override
    public FamilyModelsImpl customFence(Block block) {
        var mapping = TextureMapping.customParticle(block);

        var post = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_POST.create(block, mapping, gen.modelOutput));
        var north = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_SIDE_NORTH.create(block, mapping, gen.modelOutput));
        var east = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_SIDE_EAST.create(block, mapping, gen.modelOutput));
        var south = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_SIDE_SOUTH.create(block, mapping, gen.modelOutput));
        var west = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_SIDE_WEST.create(block, mapping, gen.modelOutput));
        gen.blockStateOutput.accept(BlockModelGenerators.createCustomFence(block, post, north, east, south, west));

        var inventory = ModelTemplates.CUSTOM_FENCE_INVENTORY.create(block, mapping, gen.modelOutput);
        gen.registerSimpleItemModel(block, inventory);

        return this;
    }

    @Override
    public FamilyModelsImpl fence(Block block) {
        var post = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_POST.create(block, mapping, gen.modelOutput));
        var side = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_SIDE.create(block, mapping, gen.modelOutput));
        gen.blockStateOutput.accept(BlockModelGenerators.createFence(block, post, side));

        var inventory = ModelTemplates.FENCE_INVENTORY.create(block, this.mapping, gen.modelOutput);
        gen.registerSimpleItemModel(block, inventory);

        return this;
    }

    @Override
    public FamilyModelsImpl customFenceGate(Block block) {
        var mapping = TextureMapping.customParticle(block);

        var open = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_GATE_OPEN.create(block, mapping, gen.modelOutput));
        var closed = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_GATE_CLOSED.create(block, mapping, gen.modelOutput));
        var wallOpen = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_GATE_WALL_OPEN.create(block, mapping, gen.modelOutput));
        var wallClosed = BlockModelGenerators.plainVariant(ModelTemplates.CUSTOM_FENCE_GATE_WALL_CLOSED.create(block, mapping, gen.modelOutput));
        gen.blockStateOutput.accept(BlockModelGenerators.createFenceGate(block, open, closed, wallOpen, wallClosed, false));

        return this;
    }

    @Override
    public FamilyModelsImpl fenceGate(Block block) {
        var open = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_OPEN.create(block, mapping, gen.modelOutput));
        var closed = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_CLOSED.create(block, mapping, gen.modelOutput));
        var wallOpen = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_OPEN.create(block, mapping, gen.modelOutput));
        var wallClosed = BlockModelGenerators.plainVariant(ModelTemplates.FENCE_GATE_WALL_CLOSED.create(block, mapping, gen.modelOutput));
        gen.blockStateOutput.accept(BlockModelGenerators.createFenceGate(block, open, closed, wallOpen, wallClosed, true));

        return this;
    }

    @Override
    public FamilyModelsImpl pressurePlate(Block block) {
        var up = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_UP.create(block, this.mapping, gen.modelOutput));
        var down = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_DOWN.create(block, this.mapping, gen.modelOutput));
        gen.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(block, up, down));

        return this;
    }

    public FamilyModelsImpl sign(Block block) {
        if (family == null) {
            throw new IllegalStateException("Family not defined");
        } else {
            return sign(block, family.get(BlockFamily.Variant.WALL_SIGN));
        }
    }

    @Override
    public FamilyModelsImpl sign(Block block, Block wallBlock) {
        var emptyModel = BlockModelGenerators.plainVariant(ModelTemplates.PARTICLE_ONLY.create(block, this.mapping, gen.modelOutput));
        gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, emptyModel));
        gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(wallBlock, emptyModel));
        gen.registerSimpleFlatItemModel(block.asItem());

        return this;
    }

    @Override
    public FamilyModelsImpl hangingSign(Block block, Block wallBlock) {
        if (strippedLog == null) {
            throw new IllegalStateException("Stripped log not generated yet");
        }

        gen.createHangingSign(strippedLog, block, wallBlock);

        return this;
    }

    @Override
    public FamilyModelsImpl slab(Block block) {
        if (fullBlock == null) {
            throw new IllegalStateException("Full block not generated yet");
        }

        var bottom = getOrCreateModel(ModelTemplates.SLAB_BOTTOM, block);
        var top = getOrCreateModel(ModelTemplates.SLAB_TOP, block);

        gen.blockStateOutput.accept(BlockModelGenerators.createSlab(
                block,
                BlockModelGenerators.plainVariant(bottom),
                BlockModelGenerators.plainVariant(top),
                BlockModelGenerators.variant(fullBlock)
        ));

        gen.registerSimpleItemModel(block, bottom);

        return this;
    }

    @Override
    public FamilyModelsImpl stairs(Block block) {
        var inner = getOrCreateModel(ModelTemplates.STAIRS_INNER, block);
        var straight = getOrCreateModel(ModelTemplates.STAIRS_STRAIGHT, block);
        var outer = getOrCreateModel(ModelTemplates.STAIRS_OUTER, block);

        gen.blockStateOutput.accept(BlockModelGenerators.createStairs(
                block,
                BlockModelGenerators.plainVariant(inner),
                BlockModelGenerators.plainVariant(straight),
                BlockModelGenerators.plainVariant(outer)
        ));

        gen.registerSimpleItemModel(block, straight);

        return this;
    }

    private FamilyModelsImpl fullBlockVariant(Block block) {
        var model = CUSTOM_MODELS.getOrDefault(block, TexturedModel.CUBE.get(block));
        var variant = BlockModelGenerators.plainVariant(model.create(block, gen.modelOutput));

        gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, variant));
        return this;
    }

    @Override
    public FamilyModelsImpl door(Block block) {
        gen.createDoor(block);
        return this;
    }

    @Override
    public FamilyModelsImpl trapdoor(Block block) {
        if (NON_ORIENTABLE_TRAPDOORS.contains(block)) {
            gen.createTrapdoor(block);
        } else {
            gen.createOrientableTrapdoor(block);
        }
        return this;
    }

    @Override
    public FamilyModelsImpl shelf(Block block) {
        if (strippedLog == null) {
            throw new IllegalStateException("Stripped log not generated yet");
        }

        gen.createShelf(block, strippedLog);
        return this;
    }

    private Identifier getOrCreateModel(ModelTemplate template, Block block) {
        return models.computeIfAbsent(template, it -> it.create(block, mapping, gen.modelOutput));
    }

    @Override
    public FamilyModelsImpl generateFor(BlockFamily family) {
        this.family = family;

        family.getVariants().forEach((variant, block) -> {
            if (!skipModels.contains(block)) {
                var shape = FAMILY_SHAPES.get(variant);
                if (shape != null) {
                    shape.accept(this, block);
                }
            }
        });

        return this;
    }
}
