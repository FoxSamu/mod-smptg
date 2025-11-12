package net.foxboi.salted.common.color;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.Level;

public record InterpolateColor(
    BiomeColor a,
    BiomeColor b,
    float t
) implements BiomeColor {
    public static final MapCodec<InterpolateColor> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        BiomeColor.CODEC.fieldOf("a").forGetter(InterpolateColor::a),
        BiomeColor.CODEC.fieldOf("b").forGetter(InterpolateColor::b),
        Codec.floatRange(0f, 1f).fieldOf("t").forGetter(InterpolateColor::t)
    ).apply(inst, InterpolateColor::new));

    public static final Type<InterpolateColor> TYPE = new Type<>(CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Override
    public int sample(Level level, BlockPos pos) {
        return ARGB.lerp(t, a.sample(level, pos), b.sample(level, pos));
    }

    @Override
    public int sampleItem() {
        return ARGB.lerp(t, a.sampleItem(), b.sampleItem());
    }
}
