package net.foxboi.salted.common.levelgen;

import java.util.List;
import java.util.stream.Stream;

import net.foxboi.salted.common.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

import static net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesBlocks;

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

    public static final List<Block> SANDY_GROW_BLOCKS = Stream.concat(
            DEFAULT_GROW_BLOCKS.stream(),
            Stream.of(
                    Blocks.SAND,
                    Blocks.RED_SAND,
                    Blocks.CLAY
            )
    ).toList();

    public static final List<Block> CAVE_GROW_BLOCKS = Stream.concat(
            SANDY_GROW_BLOCKS.stream(),
            Stream.of(
                    Blocks.STONE,
                    Blocks.DEEPSLATE,
                    Blocks.ANDESITE,
                    Blocks.GRANITE,
                    Blocks.DIORITE,
                    Blocks.TUFF,
                    Blocks.SANDSTONE,
                    Blocks.RED_SANDSTONE
            )
    ).toList();



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
}
