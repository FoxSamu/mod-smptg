package net.foxboi.salted.data.core.registry;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.*;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.util.Util;

public abstract class RegistryProvider implements DataProvider {
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> lookups;

    public RegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookups) {
        this.output = output;
        this.lookups = lookups;
    }

    protected abstract void setup(RegistrySetBuilder builder);

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return lookups
                .thenApply(DataOutput::new)
                .thenApplyAsync(this::buildRegistry, Util.backgroundExecutor().forName("buildRegistry"))
                .thenCompose(it -> it.save(output, cache));
    }

    private DataOutput buildRegistry(DataOutput output) {
        var builder = new RegistrySetBuilder();
        setup(builder);

        for (var stub : builder.entries) {
            output.accept(stub);
        }

        return output;
    }
}
