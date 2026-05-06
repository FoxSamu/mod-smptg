package net.foxboi.salted.common.block;

import java.util.function.BiFunction;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class PlantConfig {
    public static final Codec<PlantConfig> CODEC = MapCodec.unit(new PlantConfig()).codec();

    private CanGrowOn canGrowOn = GROW_DEFAULT;
    private double width = 16;
    private double height = 16;
    private boolean offsetShape = false;
    private BonemealableBlock bonemealBehavior = BonemealBehaviors.nothing();
    private boolean burning = false;

    public CanGrowOn canGrowOn() {
        return canGrowOn;
    }

    public boolean canGrowOn(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
        return canGrowOn.canGrowOn(state, level, pos, face);
    }

    public PlantConfig canGrowOn(CanGrowOn canGrowOn) {
        this.canGrowOn = canGrowOn;
        return this;
    }

    public PlantConfig canGrowOn(TagKey<Block> tag) {
        this.canGrowOn = (state, _, _, _) -> state.is(tag);
        return this;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }

    public VoxelShape upShape() {
        return Block.column(width, 0, height);
    }

    public VoxelShape downShape() {
        return Block.column(width, 16 - height, 16);
    }

    public VoxelShape columnShape() {
        return Block.column(width, 0, 16);
    }

    public PlantConfig size(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public boolean hasShapeOffset() {
        return offsetShape;
    }

    public PlantConfig offsetShape() {
        this.offsetShape = true;
        return this;
    }

    public PlantConfig offsetShape(boolean offsetShape) {
        this.offsetShape = offsetShape;
        return this;
    }

    public boolean isBurning() {
        return burning;
    }

    public PlantConfig burning() {
        this.burning = true;
        return this;
    }

    public PlantConfig burning(boolean burning) {
        this.burning = burning;
        return this;
    }

    public BonemealableBlock bonemealBehavior() {
        return bonemealBehavior;
    }

    public PlantConfig bonemealBehavior(BonemealableBlock bonemealBehavior) {
        this.bonemealBehavior = bonemealBehavior;
        return this;
    }

    public static <B extends Block & PlantBlock> MapCodec<B> plantBlockCodec(BiFunction<PlantConfig, BlockBehaviour.Properties, B> factory) {
        return RecordCodecBuilder.mapCodec(inst -> inst.group(
                PlantConfig.CODEC.fieldOf("plant_config").forGetter(PlantBlock::getPlantConfig),
                BlockBehaviour.Properties.CODEC.fieldOf("properties").forGetter(Block::properties)
        ).apply(inst, factory));
    }

    public interface CanGrowOn {
        boolean canGrowOn(BlockState state, BlockGetter level, BlockPos pos, Direction face);
    }

    private static boolean isDirt(BlockState state, Direction face) {
        return state.is(BlockTags.DIRT)
                || state.is(Blocks.FARMLAND)
                || state.is(Blocks.DIRT_PATH) && face == Direction.DOWN;
    }

    public static final CanGrowOn GROW_ON_STURDY_FACE =
            BlockState::isFaceSturdy;

    public static final CanGrowOn GROW_ON_FULL_FACE =
            (state, level, pos, dir) -> Block.isFaceFull(state.getBlockSupportShape(level, pos), dir) || Block.isFaceFull(state.getCollisionShape(level, pos), dir);

    public static final CanGrowOn GROW_DEFAULT =
            (state, _, _, _) -> state.is(BlockTags.SUPPORTS_VEGETATION);

    public static PlantConfig of() {
        return new PlantConfig();
    }

    public static PlantConfig of(PlantConfig config) {
        return new PlantConfig()
                .canGrowOn(config.canGrowOn)
                .size(config.width, config.height)
                .offsetShape(config.offsetShape)
                .bonemealBehavior(config.bonemealBehavior);
    }

    public static PlantConfig of(Block block) {
        if (block instanceof PlantBlock plant) {
            return of(plant.getPlantConfig());
        } else {
            return of();
        }
    }

    public static PlantConfig defaultMultiface() {
        return PlantConfig.of()
                .size(16, 1)
                .canGrowOn(GROW_ON_FULL_FACE)
                .bonemealBehavior(BonemealBehaviors.spreadMultifaceBlock());
    }
}
