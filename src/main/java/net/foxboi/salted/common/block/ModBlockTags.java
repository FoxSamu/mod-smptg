package net.foxboi.salted.common.block;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.Translator;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public record ModBlockTags() {
    // TAGS
    // =============================================

    public static final TagKey<Block> ASPEN_LOGS = tag("aspen_logs");
    public static final TagKey<Block> BEECH_LOGS = tag("beech_logs");
    public static final TagKey<Block> MAPLE_LOGS = tag("maple_logs");
    public static final TagKey<Block> REDWOOD_LOGS = tag("redwood_logs");
    public static final TagKey<Block> DEAD_LOGS = tag("dead_logs");

    public static final TagKey<Block> SUPPORTS_CAVE_VEGETATION = tag("supports_cave_vegetation");
    public static final TagKey<Block> SUPPORTS_WATER_VEGETATION = tag("supports_water_vegetation");
    public static final TagKey<Block> SUPPORTS_ASH_VEGETATION = tag("supports_ash_vegetation");
    public static final TagKey<Block> SUPPORTS_EMBER_VEGETATION = tag("supports_ember_vegetation");

    public static final TagKey<Block> LIMESTONE_REPLACEABLE = tag("limestone_replaceable");
    public static final TagKey<Block> LIMESTONE_ORE_REPLACEABLES = tag("limestone_ore_replaceables");
    public static final TagKey<Block> GRASS_SPREAD_SOURCE = tag("grass_spread_source");

    public static final TagKey<Block> PEAT = tag("peat");
    public static final TagKey<Block> ANY_PEAT = tag("any_peat");
    public static final TagKey<Block> TRANSFERS_FLUID_TO_PEAT = tag("transfers_fluid_to_peat");

    public static final TagKey<Block> DAMAGES_SLIMES = tag("damages_slimes");

    public static final TagKey<Block> BRAZIERS = tag("braziers");


    // INITIALISATION
    // =============================================

    public static void init() {
        // N/A, calling this just ensures the class gets initialised
    }


    // INITIALISATION
    // =============================================

    public static void translate(Translator<TagKey<Block>> translator) {
        translator.name(ASPEN_LOGS, "Aspen Logs");
        translator.name(BEECH_LOGS, "Beech Logs");
        translator.name(MAPLE_LOGS, "Maple Logs");
        translator.name(REDWOOD_LOGS, "Redwood Logs");
        translator.name(DEAD_LOGS, "Dead Logs");

        translator.name(SUPPORTS_CAVE_VEGETATION, "Supports Cave Vegetation");
        translator.name(SUPPORTS_WATER_VEGETATION, "Supports Water Vegetation");
        translator.name(SUPPORTS_ASH_VEGETATION, "Supports Ash Vegetation");
        translator.name(SUPPORTS_EMBER_VEGETATION, "Supports Ember Vegetation");

        translator.name(LIMESTONE_REPLACEABLE, "Limestone Replaceable");
        translator.name(LIMESTONE_ORE_REPLACEABLES, "Limestone Ore Replaceables");
        translator.name(GRASS_SPREAD_SOURCE, "Grass Spread Source");
        translator.name(TRANSFERS_FLUID_TO_PEAT, "Transfers Fluids To Peat");
        translator.name(PEAT, "Peat");
        translator.name(ANY_PEAT, "Any Peat");

        translator.name(DAMAGES_SLIMES, "Damages Slimes");

        translator.name(BRAZIERS, "Braziers");
    }


    // FACTORY
    // =============================================

    private static TagKey<Block> tag(String id) {
        return Smptg.tag(Registries.BLOCK, id);
    }
}
