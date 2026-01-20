package net.foxboi.salted.common;

import net.foxboi.salted.common.levelgen.biome.ModBiomeTags;
import net.foxboi.salted.common.levelgen.biome.ModBiomes;
import net.foxboi.salted.common.block.ModBlockSetTypes;
import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.block.ModWoodTypes;
import net.foxboi.salted.common.color.BiomeColors;
import net.foxboi.salted.common.item.ModItemTags;
import net.foxboi.salted.common.item.ModItems;
import net.foxboi.salted.common.levelgen.biome.modifications.BiomeModificationHelper;
import net.foxboi.salted.common.levelgen.*;
import net.foxboi.salted.common.levelgen.noise.ModNoises;
import net.foxboi.salted.common.levelgen.stateprovider.ModBlockStateProviderTypes;
import net.foxboi.salted.common.levelgen.feature.ModFeatures;
import net.foxboi.salted.common.levelgen.placement.ModPlacementTypes;
import net.foxboi.salted.common.levelgen.surface.ModSurfaceRules;
import net.foxboi.salted.common.levelgen.tree.ModFoliagePlacerTypes;
import net.foxboi.salted.common.levelgen.tree.ModRootPlacerTypes;
import net.foxboi.salted.common.levelgen.tree.ModTreeDecoratorTypes;
import net.foxboi.salted.common.levelgen.tree.ModTrunkPlacerTypes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The SMPTG main mod class. This also acts as a delegate for client methods that are not available on the server.
 *
 * @author Samū Ketuvo
 */
public class Smptg {
    private static Smptg instance;

    public static final String ID = "smptg";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    protected Smptg() {
        if (instance != null) {
            throw new RuntimeException("SMPTG: There can only be one instance of the mod class");
        }
        instance = this;
    }

    public static Smptg get() {
        return instance;
    }

    /**
     * Initialises the mod.
     */
    public void init() {
        LOGGER.info("Hello biome world!");

        // Init registries
        ModRegistries.init();

        // Init tags
        ModBlockTags.init();
        ModItemTags.init();
        ModBiomeTags.init();

        // Init built in registries
        ModBlockSetTypes.init();
        ModWoodTypes.init();

        ModBlocks.init();
        ModItems.init();

        ModFeatures.init();
        ModPlacementTypes.init();
        ModBlockStateProviderTypes.init();
        ModFoliagePlacerTypes.init();
        ModTrunkPlacerTypes.init();
        ModRootPlacerTypes.init();
        ModTreeDecoratorTypes.init();
        ModSurfaceRules.init();

        BiomeColors.init();

        // Init worldgen registry
        ModBiomes.init();
        ModNoises.init();

        ModTreeFeatures.init();
        ModTreePlacements.init();
        ModVegetationFeatures.init();
        ModVegetationPlacements.init();

        // Init handlers
        BiomeModificationHelper.init();
    }

    /**
     * Returns the biome blend radius, as configured on the client. When running on a dedicated server, this returns 0.
     * @return The biome blend radius.
     */
    public int getBiomeBlendRadius() {
        return 0;
    }

    /**
     * Creates the string form of a {@link Identifier} with {@code smptg} as namespace.
     *
     * @param path The path
     */
    public static String sid(String path) {
        return ID + ":" + path;
    }

    /**
     * Creates a {@link Identifier} with {@code smptg} as namespace.
     *
     * @param path The path
     */
    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(ID, path);
    }

    /**
     * Creates a {@link ResourceKey} for a registry with {@code smptg} as namespace.
     *
     * @param path     The path
     */
    public static <T> ResourceKey<Registry<T>> reg(String path) {
        return ResourceKey.createRegistryKey(id(path));
    }

    /**
     * Creates a {@link ResourceKey} for the given registry with {@code smptg} as namespace.
     *
     * @param registry The registry key
     * @param path     The path
     */
    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String path) {
        return ResourceKey.create(registry, id(path));
    }

    /**
     * Creates a {@link TagKey} for the given registry with {@code smptg} as namespace.
     *
     * @param registry The registry key
     * @param path     The path
     */
    public static <T> TagKey<T> tag(ResourceKey<? extends Registry<T>> registry, String path) {
        return TagKey.create(registry, id(path));
    }
}