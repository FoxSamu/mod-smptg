package net.foxboi.salted.common.levelgen.feature;

import java.util.Optional;

import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record SpeleothemConfiguration(
        Speleothem speleothem,
        float chanceOfTallerSpeleothem,
        Optional<Patch> patch
) implements FeatureConfiguration {
    public static final Codec<SpeleothemConfiguration> CODEC = RecordCodecBuilder.create(i -> i.group(
            Speleothem.CODEC
                    .fieldOf("speleothem")
                    .forGetter(SpeleothemConfiguration::speleothem),

            Codec.floatRange(0, 1)
                    .fieldOf("chance_of_taller_speleothem")
                    .orElse(0.2f)
                    .forGetter(SpeleothemConfiguration::chanceOfTallerSpeleothem),

            Patch.CODEC
                    .optionalFieldOf("patch")
                    .forGetter(SpeleothemConfiguration::patch)
    ).apply(i, SpeleothemConfiguration::new));

    public record Patch(
            float chanceOfDirectionalSpread,
            float chanceOfSpreadRadius2,
            float chanceOfSpreadRadius3
    ) {
        public static final Codec<Patch> CODEC = RecordCodecBuilder.create(i -> i.group(
                Codec.floatRange(0, 1)
                        .fieldOf("chance_of_directional_spread")
                        .orElse(0.7f)
                        .forGetter(Patch::chanceOfDirectionalSpread),

                Codec.floatRange(0, 1)
                        .fieldOf("chance_of_spread_radius2")
                        .orElse(0.5f)
                        .forGetter(Patch::chanceOfSpreadRadius2),

                Codec.floatRange(0, 1)
                        .fieldOf("chance_of_spread_radius3")
                        .orElse(0.5f)
                        .forGetter(Patch::chanceOfSpreadRadius3)
        ).apply(i, Patch::new));
    }
}
