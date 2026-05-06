package net.foxboi.salted.data.model;

import net.minecraft.world.level.block.Block;

import net.foxboi.summon.api.model.TextureMap;

public class BlockTextures {
    public static TextureMap texture(Block block) {
        return TextureMap.map()
                .put(TextureKeys.TEXTURE, block);
    }

    public static TextureMap texture(Block block, String suffix) {
        return TextureMap.map()
                .put(TextureKeys.TEXTURE, block, suffix);
    }

    public static TextureMap particle(Block block) {
        return TextureMap.map()
                .put(TextureKeys.PARTICLE, block);
    }

    public static TextureMap particle(Block block, String suffix) {
        return TextureMap.map()
                .put(TextureKeys.PARTICLE, block, suffix);
    }

    public static TextureMap all(Block block) {
        return TextureMap.map()
                .put(TextureKeys.ALL, block);
    }

    public static TextureMap all(Block block, String suffix) {
        return TextureMap.map()
                .put(TextureKeys.ALL, block, suffix);
    }

    public static TextureMap column(Block block) {
        return TextureMap.map()
                .put(TextureKeys.SIDE, block)
                .put(TextureKeys.END, block, "_top");
    }

    public static TextureMap column(Block block, String suffix) {
        return TextureMap.map()
                .put(TextureKeys.SIDE, block, suffix)
                .put(TextureKeys.END, block, suffix + "_top");
    }

    public static TextureMap bottomTop(Block block) {
        return TextureMap.map()
                .put(TextureKeys.SIDE, block)
                .put(TextureKeys.BOTTOM, block, "_bottom")
                .put(TextureKeys.TOP, block, "_top");
    }

    public static TextureMap bottomTop(Block block, String suffix) {
        return TextureMap.map()
                .put(TextureKeys.SIDE, block, suffix)
                .put(TextureKeys.BOTTOM, block, suffix + "_bottom")
                .put(TextureKeys.TOP, block, suffix + "_top");
    }

    public static TextureMap cross(Block block) {
        return TextureMap.map()
                .put(TextureKeys.CROSS, block);
    }

    public static TextureMap cross(Block block, String suffix) {
        return TextureMap.map()
                .put(TextureKeys.CROSS, block, suffix);
    }

    public static TextureMap crossEmissive(Block block) {
        return TextureMap.map()
                .put(TextureKeys.CROSS, block)
                .put(TextureKeys.CROSS_EMISSIVE, block, "_emissive");
    }

    public static TextureMap crossEmissive(Block block, String suffix) {
        return TextureMap.map()
                .put(TextureKeys.CROSS, block, suffix)
                .put(TextureKeys.CROSS_EMISSIVE, block, suffix + "_emissive");
    }

    public static TextureMap crossLayered(Block block) {
        return TextureMap.map()
                .put(TextureKeys.CROSS, block)
                .put(ModTextureKeys.OVERLAY, block, "_overlay");
    }

    public static TextureMap crossLayered(Block block, String suffix) {
        return TextureMap.map()
                .put(TextureKeys.CROSS, block, suffix)
                .put(ModTextureKeys.OVERLAY, block, suffix + "_overlay");
    }

    public static TextureMap textureEmissive(Block block) {
        return TextureMap.map()
                .put(TextureKeys.TEXTURE, block)
                .put(ModTextureKeys.EMISSIVE, block, "_emissive");
    }

    public static TextureMap textureEmissive(Block block, String suffix) {
        return TextureMap.map()
                .put(TextureKeys.TEXTURE, block, suffix)
                .put(ModTextureKeys.EMISSIVE, block, suffix + "_emissive");
    }

    public static TextureMap textureLayered(Block block) {
        return TextureMap.map()
                .put(TextureKeys.TEXTURE, block)
                .put(ModTextureKeys.OVERLAY, block, "_overlay");
    }

    public static TextureMap textureLayered(Block block, String suffix) {
        return TextureMap.map()
                .put(TextureKeys.TEXTURE, block, suffix)
                .put(ModTextureKeys.OVERLAY, block, suffix + "_overlay");
    }

    public static TextureMap flowerbed(Block block) {
        return TextureMap.map()
                .put(TextureKeys.FLOWERBED, block)
                .put(TextureKeys.STEM, block, "_stem");
    }

    public static TextureMap flowerbed(Block block, String suffix) {
        return TextureMap.map()
                .put(TextureKeys.FLOWERBED, block, suffix)
                .put(TextureKeys.STEM, block, suffix + "_stem");
    }

    public static TextureMap flowerbedEmissive(Block block) {
        return TextureMap.map()
                .put(TextureKeys.FLOWERBED, block)
                .put(TextureKeys.STEM, block, "_stem")
                .put(ModTextureKeys.EMISSIVE, block, "_emissive");
    }

    public static TextureMap flowerbedEmissive(Block block, String suffix) {
        return TextureMap.map()
                .put(TextureKeys.FLOWERBED, block, suffix)
                .put(TextureKeys.STEM, block, suffix + "_stem")
                .put(ModTextureKeys.EMISSIVE, block, suffix + "_emissive");
    }

    public static TextureMap flowerbedLayered(Block block) {
        return TextureMap.map()
                .put(TextureKeys.FLOWERBED, block)
                .put(TextureKeys.STEM, block, "_stem")
                .put(ModTextureKeys.OVERLAY, block, "_overlay");
    }

    public static TextureMap flowerbedLayered(Block block, String suffix) {
        return TextureMap.map()
                .put(TextureKeys.FLOWERBED, block, suffix)
                .put(TextureKeys.STEM, block, suffix + "_stem")
                .put(ModTextureKeys.OVERLAY, block, suffix + "_overlay");
    }
}
