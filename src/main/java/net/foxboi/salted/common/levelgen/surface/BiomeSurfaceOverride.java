package net.foxboi.salted.common.levelgen.surface;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.SurfaceRules;

public record BiomeSurfaceOverride(
        HolderSet<Biome> biomes,
        SurfaceRules.RuleSource rule
) {
    public static final Codec<BiomeSurfaceOverride> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(it -> it.biomes),
            SurfaceRules.RuleSource.CODEC.fieldOf("rule").forGetter(it -> it.rule)
    ).apply(inst, BiomeSurfaceOverride::new));
}
