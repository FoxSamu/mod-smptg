package net.foxboi.salted.common.levelgen.biome.modifications;

import java.util.Set;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.biome.BiomeEditor;
import net.foxboi.salted.common.misc.biome.BiomeModifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class BiomeModificationHelper {
    public static void init() {
        add(
                "plains_base",
                Set.of(
                        Biomes.PLAINS,
                        Biomes.SUNFLOWER_PLAINS,
                        Biomes.SAVANNA,
                        Biomes.SAVANNA_PLATEAU,
                        Biomes.WINDSWEPT_SAVANNA
                ),
                new PlainsBaseModification()
        );

        add(
                "forest_base",
                Set.of(
                        Biomes.FOREST,
                        Biomes.FLOWER_FOREST,
                        Biomes.DARK_FOREST,
                        Biomes.TAIGA,
                        Biomes.OLD_GROWTH_PINE_TAIGA,
                        Biomes.OLD_GROWTH_SPRUCE_TAIGA
                ),
                new ForestBaseModification()
        );

        add(
                "birch_forest",
                Set.of(
                        Biomes.BIRCH_FOREST
                ),
                new BirchForestModifications(false)
        );

        add(
                "old_growth_birch_forest",
                Set.of(
                        Biomes.OLD_GROWTH_BIRCH_FOREST
                ),
                new BirchForestModifications(true)
        );

        add(
                "swamp",
                Set.of(
                        Biomes.SWAMP
                ),
                new SwampModification()
        );

        add(
                "pale_garden",
                Set.of(
                        Biomes.PALE_GARDEN
                ),
                new PaleGardenModification()
        );
    }

    private static void add(String id, Set<ResourceKey<Biome>> biomes, Consumer<BiomeEditor> modification) {
        BiomeModifications.create(Smptg.id(id)).add(
                ModificationPhase.ADDITIONS,
                ctx -> biomes.contains(ctx.getBiomeKey()),
                ctx -> modification.accept(new BiomeModifier(ctx))
        );
    }
}
