package net.foxboi.salted.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.serialization.MapCodec;

public class EmbersBlock extends SegmentedPlantBlock {
    public static final MapCodec<EmbersBlock> CODEC = PlantConfig.plantBlockCodec(EmbersBlock::new);

    public EmbersBlock(PlantConfig plantConfig, Properties properties) {
        super(plantConfig, properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (!entity.isSteppingCarefully() && entity instanceof LivingEntity && entity.getY() - entity.getBlockY() < getPlantConfig().height() / 16d) {
            entity.hurt(level.damageSources().hotFloor(), 1f);
        }

        super.entityInside(state, level, pos, entity, effectApplier, isPrecise);
    }
}
