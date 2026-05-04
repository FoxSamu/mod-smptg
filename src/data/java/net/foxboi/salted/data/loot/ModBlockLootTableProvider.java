package net.foxboi.salted.data.loot;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;

import net.foxboi.salted.data.ModBlockData;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootSubProvider {
    public ModBlockLootTableProvider(FabricPackOutput out, CompletableFuture<HolderLookup.Provider> regs) {
        super(out, regs);
    }

    @Override
    public void generate() {
        var blockDrops = new BlockDrops(this, registries);
        ModBlockData.drops(blockDrops);
    }
}
