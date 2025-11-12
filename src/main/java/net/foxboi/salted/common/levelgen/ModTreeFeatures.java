package net.foxboi.salted.common.levelgen;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import com.google.common.collect.ImmutableList;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.levelgen.feature.DefinedFeature;
import net.foxboi.salted.common.levelgen.tree.AddShelfFungusDecorator;
import net.foxboi.salted.common.levelgen.tree.NoiseBasedMultifaceDecorator;
import net.foxboi.salted.common.levelgen.tree.PointyFoliagePlacer;
import net.foxboi.salted.common.util.DataRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FallenTreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLogsDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.PlaceOnGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public record ModTreeFeatures() {
    private static final DataRegistry<ConfiguredFeature<?, ?>> REGISTRY = DataRegistry.of(Registries.CONFIGURED_FEATURE);

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN = REGISTRY.register("aspen", DefinedFeature.of(
            Feature.TREE,
            createAspen(false, false, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_BEES_005 = REGISTRY.register("aspen", DefinedFeature.of(
            Feature.TREE,
            createAspen(false, false, false, 0.05f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_LEAF_LITTER = REGISTRY.register("aspen_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            createAspen(true, false, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_SHAPED_BIRCH_LEAF_LITTER = REGISTRY.register("aspen_shaped_birch_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            createAspen(true, true, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_BEES_0002_LEAF_LITTER = REGISTRY.register("aspen_bees_0002_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            createAspen(true, false, true, 0.002f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_SHAPED_BIRCH_BEES_0002_LEAF_LITTER = REGISTRY.register("aspen_shaped_birch_bees_0002_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            createAspen(true, true, true, 0.002f)
    ));

    private static TreeConfiguration createAspen(boolean leafLitter, boolean birch, boolean shelfFungi, float beesProbability) {
        var builder = new TreeConfiguration.TreeConfigurationBuilder(
                birch ? FeatureBlocks.BIRCH_LOG : FeatureBlocks.ASPEN_LOG,
                new StraightTrunkPlacer(7, 3, 1),

                birch ? FeatureBlocks.BIRCH_LEAVES : FeatureBlocks.ASPEN_LEAVES,
                new PointyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 4),

                new TwoLayersFeatureSize(1, 0, 1)
        );

        var decorators = new ArrayList<TreeDecorator>();

        if (shelfFungi) {
            decorators.add(shelfFungi());
        }

        if (leafLitter) {
            decorators.add(leafLitter());
            decorators.add(closebyLeafLitter());
        }

        if (beesProbability > 0f) {
            decorators.add(bees(beesProbability));
        }

        return builder
                .decorators(List.copyOf(decorators))
                .ignoreVines()
                .build();
    }


    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE = REGISTRY.register("red_maple", DefinedFeature.of(
            Feature.TREE,
            createMaple(FeatureBlocks.RED_MAPLE_LEAVES, false, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_LEAF_LITTER = REGISTRY.register("red_maple_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            createMaple(FeatureBlocks.RED_MAPLE_LEAVES, true, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_RED_MAPLE = REGISTRY.register("fancy_red_maple", DefinedFeature.of(
            Feature.TREE,
            createMaple(FeatureBlocks.RED_MAPLE_LEAVES, false, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_RED_MAPLE_LEAF_LITTER = REGISTRY.register("fancy_red_maple_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            createMaple(FeatureBlocks.RED_MAPLE_LEAVES, true, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE = REGISTRY.register("orange_maple", DefinedFeature.of(
            Feature.TREE,
            createMaple(FeatureBlocks.ORANGE_MAPLE_LEAVES, false, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE_LEAF_LITTER = REGISTRY.register("orange_maple_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            createMaple(FeatureBlocks.ORANGE_MAPLE_LEAVES, true, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_ORANGE_MAPLE = REGISTRY.register("fancy_orange_maple", DefinedFeature.of(
            Feature.TREE,
            createMaple(FeatureBlocks.ORANGE_MAPLE_LEAVES, false, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_ORANGE_MAPLE_LEAF_LITTER = REGISTRY.register("fancy_orange_maple_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            createMaple(FeatureBlocks.ORANGE_MAPLE_LEAVES, true, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_MAPLE = REGISTRY.register("yellow_maple", DefinedFeature.of(
            Feature.TREE,
            createMaple(FeatureBlocks.YELLOW_MAPLE_LEAVES, false, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_MAPLE_LEAF_LITTER = REGISTRY.register("yellow_maple_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            createMaple(FeatureBlocks.YELLOW_MAPLE_LEAVES, true, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_YELLOW_MAPLE = REGISTRY.register("fancy_yellow_maple", DefinedFeature.of(
            Feature.TREE,
            createMaple(FeatureBlocks.YELLOW_MAPLE_LEAVES, false, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_YELLOW_MAPLE_LEAF_LITTER = REGISTRY.register("fancy_yellow_maple_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            createMaple(FeatureBlocks.YELLOW_MAPLE_LEAVES, true, true, 0f)
    ));

    private static TreeConfiguration createMaple(BlockStateProvider leaves, boolean leafLitter, boolean fancy, float beesProbability) {
        var builder = new TreeConfiguration.TreeConfigurationBuilder(
                FeatureBlocks.MAPLE_LOG,
                fancy
                        ? new FancyTrunkPlacer(3, 11, 0)
                        : new StraightTrunkPlacer(5, 3, 0),

                leaves,
                fancy
                        ? new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4)
                        : new BlobFoliagePlacer(UniformInt.of(2, 3), ConstantInt.of(0), 3),

                new TwoLayersFeatureSize(1, 0, 1, fancy ? OptionalInt.of(4) : OptionalInt.empty())
        );

        var decorators = new ArrayList<TreeDecorator>();

        if (beesProbability > 0f) {
            decorators.add(bees(beesProbability));
        }

        decorators.add(patchmoss());

        if (leafLitter) {
            decorators.add(leafLitter());
        }

        return builder
                .decorators(List.copyOf(decorators))
                .ignoreVines()
                .build();
    }

    public static final ResourceKey<ConfiguredFeature<?, ?>> BEECH = REGISTRY.register("beech", DefinedFeature.of(
            Feature.TREE,
            createBeech(false)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> BEECH_LEAF_LITTER = REGISTRY.register("beech_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            createBeech(true)
    ));

    private static TreeConfiguration createBeech(boolean leafLitter) {
        var builder = new TreeConfiguration.TreeConfigurationBuilder(
                FeatureBlocks.BEECH_LOG,
                new StraightTrunkPlacer(5, 3, 2),

                FeatureBlocks.BEECH_LEAVES,
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),

                new TwoLayersFeatureSize(1, 0, 1)
        );

        var decorators = new ArrayList<TreeDecorator>();

        decorators.add(patchmoss());

        if (leafLitter) {
            decorators.add(leafLitter());
        }

        return builder
                .decorators(List.copyOf(decorators))
                .ignoreVines()
                .build();
    }


    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_ASPEN_TREE = REGISTRY.register("fallen_aspen_tree", DefinedFeature.of(
            Feature.FALLEN_TREE,
            createFallenTree(ModBlocks.ASPEN_LOG, 7, 10).build()
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_MAPLE_TREE = REGISTRY.register("fallen_maple_tree", DefinedFeature.of(
            Feature.FALLEN_TREE,
            createFallenTree(ModBlocks.MAPLE_LOG, 5, 8).build()
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BEECH_TREE = REGISTRY.register("fallen_beech_tree", DefinedFeature.of(
            Feature.FALLEN_TREE,
            createFallenTree(ModBlocks.BEECH_LOG, 5, 9).build()
    ));

    private static FallenTreeConfiguration.FallenTreeConfigurationBuilder createFallenTree(Block log, int minLength, int maxLength) {
        return new FallenTreeConfiguration.FallenTreeConfigurationBuilder(BlockStateProvider.simple(log), UniformInt.of(minLength, maxLength)).logDecorators(
                ImmutableList.of(
                        new AttachedToLogsDecorator(
                                0.1F,
                                new WeightedStateProvider(
                                        WeightedList.<BlockState>builder()
                                                .add(Blocks.RED_MUSHROOM.defaultBlockState(), 2)
                                                .add(Blocks.BROWN_MUSHROOM.defaultBlockState(), 1)
                                ),
                                List.of(Direction.UP)
                        )
                )
        );
    }


    public static void init() {
        // N/A, just initialises the class
    }

    private static TreeDecorator bees(float probability) {
        return new BeehiveDecorator(probability);
    }

    private static TreeDecorator leafLitter() {
        return new PlaceOnGroundDecorator(
                96, 4, 2,
                new WeightedStateProvider(VegetationFeatures.leafLitterPatchBuilder(1, 3))
        );
    }

    private static TreeDecorator closebyLeafLitter() {
        return new PlaceOnGroundDecorator(
                150, 2, 2,
                new WeightedStateProvider(VegetationFeatures.leafLitterPatchBuilder(1, 4))
        );
    }

    private static TreeDecorator shelfFungi() {
        return new AddShelfFungusDecorator(
                ConstantInt.of(0),
                ConstantInt.of(2),
                0.07f,
                FeatureBlocks.SHELF_FUNGUS
        );
    }

    private static TreeDecorator patchmoss() {
        return new NoiseBasedMultifaceDecorator(
                ConstantInt.of(0),
                ConstantInt.of(4),
                0.3f, 0.7f, // Probability range
                0.7114, 0.4, // Noise field and value scale
                FeatureBlocks.PATCHMOSS,
                Holder.direct(new NormalNoise.NoiseParameters(-8, 1, 0.5, 0.25))
        );
    }
}
