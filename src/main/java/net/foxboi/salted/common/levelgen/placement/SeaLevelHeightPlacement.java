package net.foxboi.salted.common.levelgen.placement;

import java.util.stream.Stream;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class SeaLevelHeightPlacement extends PlacementModifier {
    public static final MapCodec<SeaLevelHeightPlacement> CODEC = Codec.intRange(-4096, 4096)
            .xmap(SeaLevelHeightPlacement::new, it -> it.offset)
            .fieldOf("offset");

    private final int offset;

    public SeaLevelHeightPlacement(int offset) {
        this.offset = offset;
    }

    @Override
    public Stream<BlockPos> getPositions(PlacementContext ctx, RandomSource rng, BlockPos pos) {
        return Stream.of(pos.atY(ctx.generator().getSeaLevel()));
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacementTypes.AT_SEA_LEVEL;
    }

    public static SeaLevelHeightPlacement atSeaLevel() {
        return new SeaLevelHeightPlacement(0);
    }

    public static SeaLevelHeightPlacement atSeaLevel(int offset) {
        return new SeaLevelHeightPlacement(offset);
    }
}
