package net.foxboi.salted.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;

import net.foxboi.salted.common.block.ModBlockTags;
import net.foxboi.salted.common.block.ModBlocks;
import net.foxboi.salted.common.entity.ModEntityTypes;
import net.foxboi.salted.data.ModBlockData;
import net.foxboi.salted.data.core.tags.LookupTagSet;
import net.foxboi.salted.data.core.tags.TagProvider;

public class EntityTypeTagSet extends LookupTagSet<EntityType<?>> {
    public EntityTypeTagSet(TagProvider provider) {
        super(provider, BuiltInRegistries.ENTITY_TYPE);
    }

    @Override
    protected void register(HolderLookup.Provider lookups) {
        builder(EntityTypeTags.BOAT)
                .element(ModEntityTypes.ASPEN_BOAT)
                .element(ModEntityTypes.BEECH_BOAT)
                .element(ModEntityTypes.MAPLE_BOAT)
                .element(ModEntityTypes.REDWOOD_BOAT)
                .element(ModEntityTypes.DEAD_WOOD_BOAT)
                .element(ModEntityTypes.ASPEN_CHEST_BOAT)
                .element(ModEntityTypes.BEECH_CHEST_BOAT)
                .element(ModEntityTypes.MAPLE_CHEST_BOAT)
                .element(ModEntityTypes.REDWOOD_CHEST_BOAT)
                .element(ModEntityTypes.DEAD_WOOD_CHEST_BOAT);
    }

}
