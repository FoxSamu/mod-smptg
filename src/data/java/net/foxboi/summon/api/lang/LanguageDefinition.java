package net.foxboi.summon.api.lang;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatType;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StrictJsonParser;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import com.google.gson.JsonObject;

public abstract class LanguageDefinition {
    private final String languageCode;

    private String fallback;
    private final TreeMap<String, String> translations = new TreeMap<>();

    private TreeMap<String, String> fullTranslations;

    public LanguageDefinition(String languageCode) {
        this.languageCode = languageCode;
    }

    String getLanguageCode() {
        return languageCode;
    }

    protected abstract void setup(HolderLookup.Provider lookups);

    private void ensureSetup(LanguageProvider provider, HolderLookup.Provider lookups) {
        if (fullTranslations == null) {
            setup(lookups);

            fullTranslations = new TreeMap<>();

            if (fallback != null) {
                var fallbackDef = provider.getDefinition(fallback);
                if (fallbackDef == null) {
                    throw new RuntimeException("Fallback language " + fallback + " is not defined");
                }

                fallbackDef.ensureSetup(provider, lookups);

                fullTranslations.putAll(fallbackDef.fullTranslations);
            }

            fullTranslations.putAll(translations);
        }
    }

    CompletableFuture<?> save(LanguageProvider provider, HolderLookup.Provider lookups, CachedOutput output, Path path) {
        ensureSetup(provider, lookups);

        var json = new JsonObject();
        fullTranslations.forEach((k, v) -> json.addProperty(k, v));

        return DataProvider.saveStable(output, json, path);
    }


    public final void fallback(String fallbackCode) {
        this.fallback = fallbackCode;
    }

    public final void add(String languageKey, String value) {
        if (translations.containsKey(languageKey)) {
            throw new RuntimeException("Duplicate definition for language key " + languageKey + " in " + languageCode);
        }

        translations.put(languageKey, value);
    }

    public final void add(Identifier identifier, String resourceType, String value) {
        add(identifier.toLanguageKey(resourceType), value);
    }

    public final void add(Identifier identifier, String value) {
        add(identifier.toLanguageKey(), value);
    }

    public final void add(ResourceKey<?> key, String resourceType, String value) {
        add(key.identifier().toLanguageKey(resourceType), value);
    }

    public final void add(ResourceKey<?> key, String value) {
        add(key.identifier().toLanguageKey(key.registry().getPath()), value);
    }

    public final void add(Item item, String value) {
        add(item.getDescriptionId(), value);
    }

    public final void add(Block block, String value) {
        add(block.getDescriptionId(), value);
    }

    public final void add(EntityType<?> entityType, String value) {
        add(entityType.getDescriptionId(), value);
    }

    public final void add(Attribute attribute, String value) {
        add(attribute.getDescriptionId(), value);
    }

    public final void add(StatType<?> statType, String value) {
        add("stat_type." + BuiltInRegistries.STAT_TYPE.getKey(statType).toString().replace(':', '.'), value);
    }

    public final void add(MobEffect mobEffect, String value) {
        add(mobEffect.getDescriptionId(), value);
    }

    public final void add(TagKey<?> tagKey, String value) {
        add(tagKey.getTranslationKey(), value);
    }

    public final void add(SoundEvent sound, String value) {
        add(Util.makeDescriptionId("subtitles", sound.location()), value);
    }

    public final void add(Path existingLanguageFile) throws IOException {
        try (var reader = Files.newBufferedReader(existingLanguageFile)) {
            var translations = StrictJsonParser.parse(reader).getAsJsonObject();

            for (var key : translations.keySet()) {
                add(key, translations.get(key).getAsString());
            }
        }
    }
}
