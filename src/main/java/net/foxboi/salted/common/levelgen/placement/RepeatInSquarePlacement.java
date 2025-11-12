package net.foxboi.salted.common.levelgen.placement;

import java.util.stream.Stream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

/**
 * Placement that will repeat a feature at every position in a 16 by 16 square, with its lowest coordinates at the
 * input position. At every repeat, a random number is rolled and if it is less than the given probability value, the
 * position is included. This modifier is used in biomes like the barley field, where a single barley plant feature is
 * repeatedly applied at every block column in a chunk.
 */
public class RepeatInSquarePlacement extends PlacementModifier {
    public static final MapCodec<RepeatInSquarePlacement> CODEC = Codec.floatRange(0f, 1f)
            .xmap(RepeatInSquarePlacement::new, it -> it.probability)
            .fieldOf("probability");

    private final float probability;

    public RepeatInSquarePlacement(float probability) {
        this.probability = probability;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext ctx, RandomSource rng, BlockPos pos) {
        return BlockPos.betweenClosedStream(pos, pos.offset(15, 0, 15))
                .filter(it -> rng.nextFloat() < probability);
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacementTypes.REPEAT_IN_SQUARE;
    }

    public static RepeatInSquarePlacement fill(float probability) {
        return new RepeatInSquarePlacement(probability);
    }
}
