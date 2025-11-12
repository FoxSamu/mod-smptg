package net.foxboi.salted.common.block;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.foxboi.salted.common.Smptg;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public record ModBlockSetTypes() {
    public static final BlockSetType ASPEN = BlockSetTypeBuilder.copyOf(BlockSetType.BIRCH)
            .register(Smptg.id("aspen"));

    public static final BlockSetType BEECH = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
            .register(Smptg.id("beech"));

    public static final BlockSetType MAPLE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
            .register(Smptg.id("maple"));

    public static final BlockSetType REDWOOD = BlockSetTypeBuilder.copyOf(BlockSetType.SPRUCE)
            .register(Smptg.id("redwood"));

    public static final BlockSetType DEAD_WOOD = BlockSetTypeBuilder.copyOf(BlockSetType.CHERRY)
            .register(Smptg.id("dead_wood"));

    public static void init() {
    }
}
