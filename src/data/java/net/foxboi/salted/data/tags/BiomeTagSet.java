package net.foxboi.salted.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;

import net.foxboi.salted.common.levelgen.biome.ModBiomeTags;
import net.foxboi.summon.api.tags.DataTagSet;
import net.foxboi.summon.api.tags.TagProvider;

public class BiomeTagSet extends DataTagSet<Biome> {
    public BiomeTagSet(TagProvider provider) {
        super(provider, Registries.BIOME);
    }

    @Override
    protected void register(HolderLookup.Provider lookups) {
        builder(ModBiomeTags.HAS_SALT_CRUST)
                .tag(ConventionalBiomeTags.IS_OCEAN)
                .tag(ConventionalBiomeTags.IS_BEACH);
    }
}
