package net.foxboi.salted.common.levelgen.feature;

import java.util.function.Function;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Fluid;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record PuddleConfiguration(
        BlockStateProvider fluid,
        BlockPredicate canPlaceAt,
        FloatProvider size
) implements FeatureConfiguration {
    public static final Codec<PuddleConfiguration> CODEC = RecordCodecBuilder.create(i -> i.group(
            BlockStateProvider.CODEC.fieldOf("fluid").forGetter(PuddleConfiguration::fluid),
            BlockPredicate.CODEC.fieldOf("can_place_at").forGetter(PuddleConfiguration::canPlaceAt),
            FloatProviders.codec(1f, 64f).fieldOf("size").forGetter(PuddleConfiguration::size)
    ).apply(i, PuddleConfiguration::new));

    private static BlockPredicate support(Function<Vec3i, BlockPredicate> support, Fluid fluid, Vec3i vec) {
        return BlockPredicate.anyOf(support.apply(vec), BlockPredicate.matchesFluids(vec, fluid));
    }

    public static PuddleConfiguration create(Fluid fluid, Function<Vec3i, BlockPredicate> support, FloatProvider size) {
        var testPredicate = BlockPredicate.allOf(
                support(support, fluid, Vec3i.ZERO),
                support(support, fluid, Direction.DOWN.getUnitVec3i()),
                support(support, fluid, Direction.NORTH.getUnitVec3i()),
                support(support, fluid, Direction.EAST.getUnitVec3i()),
                support(support, fluid, Direction.SOUTH.getUnitVec3i()),
                support(support, fluid, Direction.WEST.getUnitVec3i()),
                BlockPredicate.matchesTag(Direction.UP.getUnitVec3i(), BlockTags.AIR)
        );

        return new PuddleConfiguration(BlockStateProvider.simple(fluid.defaultFluidState().createLegacyBlock()), testPredicate, size);
    }
}
