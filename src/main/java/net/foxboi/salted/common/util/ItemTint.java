package net.foxboi.salted.common.util;

import net.minecraft.resources.ResourceLocation;

public sealed interface ItemTint {
    static ItemTint grass() {
        return new Grass(0.5f, 1f);
    }

    static ItemTint constant(int color) {
        return new Constant(color);
    }

    static ItemTint biomeColor(ResourceLocation name) {
        return new BiomeColor(name);
    }

    record Grass(float temperature, float downfall) implements ItemTint {
    }

    record Constant(int color) implements ItemTint {
    }

    record BiomeColor(ResourceLocation color) implements ItemTint {
    }
}
