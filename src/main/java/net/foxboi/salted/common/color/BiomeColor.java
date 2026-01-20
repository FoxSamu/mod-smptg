package net.foxboi.salted.common.color;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.Map;
import java.util.Optional;

public interface BiomeColor {
    Codec<BiomeColor> CODEC = BiomeColors.CODEC;
    Codec<Integer> HEX_CODEC = BiomeColors.HEX_CODEC;

    static void register(Identifier id, Type<?> type) {
        BiomeColors.register(id, type);
    }

    static BiomeColor solid(int color) {
        return new SolidColor(color);
    }

    static BiomeColor grass() {
        return GrassColor.INSTANCE;
    }

    static BiomeColor birch() {
        return BirchColor.INSTANCE;
    }

    static BiomeColor spruce() {
        return SpruceColor.INSTANCE;
    }

    static BiomeColor mangrove() {
        return MangroveColor.INSTANCE;
    }

    static BiomeColor foliage() {
        return FoliageColor.INSTANCE;
    }

    static BiomeColor dryFoliage() {
        return DryFoliageColor.INSTANCE;
    }

    static BiomeColor water() {
        return WaterColor.INSTANCE;
    }

    static BiomeColor colormap(Identifier name) {
        return new ColormapColor(name, .5f, 1f);
    }

    static BiomeColor colormap(Identifier name, float defaultTemperature, float defaultDownfall) {
        return new ColormapColor(name, defaultTemperature, defaultDownfall);
    }

    static BiomeColor dispatch(Map<ResourceKey<Biome>, BiomeColor> dispatch, BiomeColor fallback) {
        return new DispatchColor(dispatch, Optional.empty(), fallback);
    }

    static BiomeColor dispatch(Map<ResourceKey<Biome>, BiomeColor> dispatch, BiomeColor item, BiomeColor fallback) {
        return new DispatchColor(dispatch, Optional.of(item), fallback);
    }

    static BiomeColor lerp(BiomeColor a, BiomeColor b, float t) {
        return new InterpolateColor(a, b, t);
    }

    static BiomeColor error() {
        return ErrorColor.INSTANCE;
    }

    Type<?> type();

    int sample(Level level, BlockPos pos);
    int sampleItem();

    record Type<C extends BiomeColor>(MapCodec<C> codec) {
    }
}
