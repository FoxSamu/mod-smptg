package net.foxboi.salted.mixin.main;

import net.foxboi.salted.common.block.SaltCrystalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Redirect(
            method = "tickChunk",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getBlockRandomPos(IIII)Lnet/minecraft/core/BlockPos;", ordinal = 1)
    )
    private BlockPos onRandomTick(ServerLevel self, int i, int j, int k, int l) {
        var pos = self.getBlockRandomPos(i, j, k, l);

        SaltCrystalBlock.spontaneouslyGenerateSaltCrystal(self, pos);

        return pos;
    }
}
