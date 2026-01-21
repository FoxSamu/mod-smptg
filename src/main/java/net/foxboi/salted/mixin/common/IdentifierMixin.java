package net.foxboi.salted.mixin.common;

import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Identifier.class)
@Debug(export = true)
public class IdentifierMixin {
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
