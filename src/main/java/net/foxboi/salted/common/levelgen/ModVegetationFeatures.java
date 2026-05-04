package net.foxboi.salted.common.levelgen;

import java.util.List;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.levelgen.feature.ColumnPlantConfiguration;
import net.foxboi.salted.common.levelgen.feature.DefinedFeature;
import net.foxboi.salted.common.levelgen.feature.ModFeatures;
import net.foxboi.salted.common.misc.reg.DataRegistry;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public record ModVegetationFeatures() {
    private static final DataRegistry<ConfiguredFeature<?, ?>> REGISTRY = Smptg.REGISTRAR.data(Registries.CONFIGURED_FEATURE);

    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_SPROUTS = REGISTRY.register("grass_sprouts", block(FeatureBlocks.GRASS_SPROUTS));
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_BARLEY = REGISTRY.register("random_barley", block(FeatureBlocks.RANDOM_BARLEY));
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_BARLEY = REGISTRY.register("small_barley", block(FeatureBlocks.BARLEY));
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_LAVENDER = REGISTRY.register("random_lavender", block(FeatureBlocks.RANDOM_LAVENDER));
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_LAVENDER = REGISTRY.register("small_lavender", block(FeatureBlocks.LAVENDER));
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOBE_THISTLE = REGISTRY.register("globe_thistle", block(FeatureBlocks.GLOBE_THISTLE));
    public static final ResourceKey<ConfiguredFeature<?, ?>> RANDOM_CATTAIL = REGISTRY.register("random_cattail", block(FeatureBlocks.RANDOM_CATTAIL));
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_CATTAIL = REGISTRY.register("tall_cattail", block(FeatureBlocks.TALL_CATTAIL));
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOVERS = REGISTRY.register("clovers", block(FeatureBlocks.CLOVERS_1_3));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DENSE_CLOVERS = REGISTRY.register("dense_clovers", block(FeatureBlocks.CLOVERS_1_4));
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSS_CARPET = REGISTRY.register("moss_carpet", block(FeatureBlocks.MOSS_CARPET));
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIREFLY_BUSH = REGISTRY.register("firefly_bush", block(FeatureBlocks.FIREFLY_BUSH));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASH_LAYER = REGISTRY.register("ash_layer", block(FeatureBlocks.ASH_LAYER));
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHCREEP = REGISTRY.register("ashcreep", block(FeatureBlocks.ASHCREEP));
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASHVINE = REGISTRY.register("ashvine", columnPlant(
            Direction.DOWN,
            FeatureBlocks.ASHVINE,
            BlockPredicate.ONLY_IN_AIR_PREDICATE,
            UniformFloat.of(0, .75f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> BURNED_STEM = REGISTRY.register("burned_stem", column(
            Direction.UP,
            FeatureBlocks.BURNED_STEM,
            new WeightedListInt(
                    weightedIntProvider()
                            .add(UniformInt.of(1, 2), 7)
                            .add(UniformInt.of(3, 6), 3)
                            .build()
            ),
            BlockPredicate.ONLY_IN_AIR_PREDICATE
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_FOREST_TREE = REGISTRY.register("aspen_forest_tree", DefinedFeature.of(
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

    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_FOREST_TREE = REGISTRY.register("maple_forest_tree", DefinedFeature.of(
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

    public static final ResourceKey<ConfiguredFeature<?, ?>> WOODED_PLAINS_TREE = REGISTRY.register("wooded_plains_tree", DefinedFeature.of(
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

    public static final ResourceKey<ConfiguredFeature<?, ?>> GIANT_REDWOOD_FOREST_TREE = REGISTRY.register("giant_redwood_forest_tree", DefinedFeature.of(
            Feature.RANDOM_SELECTOR,
            Registries.PLACED_FEATURE,
            features -> new RandomFeatureConfiguration(
                    List.of(
                            new WeightedPlacedFeature(features.getOrThrow(ModTreePlacements.REDWOOD_MASSIVE), 0.4f),
                            new WeightedPlacedFeature(features.getOrThrow(ModTreePlacements.REDWOOD_THIN), 0.2f)
                    ),
                    features.getOrThrow(ModTreePlacements.REDWOOD_DECENT)
            )
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_REDWOOD_FOREST_TREE = REGISTRY.register("small_redwood_forest_tree", DefinedFeature.of(
            Feature.RANDOM_SELECTOR,
            Registries.PLACED_FEATURE,
            features -> new RandomFeatureConfiguration(
                    List.of(
                            new WeightedPlacedFeature(features.getOrThrow(ModTreePlacements.REDWOOD_TINY), 0.5f)
                    ),
                    features.getOrThrow(ModTreePlacements.REDWOOD_VERY_TINY)
            )
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> BARLEY_FIELD_PLANT = REGISTRY.register("barley_field_plant", block(
            new WeightedStateProvider(
                    weightedBlockState()
                            .add(ModBlocks.BARLEY.defaultBlockState(), 1200)
                            .add(ModBlocks.TALL_BARLEY.defaultBlockState(), 200)
                            .add(Blocks.SHORT_GRASS.defaultBlockState(), 400)
                            .add(Blocks.TALL_GRASS.defaultBlockState(), 120)
                            .add(Blocks.SHORT_DRY_GRASS.defaultBlockState(), 100)
                            .add(Blocks.TALL_DRY_GRASS.defaultBlockState(), 35)
                            .build()
            )
    ));


    private static WeightedList.Builder<BlockState> weightedBlockState() {
        return new WeightedList.Builder<>();
    }

    private static WeightedList.Builder<IntProvider> weightedIntProvider() {
        return new WeightedList.Builder<>();
    }


    private static DefinedFeature<?> block(BlockStateProvider block) {
        return DefinedFeature.of(
                Feature.SIMPLE_BLOCK,
                () -> new SimpleBlockConfiguration(block)
        );
    }

    private static DefinedFeature<?> column(Direction dir, BlockStateProvider body, BlockStateProvider end, IntProvider bodyLength, BlockPredicate replace) {
        return DefinedFeature.of(
                Feature.BLOCK_COLUMN,
                () -> new BlockColumnConfiguration(
                        List.of(
                                BlockColumnConfiguration.layer(bodyLength, body),
                                BlockColumnConfiguration.layer(ConstantInt.of(1), end)
                        ),
                        dir,
                        replace,
                        true
                )
        );
    }

    private static DefinedFeature<?> column(Direction dir, BlockStateProvider block, IntProvider length, BlockPredicate replace) {
        return DefinedFeature.of(
                Feature.BLOCK_COLUMN,
                () -> new BlockColumnConfiguration(
                        List.of(
                                BlockColumnConfiguration.layer(length, block)
                        ),
                        dir,
                        replace,
                        false
                )
        );
    }

    private static DefinedFeature<?> columnPlant(Direction dir, BlockStateProvider block, BlockPredicate replace, FloatProvider proportionalLength) {
        return DefinedFeature.of(
                ModFeatures.COLUMN_PLANT,
                () -> new ColumnPlantConfiguration(
                        dir,
                        block,
                        replace,
                        ConstantInt.of(0),
                        proportionalLength,
                        1,
                        128,
                        false
                )
        );
    }

    public static void init() {
        // N/A, just initialises the class
    }
}
