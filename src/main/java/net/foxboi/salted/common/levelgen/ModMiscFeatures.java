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
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Fluids;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.levelgen.feature.*;
import net.foxboi.salted.common.misc.reg.DataRegistry;

public record ModMiscFeatures() {
    private static final DataRegistry<ConfiguredFeature<?, ?>> REGISTRY = Smptg.REGISTRAR.data(Registries.CONFIGURED_FEATURE);

    public static final ResourceKey<ConfiguredFeature<?, ?>> PUDDLE = REGISTRY.register("puddle", DefinedFeature.of(
            ModFeatures.PUDDLE,
            () -> PuddleConfiguration.createInFlatArea(
                    Fluids.WATER,
                    vec -> BlockPredicate.solid(vec),
                    UniformFloat.of(16, 64f)
            )
                    .withFloor(FeatureBlocks.MUD, BlockPredicate.solid())
                    .withEdge(FeatureBlocks.MUD, BlockPredicate.solid())
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
