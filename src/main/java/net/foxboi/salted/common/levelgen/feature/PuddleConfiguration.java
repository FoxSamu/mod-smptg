package net.foxboi.salted.common.levelgen.feature;

import java.util.Optional;
import java.util.function.Function;

import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;
import net.minecraft.world.level.material.Fluid;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record PuddleConfiguration(
        BlockStateProvider fluid,
        BlockPredicate canPlaceAt,
        FloatProvider size,
        Optional<BlockStateProvider> edge,
        FloatProvider edgeRadius,
        Optional<BlockStateProvider> floor
) implements FeatureConfiguration {
    public static final Codec<PuddleConfiguration> CODEC = RecordCodecBuilder.create(i -> i.group(
            BlockStateProvider.CODEC.fieldOf("fluid").forGetter(PuddleConfiguration::fluid),
            BlockPredicate.CODEC.fieldOf("can_place_at").forGetter(PuddleConfiguration::canPlaceAt),
            FloatProviders.codec(1f, 64f).fieldOf("size").forGetter(PuddleConfiguration::size),
            BlockStateProvider.CODEC.optionalFieldOf("edge").forGetter(PuddleConfiguration::edge),
            FloatProviders.codec(1f, 2f).optionalFieldOf("edge_radius", ConstantFloat.of(1.3f)).forGetter(PuddleConfiguration::edgeRadius),
            BlockStateProvider.CODEC.optionalFieldOf("floor").forGetter(PuddleConfiguration::floor)
    ).apply(i, PuddleConfiguration::new));

    public PuddleConfiguration(
            BlockStateProvider fluid,
            BlockPredicate canPlaceAt,
            FloatProvider size
    ) {
        this(fluid, canPlaceAt, size, Optional.empty(), ConstantFloat.of(1.3f), Optional.empty());
    }

    public PuddleConfiguration withFloor(BlockStateProvider floor) {
        return new PuddleConfiguration(fluid, canPlaceAt, size, edge, edgeRadius, Optional.of(floor));
    }

    public PuddleConfiguration withFloor(BlockStateProvider floor, BlockPredicate canReplace) {
        return withFloor(RuleBasedStateProvider.ifTrueThenProvide(canReplace, floor));
    }

    public PuddleConfiguration withEdge(BlockStateProvider edge) {
        return new PuddleConfiguration(fluid, canPlaceAt, size, Optional.of(edge), edgeRadius, floor);
    }

    public PuddleConfiguration withEdge(BlockStateProvider edge, BlockPredicate canReplace) {
        return withEdge(RuleBasedStateProvider.ifTrueThenProvide(canReplace, edge));
    }

    public PuddleConfiguration withEdge(BlockStateProvider edge, FloatProvider radius) {
        return new PuddleConfiguration(fluid, canPlaceAt, size, Optional.of(edge), radius, floor);
    }

    public PuddleConfiguration withEdge(BlockStateProvider edge, BlockPredicate canReplace, FloatProvider radius) {
        return withEdge(RuleBasedStateProvider.ifTrueThenProvide(canReplace, edge), radius);
    }

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

    public static PuddleConfiguration createInFlatArea(Fluid fluid, Function<Vec3i, BlockPredicate> support, FloatProvider size) {
        var testPredicate = BlockPredicate.allOf(
                support(support, fluid, Vec3i.ZERO),
                support(support, fluid, Direction.DOWN.getUnitVec3i()),
                support(support, fluid, Direction.NORTH.getUnitVec3i()),
                support(support, fluid, Direction.EAST.getUnitVec3i()),
                support(support, fluid, Direction.SOUTH.getUnitVec3i()),
                support(support, fluid, Direction.WEST.getUnitVec3i()),
                BlockPredicate.matchesTag(Direction.UP.getUnitVec3i(), BlockTags.AIR),
                BlockPredicate.matchesTag(Direction.NORTH.getUnitVec3i().above(), BlockTags.AIR),
                BlockPredicate.matchesTag(Direction.EAST.getUnitVec3i().above(), BlockTags.AIR),
                BlockPredicate.matchesTag(Direction.SOUTH.getUnitVec3i().above(), BlockTags.AIR),
                BlockPredicate.matchesTag(Direction.WEST.getUnitVec3i().above(), BlockTags.AIR)
        );

        return new PuddleConfiguration(BlockStateProvider.simple(fluid.defaultFluidState().createLegacyBlock()), testPredicate, size);
    }
}
