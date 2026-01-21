package net.foxboi.salted.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import net.foxboi.salted.common.misc.data.DataRegistry;
import net.minecraft.core.RegistrySetBuilder;
import org.jspecify.annotations.Nullable;

public class SmptgData implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var pack = generator.createPack();

        var generators = switch (FabricLoader.getInstance().getEnvironmentType()) {
            case SERVER -> new CommonDataGenerators();
            case CLIENT -> new ClientDataGenerators();
        };

        generators.setup(pack);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder builder) {
        DataRegistry.depositAll(builder);
    }

    @Override
    public @Nullable String getEffectiveModId() {
        return "smptg";
    }
}
