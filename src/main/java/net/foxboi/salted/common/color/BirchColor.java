package net.foxboi.salted.common.color;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.Level;

public record BirchColor() implements BiomeColor {
    public static final BirchColor INSTANCE = new BirchColor();
    public static final MapCodec<BirchColor> CODEC = MapCodec.unit(INSTANCE);
    public static final Type<BirchColor> TYPE = new Type<>(CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Override
    public int sample(Level level, BlockPos pos) {
        return FoliageColor.FOLIAGE_BIRCH;
    }

    @Override
    public int sampleItem() {
        return FoliageColor.FOLIAGE_BIRCH;
    }
}
