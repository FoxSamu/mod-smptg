package net.foxboi.salted.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.LevelReader;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.foxboi.salted.common.block.ModBlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Animal.class)
public class AnimalMixin {
    @ModifyExpressionValue(
            method = "getWalkTargetValue",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z")
    )
    private boolean isNiceBlockToWalkOn(boolean original, BlockPos pos, LevelReader level) {
        return original || level.getBlockState(pos.below()).is(ModBlockTags.ANIMALS_PREFER_WALKING_ON);
    }
}
