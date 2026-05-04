package net.foxboi.salted.data.core.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import org.jetbrains.annotations.Nullable;

public abstract sealed class TagSet<T, B extends TagBuilder<T>> permits DataTagSet, LookupTagSet {
    private final TagProvider provider;
    private final ResourceKey<Registry<T>> registry;
    private final BiFunction<TagProvider, TagKey<T>, B> builderFactory;

    private final Map<TagKey<T>, B> tagBuilders = new HashMap<>();

    public TagSet(TagProvider provider, ResourceKey<Registry<T>> registry, BiFunction<TagProvider, TagKey<T>, B> builderFactory) {
        this.provider = provider;
        this.registry = registry;
        this.builderFactory = builderFactory;
    }

    public final ResourceKey<Registry<T>> getRegistry() {
        return registry;
    }

    @Nullable
    protected final B existing(TagKey<?> key) {
        return tagBuilders.get(key);
    }

    protected final B builder(TagKey<T> key) {
        return tagBuilders.computeIfAbsent(key, k -> builderFactory.apply(provider, k));
    }

    protected abstract void register(HolderLookup.Provider lookups);

    CompletableFuture<?> saveAll(PackOutput.PathProvider output, CachedOutput cache) {
        var futures = new ArrayList<CompletableFuture<?>>();

        for (var builder : tagBuilders.values()) {
            var json = builder.toJson();
            var key = builder.getKey();

            if (provider.shouldWrite(key, builder)) {
                futures.add(DataProvider.saveStable(cache, json, output.json(key.location())));
            }
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }
}
