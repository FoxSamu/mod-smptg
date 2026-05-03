package net.foxboi.salted.common.levelgen.tree;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class RedwoodFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<RedwoodFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            IntProviders.codec(0, 16).fieldOf("offset").forGetter(it -> it.offset),
            Codec.intRange(0, 16).listOf().fieldOf("radii").forGetter(it -> it.radii),
            Codec.intRange(1, 4).fieldOf("layers_per_radius").forGetter(it -> it.layersPerRadius)
    ).apply(inst, RedwoodFoliagePlacer::new));

    public static final FoliagePlacerType<RedwoodFoliagePlacer> TYPE = new FoliagePlacerType<>(CODEC);

    private final List<Integer> radii;
    private final int layersPerRadius;
    private final int maxRadius;

    public RedwoodFoliagePlacer(IntProvider offset, List<Integer> radii, int layersPerRadius) {
        super(ConstantInt.of(maxRadius(radii)), offset);

        this.radii = radii;
        this.layersPerRadius = layersPerRadius;
        this.maxRadius = maxRadius(radii);
    }

    private static int maxRadius(List<Integer> radii) {
        return radii.stream().mapToInt(it -> it).max().orElse(1);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return TYPE;
    }

    @Override
    protected void createFoliage(
            WorldGenLevel level,
            FoliageSetter setter,
            RandomSource rng,
            TreeConfiguration config,
            int treeHeight,
            FoliageAttachment attachment,
            int height,
            int radius,
            int offset
    ) {
        var radiusOffset = attachment.radiusOffset() + (radius - maxRadius);

        var pos = attachment.pos().above(offset);
        var currentHeight = 0;

        for (int layerBaseRadius : radii) {
            for (var layer = 0; layer < layersPerRadius; layer ++) {
                var layerRadius = layerBaseRadius - (layersPerRadius - (layer + 1)) + radiusOffset;

                placeLeavesRow(level, setter, rng, config, pos, layerRadius, -currentHeight, attachment.doubleTrunk());

                currentHeight ++;

                if (currentHeight > height) {
                    return;
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
        // Compute the amount of layers we can fit
        var height = 0;
        var radiiCount = radii.size();

        for (var i = 0; i < radiiCount; i ++) {
            if (height + layersPerRadius >= treeHeight) {
                return height;
            }

            height += layersPerRadius;
        }

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
        var sum = localX + localZ;

        var cutoff = (radius + 1) / 2;
        var maxSum = 2 * radius - cutoff;

        return sum > maxSum;
    }
}
