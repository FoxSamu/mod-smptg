package net.foxboi.salted.common.color;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.Level;

public record MangroveColor() implements BiomeColor {
    public static final MangroveColor INSTANCE = new MangroveColor();
    public static final MapCodec<MangroveColor> CODEC = MapCodec.unit(INSTANCE);
    public static final Type<MangroveColor> TYPE = new Type<>(CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Override
    public int sample(Level level, BlockPos pos) {
        return FoliageColor.FOLIAGE_MANGROVE;
    }

    @Override
    public int sampleItem() {
        return FoliageColor.FOLIAGE_MANGROVE;
    }
}
