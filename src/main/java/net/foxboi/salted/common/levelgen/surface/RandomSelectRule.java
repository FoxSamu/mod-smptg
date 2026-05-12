package net.foxboi.salted.common.levelgen.surface;

import net.minecraft.resources.Identifier;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.SurfaceRules;

import com.mojang.serialization.codecs.RecordCodecBuilder;

public record RandomSelectRule(
        WeightedList<SurfaceRules.RuleSource> selector,
        Identifier randomName
) implements SurfaceRules.RuleSource {
    public static final KeyDispatchDataCodec<RandomSelectRule> CODEC = new KeyDispatchDataCodec<>(RecordCodecBuilder.mapCodec(i -> i.group(
            WeightedList.nonEmptyCodec(SurfaceRules.RuleSource.CODEC).fieldOf("selector").forGetter(RandomSelectRule::selector),
            Identifier.CODEC.fieldOf("random_name").forGetter(RandomSelectRule::randomName)
    ).apply(i, RandomSelectRule::new)));

    @Override
    public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
        return CODEC;
    }

    @Override
    public SurfaceRules.SurfaceRule apply(SurfaceRules.Context context) {
        var selector = selector().map(src -> src.apply(context));
        var random = context.randomState.getOrCreateRandomFactory(randomName);
        return new Instance(selector, random);
    }

    private record Instance(WeightedList<SurfaceRules.SurfaceRule> selector, PositionalRandomFactory random) implements SurfaceRules.SurfaceRule {

        @Override
        public BlockState tryApply(int blockX, int blockY, int blockZ) {
            var elem = selector.getRandomOrThrow(random.at(blockX, blockY, blockZ));
            return elem.tryApply(blockX, blockY, blockZ);
        }
    }
}
