package net.foxboi.salted.common.levelgen.feature;

import java.util.function.Consumer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.levelgen.FeatureConditions;

public record Speleothem(
        BlockStateProvider speleothem,
        BlockStateProvider base,
        BlockPredicate canGrowFrom
) {
    public static final Codec<Speleothem> CODEC = RecordCodecBuilder.create(i -> i.group(
            BlockStateProvider.CODEC.fieldOf("speleothem").forGetter(Speleothem::speleothem),
            BlockStateProvider.CODEC.fieldOf("base").forGetter(Speleothem::base),
            BlockPredicate.CODEC.fieldOf("can_grow_from").forGetter(Speleothem::canGrowFrom)
    ).apply(i, Speleothem::new));

    public static final Speleothem DRIPSTONE = new Speleothem(
            Blocks.POINTED_DRIPSTONE,
            Blocks.DRIPSTONE_BLOCK,
            BlockPredicate.matchesTag(BlockTags.DRIPSTONE_REPLACEABLE)
    );

    public static final Speleothem LIMESTONE = new Speleothem(
            ModBlocks.POINTED_LIMESTONE,
            ModBlocks.LIMESTONE,
            BlockPredicate.matchesTag(ModBlockTags.LIMESTONE_REPLACEABLE)
    );

    public Speleothem(Block speleothem, Block base, BlockPredicate canReplace) {
        this(
                BlockStateProvider.simple(speleothem),
                RuleBasedStateProvider.builder()
                        .ifTrueThenProvide(BlockPredicate.anyOf(
                                canReplace,
                                FeatureConditions.AIR_WATER_OR_LAVA
                        ), base)
                        .build(),
                BlockPredicate.matchesBlocks(base)
        );
    }



    private void buildBaseToTipColumn(int totalLength, boolean mergedTip, Consumer<DripstoneThickness> consumer) {
        if (totalLength >= 3) {
            consumer.accept(DripstoneThickness.BASE);

            for (var i = 0; i < totalLength - 3; i++) {
                consumer.accept(DripstoneThickness.MIDDLE);
            }
        }

        if (totalLength >= 2) {
            consumer.accept(DripstoneThickness.FRUSTUM);
        }

        if (totalLength >= 1) {
            consumer.accept(mergedTip ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP);
        }
    }

    public void grow(WorldGenLevel level, BlockPos startPos, RandomSource random, Direction growDirection, int height, boolean mergedTip) {
        if (!canGrowFrom.test(level, startPos.relative(growDirection, -1))) {
            return;
        }

        var mpos = startPos.mutable();

        buildBaseToTipColumn(height, mergedTip, thickness -> {
            var state = speleothem.getState(level, random, mpos)
                    .trySetValue(BlockStateProperties.WATERLOGGED, level.isWaterAt(mpos))
                    .trySetValue(PointedDripstoneBlock.TIP_DIRECTION, growDirection)
                    .trySetValue(PointedDripstoneBlock.THICKNESS, thickness);

            level.setBlock(mpos, state, 2);
            mpos.move(growDirection);
        });
    }

    public boolean placeBase(WorldGenLevel level, BlockPos pos, RandomSource random) {
        var state = base.getOptionalState(level, random, pos);
        if (state != null) {
            level.setBlock(pos, state, 2);
            return true;
        }

        return false;
    }
}
