package net.foxboi.salted.data.model;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        ModBlocks.models(new BlockModels(gen));
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {
        ModItems.models(new ItemModels(gen));
    }
}
