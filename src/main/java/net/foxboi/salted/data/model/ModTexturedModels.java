package net.foxboi.salted.data.model;

import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;

import static net.minecraft.client.data.models.model.TexturedModel.createDefault;

public class ModTexturedModels {
    public static final TexturedModel.Provider TINTED_FLOWERBED_1 = createDefault(TextureMapping::flowerbed, ModModelTemplates.TINTED_FLOWERBED_1);
    public static final TexturedModel.Provider TINTED_FLOWERBED_2 = createDefault(TextureMapping::flowerbed, ModModelTemplates.TINTED_FLOWERBED_2);
    public static final TexturedModel.Provider TINTED_FLOWERBED_3 = createDefault(TextureMapping::flowerbed, ModModelTemplates.TINTED_FLOWERBED_3);
    public static final TexturedModel.Provider TINTED_FLOWERBED_4 = createDefault(TextureMapping::flowerbed, ModModelTemplates.TINTED_FLOWERBED_4);

    public static final TexturedModel.Provider LAYER_HEIGHT2 = createDefault(TextureMapping::defaultTexture, ModModelTemplates.LAYER_HEIGHT2);
    public static final TexturedModel.Provider LAYER_HEIGHT4 = createDefault(TextureMapping::defaultTexture, ModModelTemplates.LAYER_HEIGHT4);
    public static final TexturedModel.Provider LAYER_HEIGHT6 = createDefault(TextureMapping::defaultTexture, ModModelTemplates.LAYER_HEIGHT6);
    public static final TexturedModel.Provider LAYER_HEIGHT8 = createDefault(TextureMapping::defaultTexture, ModModelTemplates.LAYER_HEIGHT8);
    public static final TexturedModel.Provider LAYER_HEIGHT10 = createDefault(TextureMapping::defaultTexture, ModModelTemplates.LAYER_HEIGHT10);
    public static final TexturedModel.Provider LAYER_HEIGHT12 = createDefault(TextureMapping::defaultTexture, ModModelTemplates.LAYER_HEIGHT12);
    public static final TexturedModel.Provider LAYER_HEIGHT14 = createDefault(TextureMapping::defaultTexture, ModModelTemplates.LAYER_HEIGHT14);
    public static final TexturedModel.Provider LAYER_HEIGHT16 = createDefault(TextureMapping::defaultTexture, ModModelTemplates.LAYER_HEIGHT16);
}
