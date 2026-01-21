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

    public static final TagKey<Block> OVERWORLD_STONE = tag("stone");
    public static final TagKey<Block> CAVE_PLANT_CAN_GROW_ON = tag("cave_plant_can_grow_on");
    public static final TagKey<Block> ASH_PLANT_CAN_GROW_ON = tag("ash_plant_can_grow_on");

    public static final TagKey<Block> GROWS_SALT = tag("grows_salt");
    public static final TagKey<Block> GROWS_SALT_MORE_LIKELY = tag("grows_salt_more_likely");
    public static final TagKey<Block> DAMAGES_SLIMES = tag("damages_slimes");
    public static final TagKey<Block> APPLIES_SALT_TO_DROPS = tag("applies_salt_to_drops");
    public static final TagKey<Block> SALT_CRUST_CAN_REPLACE = tag("salt_crust_can_replace");

    public static final TagKey<Block> SALT_ORES = tag("salt_ores");


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

        translator.name(OVERWORLD_STONE, "Stone");
        translator.name(CAVE_PLANT_CAN_GROW_ON, "Cave Plants Can Grow On");
        translator.name(ASH_PLANT_CAN_GROW_ON, "Ash Plants Can Grow On");

        translator.name(GROWS_SALT, "Grows Salt");
        translator.name(GROWS_SALT_MORE_LIKELY, "Grows Salt More Likely");
        translator.name(DAMAGES_SLIMES, "Damages Slimes");
        translator.name(APPLIES_SALT_TO_DROPS, "Applies Salt To Drops");
        translator.name(SALT_CRUST_CAN_REPLACE, "Salt Crust Can Replace");

        translator.name(SALT_ORES, "Salt Ores");
    }


    // FACTORY
    // =============================================

    private static TagKey<Block> tag(String id) {
        return Smptg.tag(Registries.BLOCK, id);
    }
}
