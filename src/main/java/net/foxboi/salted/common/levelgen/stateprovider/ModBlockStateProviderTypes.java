package net.foxboi.salted.common.levelgen.stateprovider;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.reg.GameRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

@SuppressWarnings("unused")
public record ModBlockStateProviderTypes() {
    private static final GameRegistry<BlockStateProviderType<?>> REGISTRY = Smptg.REGISTRAR.game(Registries.BLOCK_STATE_PROVIDER_TYPE);

    public static final BlockStateProviderType<EitherStateProvider> EITHER = REGISTRY.register("either", EitherStateProvider.TYPE);

    public static void init() {
    }
}
