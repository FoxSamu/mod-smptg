package net.foxboi.salted.data.loot;

import net.foxboi.salted.data.ModBlockData;
import net.foxboi.summon.api.loot.BlockLootProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends BlockLootProvider {
    public ModBlockLootTableProvider(PackOutput out, CompletableFuture<HolderLookup.Provider> regs) {
        super(out, regs);
    }

    @Override
    public void generate() {
        var blockDrops = new BlockDrops(this, registries);
        ModBlockData.drops(blockDrops);
    }

    @Override
    public String getName() {
        return "BlockLoot";
    }
}
