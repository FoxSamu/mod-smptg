package net.foxboi.salted.common.levelgen;

import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.block.SaltCrystalBlock;
import net.foxboi.salted.common.levelgen.stateprovider.EitherStateProvider;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SegmentableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class FeatureBlocks {
    public static final BlockStateProvider SALT_CRUST = BlockStateProvider.simple(ModBlocks.SALT_CRUST);
    public static final BlockStateProvider SALT_BLOCK = BlockStateProvider.simple(ModBlocks.SALT_BLOCK);
    public static final BlockStateProvider SALT_CRYSTAL = saltCrystal();

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
    public static final BlockStateProvider CLOVERS_1_3 = segmented(ModBlocks.CLOVERS, 1, 3);
    public static final BlockStateProvider CLOVERS_1_4 = segmented(ModBlocks.CLOVERS, 1, 4);
    public static final BlockStateProvider PATCHMOSS = BlockStateProvider.simple(ModBlocks.PATCHMOSS);
    public static final BlockStateProvider MOSS_CARPET = BlockStateProvider.simple(Blocks.MOSS_CARPET);
    public static final BlockStateProvider FIREFLY_BUSH = BlockStateProvider.simple(Blocks.FIREFLY_BUSH);

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

    private static BlockStateProvider saltCrystal() {
        var weightedList = new WeightedList.Builder<BlockState>();

        var weights = new int[] { -1, 1, 1, 1, 1, 1, 2, 2, 3 };

        for (int age = 1; age <= 8; age ++) {
            weightedList.add(
                    ModBlocks.SALT_CRYSTAL.defaultBlockState()
                            .setValue(SaltCrystalBlock.AGE, age)
                            .setValue(SaltCrystalBlock.PERSISTENT, true),
                    weights[age]
            );
        }

        return new WeightedStateProvider(weightedList);
    }
}
