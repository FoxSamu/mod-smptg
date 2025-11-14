package net.foxboi.salted.common.levelgen.tree;

import java.util.List;
import java.util.function.BiConsumer;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

public abstract class AbstractRedwoodTrunkPlacer extends TrunkPlacer {
    protected static <P extends AbstractRedwoodTrunkPlacer> Products.P5<RecordCodecBuilder.Mu<P>, Integer, Integer, Integer, IntProvider, IntProvider> redwoodTrunkPlacerParts(RecordCodecBuilder.Instance<P> inst) {
        return inst.group(
                Codec.intRange(0, 64).fieldOf("base_height").forGetter(it -> it.baseHeight), // Minecraft allows up to 32 here, but redwood trees are huge so let's allow some more
                Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter(it -> it.heightRandA),
                Codec.intRange(0, 24).fieldOf("height_rand_b").forGetter(it -> it.heightRandB),
                IntProvider.codec(0, 20).fieldOf("depth").forGetter(it -> it.depth),
                IntProvider.codec(0, 5).fieldOf("dirt_layers").forGetter(it -> it.dirtLayers)
        );
    }

    protected final IntProvider depth;
    protected final IntProvider dirtLayers;

    public AbstractRedwoodTrunkPlacer(int baseHeight, int heightRandA, int heightRandB, IntProvider depth, IntProvider dirtLayers) {
        super(baseHeight, heightRandA, heightRandB);
        this.depth = depth;
        this.dirtLayers = dirtLayers;
    }

    protected abstract int baseRadius();
    protected abstract float getProportion(int index);
    protected abstract int getRandomnessMin(int index);
    protected abstract int getRandomnessMax(int index);

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> trunkSetter, RandomSource rng, int height, BlockPos origin, TreeConfiguration config) {
        var dirtLayers = this.dirtLayers.sample(rng);
        var depth = this.depth.sample(rng) + dirtLayers;

        var mpos = origin.mutable();

        var log = config.trunkProvider;
        var dirt = config.dirtProvider;

        var radius = baseRadius();
        var diameter = radius * 2;
        var offset = radius - 1;

        for (var ix = 0; ix < diameter; ix ++) {
            for (var iz = 0; iz < diameter; iz ++) {
                var x = ix - offset;
                var z = iz - offset;

                var index = iz * diameter + ix;

                var prop = getProportion(index);
                if (prop == 0f) {
                    continue;
                }

                var min = getRandomnessMin(index);
                var max = getRandomnessMax(index);

                var colHeight = Mth.floor(height * prop) + rng.nextIntBetweenInclusive(min, max);

                generateColumn(level, trunkSetter, rng, log, dirt, dirtLayers, x, z, colHeight, depth + rng.nextInt(3), origin, mpos);
            }
        }

        return List.of(
                new FoliagePlacer.FoliageAttachment(origin.above(height), 0, true)
        );
    }

    private void generateColumn(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> trunkSetter, RandomSource rng, BlockStateProvider log, BlockStateProvider dirt, int dirtLayers, int x, int z, int height, int depth, BlockPos origin, BlockPos.MutableBlockPos mpos) {
        for (var y = -depth; y < height; y++) {
            mpos.setWithOffset(origin, x, y, z);

            var isDirt = y + depth < dirtLayers;
            var state = (isDirt ? dirt : log).getState(rng, mpos);
            if (isDirt ? checkCanPlaceDirt(level, mpos) : checkCanPlaceLog(level, mpos)) {
                trunkSetter.accept(mpos, state);
            }
        }
    }

    @Override
    public boolean isFree(LevelSimulatedReader level, BlockPos pos) {
        return checkCanPlaceLog(level, pos);
    }

    public static boolean checkCanPlaceLog(LevelSimulatedReader level, BlockPos pos) {
        // These trees just root through sand and stone
        return level.isStateAtPosition(pos, it -> it.isAir() || it.is(BlockTags.REPLACEABLE_BY_TREES) || it.is(BlockTags.DIRT) || it.is(BlockTags.SAND) || it.is(BlockTags.BASE_STONE_OVERWORLD));
    }

    public static boolean checkCanPlaceDirt(LevelSimulatedReader level, BlockPos pos) {
        return level.isStateAtPosition(pos, it -> it.isAir() || it.is(BlockTags.REPLACEABLE_BY_TREES) || it.is(BlockTags.DIRT));
    }
}
