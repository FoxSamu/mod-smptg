package net.foxboi.salted.data.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;

import net.foxboi.salted.common.entity.ModEntityTypes;
import net.foxboi.summon.api.tags.LookupTagSet;
import net.foxboi.summon.api.tags.TagProvider;

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
