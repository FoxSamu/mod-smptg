package net.foxboi.salted.common.levelgen.tree;

import net.foxboi.salted.common.Smptg;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;

@SuppressWarnings("unused")
public record ModRootPlacerTypes() {
    private static <RP extends RootPlacer> RootPlacerType<RP> register(String id, RootPlacerType<RP> type) {
        return Registry.register(BuiltInRegistries.ROOT_PLACER_TYPE, Smptg.id(id), type);
    }

    public static void init() {
    }
}
