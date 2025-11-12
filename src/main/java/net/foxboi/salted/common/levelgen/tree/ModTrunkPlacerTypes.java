package net.foxboi.salted.common.levelgen.tree;

import net.foxboi.salted.common.Smptg;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

@SuppressWarnings("unused")
public record ModTrunkPlacerTypes() {
    private static <TP extends TrunkPlacer> TrunkPlacerType<TP> register(String id, TrunkPlacerType<TP> type) {
        return Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, Smptg.id(id), type);
    }

    public static void init() {
    }
}
