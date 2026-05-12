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

public record ModMiscPlacements() {
    private static final DataRegistry<PlacedFeature> REGISTRY = Smptg.REGISTRAR.data(Registries.PLACED_FEATURE);


    public static final ResourceKey<PlacedFeature> PUDDLE = REGISTRY.register(
            "puddle",
            DefinedPlacement
                    .place(ModMiscFeatures.PUDDLE)
                    .onAverageOnceEvery(7)
                    .spreadInChunk()
                    .onOceanFloorWg()
                    .inBiome()
    );


    // Utility functions
    // ===============================================================

    public static void init() {
        // N/A, just initialises the class
    }
}
