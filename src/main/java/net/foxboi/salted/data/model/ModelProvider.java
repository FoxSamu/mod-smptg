package net.foxboi.salted.data.model;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.foxboi.salted.client.color.BiomeColorTint;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.item.ModItems;
import net.foxboi.salted.common.misc.ItemTint;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        ModBlocks.models(new BlockModelsImpl(gen));
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {
        ModItems.models(new ItemModelsImpl(gen));
    }


    public static ItemTintSource createTintSource(ItemTint tint) {
        return switch (tint) {
            case ItemTint.BiomeColor c -> new BiomeColorTint(c.color());
            case ItemTint.Constant c -> ItemModelUtils.constantTint(c.color());
            case ItemTint.Grass c -> new GrassColorSource(c.temperature(), c.downfall());
        };
    }
}
