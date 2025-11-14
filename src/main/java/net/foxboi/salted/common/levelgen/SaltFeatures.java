package net.foxboi.salted.common.levelgen;

import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.levelgen.feature.DefinedFeature;
import net.foxboi.salted.common.levelgen.feature.ModFeatures;
import net.foxboi.salted.common.levelgen.feature.RandomBlobsAndCrystalsConfig;
import net.foxboi.salted.common.misc.data.DataRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

@Deprecated
public class SaltFeatures {
    private static final DataRegistry<ConfiguredFeature<?, ?>> REGISTRY = DataRegistry.of(Registries.CONFIGURED_FEATURE);

    public static final ResourceKey<ConfiguredFeature<?, ?>> SALT_CRUST = REGISTRY.register("salt_crust", DefinedFeature.of(
            ModFeatures.RANDOM_BLOBS_AND_CRYSTALS,
            () -> RandomBlobsAndCrystalsConfig.builder()
                    .blob(FeatureBlocks.SALT_CRUST, BlockPredicate.matchesTag(ModBlockTags.SALT_CRUST_CAN_REPLACE))
                    .crystal(FeatureBlocks.SALT_CRYSTAL, BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE)
                    .crystalChance(0.1f)
                    .tryRotateCrystals()
                    .build()
    ));

    public static void init() {
        // N/A, just initialises the class
    }
}
