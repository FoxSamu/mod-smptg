package net.foxboi.salted.common.color;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;

public record ColormapColor(
        Identifier name,
        float defaultTemperature,
        float defaultDownfall
) implements BiomeColor {
    public static final MapCodec<ColormapColor> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Identifier.CODEC.fieldOf("name").forGetter(ColormapColor::name),
            Codec.floatRange(0f, 1f).optionalFieldOf("default_temperature", 0.5f).forGetter(ColormapColor::defaultTemperature),
            Codec.floatRange(0f, 1f).optionalFieldOf("default_downfall", 1.0f).forGetter(ColormapColor::defaultDownfall)
    ).apply(inst, ColormapColor::new));

    public static final Type<ColormapColor> TYPE = new Type<>(CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Override
    public int sample(Level level, BlockPos pos) {
        var map = BiomeColors.COLORMAPS.get(name);
        if (map == null) {
            return ErrorColor.INSTANCE.sample(level, pos);
        }

        var biome = level.getBiome(pos).value();
        return map.get(
                BiomeColors.temperature(biome),
                BiomeColors.downfall(biome)
        );
    }

    @Override
    public int sampleItem() {
        var map = BiomeColors.COLORMAPS.get(name);
        if (map == null) {
            return ErrorColor.INSTANCE.sampleItem();
        }

        return map.get(
                defaultTemperature,
                defaultDownfall
        );
    }
}
