package net.foxboi.salted.common.misc;

import net.minecraft.resources.Identifier;

public sealed interface ItemTint {
    static ItemTint grass() {
        return new Grass(0.5f, 1f);
    }

    static ItemTint constant(int color) {
        return new Constant(color);
    }

    static ItemTint biomeColor(Identifier name) {
        return new BiomeColor(name);
    }

    record Grass(float temperature, float downfall) implements ItemTint {
    }

    record Constant(int color) implements ItemTint {
    }

    record BiomeColor(Identifier color) implements ItemTint {
    }
}
