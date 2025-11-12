package net.foxboi.salted.common.color;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public record FoliageColor() implements BiomeColor {
    public static final FoliageColor INSTANCE = new FoliageColor();
    public static final MapCodec<FoliageColor> CODEC = MapCodec.unit(INSTANCE);
    public static final Type<FoliageColor> TYPE = new Type<>(CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Override
    public int sample(Level level, BlockPos pos) {
        return BiomeColors.calculateBlockTintUncached(level, pos, net.minecraft.client.renderer.BiomeColors.FOLIAGE_COLOR_RESOLVER);
    }

    @Override
    public int sampleItem() {
        return net.minecraft.world.level.FoliageColor.FOLIAGE_DEFAULT;
    }
}
