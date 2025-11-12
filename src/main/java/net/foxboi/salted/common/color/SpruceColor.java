package net.foxboi.salted.common.color;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.Level;

public record SpruceColor() implements BiomeColor {
    public static final SpruceColor INSTANCE = new SpruceColor();
    public static final MapCodec<SpruceColor> CODEC = MapCodec.unit(INSTANCE);
    public static final Type<SpruceColor> TYPE = new Type<>(CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Override
    public int sample(Level level, BlockPos pos) {
        return FoliageColor.FOLIAGE_EVERGREEN;
    }

    @Override
    public int sampleItem() {
        return FoliageColor.FOLIAGE_EVERGREEN;
    }
}
