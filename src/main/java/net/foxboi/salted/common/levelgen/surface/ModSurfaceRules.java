package net.foxboi.salted.common.levelgen.surface;

import com.mojang.serialization.MapCodec;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.reg.GameRegistry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
    private static final GameRegistry<MapCodec<? extends SurfaceRules.RuleSource>> REGISTRY = Smptg.REGISTRAR.game(Registries.MATERIAL_RULE);

    public static SurfaceRules.RuleSource biomeOverrides(Holder<BiomeSurfaceOverrides> overrides) {
        return new BiomeOverrideRule(overrides);
    }

    public static SurfaceRules.RuleSource randomSelect(Identifier randomName, WeightedList.Builder<SurfaceRules.RuleSource> builder) {
        return new RandomSelectRule(builder.build(), randomName);
    }

    public static void init() {
        REGISTRY.register(Smptg.id("biome_overrides"), BiomeOverrideRule.CODEC.codec());
        REGISTRY.register(Smptg.id("random_select"), RandomSelectRule.CODEC.codec());
    }
}
