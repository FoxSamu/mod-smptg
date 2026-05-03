package net.foxboi.salted.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class DripmossBlock extends HangingColumnPlantBlock {
    public static final MapCodec<DripmossBlock> CODEC = simpleCodec(DripmossBlock::new);

    private static final double PARTICLE_PROBABILITY = 0.03;

    @Override
    protected MapCodec<DripmossBlock> codec() {
        return CODEC;
    }

    public DripmossBlock(Properties properties) {
        super(
                PlantConfig.of()
                        .canGrowOn(PlantConfig.GROW_ON_STURDY_FACE)
                        .size(14, 14)
                        .bonemealBehavior(BonemealBehaviors.growColumnPlant(1, 3)),
                properties
        );
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlockTags.CAVE_PLANT_CAN_GROW_ON);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rng) {
        var offset = state.getOffset(pos);

        if (rng.nextDouble() < PARTICLE_PROBABILITY) {
            // Spawn a particle on the cross model of the block
            var xDir = rng.nextBoolean() ? 1d : -1d;
            var zDir = rng.nextBoolean() ? 1d : -1d;

            var h = rng.nextDouble() * (7d / 16d);
            var v = rng.nextDouble() * (state.getValue(SHAPE) != ColumnPlantShape.BODY ? 14d / 16d : 1d);

            var x = pos.getX() + 0.5d + xDir * h + offset.x;
            var y = pos.getY() + 1d - v + offset.y;
            var z = pos.getZ() + 0.5d + zDir * h + offset.z;

            level.addParticle(ParticleTypes.DRIPPING_DRIPSTONE_WATER, x, y, z, 0, 0, 0);
        }
    }
}
