package net.foxboi.salted.common.levelgen.surface;

import com.mojang.serialization.MapCodec;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.reg.GameRegistry;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class ModSurfaceRules {
    private static final GameRegistry<MapCodec<? extends SurfaceRules.RuleSource>> REGISTRY = Smptg.REGISTRAR.game(Registries.MATERIAL_RULE);

    public static SurfaceRules.RuleSource biomeOverrides(Holder<BiomeSurfaceOverrides> overrides) {
        return new BiomeOverrideRule(overrides);
    }

    public static void init() {
        REGISTRY.register(Smptg.id("biome_overrides"), BiomeOverrideRule.CODEC.codec());
    }
}
