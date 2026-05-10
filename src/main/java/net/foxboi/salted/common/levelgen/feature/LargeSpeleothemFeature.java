package net.foxboi.salted.common.levelgen.feature;

import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.phys.Vec3;

import net.foxboi.salted.common.levelgen.LevelgenUtil;
import net.foxboi.salted.common.misc.Range;

import static net.foxboi.salted.common.levelgen.LevelgenUtil.isEmptyOrWaterOrLava;

public class LargeSpeleothemFeature extends Feature<LargeSpeleothemConfiguration> {
    public LargeSpeleothemFeature() {
        super(LargeSpeleothemConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<LargeSpeleothemConfiguration> context) {
        var level = context.level();
        var origin = context.origin();
        var config = context.config();
        var random = context.random();

        if (!LevelgenUtil.isEmptyOrWater(level, origin)) {
            return false;
        }

        var column = LevelgenUtil.scanColumn(level, origin, config.floorToCeilingSearchRange(), LevelgenUtil::isEmptyOrWater, config::isBaseMaterialOrLava);
        if (column == null || column.length().isEmpty()) {
            return false;
        }

        var start = column.start().orElseThrow();
        var end = column.end().orElseThrow();
        var length = column.length().orElseThrow();

        if (length < 4) {
            return false;
        }

        var maxColumnRadiusBasedOnColumnHeight = (int) (length * config.maxColumnRadiusToCaveHeightRatio());
        var maxColumnRadius = Mth.clamp(maxColumnRadiusBasedOnColumnHeight, config.columnRadius().minInclusive(), config.columnRadius().maxInclusive());

        var radius = Mth.randomBetweenInclusive(random, config.columnRadius().minInclusive(), maxColumnRadius);

        var stalactite = makeSpeleothem(config.speleothem(), origin.atY(end - 1), false, random, radius, config.stalactiteBluntness(), config.heightScale());
        var stalagmite = makeSpeleothem(config.speleothem(), origin.atY(start + 1), true, random, radius, config.stalagmiteBluntness(), config.heightScale());

        var wind = stalactite.isSuitableForWind(config) && stalagmite.isSuitableForWind(config)
                ? new WindOffsetter(origin.getY(), random, config.windSpeed())
                : new WindOffsetter();

        var stalactiteBaseEmbeddedInStone = stalactite.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(level, wind);
        var stalagmiteBaseEmbeddedInStone = stalagmite.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(level, wind);

        if (stalactiteBaseEmbeddedInStone) {
            stalactite.placeBlocks(level, random, wind);
        }

        if (stalagmiteBaseEmbeddedInStone) {
            stalagmite.placeBlocks(level, random, wind);
        }

        if (SharedConstants.DEBUG_LARGE_DRIPSTONE) {
            placeDebugMarkers(level, origin, column, wind);
        }

        return true;
    }

    private static LargeDripstone makeSpeleothem(Speleothem speleothem, BlockPos root, boolean pointingUp, RandomSource random, int radius, FloatProvider bluntness, FloatProvider heightScale) {
        return new LargeDripstone(speleothem, root, pointingUp, radius, bluntness.sample(random), heightScale.sample(random));
    }

    private void placeDebugMarkers(WorldGenLevel level, BlockPos origin, Range range, WindOffsetter wind) {
        var start = range.start().orElseThrow();
        var end = range.end().orElseThrow();

        level.setBlock(wind.offset(origin.atY(end - 1)), Blocks.DIAMOND_BLOCK.defaultBlockState(), 2);
        level.setBlock(wind.offset(origin.atY(start + 1)), Blocks.GOLD_BLOCK.defaultBlockState(), 2);

        var mpos = origin.atY(start + 2).mutable();
        while (mpos.getY() < end - 1) {
            var windAdjustedPos = wind.offset(mpos);

            if (LevelgenUtil.isEmptyOrWater(level, windAdjustedPos) || level.getBlockState(windAdjustedPos).is(Blocks.DRIPSTONE_BLOCK)) {
                level.setBlock(windAdjustedPos, Blocks.CREEPER_HEAD.defaultBlockState(), 2);
            }
            mpos.move(Direction.UP);
        }
    }

    private static double getSpeleothemHeight(double xzDistanceFromCenter, double dripstoneRadius, double scale, double bluntness) {
        if (xzDistanceFromCenter < bluntness) {
            xzDistanceFromCenter = bluntness;
        }

        var cutoff = 0.384;

        var r = xzDistanceFromCenter / dripstoneRadius * cutoff;

        var part1 = 3 / 4f * Math.pow(r, 4 / 3f);
        var part2 = Math.pow(r, 2 / 3f);
        var part3 = 1 / 3f * Math.log(r);

        var heightRelativeToMaxRadius = scale * (part1 - part2 - part3);
        heightRelativeToMaxRadius = Math.max(heightRelativeToMaxRadius, 0);

        return heightRelativeToMaxRadius / cutoff * dripstoneRadius;
    }

    protected static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel level, BlockPos center, int xzRadius) {
        if (isEmptyOrWaterOrLava(level, center)) {
            return false;
        }

        var arcLength = 6f;
        var angleIncrement = arcLength / xzRadius;

        for (var angle = 0f; angle < (float) (Math.PI * 2); angle += angleIncrement) {
            var dx = (int)(Mth.cos(angle) * xzRadius);
            var dz = (int)(Mth.sin(angle) * xzRadius);

            if (isEmptyOrWaterOrLava(level, center.offset(dx, 0, dz))) {
                return false;
            }
        }

        return true;
    }

    private static final class LargeDripstone {
        private final Speleothem speleothem;
        private BlockPos root;
        private final boolean pointingUp;
        private int radius;
        private final double bluntness;
        private final double scale;

        LargeDripstone(Speleothem speleothem, BlockPos root, boolean pointingUp, int radius, double bluntness, double scale) {
            this.speleothem = speleothem;
            this.root = root;
            this.pointingUp = pointingUp;
            this.radius = radius;
            this.bluntness = bluntness;
            this.scale = scale;
        }

        int getHeight() {
            return getHeightAtRadius(0);
        }

        int getMinY() {
            return pointingUp
                    ? root.getY()
                    : root.getY() - getHeight();
        }

        int getMaxY() {
            return !pointingUp
                    ? root.getY()
                    : root.getY() + getHeight();
        }

        boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel level, WindOffsetter wind) {
            while (radius > 1) {
                var newRoot = root.mutable();
                var maxTries = Math.min(10, getHeight());

                for (var i = 0; i < maxTries; i++) {
                    if (level.getBlockState(newRoot).is(Blocks.LAVA)) {
                        return false;
                    }

                    if (isCircleMostlyEmbeddedInStone(level, wind.offset(newRoot), radius)) {
                        root = newRoot;
                        return true;
                    }

                    newRoot.move(pointingUp ? Direction.DOWN : Direction.UP);
                }

                this.radius /= 2;
            }

            return false;
        }

        int getHeightAtRadius(float checkRadius) {
            return (int) getSpeleothemHeight(checkRadius, radius, scale, bluntness);
        }

        void placeBlocks(WorldGenLevel level, RandomSource random, LargeSpeleothemFeature.WindOffsetter wind) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    float currentRadius = Mth.sqrt(dx * dx + dz * dz);
                    if (currentRadius > radius) {
                        continue;
                    }

                    int height = getHeightAtRadius(currentRadius);
                    if (height <= 0) {
                        continue;
                    }

                    if (random.nextFloat() < 0.2) {
                        height = (int) (height * Mth.randomBetween(random, .8f, 1));
                    }

                    var pos = root.offset(dx, 0, dz).mutable();
                    var hasBeenOutOfStone = false;

                    int maxY = pointingUp
                            ? level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ())
                            : Integer.MAX_VALUE;

                    for (var i = 0; i < height && pos.getY() < maxY; i++) {
                        var windAdjustedPos = wind.offset(pos);

                        if (LevelgenUtil.isEmptyOrWaterOrLava(level, windAdjustedPos)) {
                            hasBeenOutOfStone = true;

                            var block = SharedConstants.DEBUG_LARGE_DRIPSTONE
                                    ? Blocks.GLASS.defaultBlockState()
                                    : speleothem.base().getState(level, random, windAdjustedPos);

                            level.setBlock(windAdjustedPos, block, 2);
                            continue;
                        }

                        if (hasBeenOutOfStone && level.getBlockState(windAdjustedPos).is(BlockTags.BASE_STONE_OVERWORLD)) {
                            break;
                        }

                        pos.move(pointingUp ? Direction.UP : Direction.DOWN);
                    }
                }
            }
        }

        boolean isSuitableForWind(LargeSpeleothemConfiguration config) {
            return this.radius >= config.minRadiusForWind() && bluntness >= config.minBluntnessForWind();
        }
    }

    private static final class WindOffsetter {
        private final int originY;
        private final Vec3 windSpeed;

        WindOffsetter(int originY, RandomSource random, FloatProvider windSpeedRange) {
            this.originY = originY;

            var speed = windSpeedRange.sample(random);
            var direction = Mth.randomBetween(random, 0, Mth.PI);

            windSpeed = new Vec3(
                    Mth.cos(direction) * speed,
                    0,
                    Mth.sin(direction) * speed
            );
        }

        WindOffsetter() {
            this.originY = 0;
            this.windSpeed = null;
        }

        BlockPos offset(BlockPos pos) {
            if (windSpeed == null) {
                return pos;
            }

            var dy = originY - pos.getY();
            var totalWindAdjust = windSpeed.scale(dy);
            return pos.offset(Mth.floor(totalWindAdjust.x), 0, Mth.floor(totalWindAdjust.z));
        }
    }
}
