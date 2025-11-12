package net.foxboi.salted.common.util;

import net.minecraft.resources.ResourceLocation;

public sealed interface ItemTint {
    static ItemTint grass() {
        return Defaults.GRASS;
    }

    static ItemTint constant(int color) {
        return new Constant(color);
    }

    static ItemTint biomeColor(ResourceLocation name) {
        return new BiomeColor(name);
    }

    enum Defaults implements ItemTint {
        GRASS
    }

    record Constant(int color) implements ItemTint {
    }

    record BiomeColor(ResourceLocation color) implements ItemTint {
    }
}
