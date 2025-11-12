package net.foxboi.salted.common.color;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public record DryFoliageColor() implements BiomeColor {
    public static final DryFoliageColor INSTANCE = new DryFoliageColor();
    public static final MapCodec<DryFoliageColor> CODEC = MapCodec.unit(INSTANCE);
    public static final Type<DryFoliageColor> TYPE = new Type<>(CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Override
    public int sample(Level level, BlockPos pos) {
        return BiomeColors.calculateBlockTintUncached(level, pos, net.minecraft.client.renderer.BiomeColors.DRY_FOLIAGE_COLOR_RESOLVER);
    }

    @Override
    public int sampleItem() {
        return net.minecraft.world.level.DryFoliageColor.FOLIAGE_DRY_DEFAULT;
    }
}
