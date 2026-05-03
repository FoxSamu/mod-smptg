package net.foxboi.salted.common.levelgen;

import net.foxboi.salted.common.levelgen.placement.DefinedPlacement;
import net.foxboi.salted.common.misc.data.DataRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.Fluids;

import static net.foxboi.salted.common.levelgen.FeatureConditions.*;
import static net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.*;

public record ModVegetationPlacements() {
    private static final DataRegistry<PlacedFeature> REGISTRY = DataRegistry.of(Registries.PLACED_FEATURE);

    private static final DefinedPlacement.Modifier TREE_THRESHOLD = it -> it.atMaxDepth(0);


    // Trees
    // ===============================================================

    public static final ResourceKey<PlacedFeature> TREES_ASPEN_FOREST = REGISTRY.register(
            "trees_aspen_forest",
            DefinedPlacement
                    .place(ModVegetationFeatures.ASPEN_FOREST_TREE)
                    .modified(treePlacement(it -> it.countExtra(10, 0.1f, 1)))
    );

    public static final ResourceKey<PlacedFeature> TREES_MAPLE_FOREST = REGISTRY.register(
            "trees_maple_forest",
            DefinedPlacement
                    .place(ModVegetationFeatures.MAPLE_FOREST_TREE)
                    .modified(treePlacement(it -> it.countExtra(10, 0.1f, 1)))
    );

    public static final ResourceKey<PlacedFeature> TREES_WOODED_PLAINS = REGISTRY.register(
            "trees_wooded_plains",
            DefinedPlacement
                    .place(ModVegetationFeatures.WOODED_PLAINS_TREE)
                    .modified(treePlacement(it -> it.countExtra(2, 0.05f, 1)))
    );

    public static final ResourceKey<PlacedFeature> TREES_REDWOOD_FOREST_GIANT = REGISTRY.register(
            "trees_redwood_forest_giant",
            DefinedPlacement
                    .place(ModVegetationFeatures.GIANT_REDWOOD_FOREST_TREE)
                    .modified(treePlacement(it -> it.countExtra(1, 0.02f, 1)))
    );

    public static final ResourceKey<PlacedFeature> TREES_REDWOOD_FOREST_SMALL = REGISTRY.register(
            "trees_redwood_forest_small",
            DefinedPlacement
                    .place(ModVegetationFeatures.SMALL_REDWOOD_FOREST_TREE)
                    .modified(treePlacement(it -> it.countExtra(4, 0.1f, 1)))
    );

    public static final ResourceKey<PlacedFeature> TREES_REDWOOD_FOREST_SPARSE = REGISTRY.register(
            "trees_redwood_forest_sparse",
            DefinedPlacement
                    .place(ModVegetationFeatures.SMALL_REDWOOD_FOREST_TREE)
                    .modified(treePlacement(it -> it.countExtra(2, 0.05f, 1)))
    );


    // Grass sprouts
    // ===============================================================

    public static final ResourceKey<PlacedFeature> PATCH_GRASS_SPROUTS = REGISTRY.register(
            "patch_grass_sprouts",
            DefinedPlacement
                    .place(ModVegetationFeatures.GRASS_SPROUTS)
                    .count(UniformInt.of(2, 4))
                    .modified(patchPlacement())
                    .randomPatch(inAir(DEFAULT_GROW_BLOCKS), 96)
    );

    public static final ResourceKey<PlacedFeature> PATCH_GRASS_SPROUTS_EXTRA_COMMON = REGISTRY.register(
            "patch_grass_sprouts_extra_common",
            DefinedPlacement
                    .place(ModVegetationFeatures.GRASS_SPROUTS)
                    .count(UniformInt.of(4, 8))
                    .modified(patchPlacement())
                    .randomPatch(inAir(DEFAULT_GROW_BLOCKS), 96)
    );


    // Barley
    // ===============================================================

    public static final ResourceKey<PlacedFeature> PATCH_BARLEY_RARE = REGISTRY.register(
            "patch_barley_rare",
            DefinedPlacement
                    .place(ModVegetationFeatures.RANDOM_BARLEY)
                    .onAverageOnceEvery(14)
                    .modified(patchPlacement())
                    .randomPatch(inAir(SANDY_GROW_BLOCKS), 96)
    );

    public static final ResourceKey<PlacedFeature> PATCH_BARLEY_COMMON = REGISTRY.register(
            "patch_barley_common",
            DefinedPlacement
                    .place(ModVegetationFeatures.RANDOM_BARLEY)
                    .onAverageOnceEvery(2)
                    .modified(patchPlacement())
                    .randomPatch(inAir(SANDY_GROW_BLOCKS), 96)
    );


    // Lavender
    // ===============================================================


    // Cattail
    // ===============================================================

    public static final ResourceKey<PlacedFeature> PATCH_CATTAIL_NEAR_WATER = REGISTRY.register(
            "patch_cattail_near_water",
            DefinedPlacement
                    .place(ModVegetationFeatures.RANDOM_CATTAIL)
                    .count(UniformInt.of(1, 2))
                    .modified(patchPlacement(matchesFluids(Direction.DOWN.getUnitVec3i(), Fluids.WATER)))
                    .randomPatch(inAir(SANDY_GROW_BLOCKS), 96)
    );

    public static final ResourceKey<PlacedFeature> PATCH_CATTAIL_IN_WATER = REGISTRY.register(
            "patch_cattail_in_water",
            DefinedPlacement
                    .place(ModVegetationFeatures.TALL_CATTAIL)
                    .count(UniformInt.of(1, 2))
                    .modified(patchPlacement())
                    .randomPatch(inShallowWater(SANDY_GROW_BLOCKS), 96)
    );

    public static final ResourceKey<PlacedFeature> PATCH_CATTAIL_SWAMP = REGISTRY.register(
            "patch_cattail_swamp",
            DefinedPlacement
                    .place(ModVegetationFeatures.RANDOM_CATTAIL)
                    .count(UniformInt.of(4, 7))
                    .modified(patchPlacement())
                    .randomPatch(inAir(SANDY_GROW_BLOCKS), 96)
    );

    public static final ResourceKey<PlacedFeature> PATCH_CATTAIL_IN_WATER_SWAMP = REGISTRY.register(
            "patch_cattail_in_water_swamp",
            DefinedPlacement
                    .place(ModVegetationFeatures.TALL_CATTAIL)
                    .count(UniformInt.of(4, 7))
                    .modified(patchPlacement())
                    .randomPatch(inShallowWater(SANDY_GROW_BLOCKS), 96)
    );


    // Clovers
    // ===============================================================

    public static final ResourceKey<PlacedFeature> PATCH_CLOVERS_RARE = REGISTRY.register(
            "patch_clovers_rare",
            DefinedPlacement
                    .place(ModVegetationFeatures.CLOVERS)
                    .onAverageOnceEvery(2)
                    .modified(patchPlacement())
                    .randomPatch(inAir(DEFAULT_GROW_BLOCKS), 64)
    );

    public static final ResourceKey<PlacedFeature> PATCH_CLOVERS = REGISTRY.register(
            "patch_clovers",
            DefinedPlacement
                    .place(ModVegetationFeatures.CLOVERS)
                    .count(2)
                    .modified(patchPlacement())
                    .randomPatch(inAir(DEFAULT_GROW_BLOCKS), 64)
    );

    public static final ResourceKey<PlacedFeature> PATCH_CLOVERS_DENSE = REGISTRY.register(
            "patch_clovers",
            DefinedPlacement
                    .place(ModVegetationFeatures.DENSE_CLOVERS)
                    .count(UniformInt.of(2, 6))
                    .modified(patchPlacement())
                    .randomPatch(inAir(DEFAULT_GROW_BLOCKS), 96)
    );


    // Misc
    // ===============================================================

    public static final ResourceKey<PlacedFeature> PATCH_FIREFLY_BUSH_COMMON = REGISTRY.register(
            "patch_firefly_bushes_common",
            DefinedPlacement
                    .place(ModVegetationFeatures.FIREFLY_BUSH)
                    .count(ConstantInt.of(3))
                    .modified(patchPlacement())
                    .randomPatch(inAir(DEFAULT_GROW_BLOCKS), 96)
    );

    public static final ResourceKey<PlacedFeature> PATCH_MOSS_CARPET_AROUND_MOSS = REGISTRY.register(
            "patch_moss_carpet_around_moss",
            DefinedPlacement
                    .place(ModVegetationFeatures.MOSS_CARPET)
                    .count(ConstantInt.of(4))
                    .modified(patchPlacement(matchesBlocks(Direction.DOWN.getUnitVec3i(), FeatureConditions.MOSS_GROW_BLOCKS)))
                    .randomPatch(inAir(DEFAULT_GROW_BLOCKS), 96, 5)
    );


    // Vegetation mixes
    // ===============================================================

    public static final ResourceKey<PlacedFeature> VEGETATION_BARLEY_FIELD = REGISTRY.register(
            "vegetation_barley_field",
            DefinedPlacement
                    .place(ModVegetationFeatures.BARLEY_FIELD_PLANT)
                    .fillChunk(0.75f)
                    .onOceanFloorWg()
                    .onlyIf(allOf(ONLY_IN_AIR_PREDICATE, matchesBlocks(Direction.DOWN.getUnitVec3i(), FeatureConditions.SANDY_GROW_BLOCKS)))
                    .inBiome()
    );


    // Utility functions
    // ===============================================================

    public static void init() {
        // N/A, just initialises the class
    }

    private static DefinedPlacement.Modifier patchPlacement(BlockPredicate predicate) {
        return it -> it
                .spreadInChunk()
                .onOceanFloorWg()
                .onlyIf(predicate)
                .inBiome();
    }

    private static DefinedPlacement.Modifier patchPlacement() {
        return it -> it
                .spreadInChunk()
                .onOceanFloorWg()
                .inBiome();
    }

    private static DefinedPlacement.Modifier treePlacement(DefinedPlacement.Modifier amount) {
        return it -> it
                .modified(amount)
                .spreadInChunk()
                .modified(TREE_THRESHOLD)
                .onOceanFloor()
                .inBiome();
    }

    private static DefinedPlacement.Modifier treePlacement(DefinedPlacement.Modifier amount, Block sapling) {
        return it -> it
                .modified(treePlacement(amount))
                .onlyIf(wouldSurvive(sapling.defaultBlockState(), BlockPos.ZERO));
    }
}
