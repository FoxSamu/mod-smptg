package net.foxboi.salted.mixin.common;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.NodeEvaluator;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.block.PlantBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NodeEvaluator.class)
public class NodeEvaluatorMixin {
    @ModifyReturnValue(
            method = "isBurningBlock",
            at = @At("RETURN")
    )
    private static boolean checkIsBurning(boolean original, BlockState state) {
        if (state.getBlock() instanceof PlantBlock plant) {
            return plant.getPlantConfig().isBurning();
        }

        return original;
    }
}
