package net.foxboi.salted.data.model;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.block.AbstractColumnPlantBlock;
import net.foxboi.salted.common.block.ColumnPlantShape;
import net.foxboi.salted.common.block.DiagonallyAttachableBlock;
import net.foxboi.salted.common.block.MultilayerBlock;
import net.foxboi.salted.common.misc.DiagonalDirection;
import net.foxboi.salted.data.core.model.*;

public class BlockModels {
    private final BlockDispatchSink blocks;
    private final ItemDispatchSink items;

    public BlockModels(BlockDispatchSink blocks, ItemDispatchSink items) {
        this.blocks = blocks;
        this.items = items;
    }




    // MODELS

    // Full Blocks

    public void cube(Block block) {
        var cube = ModelTemplates.CUBE_ALL.block(BlockTextures::all).create(block);
        saveStationary(block, cube);
    }

    public void columnCube(Block block) {
        var cube = ModelTemplates.CUBE_COLUMN.block(BlockTextures::column).create(block);
        saveStationary(block, cube);
    }

    public void bottomTopCube(Block block) {
        var cube = ModelTemplates.CUBE_BOTTOM_TOP.block(BlockTextures::bottomTop).create(block);
        saveStationary(block, cube);
    }

    public void randomlyRotatedCube(Block block) {
        var cube = ModelTemplates.CUBE_ALL.block(BlockTextures::all).create(block);

        var variants = WeightedVariants.of(cube)
                .flatTransform(RANDOM_ROTATE);

        saveStationary(block, variants, cube);
    }

    public void randomlyMirroredCube(Block block) {
        var cube = ModelTemplates.CUBE_ALL.block(BlockTextures::all).create(block);
        var cubeMirrored = ModelTemplates.CUBE_MIRRORED_ALL.block(BlockTextures::all).create(block);

        var variants = WeightedVariants.of()
                .with(cube)
                .with(cubeMirrored)
                .flatTransform(RANDOM_MIRROR);

        saveStationary(block, variants, cube);
    }

    public void log(Block block) {
        var column = ModelTemplates.CUBE_COLUMN.block(BlockTextures::column).create(block);

        var dispatch = StateDispatch.variants(RotatedPillarBlock.AXIS)
                .put(Direction.Axis.Y, WeightedVariants.of(column))
                .put(Direction.Axis.X, WeightedVariants.of(column, VariantTransform.ofX(90).y(90)))
                .put(Direction.Axis.Z, WeightedVariants.of(column, VariantTransform.ofX(90)));

        BlockDispatch.variants(block, dispatch).save(blocks);
        saveItem(block, column);
    }

    public void logWithHorizontal(Block block) {
        var vert = ModelTemplates.CUBE_COLUMN.block(BlockTextures::column).create(block);
        var horiz = ModelTemplates.CUBE_COLUMN_HORIZONTAL.block(BlockTextures::column).create(block);

        var dispatch = StateDispatch.variants(RotatedPillarBlock.AXIS)
                .put(Direction.Axis.Y, WeightedVariants.of(vert))
                .put(Direction.Axis.X, WeightedVariants.of(horiz, VariantTransform.ofX(90).y(90)))
                .put(Direction.Axis.Z, WeightedVariants.of(horiz, VariantTransform.ofX(90)));

        BlockDispatch.variants(block, dispatch).save(blocks);
        saveItem(block, vert);
    }

    public void logWithUvLock(Block block) {
        var x = ModelTemplates.CUBE_COLUMN_UV_LOCKED_X.block(BlockTextures::column).create(block);
        var y = ModelTemplates.CUBE_COLUMN_UV_LOCKED_Y.block(BlockTextures::column).create(block);
        var z = ModelTemplates.CUBE_COLUMN_UV_LOCKED_Z.block(BlockTextures::column).create(block);

        var dispatch = StateDispatch.model(RotatedPillarBlock.AXIS)
                .put(Direction.Axis.Y, y)
                .put(Direction.Axis.X, x)
                .put(Direction.Axis.Z, z);

        BlockDispatch.variants(block, dispatch.map(WeightedVariants::of)).save(blocks);
        saveItem(block, y);
    }

    public void wood(Block block, Block log) {
        var column = ModelTemplates.CUBE_COLUMN.create(block, BlockTextures.all(log));

        var dispatch = StateDispatch.variants(RotatedPillarBlock.AXIS)
                .put(Direction.Axis.Y, WeightedVariants.of(column))
                .put(Direction.Axis.X, WeightedVariants.of(column, VariantTransform.ofX(90).y(90)))
                .put(Direction.Axis.Z, WeightedVariants.of(column, VariantTransform.ofX(90)));

        BlockDispatch.variants(block, dispatch).save(blocks);
        saveItem(block, column);
    }

    public void overgrownDirt(Block block) {
        overgrownBlock(block, Blocks.DIRT, true);
    }

    public FamilyBuilder family(Block block) {
        var map = BlockTextures.all(block);

        var cube = ModelTemplates.CUBE_ALL.create(block, map);
        saveStationary(block, cube);

        return new InternalFamilyBuilder(block, map);
    }

    public FamilyBuilder columnFamily(Block block) {
        var map = BlockTextures.column(block);

        var cube = ModelTemplates.CUBE_COLUMN.create(block, map);
        saveStationary(block, cube);

        return new InternalFamilyBuilder(block, map);
    }

    public FamilyBuilder bottomTopFamily(Block block) {
        var map = BlockTextures.bottomTop(block);

        var cube = ModelTemplates.CUBE_BOTTOM_TOP.create(block, map);
        saveStationary(block, cube);

        return new InternalFamilyBuilder(block, map);
    }

    public void leaves(Block block) {
        var cube = ModelTemplates.LEAVES.block(BlockTextures::all).create(block);
        saveStationary(block, cube);
    }

    public void leaves(Block block, ItemTint tint) {
        var cube = ModelTemplates.LEAVES.block(BlockTextures::all).create(block);
        saveStationary(block, cube, tint);
    }

    public void leaves(Block block, int tint) {
        leaves(block, ItemTint.constant(tint));
    }

    public void mapleLeaves(Block block, ItemTint tint) {
        var cube = ModelTemplates.LEAVES.create(block, TextureMap.map().put("all", Smptg.id("block/maple_leaves")));
        saveStationary(block, cube, tint);
    }

    public void mapleLeaves(Block block, int tint) {
        mapleLeaves(block, ItemTint.constant(tint));
    }

    public void multilayer(Block block, Block fullBlock) {
        var dispatch = StateDispatch.model(MultilayerBlock.LAYERS);

        var models = MULTILAYER_MODELS.stream()
                .map(it -> it.create(block, BlockTextures.texture(fullBlock)))
                .toList();

        for (int i = 0; i < MultilayerBlock.MAX_HEIGHT; i++) {
            var height = i + 1;
            dispatch.put(height, models.get(i));
        }

        BlockDispatch.variants(block, dispatch.map(WeightedVariants::of)).save(blocks);
        ItemDispatch.of(block, models.getFirst()).save(items);
    }


    // Plants

    public PlantBuilder crossPlant(Block block) {
        return new ShapedPlantBuilder(block, PlantShape.CROSS, this::plant);
    }

    public PlantBuilder tallCrossPlant(Block block) {
        return new ShapedPlantBuilder(block, PlantShape.CROSS, this::tallPlant);
    }

    public PlantBuilder columnCrossPlant(Block block) {
        return new ShapedPlantBuilder(block, PlantShape.CROSS, this::columnPlant);
    }

    public PlantBuilder columnCrossPlantWithBase(Block block) {
        return new ShapedPlantBuilder(block, PlantShape.CROSS, this::columnPlantWithBase);
    }

    public PlantBuilder flatPlant(Block block) {
        return new BasicPlantBuilder(block, this::flatPlant);
    }

    public PlantBuilder multifacePlant(Block block) {
        return new BasicPlantBuilder(block, this::multifacePlant);
    }

    public PlantBuilder flowerbedPlant(Block block) {
        return new BasicPlantBuilder(block, this::flowerbedPlant);
    }

    public PlantBuilder flatSegmentedPlant(Block block) {
        return new BasicPlantBuilder(block, this::flatSegmentedPlant);
    }

    public void shelfFungus(Block block) {
        var dispatch = StateDispatch.variants(DiagonallyAttachableBlock.FACING);

        var aligned1 = createAlignedShelfFungus(block, "_1");
        var aligned2 = createAlignedShelfFungus(block, "_2");
        var aligned3 = createAlignedShelfFungus(block, "_3");
        var aligned = WeightedVariants.of().with(aligned1).with(aligned2).with(aligned3);

        var diagonal1 = createDiagonalShelfFungus(block, "_1");
        var diagonal2 = createDiagonalShelfFungus(block, "_2");
        var diagonal3 = createDiagonalShelfFungus(block, "_3");
        var diagonal = WeightedVariants.of().with(diagonal1).with(diagonal2).with(diagonal3);

        for (int i = 0; i < 4; i++) {
            dispatch.put(ALIGNED_DIRECTIONS[i], aligned.copy().transform(SHELF_FUNGUS_ROTATIONS[i]));
            dispatch.put(DIAGONAL_DIRECTIONS[i], diagonal.copy().transform(SHELF_FUNGUS_ROTATIONS[i]));
        }

        BlockDispatch.variants(block, dispatch).save(blocks);
        saveFlatItem(block);
    }





    // CONSTANTS

    private static final WeightedTransforms RANDOM_ROTATE = WeightedTransforms.of()
            .with(VariantTransform.ofY(0))
            .with(VariantTransform.ofY(90))
            .with(VariantTransform.ofY(180))
            .with(VariantTransform.ofY(270));

    private static final WeightedTransforms RANDOM_MIRROR = WeightedTransforms.of()
            .with(VariantTransform.ofY(0))
            .with(VariantTransform.ofY(180));

    private static final Function<WeightedVariants, WeightedVariants> UV_LOCK = variants -> variants.uvlock(true);

    private static final Map<Direction, VariantTransform> MULTIFACE_TRANSFORMS = Map.of(
            Direction.NORTH, VariantTransform.ofX(270),
            Direction.EAST, VariantTransform.ofX(270).y(90),
            Direction.SOUTH, VariantTransform.ofX(270).y(180),
            Direction.WEST, VariantTransform.ofX(270).y(270),
            Direction.UP, VariantTransform.ofX(180),
            Direction.DOWN, VariantTransform.of()
    );

    private static final Map<Block, Identifier> SNOWY_SIDE_TEXTURES = Map.of(
            Blocks.DIRT, Identifier.withDefaultNamespace("block/grass_block_snow")
    );

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

    private static final VariantTransform[] SHELF_FUNGUS_ROTATIONS = {
            VariantTransform.ofY(180),
            VariantTransform.ofY(270),
            VariantTransform.of(),
            VariantTransform.ofY(90)
    };

    private static StateDispatch<Function<WeightedVariants, WeightedVariants>> horizontalFacing(Direction zeroRotation) {
        return StateDispatch.variantsMapper(BlockStateProperties.HORIZONTAL_FACING)
                .put(zeroRotation, variants -> variants.copy())
                .put(zeroRotation.getClockWise(), variants -> variants.copy().y(90))
                .put(zeroRotation.getOpposite(), variants -> variants.copy().y(180))
                .put(zeroRotation.getCounterClockWise(), variants -> variants.copy().y(270));
    }

    private static StateDispatch<Function<WeightedVariants, WeightedVariants>> attachedFacing(Direction zeroRotationFacing, AttachFace zeroRotationFace) {
        var axis = zeroRotationFacing.getClockWise();

        var rotationOffset = switch (zeroRotationFace) {
            case FLOOR -> 0;
            case WALL -> -90;
            case CEILING -> -180;
        };

        var attachDispatch = StateDispatch.variantsMapper(BlockStateProperties.ATTACH_FACE)
                .put(AttachFace.FLOOR, it -> it.copy().rot(axis, rotationOffset))
                .put(AttachFace.WALL, it -> it.copy().rot(axis, 90 + rotationOffset))
                .put(AttachFace.CEILING, it -> it.copy().rot(axis, 180 + rotationOffset).y(180));

        var facingDispatch = horizontalFacing(zeroRotationFacing);

        return attachDispatch.flatMap(attachMap -> facingDispatch.map(attachMap::andThen));
    }




    // HELPERS

    private void overgrownBlock(Block block, Block base, boolean randomRotations) {
        var mapping = TextureMap.map()
                .put("top", block, "_top")
                .put("side", block, "_side")
                .put("bottom", base);

        var model = ModelTemplates.CUBE_BOTTOM_TOP.create(block, mapping);

        var dispatch = StateDispatch.model(model);

        if (block.getStateDefinition().getProperties().contains(BlockStateProperties.SNOWY) && SNOWY_SIDE_TEXTURES.containsKey(base)) {
            var snowyMapping = mapping.copy()
                    .put("side", SNOWY_SIDE_TEXTURES.get(base));

            var snowyModel = ModelTemplates.CUBE_BOTTOM_TOP.create(block, snowyMapping);

            dispatch = dispatch.flatMap(StateDispatch.dispatch(
                    BlockStateProperties.SNOWY,
                    disp -> disp
                            .put(false, it -> it)
                            .put(true, _ -> snowyModel)
            ));
        }

        var variants = BlockDispatch.variants(block, dispatch.map(WeightedVariants::of));

        if (randomRotations) {
            variants.map(it -> it.flatTransform(RANDOM_ROTATE));
        }

        variants.save(blocks);

        ItemDispatch.of(block, model).save(items);
    }

    private TextureMap createShelfFungusMapping(Block block, String suffix) {
        return TextureMap.map().put(TextureKeys.END, block, suffix);
    }

    private Model createAlignedShelfFungus(Block block, String suffix) {
        return ModModelTemplates.SHELF_FUNGUS.create(block, suffix, createShelfFungusMapping(block, suffix));
    }

    private Model createDiagonalShelfFungus(Block block, String suffix) {
        return ModModelTemplates.SHELF_FUNGUS_DIAGONAL.create(block, "_diagonal" + suffix, createShelfFungusMapping(block, "_diagonal" + suffix));
    }




    // BLOCK FAMILIES

    public interface FamilyBuilder {
        FamilyBuilder slab(Block block, boolean custom);

        FamilyBuilder stairs(Block block);

        FamilyBuilder wall(Block block);

        FamilyBuilder fence(Block block, boolean custom);

        FamilyBuilder fenceGate(Block block, boolean custom);

        FamilyBuilder button(Block block);

        FamilyBuilder pressurePlate(Block block);

        FamilyBuilder door(Block block);

        FamilyBuilder trapdoor(Block block, boolean orientable);

        FamilyBuilder shelf(Block block, Block particleMaterial);

        FamilyBuilder sign(Block block, Block wallBlock);

        FamilyBuilder hangingSign(Block block, Block wallBlock, Block particleMaterial);

        void build();
    }

    private class InternalFamilyBuilder implements FamilyBuilder {
        final Block base;
        final TextureMap map;

        Block slab;
        boolean slabCustom;

        Block stairs;

        Block wall;

        Block fence;
        boolean fenceCustom;

        Block fenceGate;
        boolean fenceGateCustom;

        Block button;
        Block pressurePlate;

        Block door;

        Block trapdoor;
        boolean trapdoorOrientable;

        Block shelf;
        Block shelfParticleMaterial;

        Block sign;
        Block signWall;

        Block hangingSign;
        Block hangingSignWall;
        Block hangingSignParticleMaterial;

        public InternalFamilyBuilder(Block base, TextureMap map) {
            this.base = base;
            this.map = map;
        }

        @Override
        public FamilyBuilder slab(Block block, boolean custom) {
            this.slab = block;
            this.slabCustom = custom;
            return this;
        }

        @Override
        public FamilyBuilder stairs(Block block) {
            this.stairs = block;
            return this;
        }

        @Override
        public FamilyBuilder wall(Block block) {
            this.wall = block;
            return this;
        }

        @Override
        public FamilyBuilder fence(Block block, boolean custom) {
            this.fence = block;
            this.fenceCustom = custom;
            return this;
        }

        @Override
        public FamilyBuilder fenceGate(Block block, boolean custom) {
            this.fenceGate = block;
            this.fenceGateCustom = custom;
            return this;
        }

        @Override
        public FamilyBuilder button(Block block) {
            this.button = block;
            return this;
        }

        @Override
        public FamilyBuilder pressurePlate(Block block) {
            this.pressurePlate = block;
            return this;
        }

        @Override
        public FamilyBuilder door(Block block) {
            this.door = block;
            return this;
        }

        @Override
        public FamilyBuilder trapdoor(Block block, boolean orientable) {
            this.trapdoor = block;
            this.trapdoorOrientable = orientable;
            return this;
        }

        @Override
        public FamilyBuilder shelf(Block block, Block particleMaterial) {
            this.shelf = block;
            this.shelfParticleMaterial = particleMaterial;
            return this;
        }

        @Override
        public FamilyBuilder sign(Block block, Block wallBlock) {
            this.sign = block;
            this.signWall = wallBlock;
            return this;
        }

        @Override
        public FamilyBuilder hangingSign(Block block, Block wallBlock, Block particleMaterial) {
            this.hangingSign = block;
            this.hangingSignWall = wallBlock;
            this.hangingSignParticleMaterial = particleMaterial;
            return this;
        }

        private void ifNotNull(Block block, Consumer<Block> consumer) {
            if (block != null) {
                consumer.accept(block);
            }
        }

        @Override
        public void build() {
            ifNotNull(slab, it -> createSlab(it, base, slabCustom, map));
            ifNotNull(stairs, it -> createStairs(it, map));
            ifNotNull(wall, it -> createWall(it, map));
            ifNotNull(fence, it -> createFence(it, fenceCustom, map));
            ifNotNull(fenceGate, it -> createFenceGate(it, fenceGateCustom, map));
            ifNotNull(button, it -> createButton(it, map));
            ifNotNull(pressurePlate, it -> createPressurePlate(it, map));
            ifNotNull(door, it -> createDoor(it));
            ifNotNull(trapdoor, it -> createTrapdoor(it, trapdoorOrientable));
            ifNotNull(shelf, it -> createShelf(it, shelfParticleMaterial));
            ifNotNull(sign, it -> createSign(it, signWall, base));
            ifNotNull(hangingSign, it -> createSign(it, hangingSignWall, hangingSignParticleMaterial));
        }
    }

    private TextureMap createCustomSlabMap(TextureMap baseMap, Block slabBlock) {
        return baseMap.copy()
                .translate(List.of(TextureKeys.BOTTOM, TextureKeys.TOP, TextureKeys.SIDE))
                .put("side", slabBlock);
    }

    private void createSlab(Block block, Block full, boolean custom, TextureMap baseMap) {
        var bottom = ModelTemplates.SLAB_BOTTOM.create(block, baseMap);
        var top = ModelTemplates.SLAB_TOP.create(block, baseMap);
        var both = custom
                ? ModelTemplates.CUBE_BOTTOM_TOP.create(block, createCustomSlabMap(baseMap, block))
                : Model.reference(full);

        saveDispatchedModels(block, StateDispatch.dispatch(
                SlabBlock.TYPE,
                disp -> disp
                        .put(SlabType.BOTTOM, bottom)
                        .put(SlabType.TOP, top)
                        .put(SlabType.DOUBLE, both)
        ), bottom);
    }

    private void createStairs(Block block, TextureMap baseMap) {
        var straight = ModelTemplates.STAIRS_STRAIGHT.create(block, baseMap);
        var outer = ModelTemplates.STAIRS_OUTER.create(block, baseMap);
        var inner = ModelTemplates.STAIRS_INNER.create(block, baseMap);

        var straightHalves = StateDispatch.variants(StairBlock.HALF)
                .put(Half.BOTTOM, WeightedVariants.of(straight))
                .put(Half.TOP, WeightedVariants.of(straight).x(180));

        var outerRightHalves = StateDispatch.variants(StairBlock.HALF)
                .put(Half.BOTTOM, WeightedVariants.of(outer))
                .put(Half.TOP, WeightedVariants.of(outer).x(180).y(90));

        var innerRightHalves = StateDispatch.variants(StairBlock.HALF)
                .put(Half.BOTTOM, WeightedVariants.of(inner))
                .put(Half.TOP, WeightedVariants.of(inner).x(180).y(90));

        var outerLeftHalves = outerRightHalves.map(v -> v.copy().y(-90));
        var innerLeftHalves = innerRightHalves.map(v -> v.copy().y(-90));

        var combined = StateDispatch
                .valuesOf(StairBlock.SHAPE)
                .flatMap(shape -> switch (shape) {
                    case STRAIGHT -> straightHalves;
                    case INNER_LEFT -> innerLeftHalves;
                    case INNER_RIGHT -> innerRightHalves;
                    case OUTER_LEFT -> outerLeftHalves;
                    case OUTER_RIGHT -> outerRightHalves;
                })
                .flatMap(horizontalFacing(Direction.EAST))
                .map(UV_LOCK);

        saveDispatched(block, combined, straight);
    }

    private void createWall(Block block, TextureMap baseMap) {
        var low = ModelTemplates.WALL_LOW_SIDE.create(block, baseMap);
        var tall = ModelTemplates.WALL_TALL_SIDE.create(block, baseMap);
        var post = ModelTemplates.WALL_POST.create(block, baseMap);
        var item = ModelTemplates.WALL_INVENTORY.create(block, baseMap);

        saveMultipart(
                block,
                multipart -> multipart
                        .part(StateSelector.of(WallBlock.UP, true), post)
                        .part(StateSelector.of(WallBlock.NORTH, WallSide.LOW), low)
                        .part(StateSelector.of(WallBlock.NORTH, WallSide.TALL), tall)
                        .part(StateSelector.of(WallBlock.EAST, WallSide.LOW), low, VariantTransform.ofY(90))
                        .part(StateSelector.of(WallBlock.EAST, WallSide.TALL), tall, VariantTransform.ofY(90))
                        .part(StateSelector.of(WallBlock.SOUTH, WallSide.LOW), low, VariantTransform.ofY(180))
                        .part(StateSelector.of(WallBlock.SOUTH, WallSide.TALL), tall, VariantTransform.ofY(180))
                        .part(StateSelector.of(WallBlock.WEST, WallSide.LOW), low, VariantTransform.ofY(270))
                        .part(StateSelector.of(WallBlock.WEST, WallSide.TALL), tall, VariantTransform.ofY(270))
                        .map(UV_LOCK),
                item
        );
    }

    private void createFence(Block block, boolean custom, TextureMap baseMap) {
        if (custom) {
            createCustomFence(block);
        } else {
            createFence(block, baseMap);
        }
    }

    private void createFence(Block block, TextureMap baseMap) {
        var side = ModelTemplates.FENCE_SIDE.create(block, baseMap);
        var post = ModelTemplates.FENCE_POST.create(block, baseMap);
        var item = ModelTemplates.FENCE_INVENTORY.create(block, baseMap);

        saveMultipart(
                block,
                multipart -> multipart
                        .part(post)
                        .part(StateSelector.of(FenceBlock.NORTH, true), side)
                        .part(StateSelector.of(FenceBlock.EAST, true), side, VariantTransform.ofY(90))
                        .part(StateSelector.of(FenceBlock.SOUTH, true), side, VariantTransform.ofY(180))
                        .part(StateSelector.of(FenceBlock.WEST, true), side, VariantTransform.ofY(270))
                        .map(UV_LOCK),
                item
        );
    }

    private void createCustomFence(Block block) {
        var textureMap = TextureMap.map()
                .put("texture", block)
                .put("particle", block, "_particle");

        var north = ModelTemplates.CUSTOM_FENCE_SIDE_NORTH.create(block, textureMap);
        var east = ModelTemplates.CUSTOM_FENCE_SIDE_EAST.create(block, textureMap);
        var south = ModelTemplates.CUSTOM_FENCE_SIDE_SOUTH.create(block, textureMap);
        var west = ModelTemplates.CUSTOM_FENCE_SIDE_WEST.create(block, textureMap);
        var post = ModelTemplates.CUSTOM_FENCE_POST.create(block, textureMap);
        var item = ModelTemplates.CUSTOM_FENCE_INVENTORY.create(block, textureMap);

        saveMultipart(
                block,
                multipart -> multipart
                        .part(post)
                        .part(StateSelector.of(FenceBlock.NORTH, true), north)
                        .part(StateSelector.of(FenceBlock.EAST, true), east)
                        .part(StateSelector.of(FenceBlock.SOUTH, true), south)
                        .part(StateSelector.of(FenceBlock.WEST, true), west),
                item
        );
    }

    private void createFenceGate(Block block, boolean custom, TextureMap baseMap) {
        if (custom) {
            createCustomFenceGate(block);
        } else {
            createFenceGate(block, baseMap);
        }
    }

    private void createFenceGate(Block block, TextureMap baseMap) {
        createFenceGate(
                block,
                ModelTemplates.FENCE_GATE_CLOSED.create(block, baseMap),
                ModelTemplates.FENCE_GATE_OPEN.create(block, baseMap),
                ModelTemplates.FENCE_GATE_WALL_CLOSED.create(block, baseMap),
                ModelTemplates.FENCE_GATE_WALL_OPEN.create(block, baseMap),
                true
        );
    }

    private void createCustomFenceGate(Block block) {
        var textureMap = TextureMap.map()
                .put("texture", block)
                .put("particle", block, "_particle");

        createFenceGate(
                block,
                ModelTemplates.CUSTOM_FENCE_GATE_CLOSED.create(block, textureMap),
                ModelTemplates.CUSTOM_FENCE_GATE_OPEN.create(block, textureMap),
                ModelTemplates.CUSTOM_FENCE_GATE_WALL_CLOSED.create(block, textureMap),
                ModelTemplates.CUSTOM_FENCE_GATE_WALL_OPEN.create(block, textureMap),
                false
        );
    }

    private void createFenceGate(Block block, Model closed, Model open, Model wallClosed, Model wallOpen, boolean uvlock) {
        saveDispatched(
                block,
                StateDispatch.model(FenceGateBlock.IN_WALL, FenceGateBlock.OPEN)
                        .put(false, false, closed)
                        .put(false, true, open)
                        .put(true, false, wallClosed)
                        .put(true, true, wallOpen)
                        .map(WeightedVariants::of)
                        .flatMap(horizontalFacing(Direction.SOUTH))
                        .map(it -> it.uvlock(uvlock)),
                closed
        );
    }

    private void createButton(Block block, TextureMap baseMap) {
        var off = ModelTemplates.BUTTON.create(block, baseMap);
        var on = ModelTemplates.BUTTON_PRESSED.create(block, baseMap);
        var item = ModelTemplates.BUTTON_INVENTORY.create(block, baseMap);

        saveDispatched(
                block,
                StateDispatch.model(ButtonBlock.POWERED)
                        .put(false, off)
                        .put(true, on)
                        .map(WeightedVariants::of)
                        .flatMap(attachedFacing(Direction.NORTH, AttachFace.FLOOR))
                        .map(UV_LOCK),
                item
        );
    }

    private void createPressurePlate(Block block, TextureMap baseMap) {
        var up = ModelTemplates.PRESSURE_PLATE_UP.create(block, baseMap);
        var down = ModelTemplates.PRESSURE_PLATE_DOWN.create(block, baseMap);

        saveDispatched(
                block,
                StateDispatch.model(PressurePlateBlock.POWERED)
                        .put(false, up)
                        .put(true, down)
                        .map(WeightedVariants::of),
                up
        );
    }

    private void createDoor(Block block) {
        var textureMap = TextureMap.map()
                .put("top", block, "_top")
                .put("bottom", block, "_bottom");

        var bottomLeftClosed = ModelTemplates.DOOR_BOTTOM_LEFT.create(block, textureMap);
        var bottomRightClosed = ModelTemplates.DOOR_BOTTOM_RIGHT.create(block, textureMap);
        var topLeftClosed = ModelTemplates.DOOR_TOP_LEFT.create(block, textureMap);
        var topRightClosed = ModelTemplates.DOOR_TOP_RIGHT.create(block, textureMap);

        var bottomLeftOpen = ModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(block, textureMap);
        var bottomRightOpen = ModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(block, textureMap);
        var topLeftOpen = ModelTemplates.DOOR_TOP_LEFT_OPEN.create(block, textureMap);
        var topRightOpen = ModelTemplates.DOOR_TOP_RIGHT_OPEN.create(block, textureMap);

        saveDispatched(
                block,
                StateDispatch.variants(DoorBlock.HALF, DoorBlock.HINGE, DoorBlock.OPEN)
                        .put(DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, false, WeightedVariants.of(bottomLeftClosed))
                        .put(DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, false, WeightedVariants.of(bottomRightClosed))
                        .put(DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, false, WeightedVariants.of(topLeftClosed))
                        .put(DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, false, WeightedVariants.of(topRightClosed))
                        .put(DoubleBlockHalf.LOWER, DoorHingeSide.LEFT, true, WeightedVariants.of(bottomLeftOpen).y(90))
                        .put(DoubleBlockHalf.LOWER, DoorHingeSide.RIGHT, true, WeightedVariants.of(bottomRightOpen).y(-90))
                        .put(DoubleBlockHalf.UPPER, DoorHingeSide.LEFT, true, WeightedVariants.of(topLeftOpen).y(90))
                        .put(DoubleBlockHalf.UPPER, DoorHingeSide.RIGHT, true, WeightedVariants.of(topRightOpen).y(-90))
                        .flatMap(horizontalFacing(Direction.EAST)),
                Model.generated(block.asItem())
        );
    }

    private void createTrapdoor(Block block, boolean orientable) {
        var textureMap = BlockTextures.texture(block);

        var bottom = orientable
                ? ModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(block, textureMap)
                : ModelTemplates.TRAPDOOR_BOTTOM.create(block, textureMap);

        var top = orientable
                ? ModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(block, textureMap)
                : ModelTemplates.TRAPDOOR_TOP.create(block, textureMap);

        var open = orientable
                ? ModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(block, textureMap)
                : ModelTemplates.TRAPDOOR_OPEN.create(block, textureMap);

        saveDispatched(
                block,
                orientable
                        ? createOrientableTrapdoorDispatch(bottom, top, open)
                        : createNonOrientableTrapdoorDispatch(bottom, top, open),
                bottom
        );
    }

    private StateDispatch<WeightedVariants> createNonOrientableTrapdoorDispatch(Model bottom, Model top, Model open) {
        var closedDispatch = StateDispatch.variants(TrapDoorBlock.HALF)
                .put(Half.BOTTOM, WeightedVariants.of(bottom))
                .put(Half.TOP, WeightedVariants.of(top))
                .withCondition(TrapDoorBlock.OPEN, false);

        var openDispatch = StateDispatch.variants(WeightedVariants.of(open))
                .flatMap(horizontalFacing(Direction.NORTH))
                .withCondition(TrapDoorBlock.OPEN, true);

        return closedDispatch.concat(openDispatch);
    }

    private StateDispatch<WeightedVariants> createOrientableTrapdoorDispatch(Model bottom, Model top, Model open) {
        var closedDispatch = StateDispatch.variants(TrapDoorBlock.HALF)
                .put(Half.BOTTOM, WeightedVariants.of(bottom))
                .put(Half.TOP, WeightedVariants.of(top))
                .flatMap(horizontalFacing(Direction.NORTH))
                .withCondition(TrapDoorBlock.OPEN, false);

        var openDispatch = StateDispatch.variants(TrapDoorBlock.HALF)
                .put(Half.BOTTOM, WeightedVariants.of(open))
                .put(Half.TOP, WeightedVariants.of(open).z(180))
                .flatMap(horizontalFacing(Direction.NORTH))
                .withCondition(TrapDoorBlock.OPEN, true);

        return closedDispatch.concat(openDispatch);
    }

    private void createShelf(Block block, Block particleMaterial) {
        var textureMap = TextureMap.map()
                .put("all", block)
                .put("particle", particleMaterial);

        var body = ModelTemplates.SHELF_BODY.create(block, textureMap);
        var unpowered = ModelTemplates.SHELF_UNPOWERED.create(block, textureMap);
        var unconnected = ModelTemplates.SHELF_UNCONNECTED.create(block, textureMap);
        var left = ModelTemplates.SHELF_LEFT.create(block, textureMap);
        var right = ModelTemplates.SHELF_RIGHT.create(block, textureMap);
        var center = ModelTemplates.SHELF_CENTER.create(block, textureMap);
        var item = ModelTemplates.SHELF_INVENTORY.create(block, textureMap);

        var bodyDispatch = StateDispatch.model(body)
                .map(WeightedVariants::of)
                .flatMap(horizontalFacing(Direction.NORTH));

        var unpoweredDispatch = StateDispatch.model(unpowered)
                .withCondition(ShelfBlock.POWERED, false)
                .map(WeightedVariants::of)
                .flatMap(horizontalFacing(Direction.NORTH));

        var poweredDispatch = StateDispatch.model(ShelfBlock.SIDE_CHAIN_PART)
                .put(SideChainPart.UNCONNECTED, unconnected)
                .put(SideChainPart.LEFT, left)
                .put(SideChainPart.RIGHT, right)
                .put(SideChainPart.CENTER, center)
                .withCondition(ShelfBlock.POWERED, true)
                .map(WeightedVariants::of)
                .flatMap(horizontalFacing(Direction.NORTH));

        saveMultipart(
                block,
                multipart -> multipart
                        .withDispatch(bodyDispatch)
                        .withDispatch(unpoweredDispatch)
                        .withDispatch(poweredDispatch),
                item
        );
    }

    private void createSign(Block block, Block wallBlock, Block particleMaterial) {
        var emptyModel = ModelTemplates.PARTICLE_ONLY.create(block, BlockTextures.particle(particleMaterial));

        BlockDispatch.of(block, emptyModel).save(blocks);
        BlockDispatch.of(wallBlock, emptyModel).save(blocks);
        saveFlatItem(block);
    }




    // PLANTS

    public interface PlantBuilder {
        PlantBuilder basic();

        PlantBuilder tinted(ItemTint tint);

        PlantBuilder tinted(int tint);

        PlantBuilder layered(ItemTint tint);

        PlantBuilder layered(int tint);

        PlantBuilder emissive();

        PlantBuilder glowing();

        PlantBuilder tintedGlowing(ItemTint tint);

        PlantBuilder tintedGlowing(int tint);

        PlantBuilder withGeneratedItem();

        PlantBuilder withHandheldItem();

        PlantBuilder withRodItem();

        void build();
    }

    private static abstract class InternalPlantBuilder implements PlantBuilder {
        final Block block;

        PlantMaterial material = PlantMaterial.BASIC;
        ItemTint[] tints = new ItemTint[0];
        BlockModelTemplate itemModel;

        InternalPlantBuilder(Block block) {
            this.block = block;
        }

        @Override
        public PlantBuilder basic() {
            this.material = PlantMaterial.BASIC;
            this.tints = new ItemTint[0];
            return this;
        }

        @Override
        public PlantBuilder tinted(ItemTint tint) {
            this.material = PlantMaterial.TINTED;
            this.tints = new ItemTint[] { tint };
            return this;
        }

        @Override
        public PlantBuilder tinted(int tint) {
            return tinted(ItemTint.constant(tint));
        }

        @Override
        public PlantBuilder layered(ItemTint tint) {
            this.material = PlantMaterial.LAYERED;
            this.tints = new ItemTint[] { tint };
            return this;
        }

        @Override
        public PlantBuilder layered(int tint) {
            return layered(ItemTint.constant(tint));
        }

        @Override
        public PlantBuilder emissive() {
            this.material = PlantMaterial.EMISSIVE;
            this.tints = new ItemTint[0];
            return this;
        }

        @Override
        public PlantBuilder glowing() {
            this.material = PlantMaterial.GLOWING;
            this.tints = new ItemTint[0];
            return this;
        }

        @Override
        public PlantBuilder tintedGlowing(ItemTint tint) {
            this.material = PlantMaterial.TINTED_GLOWING;
            this.tints = new ItemTint[] { tint };
            return this;
        }

        @Override
        public PlantBuilder tintedGlowing(int tint) {
            return tintedGlowing(ItemTint.constant(tint));
        }

        @Override
        public PlantBuilder withGeneratedItem() {
            itemModel = ModelTemplates.FLAT_ITEM.blockItem(
                    block -> TextureMap.layers(Texture.of(block.asItem()))
            );
            return this;
        }

        @Override
        public PlantBuilder withHandheldItem() {
            itemModel = ModelTemplates.FLAT_HANDHELD_ITEM.blockItem(
                    block -> TextureMap.layers(Texture.of(block.asItem()))
            );
            return this;
        }

        @Override
        public PlantBuilder withRodItem() {
            itemModel = ModelTemplates.FLAT_HANDHELD_ROD_ITEM.blockItem(
                    block -> TextureMap.layers(Texture.of(block.asItem()))
            );
            return this;
        }

        BlockModelTemplate getPlantItemTemplate(String textureSuffix) {
            if (itemModel != null) {
                return itemModel;
            }

            return switch (material) {
                case BASIC, TINTED, GLOWING, TINTED_GLOWING -> ModelTemplates.FLAT_ITEM.blockItem(
                        block -> TextureMap.layers(Texture.of(block, textureSuffix))
                );

                case LAYERED -> ModelTemplates.TWO_LAYERED_ITEM.blockItem(
                        block -> TextureMap.layers(
                                Texture.of(block, textureSuffix),
                                Texture.of(block, textureSuffix + "_overlay")
                        )
                );

                case EMISSIVE -> ModelTemplates.TWO_LAYERED_ITEM.blockItem(
                        block -> TextureMap.layers(
                                Texture.of(block, textureSuffix),
                                Texture.of(block, textureSuffix + "_emissive")
                        )
                );
            };
        }

        Model createPlantItemModel(String textureSuffix) {
            return getPlantItemTemplate(textureSuffix).create(block);
        }
    }

    private static class BasicPlantBuilder extends InternalPlantBuilder {
        final Consumer<BasicPlantBuilder> build;

        BasicPlantBuilder(Block block, Consumer<BasicPlantBuilder> build) {
            super(block);
            this.build = build;
        }

        WeightedVariants createFlat(String modelSuffix, String textureSuffix) {
            var template = switch (material) {
                case BASIC -> ModModelTemplates.FLAT.block(block -> BlockTextures.texture(block, textureSuffix));
                case TINTED -> ModModelTemplates.TINTED_FLAT.block(block -> BlockTextures.texture(block, textureSuffix));
                case LAYERED -> ModModelTemplates.LAYERED_FLAT.block(block -> BlockTextures.textureLayered(block, textureSuffix));
                case EMISSIVE -> ModModelTemplates.EMISSIVE_FLAT.block(block -> BlockTextures.textureEmissive(block, textureSuffix));
                case GLOWING -> ModModelTemplates.GLOWING_FLAT.block(block -> BlockTextures.texture(block, textureSuffix));
                case TINTED_GLOWING -> ModModelTemplates.TINTED_GLOWING_FLAT.block(block -> BlockTextures.texture(block, textureSuffix));
            };

            return WeightedVariants.of(template.create(block, modelSuffix));
        }

        WeightedVariants createFlowerbed1(String modelSuffix, String textureSuffix) {
            var template = switch (material) {
                case BASIC -> ModModelTemplates.FLOWERBED_1.block(block -> BlockTextures.flowerbed(block, textureSuffix));
                case TINTED -> ModModelTemplates.TINTED_FLOWERBED_1.block(block -> BlockTextures.flowerbed(block, textureSuffix));
                case LAYERED -> ModModelTemplates.LAYERED_FLOWERBED_1.block(block -> BlockTextures.flowerbedLayered(block, textureSuffix));
                case EMISSIVE -> ModModelTemplates.EMISSIVE_FLOWERBED_1.block(block -> BlockTextures.flowerbedEmissive(block, textureSuffix));
                case GLOWING -> ModModelTemplates.GLOWING_FLOWERBED_1.block(block -> BlockTextures.flowerbed(block, textureSuffix));
                case TINTED_GLOWING -> ModModelTemplates.TINTED_GLOWING_FLOWERBED_1.block(block -> BlockTextures.flowerbed(block, textureSuffix));
            };

            return WeightedVariants.of(template.create(block, modelSuffix));
        }

        WeightedVariants createFlowerbed2(String modelSuffix, String textureSuffix) {
            var template = switch (material) {
                case BASIC -> ModModelTemplates.FLOWERBED_2.block(block -> BlockTextures.flowerbed(block, textureSuffix));
                case TINTED -> ModModelTemplates.TINTED_FLOWERBED_2.block(block -> BlockTextures.flowerbed(block, textureSuffix));
                case LAYERED -> ModModelTemplates.LAYERED_FLOWERBED_2.block(block -> BlockTextures.flowerbedLayered(block, textureSuffix));
                case EMISSIVE -> ModModelTemplates.EMISSIVE_FLOWERBED_2.block(block -> BlockTextures.flowerbedEmissive(block, textureSuffix));
                case GLOWING -> ModModelTemplates.GLOWING_FLOWERBED_2.block(block -> BlockTextures.flowerbed(block, textureSuffix));
                case TINTED_GLOWING -> ModModelTemplates.TINTED_GLOWING_FLOWERBED_2.block(block -> BlockTextures.flowerbed(block, textureSuffix));
            };

            return WeightedVariants.of(template.create(block, modelSuffix));
        }

        WeightedVariants createFlowerbed3(String modelSuffix, String textureSuffix) {
            var template = switch (material) {
                case BASIC -> ModModelTemplates.FLOWERBED_3.block(block -> BlockTextures.flowerbed(block, textureSuffix));
                case TINTED -> ModModelTemplates.TINTED_FLOWERBED_3.block(block -> BlockTextures.flowerbed(block, textureSuffix));
                case LAYERED -> ModModelTemplates.LAYERED_FLOWERBED_3.block(block -> BlockTextures.flowerbedLayered(block, textureSuffix));
                case EMISSIVE -> ModModelTemplates.EMISSIVE_FLOWERBED_3.block(block -> BlockTextures.flowerbedEmissive(block, textureSuffix));
                case GLOWING -> ModModelTemplates.GLOWING_FLOWERBED_3.block(block -> BlockTextures.flowerbed(block, textureSuffix));
                case TINTED_GLOWING -> ModModelTemplates.TINTED_GLOWING_FLOWERBED_3.block(block -> BlockTextures.flowerbed(block, textureSuffix));
            };

            return WeightedVariants.of(template.create(block, modelSuffix));
        }

        WeightedVariants createFlowerbed4(String modelSuffix, String textureSuffix) {
            var template = switch (material) {
                case BASIC -> ModModelTemplates.FLOWERBED_4.block(block -> BlockTextures.flowerbed(block, textureSuffix));
                case TINTED -> ModModelTemplates.TINTED_FLOWERBED_4.block(block -> BlockTextures.flowerbed(block, textureSuffix));
                case LAYERED -> ModModelTemplates.LAYERED_FLOWERBED_4.block(block -> BlockTextures.flowerbedLayered(block, textureSuffix));
                case EMISSIVE -> ModModelTemplates.EMISSIVE_FLOWERBED_4.block(block -> BlockTextures.flowerbedEmissive(block, textureSuffix));
                case GLOWING -> ModModelTemplates.GLOWING_FLOWERBED_4.block(block -> BlockTextures.flowerbed(block, textureSuffix));
                case TINTED_GLOWING -> ModModelTemplates.TINTED_GLOWING_FLOWERBED_4.block(block -> BlockTextures.flowerbed(block, textureSuffix));
            };

            return WeightedVariants.of(template.create(block, modelSuffix));
        }

        WeightedVariants createFlatSegment1(String modelSuffix, String textureSuffix) {
            var template = switch (material) {
                case BASIC -> ModModelTemplates.FLAT_SEGMENT_1.block(block -> BlockTextures.texture(block, textureSuffix));
                case TINTED -> ModModelTemplates.TINTED_FLAT_SEGMENT_1.block(block -> BlockTextures.texture(block, textureSuffix));
                case LAYERED -> ModModelTemplates.LAYERED_FLAT_SEGMENT_1.block(block -> BlockTextures.textureLayered(block, textureSuffix));
                case EMISSIVE -> ModModelTemplates.EMISSIVE_FLAT_SEGMENT_1.block(block -> BlockTextures.textureEmissive(block, textureSuffix));
                case GLOWING -> ModModelTemplates.GLOWING_FLAT_SEGMENT_1.block(block -> BlockTextures.texture(block, textureSuffix));
                case TINTED_GLOWING -> ModModelTemplates.TINTED_GLOWING_FLAT_SEGMENT_1.block(block -> BlockTextures.texture(block, textureSuffix));
            };

            return WeightedVariants.of(template.create(block, modelSuffix));
        }

        WeightedVariants createFlatSegment2(String modelSuffix, String textureSuffix) {
            var template = switch (material) {
                case BASIC -> ModModelTemplates.FLAT_SEGMENT_2.block(block -> BlockTextures.texture(block, textureSuffix));
                case TINTED -> ModModelTemplates.TINTED_FLAT_SEGMENT_2.block(block -> BlockTextures.texture(block, textureSuffix));
                case LAYERED -> ModModelTemplates.LAYERED_FLAT_SEGMENT_2.block(block -> BlockTextures.textureLayered(block, textureSuffix));
                case EMISSIVE -> ModModelTemplates.EMISSIVE_FLAT_SEGMENT_2.block(block -> BlockTextures.textureEmissive(block, textureSuffix));
                case GLOWING -> ModModelTemplates.GLOWING_FLAT_SEGMENT_2.block(block -> BlockTextures.texture(block, textureSuffix));
                case TINTED_GLOWING -> ModModelTemplates.TINTED_GLOWING_FLAT_SEGMENT_2.block(block -> BlockTextures.texture(block, textureSuffix));
            };

            return WeightedVariants.of(template.create(block, modelSuffix));
        }

        WeightedVariants createFlatSegment3(String modelSuffix, String textureSuffix) {
            var template = switch (material) {
                case BASIC -> ModModelTemplates.FLAT_SEGMENT_3.block(block -> BlockTextures.texture(block, textureSuffix));
                case TINTED -> ModModelTemplates.TINTED_FLAT_SEGMENT_3.block(block -> BlockTextures.texture(block, textureSuffix));
                case LAYERED -> ModModelTemplates.LAYERED_FLAT_SEGMENT_3.block(block -> BlockTextures.textureLayered(block, textureSuffix));
                case EMISSIVE -> ModModelTemplates.EMISSIVE_FLAT_SEGMENT_3.block(block -> BlockTextures.textureEmissive(block, textureSuffix));
                case GLOWING -> ModModelTemplates.GLOWING_FLAT_SEGMENT_3.block(block -> BlockTextures.texture(block, textureSuffix));
                case TINTED_GLOWING -> ModModelTemplates.TINTED_GLOWING_FLAT_SEGMENT_3.block(block -> BlockTextures.texture(block, textureSuffix));
            };

            return WeightedVariants.of(template.create(block, modelSuffix));
        }

        WeightedVariants createFlatSegment4(String modelSuffix, String textureSuffix) {
            var template = switch (material) {
                case BASIC -> ModModelTemplates.FLAT_SEGMENT_4.block(block -> BlockTextures.texture(block, textureSuffix));
                case TINTED -> ModModelTemplates.TINTED_FLAT_SEGMENT_4.block(block -> BlockTextures.texture(block, textureSuffix));
                case LAYERED -> ModModelTemplates.LAYERED_FLAT_SEGMENT_4.block(block -> BlockTextures.textureLayered(block, textureSuffix));
                case EMISSIVE -> ModModelTemplates.EMISSIVE_FLAT_SEGMENT_4.block(block -> BlockTextures.textureEmissive(block, textureSuffix));
                case GLOWING -> ModModelTemplates.GLOWING_FLAT_SEGMENT_4.block(block -> BlockTextures.texture(block, textureSuffix));
                case TINTED_GLOWING -> ModModelTemplates.TINTED_GLOWING_FLAT_SEGMENT_4.block(block -> BlockTextures.texture(block, textureSuffix));
            };

            return WeightedVariants.of(template.create(block, modelSuffix));
        }

        @Override
        public void build() {
            build.accept(this);
        }
    }

    private static class ShapedPlantBuilder extends InternalPlantBuilder {
        private final PlantShape shape;
        private final Consumer<ShapedPlantBuilder> build;

        public ShapedPlantBuilder(Block block, PlantShape shape, Consumer<ShapedPlantBuilder> build) {
            super(block);
            this.shape = shape;
            this.build = build;
        }

        BlockModelTemplate getPlantBlockTemplate(String textureSuffix) {
            return switch (material) {
                case BASIC -> switch (shape) {
                    case CROSS -> ModelTemplates.CROSS.block(block -> BlockTextures.cross(block, textureSuffix));
                };

                case TINTED -> switch (shape) {
                    case CROSS -> ModelTemplates.TINTED_CROSS.block(block -> BlockTextures.cross(block, textureSuffix));
                };

                case LAYERED -> switch (shape) {
                    case CROSS -> ModModelTemplates.LAYERED_CROSS.block(block -> BlockTextures.crossLayered(block, textureSuffix));
                };

                case EMISSIVE -> switch (shape) {
                    case CROSS -> ModelTemplates.CROSS_EMISSIVE.block(block -> BlockTextures.crossEmissive(block, textureSuffix));
                };

                case GLOWING -> switch (shape) {
                    case CROSS -> ModModelTemplates.GLOWING_CROSS.block(block -> BlockTextures.cross(block, textureSuffix));
                };

                case TINTED_GLOWING -> switch (shape) {
                    case CROSS -> ModModelTemplates.TINTED_GLOWING_CROSS.block(block -> BlockTextures.cross(block, textureSuffix));
                };
            };
        }

        WeightedVariants createPlantBlockModel(String modelSuffix, String textureSuffix) {
            return WeightedVariants.of(getPlantBlockTemplate(textureSuffix).create(block, modelSuffix));
        }

        @Override
        public void build() {
            build.accept(this);
        }
    }

    private void plant(ShapedPlantBuilder builder) {
        var plant = builder.createPlantBlockModel("", "");
        var item = builder.createPlantItemModel("");

        saveStationary(builder.block, plant, item, builder.tints);
    }

    private void tallPlant(ShapedPlantBuilder builder) {
        var bottom = builder.createPlantBlockModel("_bottom", "_bottom");
        var top = builder.createPlantBlockModel("_top", "_top");
        var item = builder.createPlantItemModel("_top");

        saveDispatched(builder.block, StateDispatch.dispatch(
                DoublePlantBlock.HALF,
                disp -> disp
                        .put(DoubleBlockHalf.LOWER, bottom)
                        .put(DoubleBlockHalf.UPPER, top)
        ), item, builder.tints);
    }

    private void columnPlant(ShapedPlantBuilder builder) {
        var body = builder.createPlantBlockModel("", "");
        var end = builder.createPlantBlockModel("_end", "_end");
        var item = builder.createPlantItemModel("_end");

        saveDispatched(builder.block, StateDispatch.dispatch(
                AbstractColumnPlantBlock.SHAPE,
                disp -> disp
                        .put(ColumnPlantShape.BODY, body)
                        .put(ColumnPlantShape.GROWING, end)
                        .put(ColumnPlantShape.PERMANENT, end)
        ), item, builder.tints);
    }

    private void columnPlantWithBase(ShapedPlantBuilder builder) {
        var single = builder.createPlantBlockModel("_base_end", "_base_end");
        var body = builder.createPlantBlockModel("", "");
        var base = builder.createPlantBlockModel("_base", "_base");
        var end = builder.createPlantBlockModel("_end", "_end");
        var item = builder.createPlantItemModel("_base_end");

        saveDispatched(builder.block, StateDispatch.dispatch(
                AbstractColumnPlantBlock.BASE,
                AbstractColumnPlantBlock.SHAPE,
                disp -> disp
                        .put(false, ColumnPlantShape.BODY, body)
                        .put(false, ColumnPlantShape.GROWING, end)
                        .put(false, ColumnPlantShape.PERMANENT, end)
                        .put(true, ColumnPlantShape.BODY, base)
                        .put(true, ColumnPlantShape.GROWING, single)
                        .put(true, ColumnPlantShape.PERMANENT, single)
        ), item, builder.tints);
    }

    private void flatPlant(BasicPlantBuilder builder) {
        var plant = builder.createFlat("", "");
        var item = builder.createPlantItemModel("");

        saveStationary(builder.block, plant, item, builder.tints);
    }

    private void multifacePlant(BasicPlantBuilder builder) {
        var plant = builder.createFlat("", "");
        var item = builder.createPlantItemModel("");

        saveMultipart(builder.block, multipart -> {
            MULTIFACE_TRANSFORMS.forEach((dir, xform) -> {
                var selector = StatePredicate.of(MultifaceBlock.getFaceProperty(dir), true);
                multipart.part(selector, plant.copy().transform(xform));
            });
        }, item, builder.tints);
    }

    private void segmentedBlock(
            Block block,
            BaseSelector condition1, WeightedVariants model1,
            BaseSelector condition2, WeightedVariants model2,
            BaseSelector condition3, WeightedVariants model3,
            BaseSelector condition4, WeightedVariants model4,
            Model itemModel,
            ItemTint... tints
    ) {
        var north = StateSelector.of(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH);
        var east = StateSelector.of(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST);
        var south = StateSelector.of(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH);
        var west = StateSelector.of(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST);

        saveMultipart(
                block,
                multipart -> multipart
                        .part(north.and(condition1), model1)
                        .part(north.and(condition2), model2)
                        .part(north.and(condition3), model3)
                        .part(north.and(condition4), model4)
                        .part(east.and(condition1), model1.copy().transform(VariantTransform.ofY(90)))
                        .part(east.and(condition2), model2.copy().transform(VariantTransform.ofY(90)))
                        .part(east.and(condition3), model3.copy().transform(VariantTransform.ofY(90)))
                        .part(east.and(condition4), model4.copy().transform(VariantTransform.ofY(90)))
                        .part(south.and(condition1), model1.copy().transform(VariantTransform.ofY(180)))
                        .part(south.and(condition2), model2.copy().transform(VariantTransform.ofY(180)))
                        .part(south.and(condition3), model3.copy().transform(VariantTransform.ofY(180)))
                        .part(south.and(condition4), model4.copy().transform(VariantTransform.ofY(180)))
                        .part(west.and(condition1), model1.copy().transform(VariantTransform.ofY(270)))
                        .part(west.and(condition2), model2.copy().transform(VariantTransform.ofY(270)))
                        .part(west.and(condition3), model3.copy().transform(VariantTransform.ofY(270)))
                        .part(west.and(condition4), model4.copy().transform(VariantTransform.ofY(270))),
                itemModel, tints
        );
    }

    private void flowerbedPlant(BasicPlantBuilder builder) {
        var part1 = builder.createFlowerbed1("", "");
        var part2 = builder.createFlowerbed2("", "");
        var part3 = builder.createFlowerbed3("", "");
        var part4 = builder.createFlowerbed4("", "");
        var item = builder.createPlantItemModel("");

        if (!(builder.block instanceof SegmentableBlock sb)) {
            throw new IllegalArgumentException("Block must be SegmentableBlock");
        }

        var amount = sb.getSegmentAmountProperty();

        segmentedBlock(
                builder.block,
                StateSelector.EMPTY, part1,
                StateSelector.of(amount, 2, 3, 4), part2,
                StateSelector.of(amount, 3, 4), part3,
                StateSelector.of(amount, 4), part4,
                item, builder.tints
        );
    }

    private void flatSegmentedPlant(BasicPlantBuilder builder) {
        var part1 = builder.createFlatSegment1("", "");
        var part2 = builder.createFlatSegment2("", "");
        var part3 = builder.createFlatSegment3("", "");
        var part4 = builder.createFlatSegment4("", "");
        var item = builder.createPlantItemModel("");

        if (!(builder.block instanceof SegmentableBlock sb)) {
            throw new IllegalArgumentException("Block must be SegmentableBlock");
        }

        var amount = sb.getSegmentAmountProperty();

        segmentedBlock(
                builder.block,
                StateSelector.of(amount, 1), part1,
                StateSelector.of(amount, 2, 3), part2, // Part 3 is added on top of part 2
                StateSelector.of(amount, 3), part3,
                StateSelector.of(amount, 4), part4,
                item, builder.tints
        );
    }




    // SAVE

    private void saveMultipart(Block block, Consumer<MultipartBlockDispatch> config, Model itemModel, ItemTint... tints) {
        BlockDispatch.multipart(block).apply(config).save(blocks);
        saveItem(block, itemModel, tints);
    }

    private void saveMultipart(Block block, MultipartBlockDispatch dispatch, Model itemModel, ItemTint... tints) {
        dispatch.save(blocks);
        saveItem(block, itemModel, tints);
    }

    private void saveDispatched(Block block, StateDispatch<WeightedVariants> dispatch, Model itemModel, ItemTint... tints) {
        BlockDispatch.variants(block, dispatch).save(blocks);
        saveItem(block, itemModel, tints);
    }

    private void saveDispatchedModels(Block block, StateDispatch<Model> dispatch, Model itemModel, ItemTint... tints) {
        BlockDispatch.variants(block, dispatch.map(WeightedVariants::of)).save(blocks);
        saveItem(block, itemModel, tints);
    }

    private void saveDispatchedVariants(Block block, StateDispatch<Variant> dispatch, Model itemModel, ItemTint... tints) {
        BlockDispatch.variants(block, dispatch.map(WeightedVariants::of)).save(blocks);
        saveItem(block, itemModel, tints);
    }

    private void saveStationary(Block block, WeightedVariants variants, Model itemModel, ItemTint... tints) {
        BlockDispatch.of(block, variants).save(blocks);
        saveItem(block, itemModel, tints);
    }

    private void saveStationary(Block block, Model blockModel, Model itemModel, ItemTint... tints) {
        saveStationary(block, WeightedVariants.of(blockModel), itemModel, tints);
    }

    private void saveStationary(Block block, Model sharedModel, ItemTint... tints) {
        saveStationary(block, sharedModel, sharedModel, tints);
    }

    private void saveFlatItem(Block block, ItemTint... tints) {
        var model = Model.generated(block.asItem());
        saveItem(block, model, tints);
    }

    private void saveHandheldItem(Block block, ItemTint... tints) {
        var model = Model.handheld(block.asItem());
        saveItem(block, model, tints);
    }

    private void saveRodItem(Block block, ItemTint... tints) {
        var model = Model.rod(block.asItem());
        saveItem(block, model, tints);
    }

    private void saveItem(Block block, Model model, ItemTint... tints) {
        ItemDispatch.of(block, ItemModel.model(model).withTint(tints)).save(items);
    }

    private void saveRotatedPillar(Block block, WeightedVariants x, WeightedVariants y, WeightedVariants z, Model itemModel, ItemTint... tints) {
        saveDispatched(block, StateDispatch.dispatch(
                RotatedPillarBlock.AXIS,
                disp -> disp
                        .put(Direction.Axis.X, x)
                        .put(Direction.Axis.Y, y)
                        .put(Direction.Axis.Z, z)
        ), itemModel, tints);
    }
}
