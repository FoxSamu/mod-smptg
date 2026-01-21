package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.Translator;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public record ModBiomeTags() {
    // TAGS
    // =============================================

    public static final TagKey<Biome> HAS_SALT_CRUST = tag("has_salt_crust");

    public static final TagKey<Biome> GRASS_BLOCK_BONEMEAL_GROWS_BARLEY = tag("grass_block_bonemeal/grows_barley");
    public static final TagKey<Biome> GRASS_BLOCK_BONEMEAL_GROWS_CLOVERS = tag("grass_block_bonemeal/grows_clovers");
    public static final TagKey<Biome> UNDERWATER_BONEMEAL_GROWS_CATTAIL = tag("underwater_bonemeal/grows_cattail");


    // INITIALISATION
    // =============================================

    public static void init() {
        // N/A, calling this just ensures the class gets initialised
    }


    // INITIALISATION
    // =============================================

    public static void translate(Translator<TagKey<Biome>> translator) {
        translator.name(HAS_SALT_CRUST, "Has Salt Crust");
        translator.name(GRASS_BLOCK_BONEMEAL_GROWS_BARLEY, "Bonemealing Grass Blocks Grows Barley");
        translator.name(GRASS_BLOCK_BONEMEAL_GROWS_CLOVERS, "Bonemealing Grass Blocks Grows Clovers");
        translator.name(UNDERWATER_BONEMEAL_GROWS_CATTAIL, "Bonemealing Grass Blocks Grows Cattail");
    }


    // FACTORY
    // =============================================

    private static TagKey<Biome> tag(String id) {
        return Smptg.tag(Registries.BIOME, id);
    }
}
