package net.foxboi.salted.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;

import net.foxboi.salted.common.levelgen.biome.ModBiomeTags;
import net.foxboi.salted.common.levelgen.biome.ModBiomes;
import net.foxboi.summon.api.tags.DataTagSet;
import net.foxboi.summon.api.tags.TagProvider;

public class BiomeTagSet extends DataTagSet<Biome> {
    public BiomeTagSet(TagProvider provider) {
        super(provider, Registries.BIOME);
    }

    @Override
    protected void register(HolderLookup.Provider lookups) {
        builder(ModBiomeTags.CAN_HAVE_SURFACE_LIMESTONE_PATCHES)
                .tag(ConventionalBiomeTags.IS_PLAINS)
                .tag(ConventionalBiomeTags.IS_FOREST)
                .tag(ConventionalBiomeTags.IS_DARK_FOREST)
                .tag(ConventionalBiomeTags.IS_FLOWER_FOREST)
                .tag(ConventionalBiomeTags.IS_SAVANNA);

        builder(ConventionalBiomeTags.IS_FOREST)
                .element(ModBiomes.ASPEN_FOREST)
                .element(ModBiomes.MAPLE_FOREST);

        builder(ConventionalBiomeTags.IS_BIRCH_FOREST)
                .element(ModBiomes.ASPEN_FOREST);

        builder(ConventionalBiomeTags.IS_PLAINS)
                .element(ModBiomes.FIREFLY_MEADOW)
                .element(ModBiomes.WOODED_PLAINS)
                .element(ModBiomes.LAVENDER_FIELD)
                .element(ModBiomes.BARLEY_FIELD);

        builder(ConventionalBiomeTags.IS_FLORAL)
                .element(ModBiomes.LAVENDER_FIELD);

        builder(ConventionalBiomeTags.IS_TAIGA)
                .element(ModBiomes.REDWOOD_TAIGA)
                .element(ModBiomes.SPARSE_REDWOOD_TAIGA);

        builder(ConventionalBiomeTags.IS_DRY_OVERWORLD)
                .element(ModBiomes.HEATHLAND);

        builder(ConventionalBiomeTags.IS_CAVE)
                .element(ModBiomes.LIMESTONE_CAVES);

        builder(ConventionalBiomeTags.IS_HOT_NETHER)
                .element(ModBiomes.BURNING_FOREST);
    }
}
