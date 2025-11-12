package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.util.DataRegistry;
import net.foxboi.salted.data.lang.Translator;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import static net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesBlocks;

public record ModBiomes() {
    private static final DataRegistry<Biome> REGISTRY = DataRegistry.of(Registries.BIOME);

    public static final ResourceKey<Biome> ASPEN_FOREST = REGISTRY.register("aspen_forest", new AspenForest());
    public static final ResourceKey<Biome> MAPLE_FOREST = REGISTRY.register("maple_forest", new MapleForest());
    public static final ResourceKey<Biome> WOODED_PLAINS = REGISTRY.register("wooded_plains", new WoodedPlains());

    public static void init() {
        // N/A, just initialises the class
    }

    public static void translate(Translator<ResourceKey<Biome>> translator) {
        translator.name(ASPEN_FOREST, "Aspen Forest");
        translator.name(MAPLE_FOREST, "Maple Forest");
        translator.name(WOODED_PLAINS, "Wooded Plains");
    }
}
