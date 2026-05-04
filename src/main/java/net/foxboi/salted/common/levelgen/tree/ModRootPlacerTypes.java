package net.foxboi.salted.common.levelgen.tree;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.reg.GameRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

@SuppressWarnings("unused")
public record ModRootPlacerTypes() {
    private static final GameRegistry<RootPlacerType<?>> REGISTRY = Smptg.REGISTRAR.game(Registries.ROOT_PLACER_TYPE);

    public static void init() {
    }
}
