package net.foxboi.salted.data.tags;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EntityTypeTags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

import net.foxboi.salted.common.entity.ModEntityTypes;

public class EntityTypeTagProvider extends FabricTagsProvider.EntityTypeTagsProvider {
    public EntityTypeTagProvider(FabricPackOutput out, CompletableFuture<HolderLookup.Provider> regs) {
        super(out, regs);
    }

    @Override
    protected void addTags(HolderLookup.Provider regs) {
        valueLookupBuilder(EntityTypeTags.BOAT)
                .add(ModEntityTypes.ASPEN_BOAT)
                .add(ModEntityTypes.BEECH_BOAT)
                .add(ModEntityTypes.MAPLE_BOAT)
                .add(ModEntityTypes.REDWOOD_BOAT)
                .add(ModEntityTypes.DEAD_WOOD_BOAT)
                .add(ModEntityTypes.ASPEN_CHEST_BOAT)
                .add(ModEntityTypes.BEECH_CHEST_BOAT)
                .add(ModEntityTypes.MAPLE_CHEST_BOAT)
                .add(ModEntityTypes.REDWOOD_CHEST_BOAT)
                .add(ModEntityTypes.DEAD_WOOD_CHEST_BOAT);
    }
}
