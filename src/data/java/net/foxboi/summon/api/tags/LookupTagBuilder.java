package net.foxboi.summon.api.tags;

import java.util.function.Function;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

public final class LookupTagBuilder<T> extends TagBuilder<T> {
    private final Function<T, ResourceKey<T>> idLookup;

    public LookupTagBuilder(TagProvider provider, TagKey<T> key, Function<T, ResourceKey<T>> idLookup) {
        super(provider, key);
        this.idLookup = idLookup;
    }

    @Override
    public LookupTagBuilder<T> replace(boolean replace) {
        super.replace(replace);
        return this;
    }

    @Override
    public LookupTagBuilder<T> copyFrom(TagBuilder<?> copy) {
        super.copyFrom(copy);
        return this;
    }

    @Override
    public LookupTagBuilder<T> copyFrom(TagKey<?> copy) {
        super.copyFrom(copy);
        return this;
    }

    public LookupTagBuilder<T> element(T elem) {
        return element(idLookup.apply(elem));
    }

    @Override
    public LookupTagBuilder<T> element(Identifier elem) {
        super.element(elem);
        return this;
    }

    @Override
    public LookupTagBuilder<T> element(ResourceKey<T> elem) {
        super.element(elem);
        return this;
    }

    @Override
    public LookupTagBuilder<T> element(Holder<T> elem) {
        return element(elem.unwrapKey().orElseGet(() -> idLookup.apply(elem.value())));
    }

    @Override
    public LookupTagBuilder<T> tag(Identifier tag) {
        super.tag(tag);
        return this;
    }

    @Override
    public LookupTagBuilder<T> tag(TagKey<T> tag) {
        super.tag(tag);
        return this;
    }

    public LookupTagBuilder<T> optionalElement(T elem) {
        return optionalElement(idLookup.apply(elem));
    }

    @Override
    public LookupTagBuilder<T> optionalElement(Identifier elem) {
        super.optionalElement(elem);
        return this;
    }

    @Override
    public LookupTagBuilder<T> optionalElement(ResourceKey<T> elem) {
        super.optionalElement(elem);
        return this;
    }

    @Override
    public LookupTagBuilder<T> optionalElement(Holder<T> elem) {
        return optionalElement(elem.unwrapKey().orElseGet(() -> idLookup.apply(elem.value())));
    }

    @Override
    public LookupTagBuilder<T> optionalTag(Identifier tag) {
        super.optionalTag(tag);
        return this;
    }

    @Override
    public LookupTagBuilder<T> optionalTag(TagKey<T> tag) {
        super.optionalTag(tag);
        return this;
    }
}
