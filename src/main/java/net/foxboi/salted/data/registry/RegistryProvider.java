package net.foxboi.salted.data.registry;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.foxboi.salted.common.levelgen.surface.ModifiedNoiseGeneratorSettings;
import net.foxboi.salted.common.levelgen.surface.ModifiedSurfaceRuleData;
import net.foxboi.salted.common.util.DataRegistry;
import net.minecraft.core.HolderLookup;

public class RegistryProvider extends FabricDynamicRegistryProvider {
    public RegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        DataRegistry.depositAll(entries);

        ModifiedNoiseGeneratorSettings.bootstrap(entries);
    }

    @Override
    public String getName() {
        return "Registry";
    }
}
