package net.foxboi.salted.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class GrowingSpeleothemBlock extends AbstractSpeleothemBlock {
    public static final MapCodec<GrowingSpeleothemBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("grow_block").forGetter(it -> it.growBlock),
            propertiesCodec()
    ).apply(i, GrowingSpeleothemBlock::new));

    private final Block growBlock;

    public GrowingSpeleothemBlock(Block growBlock, Properties properties) {
        super(true, false, createShapes(12, 10, 8, 6, 13), properties);
        this.growBlock = growBlock;
    }

    @Override
    protected MapCodec<? extends AbstractSpeleothemBlock> codec() {
        return CODEC;
    }

    @Override
    protected GrowMode getGrowMode(BlockState state, LevelReader level, BlockPos pos) {
        return state.is(growBlock)
                ? GrowMode.GROW_SELF_ONLY
                : GrowMode.NONE;
    }
}
