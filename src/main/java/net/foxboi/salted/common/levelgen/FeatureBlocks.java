package net.foxboi.salted.common.levelgen;

import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.levelgen.stateprovider.EitherStateProvider;

import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SegmentableBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;

public class FeatureBlocks {

    public static final BlockStateProvider ASPEN_LOG = BlockStateProvider.simple(ModBlocks.ASPEN_LOG);
    public static final BlockStateProvider BIRCH_LOG = BlockStateProvider.simple(Blocks.BIRCH_LOG);
    public static final BlockStateProvider BEECH_LOG = BlockStateProvider.simple(ModBlocks.BEECH_LOG);
    public static final BlockStateProvider MAPLE_LOG = BlockStateProvider.simple(ModBlocks.MAPLE_LOG);
    public static final BlockStateProvider REDWOOD_LOG = BlockStateProvider.simple(ModBlocks.REDWOOD_LOG);
    public static final BlockStateProvider DEAD_LOG = BlockStateProvider.simple(ModBlocks.DEAD_LOG);

    public static final BlockStateProvider ASPEN_LEAVES = BlockStateProvider.simple(ModBlocks.ASPEN_LEAVES);
    public static final BlockStateProvider BIRCH_LEAVES = BlockStateProvider.simple(Blocks.BIRCH_LEAVES);
    public static final BlockStateProvider BEECH_LEAVES = BlockStateProvider.simple(ModBlocks.BEECH_LEAVES);
    public static final BlockStateProvider RED_MAPLE_LEAVES = BlockStateProvider.simple(ModBlocks.RED_MAPLE_LEAVES);
    public static final BlockStateProvider ORANGE_MAPLE_LEAVES = BlockStateProvider.simple(ModBlocks.ORANGE_MAPLE_LEAVES);
    public static final BlockStateProvider YELLOW_MAPLE_LEAVES = BlockStateProvider.simple(ModBlocks.YELLOW_MAPLE_LEAVES);
    public static final BlockStateProvider REDWOOD_LEAVES = BlockStateProvider.simple(ModBlocks.REDWOOD_LEAVES);
    public static final BlockStateProvider DEAD_LEAVES = BlockStateProvider.simple(ModBlocks.DEAD_LEAVES);

    public static final BlockStateProvider GRASS_SPROUTS = BlockStateProvider.simple(ModBlocks.GRASS_SPROUTS);
    public static final BlockStateProvider CAVE_GRASS = BlockStateProvider.simple(ModBlocks.CAVE_GRASS);
    public static final BlockStateProvider CATTAIL = BlockStateProvider.simple(ModBlocks.CATTAIL);
    public static final BlockStateProvider TALL_CATTAIL = BlockStateProvider.simple(ModBlocks.TALL_CATTAIL);
    public static final BlockStateProvider RANDOM_CATTAIL = either(CATTAIL, TALL_CATTAIL, 0.75);
    public static final BlockStateProvider LAVENDER = BlockStateProvider.simple(ModBlocks.LAVENDER);
    public static final BlockStateProvider TALL_LAVENDER = BlockStateProvider.simple(ModBlocks.TALL_LAVENDER);
    public static final BlockStateProvider RANDOM_LAVENDER = either(FeatureBlocks.LAVENDER, FeatureBlocks.TALL_LAVENDER, 0.25);
    public static final BlockStateProvider BARLEY = BlockStateProvider.simple(ModBlocks.BARLEY);
    public static final BlockStateProvider TALL_BARLEY = BlockStateProvider.simple(ModBlocks.TALL_BARLEY);
    public static final BlockStateProvider RANDOM_BARLEY = either(FeatureBlocks.BARLEY, FeatureBlocks.TALL_BARLEY, 0.25);
    public static final BlockStateProvider GLOBE_THISTLE = BlockStateProvider.simple(ModBlocks.GLOBE_THISTLE);
    public static final BlockStateProvider SHELF_FUNGUS = BlockStateProvider.simple(ModBlocks.SHELF_FUNGUS);
    public static final BlockStateProvider DRIPMOSS = BlockStateProvider.simple(ModBlocks.DRIPMOSS);
    public static final BlockStateProvider CLOVERS_1_3 = segmented(ModBlocks.CLOVERS, 1, 3);
    public static final BlockStateProvider CLOVERS_1_4 = segmented(ModBlocks.CLOVERS, 1, 4);
    public static final BlockStateProvider PATCHMOSS = BlockStateProvider.simple(ModBlocks.PATCHMOSS);
    public static final BlockStateProvider MOSS_CARPET = BlockStateProvider.simple(Blocks.MOSS_CARPET);
    public static final BlockStateProvider FIREFLY_BUSH = BlockStateProvider.simple(Blocks.FIREFLY_BUSH);
    public static final BlockStateProvider ASH_LAYER = BlockStateProvider.simple(ModBlocks.ASH_LAYER);
    public static final BlockStateProvider ASHCREEP = BlockStateProvider.simple(ModBlocks.ASHCREEP);
    public static final BlockStateProvider ASHVINE = BlockStateProvider.simple(ModBlocks.ASHVINE);
    public static final BlockStateProvider EMBERGRASS = BlockStateProvider.simple(ModBlocks.EMBERGRASS);
    public static final BlockStateProvider EMBERWEED = BlockStateProvider.simple(ModBlocks.EMBERWEED);
    public static final BlockStateProvider EMBERS = segmented(ModBlocks.EMBERS, 1, 4);
    public static final BlockStateProvider BURNED_STEM = BlockStateProvider.simple(ModBlocks.BURNED_STEM);

    public static final BlockStateProvider REPLACE_WITH_LIMESTONE = RuleBasedStateProvider.builder()
            .ifTrueThenProvide(BlockPredicate.allOf(
                    BlockPredicate.matchesTag(BlockTags.GRASS_BLOCKS),
                    BlockPredicate.matchesBlocks(Direction.UP.getUnitVec3i(), Blocks.SNOW)
            ), ModBlocks.GRASSY_LIMESTONE.defaultBlockState().setValue(BlockStateProperties.SNOWY, true))
            .ifTrueThenProvide(BlockPredicate.matchesTag(BlockTags.GRASS_BLOCKS), ModBlocks.GRASSY_LIMESTONE)
            .ifTrueThenProvide(BlockPredicate.matchesTag(BlockTags.SUBSTRATE_OVERWORLD), ModBlocks.LIMESTONE)
            .ifTrueThenProvide(BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD), ModBlocks.LIMESTONE)
            .ifTrueThenProvide(BlockPredicate.matchesTag(BlockTags.COAL_ORES), ModBlocks.LIMESTONE_COAL_ORE)
            .ifTrueThenProvide(BlockPredicate.matchesTag(BlockTags.COPPER_ORES), ModBlocks.LIMESTONE_COPPER_ORE)
            .ifTrueThenProvide(BlockPredicate.matchesTag(BlockTags.IRON_ORES), ModBlocks.LIMESTONE_IRON_ORE)
            .ifTrueThenProvide(BlockPredicate.matchesTag(BlockTags.GOLD_ORES), ModBlocks.LIMESTONE_GOLD_ORE)
            .ifTrueThenProvide(BlockPredicate.matchesTag(BlockTags.DIAMOND_ORES), ModBlocks.LIMESTONE_DIAMOND_ORE)
            .ifTrueThenProvide(BlockPredicate.matchesTag(BlockTags.EMERALD_ORES), ModBlocks.LIMESTONE_EMERALD_ORE)
            .ifTrueThenProvide(BlockPredicate.matchesTag(BlockTags.LAPIS_ORES), ModBlocks.LIMESTONE_LAPIS_ORE)
            .ifTrueThenProvide(BlockPredicate.matchesTag(BlockTags.REDSTONE_ORES), ModBlocks.LIMESTONE_REDSTONE_ORE)
            .build();

    public static BlockStateProvider either(BlockStateProvider a, BlockStateProvider b, double bChance) {
        return new EitherStateProvider(a, b, bChance);
    }

    public static BlockStateProvider either(BlockStateProvider a, BlockStateProvider b) {
        return new EitherStateProvider(a, b, 0.5);
    }

    private static BlockStateProvider segmented(Block block, int min, int max) {
        if (!(block instanceof SegmentableBlock sb)) {
            throw new IllegalArgumentException("Segmented block provider requires SegmentableBlock");
        }

        var property = sb.getSegmentAmountProperty();
        return new RandomizedIntStateProvider(
                BlockStateProvider.simple(block),
                property,
                UniformInt.of(min, max)
        );
    }
}
