package net.foxboi.salted.mixin.common;

import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.Resource;

import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import net.foxboi.salted.common.misc.datamod.DataModificationHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.resources.RegistryLoadTask$PendingRegistration")
@SuppressWarnings({"unchecked", "rawtypes"})
public class RegistryLoadTaskPendingRegistrationMixin {
    @ModifyExpressionValue(method = "loadFromResource", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Decoder;parse(Lcom/mojang/serialization/DynamicOps;Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;"))
    private static DataResult onLoadFromResource(DataResult original, Decoder elementDecoder, RegistryOps<JsonElement> ops, ResourceKey elementKey, Resource thunk) {
        return original.map(element -> DataModificationHandler.modify(elementKey, element, ops));
    }
}
