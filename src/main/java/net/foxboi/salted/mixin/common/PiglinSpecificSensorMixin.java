package net.foxboi.salted.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.sensing.PiglinSpecificSensor;
import net.minecraft.world.level.block.state.BlockState;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.foxboi.salted.common.block.BrazierBlock;
import net.foxboi.salted.common.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PiglinSpecificSensor.class)
public class PiglinSpecificSensorMixin {
    @ModifyReturnValue(
            method = "isValidRepellent",
            at = @At("RETURN")
    )
    private static boolean checkBraziers(boolean original, ServerLevel level, BlockPos pos, @Local(name = "blockState") BlockState blockState) {
        return original || blockState.is(ModBlocks.SOUL_LIMESTONE_BRAZIER) && blockState.getValue(BrazierBlock.LIT);
    }
}
