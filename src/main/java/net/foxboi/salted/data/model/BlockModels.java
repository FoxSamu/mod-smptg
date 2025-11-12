package net.foxboi.salted.data.model;

import net.foxboi.salted.common.util.ItemTint;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

/**
 * A server-safe interface to which {@link net.foxboi.salted.common.block.ModBlocks} supplies block model data to the
 * data generator.
 */
public interface BlockModels {
    /**
     * Creates a model provider for block families.
     */
    FamilyModels family(Block base);

    /**
     * Creates a model provider for log sets.
     */
    WoodModels wood(Block base);

    /**
     * Creates a simple cube block with the same texture on all sides.
     */
    void cube(Block block);

    /**
     * Creates a simple cube block with the same texture on all sides, that is randomly rotated (like dirt).
     */
    void randomlyRotatedCube(Block block);

    /**
     * Creates a simple cube block with the same texture on all sides, that is randomly mirrored (like stone).
     */
    void randomlyMirroredCube(Block block);

    /**
     * Creates an untinted cross plant.
     */
    void crossPlant(Block block);

    /**
     * Creates a tinted cross plant.
     */
    void tintedCrossPlant(Block block, ItemTint tint);

    /**
     * Creates a tinted cross plant.
     */
    default void tintedCrossPlant(Block block, int tint) {
        tintedCrossPlant(block, ItemTint.constant(tint));
    }

    /**
     * Creates a tinted cross plant.
     */
    default void tintedCrossPlant(Block block, ResourceLocation tint) {
        tintedCrossPlant(block, ItemTint.biomeColor(tint));
    }

    /**
     * Creates a tinted cross plant with an untinted overlay.
     */
    void layeredCrossPlant(Block block, ItemTint tint);

    /**
     * Creates a tinted cross plant with an untinted overlay.
     */
    default void layeredCrossPlant(Block block, int tint) {
        layeredCrossPlant(block, ItemTint.constant(tint));
    }

    /**
     * Creates a tinted cross plant with an untinted overlay.
     */
    default void layeredCrossPlant(Block block, ResourceLocation tint) {
        layeredCrossPlant(block, ItemTint.biomeColor(tint));
    }

    /**
     * Creates an untinted cross plant with an emissive overlay.
     */
    void emissiveCrossPlant(Block block);

    /**
     * Creates an untinted double-block cross plant.
     */
    void tallCrossPlant(Block block);

    /**
     * Creates a tinted double-block cross plant.
     */
    void tallTintedCrossPlant(Block block, ItemTint tint);

    /**
     * Creates a tinted double-block cross plant.
     */
    default void tallTintedCrossPlant(Block block, int tint) {
        tallTintedCrossPlant(block, ItemTint.constant(tint));
    }

    /**
     * Creates a tinted double-block cross plant.
     */
    default void tallTintedCrossPlant(Block block, ResourceLocation tint) {
        tallTintedCrossPlant(block, ItemTint.biomeColor(tint));
    }

    /**
     * Creates a tinted double-block cross plant with untinted overlay.
     */
    void tallLayeredCrossPlant(Block block, ItemTint tint);

    /**
     * Creates a tinted double-block cross plant with untinted overlay.
     */
    default void tallLayeredCrossPlant(Block block, int tint) {
        tallLayeredCrossPlant(block, ItemTint.constant(tint));
    }

    /**
     * Creates a tinted double-block cross plant with untinted overlay.
     */
    default void tallLayeredCrossPlant(Block block, ResourceLocation tint) {
        tallLayeredCrossPlant(block, ItemTint.biomeColor(tint));
    }

    /**
     * Creates an untinted double-block cross plant with emissive overlay.
     */
    void tallEmissiveCrossPlant(Block block);

    /**
     * Creates an untinted column cross plant.
     */
    void columnCrossPlant(Block block);

    /**
     * Creates a tinted column cross plant.
     */
    void columnTintedCrossPlant(Block block, ItemTint tint);

    /**
     * Creates a tinted column cross plant.
     */
    default void columnTintedCrossPlant(Block block, int tint) {
        columnTintedCrossPlant(block, ItemTint.constant(tint));
    }

    /**
     * Creates a tinted column cross plant.
     */
    default void columnTintedCrossPlant(Block block, ResourceLocation tint) {
        columnTintedCrossPlant(block, ItemTint.biomeColor(tint));
    }

    /**
     * Creates a tinted column cross plant with untinted overlay.
     */
    void columnLayeredCrossPlant(Block block, ItemTint tint);

    /**
     * Creates a tinted column cross plant with untinted overlay.
     */
    default void columnLayeredCrossPlant(Block block, int tint) {
        columnLayeredCrossPlant(block, ItemTint.constant(tint));
    }

    /**
     * Creates a tinted column cross plant with untinted overlay.
     */
    default void columnLayeredCrossPlant(Block block, ResourceLocation tint) {
        columnLayeredCrossPlant(block, ItemTint.biomeColor(tint));
    }

    /**
     * Creates an untinted column cross plant with emissive overlay.
     */
    void columnEmissiveCrossPlant(Block block);

    /**
     * Creates an untinted flower bed.
     */
    void flowerBed(Block block);

    /**
     * Creates a tinted flower bed.
     */
    void tintedFlowerBed(Block block, ItemTint tint);

    /**
     * Creates a tinted flower bed.
     */
    default void tintedFlowerBed(Block block, int tint) {
        tintedFlowerBed(block, ItemTint.constant(tint));
    }

    /**
     * Creates a tinted flower bed.
     */
    default void tintedFlowerBed(Block block, ResourceLocation tint) {
        tintedFlowerBed(block, ItemTint.biomeColor(tint));
    }

    /**
     * Creates an untinted multiface block.
     */
    void multiface(Block block);

    /**
     * Creates a tinted multiface block.
     */
    void tintedMultiface(Block block, ItemTint tint);

    /**
     * Creates a tinted multiface block.
     */
    default void tintedMultiface(Block block, int tint) {
        tintedMultiface(block, ItemTint.constant(tint));
    }

    /**
     * Creates a tinted multiface block.
     */
    default void tintedMultiface(Block block, ResourceLocation tint) {
        tintedMultiface(block, ItemTint.biomeColor(tint));
    }

    /**
     * Creates an untinted leaves model.
     */
    void leaves(Block block);

    /**
     * Creates a tinted leaves model.
     */
    void tintedLeaves(Block block, ItemTint tint);

    /**
     * Creates a tinted leaves model.
     */
    default void tintedLeaves(Block block, int tint) {
        tintedLeaves(block, ItemTint.constant(tint));
    }

    /**
     * Creates a tinted leaves model.
     */
    default void tintedLeaves(Block block, ResourceLocation tint) {
        tintedLeaves(block, ItemTint.biomeColor(tint));
    }

    /**
     * Creates a tinted leaves model using the maple leaves texture.
     */
    void mapleLeaves(Block block, ItemTint tint);

    /**
     * Creates a tinted leaves model using the maple leaves texture.
     */
    default void mapleLeaves(Block block, int tint) {
        mapleLeaves(block, ItemTint.constant(tint));
    }

    /**
     * Creates a tinted leaves model using the maple leaves texture.
     */
    default void mapleLeaves(Block block, ResourceLocation tint) {
        mapleLeaves(block, ItemTint.biomeColor(tint));
    }

    /**
     * Creates an untinted covered block model (like grass blocks and podzol).
     */
    void coveredBlock(Block block, Block base);

    /**
     * Creates an untinted covered block model with random rotations.
     */
    void randomlyRotatedCoveredBlock(Block block, Block base);

    /**
     * Creates a multilayer model.
     */
    void multilayer(Block block, Block fullVariant);

    /**
     * Creates a shelf fungus.
     */
    void shelfFungus(Block block);

    /**
     * Creates a salt crystal model.
     */
    void saltCrystal(Block block);
}
