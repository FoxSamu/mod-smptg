package net.foxboi.summon.api.lang;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

public abstract class LanguageProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;
    private final String namespace;
    private final CompletableFuture<HolderLookup.Provider> lookups;

    private final Map<String, LanguageDefinition> definitions = new HashMap<>();

    public LanguageProvider(PackOutput out, String namespace, CompletableFuture<HolderLookup.Provider> lookups) {
        this.pathProvider = out.createPathProvider(PackOutput.Target.RESOURCE_PACK, "lang");
        this.namespace = namespace;
        this.lookups = lookups;
    }

    LanguageDefinition getDefinition(String langCode) {
        return definitions.get(langCode);
    }

    protected abstract void setup(Consumer<LanguageDefinition> definitionOutput);

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return CompletableFuture.runAsync(this::doSetup, Util.backgroundExecutor())
                .thenCompose(_ -> lookups)
                .thenCompose(lookups -> doSave(lookups, cache));
    }

    private void doSetup() {
        setup(def -> {
            var lang = def.getLanguageCode();
            if (definitions.containsKey(lang)) {
                throw new RuntimeException("Duplicate definition for language " + lang);
            }

            definitions.put(lang, def);
        });
    }

    private Path pathOf(String code) {
        return pathProvider.json(Identifier.fromNamespaceAndPath(namespace, code));
    }

    private CompletableFuture<?> doSave(HolderLookup.Provider lookups, CachedOutput cache) {
        var futures = new ArrayList<CompletableFuture<?>>();
        definitions.forEach((code, def) -> {
            futures.add(def.save(this, lookups, cache, pathOf(code)));
        });
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }
}
