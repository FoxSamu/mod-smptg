package net.foxboi.salted.common.levelgen;

import java.util.List;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Fluids;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.levelgen.feature.*;
import net.foxboi.salted.common.misc.reg.DataRegistry;

public record ModCaveFeatures() {
    private static final DataRegistry<ConfiguredFeature<?, ?>> REGISTRY = Smptg.REGISTRAR.data(Registries.CONFIGURED_FEATURE);

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TO_LIMESTONE = REGISTRY.register("patch_to_limestone", DefinedFeature.of(
            Feature.DISK,
            () -> new DiskConfiguration(
                    FeatureBlocks.REPLACE_WITH_LIMESTONE,
                    BlockPredicate.alwaysTrue(), // Block checks are carried out by the block provider
                    UniformInt.of(4, 8),
                    4
            )
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> POINTED_LIMESTONE_CLUSTER = REGISTRY.register("pointed_limestone_cluster", DefinedFeature.of(
            ModFeatures.SPELEOTHEM_CLUSTER,
            () -> new SpeleothemClusterConfiguration(
                    Speleothem.LIMESTONE,
                    12, // Search range
                    UniformInt.of(3, 6), // Height
                    UniformInt.of(2, 8), // Radius
                    1, // Max height diff
                    3, // Height deviation
                    UniformInt.of(2, 4), // Layer thickness
                    UniformFloat.of(0.3f, 0.7f), // Density
                    ClampedNormalFloat.of(0.1f, 0.3f, 0.1f, 0.9f), // Wetness
                    BlockPredicate.anyOf(
                            BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD),
                            BlockPredicate.matchesBlocks(ModBlocks.LIMESTONE)
                    ), // Water canPlaceAt
                    0.1f, // Column change on edge
                    3, // Max distance of edge affecting column chance
                    8 // Max distance from center affecting height bias
            )
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_LIMESTONE_SPELEOTHEM = REGISTRY.register("large_limestone_speleothem", DefinedFeature.of(
            ModFeatures.LARGE_SPELEOTHEM,
            () -> new LargeSpeleothemConfiguration(
                    Speleothem.LIMESTONE,
                    64, // Search range
                    UniformInt.of(4, 12), // Column radius
                    UniformFloat.of(.7f, 6f), // Height scale
                    .2f, // Max column radius to cave height ratio
                    UniformFloat.of(.3f, .9f), // Stalactite bluntness
                    UniformFloat.of(.4f, 1f), // Stalagmite bluntness
                    UniformFloat.of(0f, .2f), // Wind speed
                    3, // Min radius for wind
                    .3f // Min bluntness for wind
            )
    ));

    public static final ResourceKey<ConfiguredFeature<?, ?>> PUDDLE_IN_LIMESTONE = REGISTRY.register("puddle_in_limestone", DefinedFeature.of(
            ModFeatures.PUDDLE,
            () -> PuddleConfiguration.create(
                    Fluids.WATER,
                    vec -> BlockPredicate.matchesBlocks(vec, ModBlocks.LIMESTONE),
                    UniformFloat.of(16, 32f)
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

    public static void init() {
        // N/A, just initialises the class
    }
}
