package net.foxboi.salted.common.levelgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.levelgen.placement.DefinedPlacement;
import net.foxboi.salted.common.misc.reg.DataRegistry;

public record ModCavePlacements() {
    private static final DataRegistry<PlacedFeature> REGISTRY = Smptg.REGISTRAR.data(Registries.PLACED_FEATURE);


    public static final ResourceKey<PlacedFeature> PATCH_DIRT_TO_LIMESTONE = REGISTRY.register(
            "patch_dirt_to_limestone",
            DefinedPlacement
                    .place(ModCaveFeatures.PATCH_TO_LIMESTONE)
                    .onAverageOnceEvery(7)
                    .count(UniformInt.of(2, 5))
                    .spreadInChunk()
                    .atHeight(8, 70) // Put somewhere in cave, in the range where the cave should be for it to reach the surface
                    .inBiome() // Then check biome, eliminating the ones outside the height range
                    .onOceanFloorWg() // Then after biome check, move it to surface
                    .randomPatch(BlockPredicate.alwaysTrue(), 4, 4, 6) // Place a bunch locally
    );


    public static final ResourceKey<PlacedFeature> PATCH_UNDERGROUND_TO_LIMESTONE = REGISTRY.register(
            "patch_underground_to_limestone",
            DefinedPlacement
                    .place(ModCaveFeatures.PATCH_TO_LIMESTONE)
                    .onAverageOnceEvery(3)
                    .count(UniformInt.of(6, 12))
                    .spreadInChunk()
                    .atHeight(8, 70) // Put somewhere in cave, in the range where the cave should be for it to reach the surface
                    .inBiome() // Then check biome, eliminating the ones outside the height range
                    .atHeight(20, 120)
                    .randomPatch(BlockPredicate.alwaysTrue(), 4, 4, 6) // Place a bunch locally
    );

    public static final ResourceKey<PlacedFeature> POINTED_LIMESTONE_CLUSTER = REGISTRY.register(
            "pointed_limestone_cluster",
            DefinedPlacement
                    .place(ModCaveFeatures.POINTED_LIMESTONE_CLUSTER)
                    .count(BiasedToBottomInt.of(8, 48))
                    .atHeight(-30, 70)
                    .spreadInChunk()
                    .inBiome()
    );

    public static final ResourceKey<PlacedFeature> LARGE_LIMESTONE_SPELEOTHEM = REGISTRY.register(
            "large_limestone_speleothem",
            DefinedPlacement
                    .place(ModCaveFeatures.LARGE_LIMESTONE_SPELEOTHEM)
                    .count(12)
                    .onAverageOnceEvery(3)
                    .atHeight(-30, 70)
                    .spreadInChunk()
                    .inBiome()
    );

    public static final ResourceKey<PlacedFeature> PUDDLE_IN_LIMESTONE = REGISTRY.register(
            "puddle_in_limestone",
            DefinedPlacement
                    .place(ModCaveFeatures.PUDDLE_IN_LIMESTONE)
                    .count(7)
                    .atHeight(-30, 70)
                    .spreadInChunk()
                    .inBiome()
                    .onlyIf(FeatureConditions.AIR)
    );


    // Utility functions
    // ===============================================================

    public static void init() {
        // N/A, just initialises the class
    }
}
