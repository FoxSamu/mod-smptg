package net.foxboi.salted.data.model;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import net.foxboi.salted.data.ModBlockData;
import net.foxboi.salted.data.ModItemData;
import net.foxboi.salted.data.core.model.BlockDispatchSink;
import net.foxboi.salted.data.core.model.ItemDispatchSink;
import net.foxboi.salted.data.core.model.ModelProvider;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookups) {
        super(output, lookups);
    }

    @Override
    protected void setup(BlockDispatchSink blocks, ItemDispatchSink items, HolderLookup.Provider lookups) {
        var blockModels = new BlockModels(blocks, items);
        var itemModels = new ItemModels(items);

        ModBlockData.models(blockModels);
        ModItemData.models(itemModels);
    }

    @Override
    public String getName() {
        return "Models";
    }
}
