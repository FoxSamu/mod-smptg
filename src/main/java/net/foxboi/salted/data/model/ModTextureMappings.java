package net.foxboi.salted.data.model;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import static net.minecraft.client.data.models.model.TextureMapping.getBlockTexture;

public class ModTextureMappings {
    public static TextureMapping tintedWithOverlay(Block block) {
        return tintedWithOverlay(getBlockTexture(block));
    }

    public static TextureMapping tintedWithOverlay(ResourceLocation texture) {
        return new TextureMapping()
                .put(ModTextureSlots.TINTED, texture)
                .put(ModTextureSlots.UNTINTED, texture.withSuffix("_overlay"));
    }

    public static TextureMapping crossEmissive(ResourceLocation texture) {
        return new TextureMapping()
                .put(TextureSlot.CROSS, texture)
                .put(TextureSlot.CROSS_EMISSIVE, texture.withSuffix("_emissive"));
    }
}
