package net.foxboi.salted.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record SpeleothemClusterConfiguration(
        Speleothem speleothem,
        int floorToCeilingSearchRange,
        IntProvider height,
        IntProvider radius,
        int maxStalagmiteStalactiteHeightDiff,
        int heightDeviation,
        IntProvider baseLayerThickness,
        FloatProvider density,
        FloatProvider wetness,
        BlockPredicate canSupportWater,
        float chanceOfColumnAtMaxDistanceFromCenter,
        int maxDistanceFromEdgeAffectingChanceOfColumn,
        int maxDistanceFromCenterAffectingHeightBias
) implements FeatureConfiguration {
    public static final Codec<SpeleothemClusterConfiguration> CODEC = RecordCodecBuilder.create(i -> i.group(
            Speleothem.CODEC
                    .fieldOf("speleothem")
                    .forGetter(SpeleothemClusterConfiguration::speleothem),

            Codec.intRange(1, 512)
                    .fieldOf("floor_to_ceiling_search_range")
                    .forGetter(SpeleothemClusterConfiguration::floorToCeilingSearchRange),

            IntProviders.codec(1, 128)
                    .fieldOf("height")
                    .forGetter(SpeleothemClusterConfiguration::height),

            IntProviders.codec(1, 128)
                    .fieldOf("radius")
                    .forGetter(SpeleothemClusterConfiguration::radius),

            Codec.intRange(0, 64)
                    .fieldOf("max_stalagmite_stalactite_height_diff")
                    .forGetter(SpeleothemClusterConfiguration::maxStalagmiteStalactiteHeightDiff),

            Codec.intRange(1, 64)
                    .fieldOf("height_deviation")
                    .forGetter(SpeleothemClusterConfiguration::heightDeviation),

            IntProviders.codec(0, 128)
                    .fieldOf("base_layer_thickness")
                    .forGetter(SpeleothemClusterConfiguration::baseLayerThickness),

            FloatProviders.codec(0, 2)
                    .fieldOf("density")
                    .forGetter(SpeleothemClusterConfiguration::density),

            FloatProviders.codec(0, 2)
                    .fieldOf("wetness")
                    .forGetter(SpeleothemClusterConfiguration::wetness),

            BlockPredicate.CODEC
                    .fieldOf("can_support_water")
                    .forGetter(SpeleothemClusterConfiguration::canSupportWater),

            Codec.floatRange(0, 1)
                    .fieldOf("chance_of_column_at_max_distance_from_center")
                    .forGetter(SpeleothemClusterConfiguration::chanceOfColumnAtMaxDistanceFromCenter),

            Codec.intRange(1, 64)
                    .fieldOf("max_distance_from_edge_affecting_chance_of_column")
                    .forGetter(SpeleothemClusterConfiguration::maxDistanceFromEdgeAffectingChanceOfColumn),

            Codec.intRange(1, 64)
                    .fieldOf("max_distance_from_center_affecting_height_bias")
                    .forGetter(SpeleothemClusterConfiguration::maxDistanceFromCenterAffectingHeightBias)
    ).apply(i, SpeleothemClusterConfiguration::new));
}
