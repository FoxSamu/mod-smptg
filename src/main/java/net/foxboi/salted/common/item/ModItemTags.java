package net.foxboi.salted.common.item;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.misc.Translator;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public record ModItemTags() {
    // TAGS
    // =============================================

    public static final TagKey<Item> ASPEN_LOGS = tag("aspen_logs");
    public static final TagKey<Item> BEECH_LOGS = tag("beech_logs");
    public static final TagKey<Item> MAPLE_LOGS = tag("maple_logs");
    public static final TagKey<Item> REDWOOD_LOGS = tag("redwood_logs");
    public static final TagKey<Item> DEAD_LOGS = tag("dead_logs");

    public static final TagKey<Item> SALTED_FOODS = tag("salted_foods");

    public static final TagKey<Item> SALT_ORES = tag("salt_ores");


    // INITIALISATION
    // =============================================

    public static void init() {
        // N/A, calling this just ensures the class gets initialised
    }


    // INITIALISATION
    // =============================================

    public static void translate(Translator<TagKey<Item>> translator) {
        translator.name(ASPEN_LOGS, "Aspen Logs");
        translator.name(BEECH_LOGS, "Beech Logs");
        translator.name(MAPLE_LOGS, "Maple Logs");
        translator.name(REDWOOD_LOGS, "Redwood Logs");
        translator.name(DEAD_LOGS, "Dead Logs");

        translator.name(SALTED_FOODS, "Salted Foods");
        translator.name(SALT_ORES, "Salt Ores");
    }


    // FACTORY
    // =============================================

    private static TagKey<Item> tag(String id) {
        return Smptg.tag(Registries.ITEM, id);
    }
}
