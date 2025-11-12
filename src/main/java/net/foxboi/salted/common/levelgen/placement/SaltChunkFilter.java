package net.foxboi.salted.common.levelgen.placement;

import com.mojang.serialization.MapCodec;
import net.foxboi.salted.common.util.SaltChunks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class SaltChunkFilter extends PlacementFilter {
    public static final MapCodec<SaltChunkFilter> CODEC = MapCodec.unit(SaltChunkFilter::new);

    @Override
    protected boolean shouldPlace(PlacementContext ctx, RandomSource rng, BlockPos pos) {
        var cpos = new ChunkPos(pos);
        return SaltChunks.isSaltChunk(ctx.getLevel(), cpos.x, cpos.z);
    }

    @Override
    public PlacementModifierType<?> type() {
        return ModPlacementTypes.SALT_CHUNK;
    }

    public static SaltChunkFilter inSaltChunk() {
        return new SaltChunkFilter();
    }
}
