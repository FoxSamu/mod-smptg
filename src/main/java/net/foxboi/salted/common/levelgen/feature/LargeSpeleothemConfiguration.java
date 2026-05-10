package net.foxboi.salted.common.levelgen.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record LargeSpeleothemConfiguration(
        Speleothem speleothem,
        int floorToCeilingSearchRange,
        IntProvider columnRadius,
        FloatProvider heightScale,
        float maxColumnRadiusToCaveHeightRatio,
        FloatProvider stalactiteBluntness,
        FloatProvider stalagmiteBluntness,
        FloatProvider windSpeed,
        int minRadiusForWind,
        float minBluntnessForWind
) implements FeatureConfiguration {
    public static final Codec<LargeSpeleothemConfiguration> CODEC = RecordCodecBuilder.create(i -> i.group(
            Speleothem.CODEC
                    .fieldOf("block")
                    .forGetter(LargeSpeleothemConfiguration::speleothem),

            Codec.intRange(1, 512)
                    .fieldOf("floor_to_ceiling_search_range")
                    .orElse(30)
                    .forGetter(LargeSpeleothemConfiguration::floorToCeilingSearchRange),

            IntProviders.codec(1, 60)
                    .fieldOf("column_radius")
                    .forGetter(LargeSpeleothemConfiguration::columnRadius),

            FloatProviders.codec(0, 20)
                    .fieldOf("height_scale")
                    .forGetter(LargeSpeleothemConfiguration::heightScale),

            Codec.floatRange(.1f, 1)
                    .fieldOf("max_column_radius_to_cave_height_ratio")
                    .forGetter(LargeSpeleothemConfiguration::maxColumnRadiusToCaveHeightRatio),

            FloatProviders.codec(.1f, 10)
                    .fieldOf("stalactite_bluntness")
                    .forGetter(LargeSpeleothemConfiguration::stalactiteBluntness),

            FloatProviders.codec(.1f, 10)
                    .fieldOf("stalagmite_bluntness")
                    .forGetter(LargeSpeleothemConfiguration::stalagmiteBluntness),

            FloatProviders.codec(0, 2)
                    .fieldOf("wind_speed")
                    .forGetter(LargeSpeleothemConfiguration::windSpeed),

            Codec.intRange(0, 100)
                    .fieldOf("min_radius_for_wind")
                    .forGetter(LargeSpeleothemConfiguration::minRadiusForWind),

            Codec.floatRange(0, 5)
                    .fieldOf("min_bluntness_for_wind")
                    .forGetter(LargeSpeleothemConfiguration::minBluntnessForWind)
    ).apply(i, LargeSpeleothemConfiguration::new));

    public boolean isBaseMaterialOrLava(WorldGenLevel level, BlockPos pos) {
        return speleothem.canGrowFrom().test(level, pos) || level.getBlockState(pos).is(Blocks.LAVA);
    }
}