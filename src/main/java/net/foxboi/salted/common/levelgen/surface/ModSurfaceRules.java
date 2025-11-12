package net.foxboi.salted.common.levelgen.surface;

import net.foxboi.salted.common.Smptg;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
    public static SurfaceRules.RuleSource biomeOverrides(Holder<BiomeSurfaceOverrides> overrides) {
        return new BiomeOverrideRule(overrides);
    }

    public static void init() {
        Registry.register(BuiltInRegistries.MATERIAL_RULE, Smptg.id("biome_overrides"), BiomeOverrideRule.CODEC.codec());
    }
}
