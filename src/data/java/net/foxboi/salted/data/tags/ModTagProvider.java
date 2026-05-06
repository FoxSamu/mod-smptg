package net.foxboi.salted.data.tags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;

import net.foxboi.salted.common.Smptg;
import net.foxboi.summon.api.tags.TagBuilder;
import net.foxboi.summon.api.tags.TagProvider;
import net.foxboi.summon.api.tags.TagSet;

public class ModTagProvider extends TagProvider {
    public ModTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookups) {
        super(output, lookups);
    }

    @Override
    protected List<TagSet<?, ?>> getTagSets() {
        return List.of(
                new BlockTagSet(this),
                new ItemTagSet(this),
                new BiomeTagSet(this),
                new EntityTypeTagSet(this)
        );
    }

    @Override
    protected boolean shouldWrite(TagKey<?> key, TagBuilder<?> builder) {
        // Save only non-empty tags, or
        // - replacing tag definitions, because the empty tag overwrites the existing entries
        // - tag definitions of this mod, because they need to be present in the data pack
        return !builder.isEmpty()
                || builder.isReplace()
                || key.location().getNamespace().equals(Smptg.ID);
    }

    @Override
    public String getName() {
        return "Tags";
    }
}
