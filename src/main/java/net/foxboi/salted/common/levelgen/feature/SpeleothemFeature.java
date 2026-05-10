package net.foxboi.salted.common.levelgen.feature;

import java.util.function.Consumer;

import com.mojang.serialization.Codec;
import net.foxboi.salted.common.levelgen.LevelgenUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class SpeleothemFeature extends Feature<SpeleothemConfiguration> {
    public SpeleothemFeature() {
        super(SpeleothemConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<SpeleothemConfiguration> context) {
        var level = context.level();
        var pos = context.origin();
        var random = context.random();
        var config = context.config();

        var speleothem = config.speleothem();
        var patch = config.patch();

        var tipDirection = getTipDirection(level, pos, random, speleothem);
        if (tipDirection == null) {
            return false;
        }

        var rootPos = pos.relative(tipDirection.getOpposite());
        if (patch.isPresent()) {
            createPatchBlocks(level, random, rootPos, speleothem, patch.get());
        }

        var height = random.nextFloat() < config.chanceOfTallerSpeleothem() && LevelgenUtil.isEmptyOrWater(level.getBlockState(pos.relative(tipDirection)))
                ? 2 : 1;

        speleothem.grow(level, pos, random, tipDirection, height, false);
        return true;
    }

    private static Direction getTipDirection(WorldGenLevel level, BlockPos pos, RandomSource random, Speleothem speleothem) {
        var canPlaceAbove = speleothem.canGrowFrom().test(level, pos.above());
        var canPlaceBelow = speleothem.canGrowFrom().test(level, pos.below());

        if (canPlaceAbove && canPlaceBelow) {
            return random.nextBoolean() ? Direction.DOWN : Direction.UP;
        }

        if (canPlaceAbove) {
            return Direction.DOWN;
        }

        if (canPlaceBelow) {
            return Direction.UP;
        }

        return null;
    }

    private static void createPatchBlocks(WorldGenLevel level, RandomSource random, BlockPos pos, Speleothem speleothem, SpeleothemConfiguration.Patch config) {
        speleothem.placeBase(level, pos, random);

        for (var dir : Direction.Plane.HORIZONTAL) {
            if (random.nextFloat() > config.chanceOfDirectionalSpread()) {
                continue;
            }

            var pos1 = pos.relative(dir);
            speleothem.placeBase(level, pos1, random);

            if (random.nextFloat() > config.chanceOfSpreadRadius2()) {
                continue;
            }

            var pos2 = pos1.relative(Direction.getRandom(random));
            speleothem.placeBase(level, pos2, random);

            if (random.nextFloat() > config.chanceOfSpreadRadius3()) {
                continue;
            }

            var pos3 = pos2.relative(Direction.getRandom(random));
            speleothem.placeBase(level, pos3, random);
        }
    }


    protected static void buildBaseToTipColumn(Direction direction, int totalLength, boolean mergedTip, Consumer<DripstoneThickness> consumer) {
        if (totalLength >= 3) {
            consumer.accept(DripstoneThickness.BASE);

            for (var i = 0; i < totalLength - 3; i++) {
                consumer.accept(DripstoneThickness.MIDDLE);
            }
        }

        if (totalLength >= 2) {
            consumer.accept(DripstoneThickness.FRUSTUM);
        }

        if (totalLength >= 1) {
            consumer.accept(mergedTip ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP);
        }
    }

    protected static void growSpeleothem(WorldGenLevel level, BlockPos startPos, RandomSource random, BlockPredicate canGrowFrom, BlockStateProvider material, Direction growDirection, int height, boolean mergedTip) {
        if (!canGrowFrom.test(level, startPos.relative(growDirection.getOpposite()))) {
            return;
        }

        var mpos = startPos.mutable();
        buildBaseToTipColumn(growDirection, height, mergedTip, thickness -> {
            var state = material.getState(level, random, mpos)
                    .trySetValue(BlockStateProperties.WATERLOGGED, level.isWaterAt(mpos))
                    .trySetValue(PointedDripstoneBlock.TIP_DIRECTION, growDirection)
                    .trySetValue(PointedDripstoneBlock.THICKNESS, thickness);

            level.setBlock(mpos, state, 2);
            mpos.move(growDirection);
        });
    }

}
