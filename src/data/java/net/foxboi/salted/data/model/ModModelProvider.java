package net.foxboi.salted.data.model;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import net.foxboi.salted.data.ModBlockData;
import net.foxboi.salted.data.ModItemData;
import net.foxboi.summon.api.model.BlockDispatchSink;
import net.foxboi.summon.api.model.ItemDispatchSink;
import net.foxboi.summon.api.model.ModelProvider;
import net.foxboi.summon.api.model.ModelSink;

public class ModModelProvider extends ModelProvider {
    public ModModelProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookups) {
        super(output, lookups);
    }

    @Override
    protected void setup(BlockDispatchSink blocks, ItemDispatchSink items, ModelSink models, HolderLookup.Provider lookups) {
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
