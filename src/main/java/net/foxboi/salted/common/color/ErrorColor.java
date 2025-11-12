package net.foxboi.salted.common.color;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public record ErrorColor() implements BiomeColor {
    public static final ErrorColor INSTANCE = new ErrorColor();
    public static final MapCodec<ErrorColor> CODEC = MapCodec.unit(INSTANCE);
    public static final Type<ErrorColor> TYPE = new Type<>(CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Override
    public int sample(Level level, BlockPos pos) {
        int parity = (pos.getX() & 1) + (pos.getY() & 1) + (pos.getZ() & 1) & 1;
        return parity == 0 ? 0xFFFF00FF : 0xFF000000;
    }

    @Override
    public int sampleItem() {
        return 0xFFFF00FF;
    }
}
