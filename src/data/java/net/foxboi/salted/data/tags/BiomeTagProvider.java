package net.foxboi.salted.data.tags;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;

import net.foxboi.salted.common.levelgen.biome.ModBiomeTags;

public class BiomeTagProvider extends FabricTagsProvider<Biome> {
    public BiomeTagProvider(FabricPackOutput out, CompletableFuture<HolderLookup.Provider> regs) {
        super(out, Registries.BIOME, regs);
    }

    @Override
    protected void addTags(HolderLookup.Provider regs) {
        builder(ModBiomeTags.HAS_SALT_CRUST)
                .addOptionalTag(ConventionalBiomeTags.IS_OCEAN)
                .addOptionalTag(ConventionalBiomeTags.IS_BEACH);
    }
}
