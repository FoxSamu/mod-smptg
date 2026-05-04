package net.foxboi.salted.common.attribute;

import com.mojang.serialization.MapCodec;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.reg.GameRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.attribute.AttributeType;
import net.minecraft.world.level.levelgen.SurfaceRules;

public record ModAttributeTypes() {
    private static final GameRegistry<AttributeType<?>> REGISTRY = Smptg.REGISTRAR.game(Registries.ATTRIBUTE_TYPE);

    public static void init() {
    }
}
