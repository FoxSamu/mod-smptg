package net.foxboi.salted.data.registry;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;

import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.levelgen.surface.ModifiedNoiseGeneratorSettings;
import net.foxboi.summon.api.registry.RegistryProvider;

public class ModRegistryProvider extends RegistryProvider {
    public ModRegistryProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void setup(RegistrySetBuilder builder) {
        Smptg.REGISTRAR.registerDataEntries(builder);

        // TODO this should go away and use datamod
        builder.add(Registries.NOISE_SETTINGS, ModifiedNoiseGeneratorSettings::bootstrap);
    }

    @Override
    public String getName() {
        return "Registry";
    }
}
