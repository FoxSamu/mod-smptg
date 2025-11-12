package net.foxboi.salted.common.levelgen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class BrushFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<BrushFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
            inst -> foliagePlacerParts(inst)
                    .and(inst.group(
                            Codec.intRange(0, 16).fieldOf("height").forGetter(it -> it.height),
                            IntProvider.codec(0, 16).fieldOf("hanging_length").forGetter(it -> it.hangingLength)
                    ))
                    .apply(inst, BrushFoliagePlacer::new)
    );
    public static final FoliagePlacerType<BrushFoliagePlacer> TYPE = new FoliagePlacerType<>(CODEC);

    private final int height;
    private final IntProvider hangingLength;

    public BrushFoliagePlacer(IntProvider radius, IntProvider offset, int height, IntProvider hangingLength) {
        super(radius, offset);

        this.height = height;
        this.hangingLength = hangingLength;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return TYPE;
    }

    @Override
    protected void createFoliage(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource rng,
            TreeConfiguration config,
            int treeHeight,
            FoliageAttachment attachment,
            int height,
            int radius,
            int offset
    ) {
        var pos = attachment.pos().above(offset);
        var offsetRadius = radius + attachment.radiusOffset();

        for (int localY = 0; localY > - height; localY--) {
            var layerRadius = localY == 0
                    ? offsetRadius - 1
                    : offsetRadius;

            placeLeavesRow(level, setter, rng, config, pos, layerRadius, localY, attachment.doubleTrunk());
        }

        placeLeavesRowHanging(level, setter, rng, config, pos, offsetRadius, -height, attachment.doubleTrunk());
    }

    protected void placeLeavesRowHanging(
            LevelSimulatedReader level,
            FoliageSetter setter,
            RandomSource rng,
            TreeConfiguration config,
            BlockPos basePos,
            int radius,
            int localY,
            boolean doubleTrunk
    ) {
        var trunkOff = doubleTrunk ? 1 : 0;
        var mpos = new BlockPos.MutableBlockPos();

        for (var localX = -radius; localX <= radius + trunkOff; localX++) {
            for (var localZ = -radius; localZ <= radius + trunkOff; localZ++) {
                if (!shouldSkipLocationSigned(rng, localX, localY, localZ, radius, doubleTrunk)) {
                    mpos.setWithOffset(basePos, localX, localY, localZ);
                    tryPlaceLeaf(level, setter, rng, config, mpos);

                    int extra = hangingLength.sample(rng);

                    for (int i = 0; i < extra; i++) {
                        mpos.move(Direction.DOWN);
                        if (!tryPlaceLeaf(level, setter, rng, config, mpos)) {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public int foliageHeight(
            RandomSource rng,
            int treeHeight,
            TreeConfiguration config
    ) {
        return height;
    }

    @Override
    protected boolean shouldSkipLocation(
            RandomSource rng,
            int localX,
            int localY,
            int localZ,
            int radius,
            boolean doubleTrunk
    ) {
        return localY != 0 && (localX + localZ < radius + radius);
    }
}
