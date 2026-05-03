package net.foxboi.salted.common.levelgen.feature;

import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ColumnPlantConfiguration(
        Direction direction,
        BlockStateProvider block,
        BlockPredicate canReplace,
        IntProvider staticLength,
        FloatProvider dynamicLength,
        int minLength,
        int maxLength,
        boolean allowGrowth
) implements FeatureConfiguration {
    private static final Codec<ColumnPlantConfiguration> RAW_CODEC = RecordCodecBuilder.create(i -> i.group(
            Direction.VERTICAL_CODEC.fieldOf("direction").forGetter(ColumnPlantConfiguration::direction),
            BlockStateProvider.CODEC.fieldOf("block").forGetter(ColumnPlantConfiguration::block),
            BlockPredicate.CODEC.fieldOf("can_replace").forGetter(ColumnPlantConfiguration::canReplace),
            IntProviders.codec(0, 4096).fieldOf("static_length").forGetter(ColumnPlantConfiguration::staticLength),
            FloatProviders.codec(0f, 1f).fieldOf("dynamic_length").forGetter(ColumnPlantConfiguration::dynamicLength),
            Codec.intRange(1, 4096).fieldOf("min_length").forGetter(ColumnPlantConfiguration::minLength),
            Codec.intRange(1, 4096).fieldOf("max_length").forGetter(ColumnPlantConfiguration::maxLength),
            Codec.BOOL.optionalFieldOf("allow_growth", false).forGetter(ColumnPlantConfiguration::allowGrowth)
    ).apply(i, ColumnPlantConfiguration::new));

    public static final Codec<ColumnPlantConfiguration> CODEC = RAW_CODEC.validate(it -> {
        if (it.maxLength < it.minLength) {
            return DataResult.error(() -> "minLength < maxLength", it);
        }

        return DataResult.success(it);
    });
}
