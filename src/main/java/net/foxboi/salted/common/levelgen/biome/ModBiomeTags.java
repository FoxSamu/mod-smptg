package net.foxboi.salted.common.levelgen.biome;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.data.lang.Translator;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public record ModBiomeTags() {
    // TAGS
    // =============================================

    public static final TagKey<Biome> HAS_SALT_CRUST = tag("has_salt_crust");


    // INITIALISATION
    // =============================================

    public static void init() {
        // N/A, calling this just ensures the class gets initialised
    }


    // INITIALISATION
    // =============================================

    public static void translate(Translator<TagKey<Biome>> translator) {
        translator.name(HAS_SALT_CRUST, "Has Salt Crust");
    }


    // FACTORY
    // =============================================

    private static TagKey<Biome> tag(String id) {
        return Smptg.tag(Registries.BIOME, id);
    }
}
