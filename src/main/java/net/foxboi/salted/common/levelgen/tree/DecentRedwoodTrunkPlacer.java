package net.foxboi.salted.common.levelgen.tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class DecentRedwoodTrunkPlacer extends AbstractRedwoodTrunkPlacer {
    public static final MapCodec<DecentRedwoodTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(inst -> redwoodTrunkPlacerParts(inst).apply(inst, DecentRedwoodTrunkPlacer::new));

    public static final TrunkPlacerType<DecentRedwoodTrunkPlacer> TYPE = new TrunkPlacerType<>(CODEC);

    private static final int BASE_RADIUS = 3;

    private static final float[] COLUMN_PROPORTIONS = {
            0.00f, 0.00f, 0.26f, 0.26f, 0.00f, 0.00f,
            0.00f, 0.55f, 0.70f, 0.70f, 0.55f, 0.00f,
            0.26f, 0.70f, 1.00f, 1.00f, 0.70f, 0.26f,
            0.26f, 0.70f, 1.00f, 1.00f, 0.70f, 0.26f,
            0.00f, 0.55f, 0.70f, 0.70f, 0.55f, 0.00f,
            0.00f, 0.00f, 0.26f, 0.26f, 0.00f, 0.00f,
    };

    private static final int[] COLUMN_RANDOMNESS_MIN = {
            +0, +0, +0, +0, +0, +0,
            +0, -1, -1, -1, -1, +0,
            +0, -1, -1, -1, -1, +0,
            +0, -1, -1, -1, -1, +0,
            +0, -1, -1, -1, -1, +0,
            +0, +0, +0, +0, +0, +0,
    };

    private static final int[] COLUMN_RANDOMNESS_MAX = {
            +0, +0, +3, +3, +0, +0,
            +0, +3, +0, +0, +3, +0,
            +3, +0, +0, +0, +0, +3,
            +3, +0, +0, +0, +0, +3,
            +0, +3, +0, +0, +3, +0,
            +0, +0, +3, +3, +0, +0,
    };

    public DecentRedwoodTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, IntProvider depth, IntProvider dirtLayers) {
        super(baseHeight, heightRandA, heightRandB, depth, dirtLayers);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return TYPE;
    }

    protected int baseRadius() {
        return BASE_RADIUS;
    }

    protected float getProportion(int index) {
        return COLUMN_PROPORTIONS[index];
    }

    protected int getRandomnessMin(int index) {
        return COLUMN_RANDOMNESS_MIN[index];
    }

    protected int getRandomnessMax(int index) {
        return COLUMN_RANDOMNESS_MAX[index];
    }
}
