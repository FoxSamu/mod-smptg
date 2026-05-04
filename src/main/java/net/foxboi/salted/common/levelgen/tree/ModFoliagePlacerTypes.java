package net.foxboi.salted.common.levelgen.tree;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.reg.GameRegistry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

@SuppressWarnings("unused")
public record ModFoliagePlacerTypes() {
    private static final GameRegistry<FoliagePlacerType<?>> REGISTRY = Smptg.REGISTRAR.game(Registries.FOLIAGE_PLACER_TYPE);

    public static final FoliagePlacerType<PointyFoliagePlacer> POINTY = REGISTRY.register("pointy", PointyFoliagePlacer.TYPE);
    public static final FoliagePlacerType<BrushFoliagePlacer> BRUSH = REGISTRY.register("brush", BrushFoliagePlacer.TYPE);
    public static final FoliagePlacerType<RedwoodFoliagePlacer> REDWOOD = REGISTRY.register("redwood", RedwoodFoliagePlacer.TYPE);

    public static void init() {
    }
}
