package net.foxboi.salted.common.levelgen.tree;

import net.foxboi.salted.common.Smptg;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

@SuppressWarnings("unused")
public record ModFoliagePlacerTypes() {
    public static final FoliagePlacerType<PointyFoliagePlacer> POINTY = register("pointy", PointyFoliagePlacer.TYPE);
    public static final FoliagePlacerType<BrushFoliagePlacer> BRUSH = register("brush", BrushFoliagePlacer.TYPE);
    public static final FoliagePlacerType<RedwoodFoliagePlacer> REDWOOD = register("redwood", RedwoodFoliagePlacer.TYPE);

    private static <FP extends FoliagePlacer> FoliagePlacerType<FP> register(String id, FoliagePlacerType<FP> type) {
        return Registry.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, Smptg.id(id), type);
    }

    public static void init() {
    }
}
