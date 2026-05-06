package net.foxboi.salted.data;

import net.fabricmc.api.EnvType;

import net.foxboi.salted.common.Smptg;
import net.foxboi.summon.api.DataGenerator;
import net.foxboi.summon.api.SummonModInitializer;

import net.minecraft.core.RegistrySetBuilder;

public class SmptgData implements SummonModInitializer {
    @Override
    public void onInitializeStandaloneDatagen() {
        new Smptg().init();
    }

    @Override
    public void setupDataGenerator(DataGenerator generator, EnvType environment) {
        var pack = generator.basePack();

        var generators = switch (environment) {
            case CLIENT -> new ClientDataGenerators();
            case SERVER -> new CommonDataGenerators();
        };

        generators.setup(pack);
    }

    @Override
    public void setupRegistry(RegistrySetBuilder builder) {
        Smptg.REGISTRAR.registerDataEntries(builder);
    }
}
