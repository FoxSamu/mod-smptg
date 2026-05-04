package net.foxboi.salted.common.levelgen;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.levelgen.placement.DefinedPlacement;
import net.foxboi.salted.common.misc.reg.DataRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

@SuppressWarnings("unused")
public record ModTreePlacements() {
    private static final DataRegistry<PlacedFeature> REGISTRY = Smptg.REGISTRAR.data(Registries.PLACED_FEATURE);

    // TODO use correct saplings

    public static final ResourceKey<PlacedFeature> ASPEN = place(ModTreeFeatures.ASPEN, Blocks.BIRCH_SAPLING);
    public static final ResourceKey<PlacedFeature> ASPEN_BEES_005 = place(ModTreeFeatures.ASPEN_BEES_005, Blocks.BIRCH_SAPLING);
    public static final ResourceKey<PlacedFeature> ASPEN_LEAF_LITTER = place(ModTreeFeatures.ASPEN_LEAF_LITTER, Blocks.BIRCH_SAPLING);
    public static final ResourceKey<PlacedFeature> ASPEN_SHAPED_BIRCH_LEAF_LITTER = place(ModTreeFeatures.ASPEN_SHAPED_BIRCH_LEAF_LITTER, Blocks.BIRCH_SAPLING);
    public static final ResourceKey<PlacedFeature> ASPEN_BEES_0002_LEAF_LITTER = place(ModTreeFeatures.ASPEN_BEES_0002_LEAF_LITTER, Blocks.BIRCH_SAPLING);
    public static final ResourceKey<PlacedFeature> ASPEN_SHAPED_BIRCH_BEES_0002_LEAF_LITTER = place(ModTreeFeatures.ASPEN_SHAPED_BIRCH_BEES_0002_LEAF_LITTER, Blocks.BIRCH_SAPLING);
    public static final ResourceKey<PlacedFeature> FALLEN_ASPEN_TREE = place(ModTreeFeatures.FALLEN_ASPEN_TREE, Blocks.BIRCH_SAPLING);

    public static final ResourceKey<PlacedFeature> RED_MAPLE = place(ModTreeFeatures.RED_MAPLE, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> ORANGE_MAPLE = place(ModTreeFeatures.ORANGE_MAPLE, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> YELLOW_MAPLE = place(ModTreeFeatures.YELLOW_MAPLE, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> RED_MAPLE_LEAF_LITTER = place(ModTreeFeatures.RED_MAPLE_LEAF_LITTER, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> ORANGE_MAPLE_LEAF_LITTER = place(ModTreeFeatures.ORANGE_MAPLE_LEAF_LITTER, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> YELLOW_MAPLE_LEAF_LITTER = place(ModTreeFeatures.YELLOW_MAPLE_LEAF_LITTER, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> FANCY_RED_MAPLE = place(ModTreeFeatures.FANCY_RED_MAPLE, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> FANCY_ORANGE_MAPLE = place(ModTreeFeatures.FANCY_ORANGE_MAPLE, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> FANCY_YELLOW_MAPLE = place(ModTreeFeatures.FANCY_YELLOW_MAPLE, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> FANCY_RED_MAPLE_LEAF_LITTER = place(ModTreeFeatures.FANCY_RED_MAPLE_LEAF_LITTER, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> FANCY_ORANGE_MAPLE_LEAF_LITTER = place(ModTreeFeatures.FANCY_ORANGE_MAPLE_LEAF_LITTER, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> FANCY_YELLOW_MAPLE_LEAF_LITTER = place(ModTreeFeatures.FANCY_YELLOW_MAPLE_LEAF_LITTER, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> FALLEN_MAPLE_TREE = place(ModTreeFeatures.FALLEN_MAPLE_TREE, Blocks.OAK_SAPLING);

    public static final ResourceKey<PlacedFeature> BEECH = place(ModTreeFeatures.BEECH, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> BEECH_LEAF_LITTER = place(ModTreeFeatures.BEECH_LEAF_LITTER, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> FALLEN_BEECH_TREE = place(ModTreeFeatures.FALLEN_BEECH_TREE, Blocks.OAK_SAPLING);

    public static final ResourceKey<PlacedFeature> REDWOOD_MASSIVE = place(ModTreeFeatures.REDWOOD_MASSIVE, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> REDWOOD_DECENT = place(ModTreeFeatures.REDWOOD_DECENT, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> REDWOOD_THIN = place(ModTreeFeatures.REDWOOD_THIN, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> REDWOOD_TINY = place(ModTreeFeatures.REDWOOD_TINY, Blocks.OAK_SAPLING);
    public static final ResourceKey<PlacedFeature> REDWOOD_VERY_TINY = place(ModTreeFeatures.REDWOOD_VERY_TINY, Blocks.OAK_SAPLING);

    private static ResourceKey<PlacedFeature> place(ResourceKey<ConfiguredFeature<?, ?>> key, Block sapling) {
        return REGISTRY.register(key.identifier(), DefinedPlacement.place(key).modified(PlacementUtils.filteredByBlockSurvival(sapling)));
    }

    public static void init() {
        // N/A, just initialises the class
    }
}
