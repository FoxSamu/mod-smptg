package net.foxboi.salted.common.item;

import net.foxboi.salted.common.util.SaltMelting;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

public class SaltItem extends Item {
    public SaltItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var state = level.getBlockState(pos);

        if (state.is(Blocks.SNOW) || state.is(Blocks.ICE)) {
            if (!level.isClientSide()) {
                SaltMelting.meltFromSalt(level, pos, level.random);

                // TODO Use custom sound event for proper subtitles
                level.playSound(null, context.getClickedPos(), SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1, 1);
            }

            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }
}
