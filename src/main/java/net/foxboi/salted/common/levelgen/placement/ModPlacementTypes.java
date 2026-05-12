package net.foxboi.salted.common.levelgen.placement;

import com.mojang.serialization.MapCodec;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.reg.GameRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

@SuppressWarnings("unused")
public record ModPlacementTypes() {
    private static final GameRegistry<PlacementModifierType<?>> REGISTRY = Smptg.REGISTRAR.game(Registries.PLACEMENT_MODIFIER_TYPE);

    // FEATURES
    // =============================================

    public static final PlacementModifierType<?> AT_SEA_LEVEL = register("at_sea_level", SeaLevelHeightPlacement.CODEC);
    public static final PlacementModifierType<?> REPEAT_IN_SQUARE = register("repeat_in_square", RepeatInSquarePlacement.CODEC);
    public static final PlacementModifierType<?> BIOME_LIST = register("biome_list", BiomeListFilter.CODEC);


    // INITIALISATION
    // =============================================

    public static void init() {
    }


    // REGISTRY
    // =============================================

    private static <P extends PlacementModifier> PlacementModifierType<P> register(String name, MapCodec<P> codec) {
        return REGISTRY.register(name, () -> codec);
    }
}
