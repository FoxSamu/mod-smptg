package net.foxboi.summon.api;

import net.minecraft.core.RegistrySetBuilder;

import net.fabricmc.api.EnvType;

public interface SummonModInitializer {
    void onInitializeStandaloneDatagen();

    void setupDataGenerator(DataGenerator generator, EnvType environment);

    void setupRegistry(RegistrySetBuilder builder);
}
