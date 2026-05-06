package net.foxboi.salted.data.core.model;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import com.terraformersmc.modmenu.util.mod.Mod;

public interface BlockModelTemplate {
    Model create(Block block, String suffix);

    default Model create(Block block) {
        return create(block, "");
    }
}
