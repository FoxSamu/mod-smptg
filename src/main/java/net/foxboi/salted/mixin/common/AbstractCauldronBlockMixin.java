package net.foxboi.salted.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

import net.foxboi.salted.common.block.AbstractSpeleothemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractCauldronBlock.class)
public class AbstractCauldronBlockMixin {
    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void onTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rng, CallbackInfo ci) {
        AbstractSpeleothemBlock.tryFillCauldron(state, level, pos);
    }
}
