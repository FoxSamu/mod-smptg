package net.foxboi.salted.mixin.main;

import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ResourceLocation.class)
@Debug(export = true)
public class ResourceLocationMixin {
    @ModifyVariable(
            method = "<init>",
            at = @At("HEAD"),
            argsOnly = true,
            index = 1
    )
    private static String ctor(String ns) {
        if (ns.equals("salted")) {
            return "smptg";
        }
        return ns;
    }
}
