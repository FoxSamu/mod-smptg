package net.foxboi.summon.api.tags;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public sealed class TagBuilder<T> permits LookupTagBuilder {
    private final TagProvider provider;
    private final TagKey<T> key;

    private final List<TagEntry> entries = new ArrayList<>();
    private boolean replace;

    public TagBuilder(TagProvider provider, TagKey<T> key) {
        this.provider = provider;
        this.key = key;
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public boolean isReplace() {
        return replace;
    }

    public TagProvider getProvider() {
        return provider;
    }

    public TagKey<T> getKey() {
        return key;
    }

    public List<TagEntry> getEntries() {
        return List.copyOf(entries);
    }

    public TagBuilder<T> replace(boolean replace) {
        this.replace = replace;
        return this;
    }

    public TagBuilder<T> copyFrom(TagBuilder<?> copy) {
        entries.addAll(copy.getEntries());
        return this;
    }

    public TagBuilder<T> copyFrom(TagKey<?> copy) {
        entries.addAll(provider.getEntries(copy));
        return this;
    }

    public TagBuilder<T> element(Identifier elem) {
        entries.add(new TagEntry(elem, false, false));
        return this;
    }

    public TagBuilder<T> element(ResourceKey<T> elem) {
        entries.add(new TagEntry(elem.identifier(), false, false));
        return this;
    }

    public TagBuilder<T> element(Holder<T> elem) {
        entries.add(new TagEntry(elem.unwrapKey().orElseThrow(() -> new RuntimeException("Cannot add unbound holder to tag")).identifier(), false, false));
        return this;
    }

    public TagBuilder<T> tag(Identifier tag) {
        entries.add(new TagEntry(tag, true, false));
        return this;
    }

    public TagBuilder<T> tag(TagKey<T> tag) {
        entries.add(new TagEntry(tag.location(), true, false));
        return this;
    }

    public TagBuilder<T> optionalElement(Identifier elem) {
        entries.add(new TagEntry(elem, false, true));
        return this;
    }

    public TagBuilder<T> optionalElement(ResourceKey<T> elem) {
        entries.add(new TagEntry(elem.identifier(), false, true));
        return this;
    }

    public TagBuilder<T> optionalElement(Holder<T> elem) {
        entries.add(new TagEntry(elem.unwrapKey().orElseThrow(() -> new RuntimeException("Cannot add unbound holder to tag")).identifier(), false, true));
        return this;
    }

    public TagBuilder<T> optionalTag(Identifier tag) {
        entries.add(new TagEntry(tag, false, true));
        return this;
    }

    public TagBuilder<T> optionalTag(TagKey<T> tag) {
        entries.add(new TagEntry(tag.location(), true, true));
        return this;
    }

    public JsonElement toJson() {
        var object = new JsonObject();
        object.addProperty("replace", replace);

        var entries = new JsonArray();
        for (var e : this.entries) {
            entries.add(e.toJson());
        }

        object.add("values", entries);
        return object;
    }
}
