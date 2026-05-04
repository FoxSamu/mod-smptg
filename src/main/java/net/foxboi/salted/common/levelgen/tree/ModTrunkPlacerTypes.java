package net.foxboi.salted.common.levelgen.tree;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.reg.GameRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

@SuppressWarnings("unused")
public record ModTrunkPlacerTypes() {
    private static final GameRegistry<TrunkPlacerType<?>> REGISTRY = Smptg.REGISTRAR.game(Registries.TRUNK_PLACER_TYPE);

    public static final TrunkPlacerType<ThinRedwoodTrunkPlacer> THIN_REDWOOD = REGISTRY.register("thin_redwood", ThinRedwoodTrunkPlacer.TYPE);
    public static final TrunkPlacerType<DecentRedwoodTrunkPlacer> DECENT_REDWOOD = REGISTRY.register("decent_redwood", DecentRedwoodTrunkPlacer.TYPE);
    public static final TrunkPlacerType<MassiveRedwoodTrunkPlacer> MASSIVE_REDWOOD = REGISTRY.register("massive_redwood", MassiveRedwoodTrunkPlacer.TYPE);

    public static void init() {
    }
}
