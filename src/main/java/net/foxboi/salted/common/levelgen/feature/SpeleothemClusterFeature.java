package net.foxboi.salted.common.levelgen.feature;

import net.foxboi.salted.common.levelgen.LevelgenUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SpeleothemClusterFeature extends Feature<SpeleothemClusterConfiguration> {
	public SpeleothemClusterFeature() {
		super(SpeleothemClusterConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<SpeleothemClusterConfiguration> context) {
		var level = context.level();
		var origin = context.origin();
		var config = context.config();
		var random = context.random();

		if (!LevelgenUtil.isEmptyOrWater(level, origin)) {
			return false;
		}

        var height = config.height().sample(random);
        var wetness = config.wetness().sample(random);
        var density = config.density().sample(random);
        var xRadius = config.radius().sample(random);
        var zRadius = config.radius().sample(random);

        for (int dx = -xRadius; dx <= xRadius; dx++) {
            for (int dz = -zRadius; dz <= zRadius; dz++) {
                var speleothemChance = getChanceOfStalagmiteOrStalactite(xRadius, zRadius, dx, dz, config);

                var pos = origin.offset(dx, 0, dz);
                placeColumn(level, random, pos, dx, dz, wetness, speleothemChance, height, density, config);
            }
        }

        return true;
    }

	private float getChanceOfStalagmiteOrStalactite(int xRadius, int zRadius, int dx, int dz, SpeleothemClusterConfiguration config) {
		var xDistanceFromEdge = xRadius - Math.abs(dx);
		var zDistanceFromEdge = zRadius - Math.abs(dz);
		var distanceFromEdge = Math.min(xDistanceFromEdge, zDistanceFromEdge);

		return Mth.clampedMap(
				distanceFromEdge,
				0f, config.maxDistanceFromEdgeAffectingChanceOfColumn(),
				config.chanceOfColumnAtMaxDistanceFromCenter(), 1f
		);
	}

	private void placeColumn(
		WorldGenLevel level,
		RandomSource random,
		BlockPos pos,
		int dx, int dz,
		float chanceOfWater,
		float chanceOfSpeleothem,
		int clusterHeight,
		float density,
        SpeleothemClusterConfiguration config
	) {
        var speleothem = config.speleothem();

        // Find space
		var column = LevelgenUtil.scanColumn(
				level, pos, config.floorToCeilingSearchRange(),
				(l, p) -> LevelgenUtil.isEmptyOrWater(l, p),
				(l, p) -> !LevelgenUtil.isEmptyOrWater(l, p)
		);

        var ceiling = column.end();
        var floor = column.start();
        if (ceiling.isEmpty() && floor.isEmpty()) {
            return;
        }

        var wantPool = random.nextFloat() < chanceOfWater;
        var wantStalactite = random.nextFloat() < chanceOfSpeleothem;
        var wantStalagmite = random.nextDouble() < chanceOfSpeleothem;

        // Maybe add water
        if (wantPool && floor.isPresent() && canPlacePool(level, pos.atY(floor.getAsInt()), config.canSupportWater())) {
            var floorY = floor.getAsInt();

            column = column.withStart(floorY - 1);
            level.setBlock(pos.atY(floorY), Blocks.WATER.defaultBlockState(), 2);
        }

        floor = column.start();

        // Create ceiling and compute stalactite
        var stalactiteHeight = 0;
        if (ceiling.isPresent() && wantStalactite && !isLava(level, pos.atY(ceiling.getAsInt()))) {
            var ceilingThickness = config.baseLayerThickness().sample(random);

			createBaseBlockColumn(level, random, pos.atY(ceiling.getAsInt()), ceilingThickness, Direction.UP, speleothem);

            var maxHeightForThisColumn = clusterHeight;
            if (floor.isPresent()) {
                maxHeightForThisColumn = Math.min(maxHeightForThisColumn, ceiling.getAsInt() - floor.getAsInt());
            }

            stalactiteHeight = getHeight(random, dx, dz, density, maxHeightForThisColumn, config);
        }

        // Create floor and compute stalagmite
        int stalagmiteHeight = 0;
        if (floor.isPresent() && wantStalagmite && !isLava(level, pos.atY(floor.getAsInt()))) {
            var floorThickness = config.baseLayerThickness().sample(random);
            createBaseBlockColumn(level, random, pos.atY(floor.getAsInt()), floorThickness, Direction.DOWN, speleothem);

            if (ceiling.isPresent()) {
                stalagmiteHeight = Math.max(0, stalactiteHeight + Mth.randomBetweenInclusive(random, -config.maxStalagmiteStalactiteHeightDiff(), config.maxStalagmiteStalactiteHeightDiff()));
            } else {
                stalagmiteHeight = getHeight(random, dx, dz, density, clusterHeight, config);
            }
        }

        // Compute speleothem lengths
        var actualStalagmiteHeight = stalactiteHeight;
        var actualStalactiteHeight = stalagmiteHeight;
        if (ceiling.isPresent() && floor.isPresent() && ceiling.getAsInt() - stalactiteHeight <= floor.getAsInt() + stalagmiteHeight) {
            var floorY = floor.getAsInt();
            var ceilingY = ceiling.getAsInt();

            var lowestStalactiteBottom = Math.max(ceilingY - stalactiteHeight, floorY + 1);
            var highestStalagmiteTop = Math.min(floorY + stalagmiteHeight, ceilingY - 1);

            var actualStalactiteBottom = Mth.randomBetweenInclusive(random, lowestStalactiteBottom, highestStalagmiteTop + 1);
            var actualStalagmiteTop = actualStalactiteBottom - 1;

            actualStalactiteHeight = ceilingY - actualStalactiteBottom;
            actualStalagmiteHeight = actualStalagmiteTop - floorY;
        }

        // Place speleothems
        var mergeTips = random.nextBoolean()
            && actualStalactiteHeight > 0
            && actualStalagmiteHeight > 0
            && column.length().isPresent()
            && actualStalactiteHeight + actualStalagmiteHeight == column.length().getAsInt();

        if (ceiling.isPresent()) {
            speleothem.grow(level, pos.atY(ceiling.getAsInt() - 1), random, Direction.DOWN, actualStalactiteHeight, mergeTips);
        }

        if (floor.isPresent()) {
            speleothem.grow(level, pos.atY(floor.getAsInt() + 1), random, Direction.UP, actualStalagmiteHeight, mergeTips);
        }
    }

	private boolean isLava(LevelReader level, BlockPos pos) {
		return level.getBlockState(pos).is(Blocks.LAVA);
	}

	private int getHeight(RandomSource random, int dx, int dz, float density, int maxHeight, SpeleothemClusterConfiguration config) {
		if (random.nextFloat() > density) {
			return 0;
		}

        var distanceFromCenter = Math.abs(dx) + Math.abs(dz);
        var heightMean = Mth.clampedMap(distanceFromCenter, 0f, config.maxDistanceFromCenterAffectingHeightBias(), maxHeight / 2f, 0f);

        return (int)randomBetweenBiased(random, 0f, maxHeight, heightMean, config.heightDeviation());
    }

	private boolean canPlacePool(WorldGenLevel level, BlockPos pos, BlockPredicate waterSupport) {
		var state = level.getBlockState(pos);
        if (state.is(Blocks.WATER) || state.is(Blocks.DRIPSTONE_BLOCK) || state.is(Blocks.POINTED_DRIPSTONE)) {
            return false;
        }

        if (level.getBlockState(pos.above()).getFluidState().is(FluidTags.WATER)) {
            return false;
        }

        for (var direction : Direction.Plane.HORIZONTAL) {
            if (!canBeAdjacentToWater(level, pos.relative(direction), waterSupport)) {
                return false;
            }
        }

        return canBeAdjacentToWater(level, pos.below(), waterSupport);
    }

	private boolean canBeAdjacentToWater(WorldGenLevel level, BlockPos pos, BlockPredicate predicate) {
		return predicate.test(level, pos) || level.getFluidState(pos).is(FluidTags.WATER);
	}

	private void createBaseBlockColumn(WorldGenLevel level, RandomSource random, BlockPos pos, int maxCount, Direction direction, Speleothem speleothem) {
		var mpos = pos.mutable();

		for (var i = 0; i < maxCount; i++) {
			if (!speleothem.placeBase(level, mpos, random)) {
				return;
			}

			mpos.move(direction);
		}
	}

    private static float randomBetweenBiased(RandomSource random, float min, float maxExclusive, float mean, float deviation) {
		return ClampedNormalFloat.sample(random, mean, deviation, min, maxExclusive);
	}
}
