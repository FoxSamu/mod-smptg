package net.foxboi.salted.common.levelgen.stateprovider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public final class EitherStateProvider extends BlockStateProvider {
    public static final MapCodec<EitherStateProvider> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            BlockStateProvider.CODEC.fieldOf("a").forGetter(it -> it.a),
            BlockStateProvider.CODEC.fieldOf("b").forGetter(it -> it.b),
            Codec.doubleRange(0, 1).fieldOf("b_chance").forGetter(it -> it.bChance)
    ).apply(inst, EitherStateProvider::new));

    public static final BlockStateProviderType<EitherStateProvider> TYPE = new BlockStateProviderType<>(CODEC);

    private final BlockStateProvider a;
    private final BlockStateProvider b;
    private final double bChance;

    public EitherStateProvider(
            BlockStateProvider a,
            BlockStateProvider b,
            double bChance
    ) {
        this.a = a;
        this.b = b;
        this.bChance = bChance;
    }

    @Override
    protected BlockStateProviderType<?> type() {
        return TYPE;
    }

    @Override
    public BlockState getState(WorldGenLevel level, RandomSource rng, BlockPos pos) {
        return (rng.nextDouble() >= bChance ? b : a).getState(level, rng, pos);
    }
}
