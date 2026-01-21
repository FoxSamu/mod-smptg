package net.foxboi.salted.data;

public sealed interface ItemTint {
    static ItemTint grass() {
        return new Grass(0.5f, 1f);
    }

    static ItemTint constant(int color) {
        return new Constant(color);
    }

    record Grass(float temperature, float downfall) implements ItemTint {
    }

    record Constant(int color) implements ItemTint {
    }
}
