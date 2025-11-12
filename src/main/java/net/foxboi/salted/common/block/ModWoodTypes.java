package net.foxboi.salted.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.foxboi.salted.common.Smptg;
import net.minecraft.world.level.block.state.properties.WoodType;

public record ModWoodTypes() {
    public static final WoodType ASPEN = WoodTypeBuilder.copyOf(WoodType.BIRCH)
            .register(Smptg.id("aspen"), ModBlockSetTypes.ASPEN);

    public static final WoodType BEECH = WoodTypeBuilder.copyOf(WoodType.OAK)
            .register(Smptg.id("beech"), ModBlockSetTypes.BEECH);

    public static final WoodType MAPLE = WoodTypeBuilder.copyOf(WoodType.OAK)
            .register(Smptg.id("maple"), ModBlockSetTypes.MAPLE);

    public static final WoodType REDWOOD = WoodTypeBuilder.copyOf(WoodType.SPRUCE)
            .register(Smptg.id("redwood"), ModBlockSetTypes.REDWOOD);

    public static final WoodType DEAD_WOOD = WoodTypeBuilder.copyOf(WoodType.CHERRY)
            .register(Smptg.id("dead_wood"), ModBlockSetTypes.DEAD_WOOD);

    public static void init() {
    }
}
