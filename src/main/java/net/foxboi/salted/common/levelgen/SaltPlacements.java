package net.foxboi.salted.common.levelgen;

import net.foxboi.salted.common.levelgen.placement.DefinedPlacement;
import net.foxboi.salted.common.util.DataRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.placement.*;

@Deprecated
public class SaltPlacements {
    private static final DataRegistry<PlacedFeature> REGISTRY = DataRegistry.of(Registries.PLACED_FEATURE);

    public static final ResourceKey<PlacedFeature> SALT_CRUST = REGISTRY.register(
            "salt_crust",
            DefinedPlacement
                    .place(SaltFeatures.SALT_CRUST)
                    .inSaltChunk()
                    .onAverageOnceEvery(2)
                    .count(UniformInt.of(2, 3))
                    .spread()
                    .onOceanFloorWg()
                    .inBiome()
    );

    public static final ResourceKey<PlacedFeature> COMMON_SALT_CRUST = REGISTRY.register(
            "common_salt_crust",
            DefinedPlacement
                    .place(SaltFeatures.SALT_CRUST)
                    .inSaltChunk()
                    .count(UniformInt.of(2, 4))
                    .spread()
                    .onOceanFloorWg()
                    .inBiome()
    );

    public static void init() {
        // N/A, just initialises the class
    }
}
