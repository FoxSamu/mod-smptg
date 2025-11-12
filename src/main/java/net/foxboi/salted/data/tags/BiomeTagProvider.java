package net.foxboi.salted.data.tags;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.foxboi.salted.common.levelgen.biome.ModBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

public class BiomeTagProvider extends FabricTagProvider<Biome> {
    public BiomeTagProvider(FabricDataOutput out, CompletableFuture<HolderLookup.Provider> regs) {
        super(out, Registries.BIOME, regs);
    }

    @Override
    protected void addTags(HolderLookup.Provider regs) {
        builder(ModBiomeTags.HAS_SALT_CRUST)
                .addOptionalTag(ConventionalBiomeTags.IS_OCEAN)
                .addOptionalTag(ConventionalBiomeTags.IS_BEACH);
    }
}
