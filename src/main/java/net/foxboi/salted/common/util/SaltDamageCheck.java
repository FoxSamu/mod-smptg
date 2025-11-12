package net.foxboi.salted.common.util;

import net.foxboi.salted.common.block.ModBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class SaltDamageCheck {
    public static boolean isInDamagingSalt(Mob mob) {
        return isInDamagingSalt(mob.level(), mob.getBoundingBox(), mob.onGround());
    }

    public static boolean isInDamagingSalt(Level level, AABB box, boolean checkGround) {
        var yPadding = checkGround ? 1 / 64d : 0d;

        for (var pos : BlockPos.betweenClosed(box.setMinY(box.minY - yPadding))) {
            if (level.getBlockState(pos).is(ModBlockTags.DAMAGES_SLIMES)) {
                return true;
            }
        }

        return false;
    }
}
