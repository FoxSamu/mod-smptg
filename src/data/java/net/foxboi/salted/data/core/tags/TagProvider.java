package net.foxboi.salted.data.core.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Util;

public abstract class TagProvider implements DataProvider {
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> lookups;

    private final Map<ResourceKey<? extends Registry<?>>, TagSet<?, ?>> tagSets = new HashMap<>();

    public TagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookups) {
        this.output = output;
        this.lookups = lookups;
    }

    protected boolean shouldWrite(TagKey<?> key, TagBuilder<?> builder) {
        return true;
    }

    final List<TagEntry> getEntries(TagKey<?> key) {
        var set = tagSets.get(key.registry());
        if (set == null) {
            return List.of();
        }

        var builder = set.existing(key);
        if (builder == null) {
            return List.of();
        }

        return builder.getEntries();
    }

    protected abstract List<TagSet<?, ?>> getTagSets();

    @Override
    public final CompletableFuture<?> run(CachedOutput cache) {
        return lookups
                .thenAcceptAsync(this::register, Util.backgroundExecutor())
                .thenCompose(_ -> saveAll(cache));
    }

    private void register(HolderLookup.Provider lookups) {
        var sets = getTagSets();
        for (var set : sets) {
            if (tagSets.put(set.getRegistry(), set) != null) {
                throw new RuntimeException("Duplicate tag set for registry " + set.getRegistry().identifier());
            }

            set.register(lookups);
        }
    }

    private CompletableFuture<?> saveAll(CachedOutput cache) {
        var futures = new ArrayList<CompletableFuture<?>>();

        for (var set : tagSets.values()) {
            futures.add(set.saveAll(output.createRegistryTagsPathProvider(set.getRegistry()), cache));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }
}
