package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.reg.DataRegistry;
import net.foxboi.salted.common.misc.Translator;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

import static net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.matchesBlocks;

public record ModBiomes() {
    private static final DataRegistry<Biome> REGISTRY = Smptg.REGISTRAR.data(Registries.BIOME);

    public static final ResourceKey<Biome> ASPEN_FOREST = REGISTRY.register("aspen_forest", new AspenForest());
    public static final ResourceKey<Biome> MAPLE_FOREST = REGISTRY.register("maple_forest", new MapleForest());
    public static final ResourceKey<Biome> WOODED_PLAINS = REGISTRY.register("wooded_plains", new WoodedPlains());
    public static final ResourceKey<Biome> BARLEY_FIELD = REGISTRY.register("barley_field", new BarleyField());
    public static final ResourceKey<Biome> REDWOOD_TAIGA = REGISTRY.register("redwood_taiga", new RedwoodTaiga(false));
    public static final ResourceKey<Biome> SPARSE_REDWOOD_TAIGA = REGISTRY.register("sparse_redwood_taiga", new RedwoodTaiga(true));
    public static final ResourceKey<Biome> FIREFLY_MEADOW = REGISTRY.register("firefly_meadow", new FireflyMeadow());
    public static final ResourceKey<Biome> LAVENDER_FIELD = REGISTRY.register("lavender_field", new LavenderField());
    public static final ResourceKey<Biome> HEATHLAND = REGISTRY.register("heathland", new Heathland());

    public static final ResourceKey<Biome> LIMESTONE_CAVES = REGISTRY.register("limestone_caves", new LimestoneCaves());

    public static final ResourceKey<Biome> BURNING_FOREST = REGISTRY.register("burning_forest", new BurningForest());

    public static void init() {
        // N/A, just initialises the class
    }

    public static void translate(Translator<ResourceKey<Biome>> translator) {
        translator.name(ASPEN_FOREST, "Aspen Forest");
        translator.name(MAPLE_FOREST, "Maple Forest");
        translator.name(WOODED_PLAINS, "Wooded Plains");
        translator.name(BARLEY_FIELD, "Barley Field");
        translator.name(REDWOOD_TAIGA, "Redwood Taiga");
        translator.name(SPARSE_REDWOOD_TAIGA, "Sparse Redwood Taiga");
        translator.name(FIREFLY_MEADOW, "Firefly Meadow");
        translator.name(LAVENDER_FIELD, "Lavender Field");
        translator.name(HEATHLAND, "Heathland");

        translator.name(LIMESTONE_CAVES, "Limestone Caves");

        translator.name(BURNING_FOREST, "Burning Forest");
    }
}
