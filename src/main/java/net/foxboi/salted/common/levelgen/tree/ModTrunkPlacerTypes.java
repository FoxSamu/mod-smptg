package net.foxboi.salted.common.levelgen.tree;

import net.foxboi.salted.common.Smptg;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

@SuppressWarnings("unused")
public record ModTrunkPlacerTypes() {
    public static final TrunkPlacerType<ThinRedwoodTrunkPlacer> THIN_REDWOOD = register("thin_redwood", ThinRedwoodTrunkPlacer.TYPE);
    public static final TrunkPlacerType<DecentRedwoodTrunkPlacer> DECENT_REDWOOD = register("decent_redwood", DecentRedwoodTrunkPlacer.TYPE);
    public static final TrunkPlacerType<MassiveRedwoodTrunkPlacer> MASSIVE_REDWOOD = register("massive_redwood", MassiveRedwoodTrunkPlacer.TYPE);

    private static <TP extends TrunkPlacer> TrunkPlacerType<TP> register(String id, TrunkPlacerType<TP> type) {
        return Registry.register(BuiltInRegistries.TRUNK_PLACER_TYPE, Smptg.id(id), type);
    }

    public static void init() {
    }
}
