package net.foxboi.summon.api;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public interface DataGenerator {
    Pack basePack();
    Pack builtinResourcePack(Identifier id);

    interface Pack {
        <T extends DataProvider> T addProvider(ProviderFactory<T> factory);
        <T extends DataProvider> T addProvider(LookupDependentProviderFactory<T> factory);
    }

    @FunctionalInterface
    interface ProviderFactory<T extends DataProvider> {
        T create(PackOutput output);
    }

    @FunctionalInterface
    interface LookupDependentProviderFactory<T extends DataProvider> {
        T create(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture);
    }
}
