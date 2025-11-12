package net.foxboi.salted.common.levelgen.stateprovider;

import net.foxboi.salted.common.Smptg;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

@SuppressWarnings("unused")
public record ModBlockStateProviderTypes() {
    public static final BlockStateProviderType<EitherStateProvider> EITHER = register("either", EitherStateProvider.TYPE);

    private static <BSP extends BlockStateProvider> BlockStateProviderType<BSP> register(String id, BlockStateProviderType<BSP> type) {
        return Registry.register(BuiltInRegistries.BLOCKSTATE_PROVIDER_TYPE, Smptg.id(id), type);
    }

    public static void init() {
    }
}
