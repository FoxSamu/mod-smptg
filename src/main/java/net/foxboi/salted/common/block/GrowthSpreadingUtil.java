package net.foxboi.salted.common.block;

import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public record GrowthSpreadingUtil() {
    private static final Map<Block, Block> GRASS = Map.of(
            Blocks.DIRT, Blocks.GRASS_BLOCK,
            ModBlocks.PEAT, ModBlocks.GRASSY_PEAT
    );

    private static final Map<Block, Block> MOSS = Map.of(
            Blocks.DIRT, ModBlocks.MOSSY_DIRT,
            ModBlocks.PEAT, ModBlocks.MOSSY_PEAT
    );

    private static final Map<Block, Block> MYCELIUM = Map.of(
            Blocks.DIRT, Blocks.MYCELIUM
    );

    private static BlockState addSnowIfPossible(ServerLevel level, BlockPos pos, BlockState state) {
        return state != null && state.hasProperty(BlockStateProperties.SNOWY)
                ? state.setValue(BlockStateProperties.SNOWY, level.getBlockState(pos.above()).is(Blocks.SNOW))
                : state;
    }

    private static BlockState getSpreadState(ServerLevel level, BlockPos pos, Map<Block, Block> table) {
        var state = level.getBlockState(pos);

        var spreadBlock = table.get(state.getBlock());
        if (spreadBlock == null) {
            return null;
        }

        return addSnowIfPossible(level, pos, spreadBlock.defaultBlockState());
    }

    public static BlockState getGrassSpreadState(ServerLevel level, BlockPos pos) {
        return getSpreadState(level, pos, GRASS);
    }

    public static BlockState getMossSpreadState(ServerLevel level, BlockPos pos) {
        return getSpreadState(level, pos, MOSS);
    }

    public static BlockState getMyceliumSpreadState(ServerLevel level, BlockPos pos) {
        return getSpreadState(level, pos, MYCELIUM);
    }
}
