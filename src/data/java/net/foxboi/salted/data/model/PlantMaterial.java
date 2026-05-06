package net.foxboi.salted.data.model;

public enum PlantMaterial {
    BASIC,
    TINTED,
    LAYERED,
    EMISSIVE,
    GLOWING,
    TINTED_GLOWING;

    public boolean hasTint() {
        return this == TINTED || this == LAYERED || this == TINTED_GLOWING;
    }
}
