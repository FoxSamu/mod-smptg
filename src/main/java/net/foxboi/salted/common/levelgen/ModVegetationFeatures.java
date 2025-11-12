package net.foxboi.salted.common.levelgen;

import java.util.List;

import net.foxboi.salted.common.levelgen.feature.DefinedFeature;
import net.foxboi.salted.common.util.DataRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import static net.foxboi.salted.common.levelgen.FeatureConditions.*;

public record ModVegetationFeatures() {
    private static final DataRegistry<ConfiguredFeature<?, ?>> REGISTRY = DataRegistry.of(Registries.CONFIGURED_FEATURE);

    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSS_CARPET_BONEMEAL = REGISTRY.register("moss_carpet_bonemeal", DefinedFeature.of(
            Feature.SIMPLE_BLOCK,
            new SimpleBlockConfiguration(FeatureBlocks.MOSS_CARPET)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREE_ASPEN_FOREST = REGISTRY.register("tree_aspen_forest", DefinedFeature.of(
            Feature.RANDOM_SELECTOR,
            Registries.PLACED_FEATURE,
            features -> new RandomFeatureConfiguration(
                    List.of(
                            new WeightedPlacedFeature(features.getOrThrow(TreePlacements.FALLEN_BIRCH_TREE), 0.01f * 0.07f),
                            new WeightedPlacedFeature(features.getOrThrow(ModTreePlacements.FALLEN_ASPEN_TREE), 0.01f),
                            new WeightedPlacedFeature(features.getOrThrow(ModTreePlacements.ASPEN_SHAPED_BIRCH_BEES_0002_LEAF_LITTER), 0.07f)
                    ),
                    features.getOrThrow(ModTreePlacements.ASPEN_BEES_0002_LEAF_LITTER)
            )
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREE_MAPLE_FOREST = REGISTRY.register("tree_maple_forest", DefinedFeature.of(
            Feature.RANDOM_SELECTOR,
            Registries.PLACED_FEATURE,
            features -> new RandomFeatureConfiguration(
                    List.of(
                            new WeightedPlacedFeature(features.getOrThrow(ModTreePlacements.FALLEN_BEECH_TREE), 0.01f * 0.3f),
                            new WeightedPlacedFeature(features.getOrThrow(ModTreePlacements.FALLEN_MAPLE_TREE), 0.01f),
                            new WeightedPlacedFeature(features.getOrThrow(ModTreePlacements.BEECH_LEAF_LITTER), 0.3f),
                            new WeightedPlacedFeature(features.getOrThrow(ModTreePlacements.FANCY_ORANGE_MAPLE_LEAF_LITTER), 0.05f * 0.1f),
                            new WeightedPlacedFeature(features.getOrThrow(ModTreePlacements.ORANGE_MAPLE_LEAF_LITTER), 0.05f),
                            new WeightedPlacedFeature(features.getOrThrow(ModTreePlacements.FANCY_YELLOW_MAPLE_LEAF_LITTER), 0.05f * 0.1f),
                            new WeightedPlacedFeature(features.getOrThrow(ModTreePlacements.YELLOW_MAPLE_LEAF_LITTER), 0.05f),
                            new WeightedPlacedFeature(features.getOrThrow(ModTreePlacements.FANCY_RED_MAPLE_LEAF_LITTER), 0.1f)
                    ),
                    features.getOrThrow(ModTreePlacements.RED_MAPLE_LEAF_LITTER)
            )
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREE_WOODED_PLAINS = REGISTRY.register("tree_wooded_plains", DefinedFeature.of(
            Feature.RANDOM_SELECTOR,
            Registries.PLACED_FEATURE,
            features -> new RandomFeatureConfiguration(
                    List.of(
                            new WeightedPlacedFeature(features.getOrThrow(TreePlacements.BIRCH_BEES_002), 0.1f),
                            new WeightedPlacedFeature(features.getOrThrow(TreePlacements.OAK_BEES_002), 0.9f),
                            new WeightedPlacedFeature(features.getOrThrow(TreePlacements.FALLEN_OAK_TREE), 0.0125f)
                    ),
                    features.getOrThrow(TreePlacements.FANCY_OAK_BEES_002)
            )
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GRASS_SPROUTS = REGISTRY.register("patch_grass_sprouts", patch(
            FeatureBlocks.GRASS_SPROUTS,
            inAir(DEFAULT_GROW_BLOCKS),
            96
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BARLEY = REGISTRY.register("patch_barley", patch(
            FeatureBlocks.either(FeatureBlocks.BARLEY, FeatureBlocks.TALL_BARLEY, 0.25),
            inAir(SANDY_GROW_BLOCKS),
            96
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CATTAIL = REGISTRY.register("patch_cattail", patch(
            FeatureBlocks.either(FeatureBlocks.CATTAIL, FeatureBlocks.TALL_CATTAIL, 0.75),
            inAir(SANDY_GROW_BLOCKS),
            96
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CATTAIL_IN_WATER = REGISTRY.register("patch_cattail_in_water", patch(
            FeatureBlocks.TALL_CATTAIL,
            inShallowWater(SANDY_GROW_BLOCKS),
            96
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CLOVERS = REGISTRY.register("patch_clovers", patch(
            FeatureBlocks.CLOVERS_1_3,
            inAir(DEFAULT_GROW_BLOCKS),
            64
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CLOVERS_DENSE = REGISTRY.register("patch_clovers_dense", patch(
            FeatureBlocks.CLOVERS_1_4,
            inAir(DEFAULT_GROW_BLOCKS),
            96
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MOSS_CARPET = REGISTRY.register("patch_moss_carpet", patch(
            FeatureBlocks.MOSS_CARPET,
            inAir(DEFAULT_GROW_BLOCKS),
            96,
            5
    ));


    private static DefinedFeature<?> patch(BlockStateProvider block, BlockPredicate predicate, int attempts) {
        return patch(block, predicate, attempts, 7);
    }


    private static DefinedFeature<?> patch(BlockStateProvider block, BlockPredicate predicate, int attempts, int spread) {
        return DefinedFeature.of(
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        attempts,
                        spread,
                        3,
                        PlacementUtils.filtered(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(block),
                                predicate
                        )
                )
        );
    }

    public static void init() {
        // N/A, just initialises the class
    }
}
