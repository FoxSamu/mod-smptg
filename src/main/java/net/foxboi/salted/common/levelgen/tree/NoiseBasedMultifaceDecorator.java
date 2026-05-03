package net.foxboi.salted.common.levelgen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2DoubleOpenHashMap;
import net.foxboi.salted.common.block.AbstractMultifaceBlock;
import net.foxboi.salted.common.misc.Misc;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class NoiseBasedMultifaceDecorator extends TreeDecorator {
    public static final MapCodec<NoiseBasedMultifaceDecorator> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                    IntProviders.codec(0, Integer.MAX_VALUE).fieldOf("min_y").forGetter(it -> it.yOffset),
                    IntProviders.codec(1, Integer.MAX_VALUE).fieldOf("max_y").forGetter(it -> it.layers),
                    Codec.floatRange(-1f, 1f).fieldOf("low_probability").forGetter(it -> it.lowProbability),
                    Codec.floatRange(-1f, 1f).fieldOf("high_probability").forGetter(it -> it.highProbability),
                    Codec.DOUBLE.optionalFieldOf("noise_field_scale", 1d).forGetter(it -> it.noiseFieldScale),
                    Codec.DOUBLE.optionalFieldOf("noise_value_scale", 1d).forGetter(it -> it.noiseValueScale),
                    BlockStateProvider.CODEC.fieldOf("moss").forGetter(it -> it.moss),
                    NormalNoise.NoiseParameters.CODEC.fieldOf("noise").forGetter(it -> it.noise)
            ).apply(inst, NoiseBasedMultifaceDecorator::new)
    );

    public static final TreeDecoratorType<NoiseBasedMultifaceDecorator> TYPE = new TreeDecoratorType<>(CODEC);

    /**
     * Vertical offset of the first layer of fungi, relative to the Y coordinate of the lowest log block.
     */
    private final IntProvider yOffset;

    /**
     * Amount of layers of fungi.
     */
    private final IntProvider layers;

    /**
     * Lowest probability that a fungi is placed in a certain position.
     */
    private final float lowProbability;

    /**
     * Highest probability that a fungi is placed in a certain position.
     */
    private final float highProbability;

    /**
     * Scale factor for the noise field.
     */
    private final double noiseFieldScale;

    /**
     * Scale factor for the noise value.
     */
    private final double noiseValueScale;

    /**
     * Moss block.
     */
    private final BlockStateProvider moss;

    /**
     * Noise function.
     */
    private final Holder<NormalNoise.NoiseParameters> noise;

    public NoiseBasedMultifaceDecorator(IntProvider yOffset, IntProvider layers, float lowProbability, float highProbability, double noiseFieldScale, double noiseValueScale, BlockStateProvider moss, Holder<NormalNoise.NoiseParameters> noise) {
        this.yOffset = yOffset;
        this.layers = layers;
        this.lowProbability = lowProbability;
        this.highProbability = highProbability;
        this.noiseFieldScale = noiseFieldScale;
        this.noiseValueScale = noiseValueScale;
        this.moss = moss;
        this.noise = noise;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return TYPE;
    }

    private float getProbability(double noise) {
        var t = Mth.clamp((noise + 1) / 2, 0, 1);
        var p = Mth.lerp((float) t, lowProbability, highProbability);
        return Mth.clamp(p, 0, 1);
    }

    @Override
    public void place(Context context) {
        var level = context.level();

        var rng = context.random();

        // Find lowest log value, this is the lowest layer we place moss at
        var lowestLogY = Integer.MAX_VALUE;
        for (var pos : context.logs()) {
            if (pos.getY() < lowestLogY && context.checkBlock(pos, it -> it.is(BlockTags.LOGS))) {
                lowestLogY = pos.getY();
            }
        }

        var minY = lowestLogY + yOffset.sample(rng);
        var maxY = minY + layers.sample(rng) - 1;

        var dirs = Misc.HORIZONTAL_DIRECTIONS;

        var mpos = new BlockPos.MutableBlockPos();

        var positional = context.random().forkPositional();

        var xNoise = NormalNoise.create(positional.fromHashOf("x_noise"), this.noise.value());
        var zNoise = NormalNoise.create(positional.fromHashOf("z_noise"), this.noise.value());

        var xCache = new Long2DoubleOpenHashMap();
        var zCache = new Long2DoubleOpenHashMap();

        for (var pos : context.logs()) {
            // Only place at or above min Y
            if (pos.getY() < minY) {
                continue;
            }

            // Only place at or below max Y
            //
            // Minecraft sorts the logs from lowest to highest for us, so once we reach Y values
            // above the limit, we can stop the loop.
            if (pos.getY() > maxY) {
                break;
            }

            // Only place on logs
            if (!context.checkBlock(pos, it -> it.is(BlockTags.LOGS))) {
                continue;
            }

            var cacheKey = ChunkPos.pack(pos.getX(), pos.getZ());
            var xn = xCache.computeIfAbsent(cacheKey, _ -> xNoise.getValue(pos.getX() * noiseFieldScale, 0, pos.getZ() * noiseFieldScale) * noiseValueScale);
            var zn = zCache.computeIfAbsent(cacheKey, _ -> zNoise.getValue(pos.getX() * noiseFieldScale, 0, pos.getZ() * noiseFieldScale) * noiseValueScale);

            // Try add moss in any of the 4 directions
            for (var dir : dirs) {
                var probability = switch (dir) {
                    case NORTH -> getProbability(-zn);
                    case EAST -> getProbability(xn);
                    case SOUTH -> getProbability(zn);
                    case WEST -> getProbability(-xn);
                    default -> 0f;
                };

                mpos.setWithOffset(pos, dir);

                if (rng.nextFloat() < probability && context.isAir(mpos)) {
                    var moss = this.moss.getState(level, rng, mpos);
                    var face = dir.getOpposite();

                    if (moss.getBlock() instanceof AbstractMultifaceBlock amfb) {
                        moss = amfb.tryAddFace(level, mpos, face);
                    } else if (moss.getBlock() instanceof MultifaceBlock mfb) {
                        moss = AbstractMultifaceBlock.tryAddVanillaMultiface(mfb, level, mpos, face);
                    }

                    if (moss != null) {
                        context.setBlock(mpos, moss);
                    }
                }
            }
        }
    }
}
