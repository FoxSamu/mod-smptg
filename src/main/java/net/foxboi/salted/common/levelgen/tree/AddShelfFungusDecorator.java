package net.foxboi.salted.common.levelgen.tree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.foxboi.salted.common.block.DiagonallyAttachableBlock;
import net.foxboi.salted.common.misc.DiagonalDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class AddShelfFungusDecorator extends TreeDecorator {
    public static final MapCodec<AddShelfFungusDecorator> CODEC = RecordCodecBuilder.mapCodec(
            inst -> inst.group(
                    IntProvider.codec(0, Integer.MAX_VALUE).fieldOf("min_y").forGetter(it -> it.yOffset),
                    IntProvider.codec(1, Integer.MAX_VALUE).fieldOf("max_y").forGetter(it -> it.layers),
                    Codec.floatRange(0f, 1f).fieldOf("probability").forGetter(it -> it.probability),
                    BlockStateProvider.CODEC.fieldOf("fungus").forGetter(it -> it.fungus)
            ).apply(inst, AddShelfFungusDecorator::new)
    );

    public static final TreeDecoratorType<AddShelfFungusDecorator> TYPE = new TreeDecoratorType<>(CODEC);

    /**
     * Vertical offset of the first layer of fungi, relative to the Y coordinate of the lowest log block.
     */
    private final IntProvider yOffset;

    /**
     * Amount of layers of fungi.
     */
    private final IntProvider layers;

    /**
     * Probability that a fungi is placed in a certain position.
     */
    private final float probability;

    /**
     * Fungus block.
     */
    private final BlockStateProvider fungus;

    public AddShelfFungusDecorator(IntProvider yOffset, IntProvider layers, float probability, BlockStateProvider fungus) {
        this.yOffset = yOffset;
        this.layers = layers;
        this.probability = probability;
        this.fungus = fungus;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return TYPE;
    }

    @Override
    public void place(Context context) {
        var rng = context.random();

        // Find lowest log value, this is the lowest layer we place fungi at
        var lowestLogY = Integer.MAX_VALUE;
        for (var pos : context.logs()) {
            if (pos.getY() < lowestLogY && context.checkBlock(pos, it -> it.is(BlockTags.LOGS))) {
                lowestLogY = pos.getY();
            }
        }

        var minY = lowestLogY + yOffset.sample(rng);
        var maxY = minY + layers.sample(rng) - 1;

        var dirs = DiagonalDirection.values();

        var mpos = new BlockPos.MutableBlockPos();

        for (var pos : context.logs()) {
            // Only place at or above min Y
            if (pos.getY() < minY) {
                continue;
            }

            // Only place at or below max Y
            //
            // Minecraft sorts the logs from lowest to highest for us, so once we reach Y values
            // above the limit, we can stop the loop.
            if (pos.getY() > maxY) {
                break;
            }

            // Only place on logs
            if (!context.checkBlock(pos, it -> it.is(BlockTags.LOGS))) {
                continue;
            }

            // Try add fungi in any of the 8 directions
            for (var dir : dirs) {
                mpos.setWithOffset(pos, dir.getStepX(), 0, dir.getStepZ());

                if (rng.nextFloat() < probability && context.checkBlock(mpos, BlockState::canBeReplaced)) {
                    context.setBlock(mpos, fungus.getState(rng, mpos).trySetValue(DiagonallyAttachableBlock.FACING, dir));
                }
            }
        }
    }
}
