package net.foxboi.salted.common.color;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public record GrassColor() implements BiomeColor {
    public static final GrassColor INSTANCE = new GrassColor();
    public static final MapCodec<GrassColor> CODEC = MapCodec.unit(INSTANCE);
    public static final Type<GrassColor> TYPE = new Type<>(CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Override
    public int sample(Level level, BlockPos pos) {
        return BiomeColors.calculateBlockTintUncached(level, pos, net.minecraft.client.renderer.BiomeColors.GRASS_COLOR_RESOLVER);
    }

    @Override
    public int sampleItem() {
        return net.minecraft.world.level.GrassColor.getDefaultColor();
    }
}
