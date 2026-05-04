package net.foxboi.salted.common.block;

import java.util.function.BiFunction;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

/**
 * A functional interface that creates an {@link Block} from {@link BlockBehaviour.Properties}. Used in {@link ModBlocks}.
 */
public interface BlockFactory extends BiFunction<ResourceKey<Block>, BlockBehaviour.Properties, Block> {
    /**
     * Instantiates a {@link Block}.
     *
     * @param properties The {@link BlockBehaviour.Properties} object to pass to the block. You may modify this to set additional
     *                   properties via the factory.
     * @return The created {@link Block}.
     */
    Block create(BlockBehaviour.Properties properties);

    @Override
    default Block apply(ResourceKey<Block> key, BlockBehaviour.Properties properties) {
        return create(properties.setId(key));
    }
}
