package net.foxboi.salted.common.color;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.foxboi.salted.common.Smptg;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record BlurColor(
    BiomeColor base,
    Optional<Integer> radius
) implements BiomeColor {
    public static final MapCodec<BlurColor> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        BiomeColor.CODEC.fieldOf("base").forGetter(BlurColor::base),
        Codec.intRange(0, 15).optionalFieldOf("radius").forGetter(BlurColor::radius)
    ).apply(inst, BlurColor::new));

    public static final Type<BlurColor> TYPE = new Type<>(CODEC);

    @Override
    public Type<?> type() {
        return TYPE;
    }

    @Override
    public int sample(Level level, BlockPos pos) {
        int rad = radius.orElseGet(() -> Smptg.get().getBiomeBlendRadius());

        if (rad == 0) {
            return base.sample(level, pos);
        }

        int r = 0,
            g = 0,
            b = 0,
            color,
            total = (rad * 2 + 1) * (rad * 2 + 1);

        var mpos = new BlockPos.MutableBlockPos();
        var cursor = new Cursor3D(
            pos.getX() - rad, pos.getY(), pos.getZ() - rad,
            pos.getX() + rad, pos.getY(), pos.getZ() + rad
        );

        while (cursor.advance()) {
            mpos.set(cursor.nextX(), cursor.nextY(), cursor.nextZ());

            color = base.sample(level, mpos);
            r += color >> 16 & 0xFF;
            g += color >> 8 & 0xFF;
            b += color & 0xFF;
        }

        return (r / total & 0xFF) << 16
               | (g / total & 0xFF) << 8
               | b / total & 0xFF;
    }

    @Override
    public int sampleItem() {
        return base.sampleItem();
    }
}
