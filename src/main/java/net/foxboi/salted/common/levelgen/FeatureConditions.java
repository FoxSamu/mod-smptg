package net.foxboi.salted.common.levelgen;

import java.util.List;
import java.util.stream.Stream;

import net.foxboi.salted.common.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.material.Fluids;

import static net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.*;

public class FeatureConditions {
    public static final List<Block> MOSS_GROW_BLOCKS = List.of(
            Blocks.MOSS_BLOCK,
            ModBlocks.MOSSY_DIRT
    );

    public static final List<Block> DEFAULT_GROW_BLOCKS = List.of(
            Blocks.DIRT,
            Blocks.GRASS_BLOCK,
            Blocks.PODZOL,
            Blocks.COARSE_DIRT,
            Blocks.MUD,
            ModBlocks.MOSSY_DIRT
    );

    public static final List<Block> DRY_GROW_BLOCKS = Stream.concat(
            DEFAULT_GROW_BLOCKS.stream(),
            Stream.of(
                    Blocks.SAND,
                    Blocks.RED_SAND,
                    ModBlocks.LIMESTONE,
                    Blocks.TERRACOTTA,
                    Blocks.BLACK_TERRACOTTA,
                    Blocks.GRAY_TERRACOTTA,
                    Blocks.LIGHT_GRAY_TERRACOTTA,
                    Blocks.WHITE_TERRACOTTA,
                    Blocks.BROWN_TERRACOTTA,
                    Blocks.RED_TERRACOTTA,
                    Blocks.ORANGE_TERRACOTTA,
                    Blocks.YELLOW_TERRACOTTA,
                    Blocks.LIME_TERRACOTTA,
                    Blocks.GREEN_TERRACOTTA,
                    Blocks.CYAN_TERRACOTTA,
                    Blocks.BLUE_TERRACOTTA,
                    Blocks.LIGHT_BLUE_TERRACOTTA,
                    Blocks.MAGENTA_TERRACOTTA,
                    Blocks.PURPLE_TERRACOTTA,
                    Blocks.PINK_TERRACOTTA
            )
    ).toList();

    public static final List<Block> WET_GROW_BLOCKS = Stream.concat(
            DEFAULT_GROW_BLOCKS.stream(),
            Stream.of(
                    Blocks.SAND,
                    Blocks.RED_SAND,
                    Blocks.CLAY
            )
    ).toList();

    public static final List<Block> CAVE_GROW_BLOCKS = Stream.concat(
            DRY_GROW_BLOCKS.stream(),
            Stream.of(
                    Blocks.STONE,
                    Blocks.DEEPSLATE,
                    Blocks.ANDESITE,
                    Blocks.GRANITE,
                    Blocks.DIORITE,
                    Blocks.TUFF,
                    Blocks.SANDSTONE,
                    Blocks.RED_SANDSTONE,
                    ModBlocks.LIMESTONE
            )
    ).toList();

    public static final List<Block> ASH_GROW_BLOCKS = List.of(
            Blocks.NETHERRACK,
            ModBlocks.ASH_BLOCK,
            ModBlocks.PACKED_ASH
    );

    public static final BlockPredicate LOWEST_AIR = allOf(
            BlockPredicate.ONLY_IN_AIR_PREDICATE,
            not(matchesTag(Direction.DOWN.getUnitVec3i(), BlockTags.AIR))
    );

    public static final BlockPredicate HIGHEST_AIR = allOf(
            BlockPredicate.ONLY_IN_AIR_PREDICATE,
            not(matchesTag(Direction.UP.getUnitVec3i(), BlockTags.AIR))
    );

    public static final BlockPredicate AIR = ONLY_IN_AIR_OR_WATER_PREDICATE;
    public static final BlockPredicate AIR_OR_WATER = ONLY_IN_AIR_OR_WATER_PREDICATE;
    public static final BlockPredicate AIR_WATER_OR_LAVA = anyOf(
            ONLY_IN_AIR_OR_WATER_PREDICATE,
            matchesFluids(Fluids.LAVA)
    );


    public static BlockPredicate inAir(List<Block> supportedBlocks) {
        if (!supportedBlocks.isEmpty()) {
            return BlockPredicate.allOf(
                    BlockPredicate.ONLY_IN_AIR_PREDICATE,
                    matchesBlocks(Direction.DOWN.getUnitVec3i(), supportedBlocks)
            );
        } else {
            return BlockPredicate.ONLY_IN_AIR_PREDICATE;
        }
    }

    public static BlockPredicate inShallowWater(List<Block> supportedBlocks) {
        if (!supportedBlocks.isEmpty()) {
            return BlockPredicate.allOf(
                    matchesBlocks(Direction.UP.getUnitVec3i(), Blocks.AIR),
                    matchesBlocks(Blocks.WATER),
                    matchesBlocks(Direction.DOWN.getUnitVec3i(), supportedBlocks)
            );
        } else {
            return BlockPredicate.allOf(
                    matchesBlocks(Direction.UP.getUnitVec3i(), Blocks.AIR),
                    matchesBlocks(Blocks.WATER)
            );
        }
    }

    public static BlockPredicate inAir(List<Block> supportedBlocks, Direction supportDir) {
        if (!supportedBlocks.isEmpty()) {
            return BlockPredicate.allOf(
                    BlockPredicate.ONLY_IN_AIR_PREDICATE,
                    matchesBlocks(supportDir.getUnitVec3i(), supportedBlocks)
            );
        } else {
            return BlockPredicate.ONLY_IN_AIR_PREDICATE;
        }
    }
}
