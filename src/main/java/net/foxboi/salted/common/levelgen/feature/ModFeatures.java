package net.foxboi.salted.common.levelgen.feature;

import net.foxboi.salted.common.Smptg;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;

@SuppressWarnings("unused")
public class ModFeatures {
    // FEATURES
    // =============================================

    public static final Feature<RandomBlobsAndCrystalsConfiguration> RANDOM_BLOBS_AND_CRYSTALS = register(
            "random_blobs_and_crystals",
            new RandomBlobsAndCrystalsFeature()
    );

    public static final Feature<ColumnPlantConfiguration> COLUMN_PLANT = register(
            "column_plant",
            new ColumnPlantFeature()
    );


    // INITIALISATION
    // =============================================

    public static void init() {
    }


    // REGISTRY
    // =============================================

    private static <F extends Feature<?>> F register(String name, F feature) {
        var key = Smptg.key(Registries.FEATURE, name);
        return Registry.register(BuiltInRegistries.FEATURE, key, feature);
    }
}
