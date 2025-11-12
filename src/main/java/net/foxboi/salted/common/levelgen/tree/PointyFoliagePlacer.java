package net.foxboi.salted.common.levelgen.tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class PointyFoliagePlacer extends BlobFoliagePlacer {
    public static final MapCodec<PointyFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(inst -> blobParts(inst).apply(inst, PointyFoliagePlacer::new));
    public static final FoliagePlacerType<PointyFoliagePlacer> TYPE = new FoliagePlacerType<>(CODEC);

    public PointyFoliagePlacer(IntProvider radius, IntProvider offset, int height) {
        super(radius, offset, height);
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
        var offsetRadius = radius + attachment.radiusOffset();
        var pos = attachment.pos().above(offset);

        for (int localY = 0; localY >= - height; localY--) {
            var layerRadius = switch (localY) {
                case 0 -> Math.max(offsetRadius - 2, 0);
                case 1 -> Math.max(offsetRadius - 1, 0);
                default -> Math.max(offsetRadius - (2 + localY / 2), 0);
            };

            placeLeavesRow(level, setter, rng, config, pos, layerRadius, localY, attachment.doubleTrunk());
        }
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
        var sum = localX + localZ;
        var center = localX == 0 && localZ == 0;

        if (center) {
            return false;
        } else if (localY >= 0) {
            return false;
        } else if (localY == -1) {
            return sum > radius || rng.nextInt(4) > 0;
        } else if ((localY & 1) == 0) {
            return sum > radius;
        } else {
            return sum > radius + 1;
        }
    }
}
