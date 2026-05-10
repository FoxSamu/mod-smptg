package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;

public class SpeleothemBlock extends AbstractSpeleothemBlock {
    public static final MapCodec<SpeleothemBlock> CODEC = simpleCodec(SpeleothemBlock::new);

    public SpeleothemBlock(Properties properties) {
        super(false, false, createShapes(12, 10, 8, 6, 13), properties);
    }

    @Override
    protected MapCodec<? extends AbstractSpeleothemBlock> codec() {
        return CODEC;
    }
}
