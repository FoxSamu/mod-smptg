package net.foxboi.salted.common.block;

import net.minecraft.util.StringRepresentable;

public enum ColumnPlantShape implements StringRepresentable {
    BODY("body"),
    GROWING("growing"),
    PERMANENT("permanent");

    private final String serialName;

    ColumnPlantShape(String serialName) {
        this.serialName = serialName;
    }

    @Override
    public String getSerializedName() {
        return serialName;
    }
}
