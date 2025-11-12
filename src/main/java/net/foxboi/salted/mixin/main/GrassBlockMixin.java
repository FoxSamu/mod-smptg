package net.foxboi.salted.mixin.main;

import net.foxboi.salted.common.util.BonemealGrassGrower;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GrassBlock.class)
public class GrassBlockMixin {
    @Redirect(
            method = "performBonemeal",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z", ordinal = 0)
    )
    private boolean checkIsGrassBlock(BlockState instance, Block block) {
        return BonemealGrassGrower.GRASS_VALID_BLOCKS.contains(instance.getBlock());
    }
}
