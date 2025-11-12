package net.foxboi.salted.common.color;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.util.Map;
import java.util.Optional;

public record DispatchColor(
        Map<ResourceKey<Biome>, BiomeColor> dispatch,
        Optional<BiomeColor> itemDispatch,
        BiomeColor fallback
) implements BiomeColor {
    public static final MapCodec<DispatchColor> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec
                    .unboundedMap(ResourceKey.codec(Registries.BIOME), BiomeColor.CODEC)
                    .fieldOf("dispatch")
                    .forGetter(DispatchColor::dispatch),
            BiomeColor
                    .CODEC
                    .optionalFieldOf("item")
                    .forGetter(DispatchColor::itemDispatch),
            BiomeColor
                    .CODEC
                    .fieldOf("default")
                    .forGetter(DispatchColor::fallback)
    ).apply(inst, DispatchColor::new));

    public static final Type<DispatchColor> TYPE = new Type<>(CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Override
    public int sample(Level level, BlockPos pos) {
        var biomeHolder = level.getBiome(pos);
        var keyOpt = biomeHolder.unwrapKey();
        if (keyOpt.isPresent()) {
            var key = keyOpt.get();
            if (dispatch.containsKey(key)) {
                return dispatch.get(key).sample(level, pos);
            }
        }

        return fallback.sample(level, pos);
    }

    @Override
    public int sampleItem() {
        return itemDispatch.orElse(fallback).sampleItem();
    }
}
