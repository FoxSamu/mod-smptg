package net.foxboi.salted.data.lang;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import net.foxboi.salted.common.Smptg;
import net.foxboi.summon.api.lang.LanguageDefinition;
import net.foxboi.summon.api.lang.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput out, CompletableFuture<HolderLookup.Provider> lookups) {
        super(out, Smptg.ID, lookups);
    }

    @Override
    protected void setup(Consumer<LanguageDefinition> definitionOutput) {
        definitionOutput.accept(new EnUsDefinition());
    }

    @Override
    public String getName() {
        return "Languages";
    }
}
