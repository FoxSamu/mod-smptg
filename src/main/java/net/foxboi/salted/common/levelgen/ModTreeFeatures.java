package net.foxboi.salted.common.levelgen;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

import com.google.common.collect.ImmutableList;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.levelgen.feature.DefinedFeature;
import net.foxboi.salted.common.levelgen.tree.*;
import net.foxboi.salted.common.misc.reg.DataRegistry;
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
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.*;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLogsDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.PlaceOnGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public record ModTreeFeatures() {
    private static final DataRegistry<ConfiguredFeature<?, ?>> REGISTRY = Smptg.REGISTRAR.data(Registries.CONFIGURED_FEATURE);

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN = REGISTRY.register("aspen", DefinedFeature.of(
            Feature.TREE,
            () -> createAspen(false, false, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_BEES_005 = REGISTRY.register("aspen_bees_005", DefinedFeature.of(
            Feature.TREE,
            () -> createAspen(false, false, false, 0.05f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_LEAF_LITTER = REGISTRY.register("aspen_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            () -> createAspen(true, false, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_SHAPED_BIRCH_LEAF_LITTER = REGISTRY.register("aspen_shaped_birch_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            () -> createAspen(true, true, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_BEES_0002_LEAF_LITTER = REGISTRY.register("aspen_bees_0002_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            () -> createAspen(true, false, true, 0.002f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_SHAPED_BIRCH_BEES_0002_LEAF_LITTER = REGISTRY.register("aspen_shaped_birch_bees_0002_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            () -> createAspen(true, true, true, 0.002f)
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
            () -> createMaple(FeatureBlocks.RED_MAPLE_LEAVES, false, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_LEAF_LITTER = REGISTRY.register("red_maple_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            () -> createMaple(FeatureBlocks.RED_MAPLE_LEAVES, true, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_RED_MAPLE = REGISTRY.register("fancy_red_maple", DefinedFeature.of(
            Feature.TREE,
            () -> createMaple(FeatureBlocks.RED_MAPLE_LEAVES, false, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_RED_MAPLE_LEAF_LITTER = REGISTRY.register("fancy_red_maple_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            () -> createMaple(FeatureBlocks.RED_MAPLE_LEAVES, true, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE = REGISTRY.register("orange_maple", DefinedFeature.of(
            Feature.TREE,
            () -> createMaple(FeatureBlocks.ORANGE_MAPLE_LEAVES, false, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE_LEAF_LITTER = REGISTRY.register("orange_maple_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            () -> createMaple(FeatureBlocks.ORANGE_MAPLE_LEAVES, true, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_ORANGE_MAPLE = REGISTRY.register("fancy_orange_maple", DefinedFeature.of(
            Feature.TREE,
            () -> createMaple(FeatureBlocks.ORANGE_MAPLE_LEAVES, false, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_ORANGE_MAPLE_LEAF_LITTER = REGISTRY.register("fancy_orange_maple_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            () -> createMaple(FeatureBlocks.ORANGE_MAPLE_LEAVES, true, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_MAPLE = REGISTRY.register("yellow_maple", DefinedFeature.of(
            Feature.TREE,
            () -> createMaple(FeatureBlocks.YELLOW_MAPLE_LEAVES, false, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_MAPLE_LEAF_LITTER = REGISTRY.register("yellow_maple_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            () -> createMaple(FeatureBlocks.YELLOW_MAPLE_LEAVES, true, false, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_YELLOW_MAPLE = REGISTRY.register("fancy_yellow_maple", DefinedFeature.of(
            Feature.TREE,
            () -> createMaple(FeatureBlocks.YELLOW_MAPLE_LEAVES, false, true, 0f)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_YELLOW_MAPLE_LEAF_LITTER = REGISTRY.register("fancy_yellow_maple_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            () -> createMaple(FeatureBlocks.YELLOW_MAPLE_LEAVES, true, true, 0f)
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
            () -> createBeech(false)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> BEECH_LEAF_LITTER = REGISTRY.register("beech_leaf_litter", DefinedFeature.of(
            Feature.TREE,
            () -> createBeech(true)
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

    public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD_MASSIVE = REGISTRY.register("redwood_massive", DefinedFeature.of(
            Feature.TREE,
            () -> createRedwood(RedwoodType.MASSIVE)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD_DECENT = REGISTRY.register("redwood_decent", DefinedFeature.of(
            Feature.TREE,
            () -> createRedwood(RedwoodType.DECENT)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD_THIN = REGISTRY.register("redwood_thin", DefinedFeature.of(
            Feature.TREE,
            () -> createRedwood(RedwoodType.THIN)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD_TINY = REGISTRY.register("redwood_tiny", DefinedFeature.of(
            Feature.TREE,
            () -> createRedwood(RedwoodType.TINY)
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD_VERY_TINY = REGISTRY.register("redwood_very_tiny", DefinedFeature.of(
            Feature.TREE,
            () -> createRedwood(RedwoodType.VERY_TINY)
    ));

    private static TreeConfiguration createRedwood(RedwoodType type) {
        var builder = new TreeConfiguration.TreeConfigurationBuilder(
                FeatureBlocks.REDWOOD_LOG,
                type.trunkPlacer,

                FeatureBlocks.REDWOOD_LEAVES,
                type.foliagePlacer,

                type.size
        );

        var decorators = new ArrayList<TreeDecorator>();

        return builder
                .decorators(List.copyOf(decorators))
                .ignoreVines()
                .build();
    }

    private enum RedwoodType {
        MASSIVE(
                new MassiveRedwoodTrunkPlacer(40, 5, 10, UniformInt.of(3, 4), ConstantInt.of(2)),
                new RedwoodFoliagePlacer(ConstantInt.of(1), List.of(2, 3, 3, 4, 4, 5, 5), 3),
                new TwoLayersFeatureSize(10, 4, 6)
        ),
        DECENT(
                new DecentRedwoodTrunkPlacer(30, 5, 8, UniformInt.of(2, 3), ConstantInt.of(2)),
                new RedwoodFoliagePlacer(ConstantInt.of(1), List.of(2, 3, 3, 4, 4, 4), 3),
                new TwoLayersFeatureSize(10, 3, 5)
        ),
        THIN(
                new ThinRedwoodTrunkPlacer(26, 4, 7, UniformInt.of(2, 3), ConstantInt.of(1)),
                new RedwoodFoliagePlacer(ConstantInt.of(1), List.of(2, 2, 3, 3, 4, 4), 3),
                new TwoLayersFeatureSize(10, 2, 4)
        ),
        TINY(
                new GiantTrunkPlacer(22, 4, 4),
                new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(8, 14)),
                new TwoLayersFeatureSize(8, 1, 3)
        ),
        VERY_TINY(
                new StraightTrunkPlacer(13, 3, 2),
                new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(8, 10)),
                new TwoLayersFeatureSize(1, 0, 1)
        );

        private final TrunkPlacer trunkPlacer;
        private final FoliagePlacer foliagePlacer;
        private final FeatureSize size;

        RedwoodType(TrunkPlacer trunkPlacer, FoliagePlacer foliagePlacer, FeatureSize size) {
            this.trunkPlacer = trunkPlacer;
            this.foliagePlacer = foliagePlacer;
            this.size = size;
        }
    }


    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_ASPEN_TREE = REGISTRY.register("fallen_aspen_tree", DefinedFeature.of(
            Feature.FALLEN_TREE,
            () -> createFallenTree(ModBlocks.ASPEN_LOG, 7, 10).build()
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_MAPLE_TREE = REGISTRY.register("fallen_maple_tree", DefinedFeature.of(
            Feature.FALLEN_TREE,
            () -> createFallenTree(ModBlocks.MAPLE_LOG, 5, 8).build()
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BEECH_TREE = REGISTRY.register("fallen_beech_tree", DefinedFeature.of(
            Feature.FALLEN_TREE,
            () -> createFallenTree(ModBlocks.BEECH_LOG, 5, 9).build()
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
