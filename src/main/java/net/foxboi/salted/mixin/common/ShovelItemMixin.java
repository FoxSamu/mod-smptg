package net.foxboi.salted.mixin.common;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;

import net.foxboi.salted.common.block.BrazierBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItem.class)
public class ShovelItemMixin {
    @Inject(
            method = "useOn",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onUseOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (context.getClickedFace() == Direction.DOWN) {
            return;
        }

        // Extinguish braziers

        var player = context.getPlayer();
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var state = level.getBlockState(pos);

        if (state.getBlock() instanceof BrazierBlock) {
            if (!state.getValue(BrazierBlock.LIT)) {
                return;
            }

            if (!level.isClientSide()) {
                level.levelEvent(null, LevelEvent.SOUND_EXTINGUISH_FIRE, pos, 0);
                level.setBlock(pos, state.setValue(BlockStateProperties.LIT, false), Block.UPDATE_ALL_IMMEDIATE);

                if (player != null) {
                    context.getItemInHand().hurtAndBreak(1, player, context.getHand());
                }
            }

            BrazierBlock.dowse(player, level, pos);

            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
