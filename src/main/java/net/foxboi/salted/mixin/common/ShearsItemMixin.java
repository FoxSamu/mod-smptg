package net.foxboi.salted.mixin.common;

import java.util.ArrayList;
import java.util.List;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.foxboi.salted.common.block.ModBlocks;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.component.Tool;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShearsItem.class)
public class ShearsItemMixin {
    @ModifyReturnValue(
            method = "createToolProperties",
            at = @At("RETURN")
    )
    private static Tool addShearableBlocks(Tool original) {
        var rules = new ArrayList<>(original.rules());
        var defaultMiningSpeed = original.defaultMiningSpeed();
        var damagePerBlock = original.damagePerBlock();
        var canDestroyBlocksInCreative = original.canDestroyBlocksInCreative();

        ModBlocks.addShearsRules(rules);

        return new Tool(List.copyOf(rules), defaultMiningSpeed, damagePerBlock, canDestroyBlocksInCreative);
    }
}
