package net.foxboi.salted.data.loot;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.data.ModBlockData;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {
    public BlockLootTableProvider(FabricDataOutput out, CompletableFuture<HolderLookup.Provider> regs) {
        super(out, regs);
    }

    @Override
    public void generate() {
        var blockDrops = new BlockDrops(this, registries);
        ModBlockData.drops(blockDrops);
    }
}
