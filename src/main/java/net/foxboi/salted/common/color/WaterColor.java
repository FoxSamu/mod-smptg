package net.foxboi.salted.common.color;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public record WaterColor() implements BiomeColor {
    public static final WaterColor INSTANCE = new WaterColor();
    public static final MapCodec<WaterColor> CODEC = MapCodec.unit(INSTANCE);
    public static final Type<WaterColor> TYPE = new Type<>(CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Override
    public int sample(Level level, BlockPos pos) {
        return BiomeColors.calculateBlockTintUncached(level, pos, net.minecraft.client.renderer.BiomeColors.WATER_COLOR_RESOLVER);
    }

    @Override
    public int sampleItem() {
        return 0xFF3F76E4; // Default water color used by most biomes
    }
}
