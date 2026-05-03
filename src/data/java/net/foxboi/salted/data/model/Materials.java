package net.foxboi.salted.data.model;

import net.minecraft.client.resources.model.sprite.Material;

import net.foxboi.salted.common.Smptg;

public class Materials {
    public static Material of(String id) {
        return new Material(Smptg.id(id));
    }

    public static Material of(String id, boolean forceTranslucent) {
        return new Material(Smptg.id(id), forceTranslucent);
    }

    public static Material withSuffix(Material mat, String suffix) {
        return new Material(mat.sprite().withSuffix(suffix), mat.forceTranslucent());
    }
}
