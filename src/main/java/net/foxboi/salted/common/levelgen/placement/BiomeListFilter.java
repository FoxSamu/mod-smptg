package net.foxboi.salted.common.levelgen.placement;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class BiomeListFilter extends PlacementFilter {
    public static final MapCodec<BiomeListFilter> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(it -> it.biomes),
            Codec.BOOL.optionalFieldOf("whitelist", true).forGetter(it -> it.whitelist)
    ).apply(i, BiomeListFilter::new));

    private final HolderSet<Biome> biomes;
    private final boolean whitelist;

    public BiomeListFilter(HolderSet<Biome> biomes, boolean whitelist) {
        this.biomes = biomes;
        this.whitelist = whitelist;
    }

    public static BiomeListFilter onlyInBiomes(HolderSet<Biome> biomes) {
        return new BiomeListFilter(biomes, true);
    }

    public static BiomeListFilter notInBiomes(HolderSet<Biome> biomes) {
        return new BiomeListFilter(biomes, false);
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, RandomSource random, BlockPos origin) {
        return biomes.contains(context.getLevel().getBiome(origin)) == whitelist;
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacementTypes.BIOME_LIST;
    }
}
