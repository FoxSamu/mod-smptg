package net.foxboi.salted.common.levelgen.feature;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.levelgen.FeatureBlocks;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record RandomBlobsAndCrystalsConfig(
        IntProvider iterations,
        IntProvider xzSpread,
        IntProvider ySpread,
        BlockPredicate blobPredicate,
        Optional<BlockStateProvider> blob,
        BlockPredicate crystalPredicate,
        Optional<BlockStateProvider> crystal,
        float crystalChance,
        boolean tryRotateCrystals
) implements FeatureConfiguration {
    public static final Codec<RandomBlobsAndCrystalsConfig> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            IntProvider.codec(1, 100).fieldOf("iterations").forGetter(it -> it.iterations),
            IntProvider.codec(0, 8).fieldOf("xz_spread").forGetter(it -> it.xzSpread),
            IntProvider.codec(0, 8).fieldOf("y_spread").forGetter(it -> it.ySpread),
            BlockPredicate.CODEC.optionalFieldOf("blob_predicate", BlockPredicate.alwaysTrue()).forGetter(it -> it.blobPredicate),
            BlockStateProvider.CODEC.optionalFieldOf("blob").forGetter(it -> it.blob),
            BlockPredicate.CODEC.optionalFieldOf("crystal_predicate", BlockPredicate.alwaysTrue()).forGetter(it -> it.crystalPredicate),
            BlockStateProvider.CODEC.optionalFieldOf("crystal").forGetter(it -> it.crystal),
            Codec.floatRange(0, 1).optionalFieldOf("crystal_chance", 1f).forGetter(it -> it.crystalChance),
            Codec.BOOL.optionalFieldOf("try_rotate_crystals", false).forGetter(it -> it.tryRotateCrystals)
    ).apply(inst, RandomBlobsAndCrystalsConfig::new));

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private IntProvider iterations = UniformInt.of(20, 30);
        private IntProvider xzSpread = ConstantInt.of(7);
        private IntProvider ySpread = ConstantInt.of(4);

        private BlockStateProvider blob;
        private BlockPredicate blobPredicate = BlockPredicate.alwaysTrue();

        private BlockStateProvider crystal;
        private BlockPredicate crystalPredicate = BlockPredicate.alwaysTrue();

        private float crystalChance = 1f;
        private boolean tryRotateCrystals = false;

        public Builder iterations(IntProvider iterations) {
            this.iterations = iterations;
            return this;
        }

        public Builder xzSpread(IntProvider xzSpread) {
            this.xzSpread = xzSpread;
            return this;
        }

        public Builder ySpread(IntProvider ySpread) {
            this.ySpread = ySpread;
            return this;
        }

        public Builder blob(BlockStateProvider blob, BlockPredicate blobPredicate) {
            this.blob = blob;
            this.blobPredicate = blobPredicate;
            return this;
        }

        public Builder crystal(BlockStateProvider crystal, BlockPredicate crystalPredicate) {
            this.crystal = crystal;
            this.crystalPredicate = crystalPredicate;
            return this;
        }

        public Builder crystalChance(float crystalChance) {
            this.crystalChance = crystalChance;
            return this;
        }

        public Builder tryRotateCrystals() {
            this.tryRotateCrystals = true;
            return this;
        }

        public RandomBlobsAndCrystalsConfig build() {
            return new RandomBlobsAndCrystalsConfig(
                    iterations,
                    xzSpread,
                    ySpread,
                    blobPredicate,
                    Optional.ofNullable(blob),
                    crystalPredicate,
                    Optional.ofNullable(crystal),
                    crystalChance,
                    tryRotateCrystals
            );
        }
    }
}
