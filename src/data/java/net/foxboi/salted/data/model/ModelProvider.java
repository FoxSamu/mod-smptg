package net.foxboi.salted.data.model;

import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import net.foxboi.salted.data.ItemTint;
import net.foxboi.salted.data.ModBlockData;
import net.foxboi.salted.data.ModItemData;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        ModBlockData.models(new BlockModelsImpl(gen));
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {
        ModItemData.models(new ItemModelsImpl(gen));
    }


    public static ItemTintSource createTintSource(ItemTint tint) {
        return switch (tint) {
            case ItemTint.Constant c -> ItemModelUtils.constantTint(c.color());
            case ItemTint.Grass c -> new GrassColorSource(c.temperature(), c.downfall());
        };
    }
}
