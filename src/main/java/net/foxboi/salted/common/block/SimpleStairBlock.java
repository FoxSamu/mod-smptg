package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;

public class SimpleStairBlock extends StairBlock {
    public static final MapCodec<SimpleStairBlock> CODEC = simpleCodec(SimpleStairBlock::new);

    public SimpleStairBlock(Properties props) {
        super(Blocks.AIR.defaultBlockState(), props);
    }

    @Override
    public MapCodec<? extends StairBlock> codec() {
        return CODEC;
    }
}
