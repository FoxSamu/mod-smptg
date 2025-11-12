package net.foxboi.salted.common.levelgen.biome.modifications;

import java.util.Set;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.foxboi.salted.common.Smptg;
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
                        Biomes.BIRCH_FOREST,
                        Biomes.DARK_FOREST,
                        Biomes.OLD_GROWTH_BIRCH_FOREST,
                        Biomes.TAIGA,
                        Biomes.OLD_GROWTH_PINE_TAIGA,
                        Biomes.OLD_GROWTH_SPRUCE_TAIGA
                ),
                new ForestBaseModification()
        );

        add(
                "swamp",
                Set.of(
                        Biomes.SWAMP
                ),
                new SwampModification()
        );
    }

    private static void add(String id, Set<ResourceKey<Biome>> biomes, Consumer<BiomeModificationContext> modification) {
        BiomeModifications.create(Smptg.id(id)).add(
                ModificationPhase.ADDITIONS,
                ctx -> biomes.contains(ctx.getBiomeKey()),
                modification
        );
    }
}
