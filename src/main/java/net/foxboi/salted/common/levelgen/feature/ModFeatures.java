package net.foxboi.salted.common.levelgen.feature;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.reg.GameRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;

@SuppressWarnings("unused")
public class ModFeatures {
    private static final GameRegistry<Feature<?>> REGISTRY = Smptg.REGISTRAR.game(Registries.FEATURE);

    // FEATURES
    // =============================================

    public static final Feature<RandomBlobsAndCrystalsConfiguration> RANDOM_BLOBS_AND_CRYSTALS = REGISTRY.register(
            "random_blobs_and_crystals",
            new RandomBlobsAndCrystalsFeature()
    );

    public static final Feature<ColumnPlantConfiguration> COLUMN_PLANT = REGISTRY.register(
            "column_plant",
            new ColumnPlantFeature()
    );


    // INITIALISATION
    // =============================================

    public static void init() {
    }
}
