package net.foxboi.salted.common.color;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public record SolidColor(int color) implements BiomeColor {
    public static final MapCodec<SolidColor> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        HEX_CODEC.fieldOf("color").forGetter(SolidColor::color)
    ).apply(inst, SolidColor::new));

    public static final Type<SolidColor> TYPE = new Type<>(CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Override
    public int sample(Level level, BlockPos pos) {
        return color;
    }

    @Override
    public int sampleItem() {
        return color;
    }
}
