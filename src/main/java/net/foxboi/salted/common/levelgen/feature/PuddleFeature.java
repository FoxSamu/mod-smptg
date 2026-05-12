package net.foxboi.salted.common.levelgen.feature;

import java.util.HashSet;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class PuddleFeature extends Feature<PuddleConfiguration> {
    public PuddleFeature() {
        super(PuddleConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<PuddleConfiguration> context) {
        var level = context.level();
        var pos = context.origin();
        var config = context.config();
        var rng = context.random();

        var fluid = config.fluid();
        var support = config.canPlaceAt();
        var floor = config.floor().orElse(null);
        var edge = config.edge().orElse(null);
        var size = config.size().sample(rng);

        var circles = circles(rng, size);

        var mpos = pos.mutable();
        var y = pos.getY();

        for (var i = 0; i < 32; i ++) {
            mpos.set(pos).setY(y);

            if (support.test(level, mpos)) {
                break;
            }

            y --;
        }

        var placements = false;

        var edgeBlocks = new HashSet<BlockPos>();

        for (var circle = 0; circle < circles; circle ++) {
            var fluidRadius = radius(rng, size);

            var offsetX = offset(rng, size);
            var offsetZ = offset(rng, size);

            var minX = Mth.floor(offsetX - fluidRadius);
            var minZ = Mth.floor(offsetZ - fluidRadius);

            var maxX = Mth.ceil(offsetX + fluidRadius);
            var maxZ = Mth.ceil(offsetZ + fluidRadius);

            for (var offX = minX; offX <= maxX; offX ++) {
                for (var offZ = minZ; offZ <= maxZ; offZ ++) {
                    mpos.setWithOffset(pos, offX, 0, offZ).setY(y);

                    var dx = offX - offsetX;
                    var dz = offZ - offsetZ;
                    if (dx * dx + dz * dz > fluidRadius * fluidRadius) {
                        continue;
                    }

                    if (support.test(level, mpos)) {
                        level.setBlock(mpos, fluid.getState(level, rng, mpos), 2);

                        if (edge != null) {
                            var edgeRadius = config.edgeRadius().sample(rng);

                            var minEdge = Mth.floor(- fluidRadius);
                            var maxEdge = Mth.ceil(+ fluidRadius);

                            for (var rX = minEdge; rX <= maxEdge; rX ++) {
                                for (var rZ = minEdge; rZ <= maxEdge; rZ ++) {
                                    if (rX * rX + rZ * rZ < edgeRadius * edgeRadius) {
                                        edgeBlocks.add(mpos.offset(rX, 0, rZ));
                                    }
                                }
                            }
                        }

                        if (floor != null) {
                            mpos.move(0, -1, 0);
                            level.setBlock(mpos, floor.getState(level, rng, mpos), 2);
                        }

                        placements = true;
                    }
                }
            }
        }

        if (edge != null) {
            for (var edgePos : edgeBlocks) {
                level.setBlock(edgePos, edge.getState(level, rng, edgePos), 2);
            }
        }

        return placements;
    }

    private static int circles(RandomSource rng, float size) {
        return (int) Mth.lerp(size / 64f, 1, rng.nextIntBetweenInclusive(6, 8));
    }

    private static float offset(RandomSource rng, float size) {
        var off = Mth.lerp(size / 64f, 1.7f, 7f);
        return rng.nextFloat() * off * 2 - off;
    }

    private static float radius(RandomSource rng, float size) {
        var min = Mth.lerp(size / 64f, 0.7f, 1.8f);
        var max = Mth.lerp(size / 64f, 2.1f, 7f);
        return Mth.lerp(rng.nextFloat(), min, max);
    }
}
