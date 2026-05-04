package net.foxboi.salted.common.misc.reg;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Registrar {
    private final String namespace;

    private final Map<ResourceKey<? extends Registry<?>>, DataRegistry<?>> dataRegistries = new LinkedHashMap<>();
    private final Map<ResourceKey<? extends Registry<?>>, GameRegistry<?>> gameRegistries = new LinkedHashMap<>();

    private boolean dataFrozen = false;
    private boolean gameFrozen = false;

    public Registrar(String namespace) {
        this.namespace = namespace;
    }

    public <T> DataRegistry<T> data(ResourceKey<Registry<T>> registryKey) {
        if (dataFrozen) {
            throw new IllegalStateException("Cannot create data registry as data registries have already been frozen");
        }

        return (DataRegistry<T>) dataRegistries.computeIfAbsent(registryKey, _ -> new DataRegistry<>(namespace, registryKey));
    }

    public <T> GameRegistry<T> game(ResourceKey<Registry<T>> registryKey) {
        if (gameFrozen) {
            throw new IllegalStateException("Cannot create game registry as game registries have already been frozen");
        }

        return (GameRegistry<T>) gameRegistries.computeIfAbsent(registryKey, _ -> new GameRegistry<>(namespace, registryKey));
    }

    public void registerDataEntries(FabricDynamicRegistryProvider.Entries entries) {
        dataRegistries.forEach((_, reg) -> deposit(reg, entries));
        freezeData();
    }

    public void registerDataEntries(RegistrySetBuilder builder) {
        dataRegistries.forEach((_, reg) -> deposit(reg, builder));
        freezeData();
    }

    public void registerGameEntries(Registry<? extends Registry<?>> registryRegistry) {
        gameRegistries.forEach((_, reg) -> deposit(reg, registryRegistry));
        freezeGame();
    }

    public void registerGameEntries() {
        registerGameEntries(BuiltInRegistries.REGISTRY);
    }

    private void freezeData() {
        dataRegistries.forEach((_, reg) -> reg.freeze());
        dataFrozen = true;
    }

    private void freezeGame() {
        gameRegistries.forEach((_, reg) -> reg.freeze());
        gameFrozen = true;
    }

    public void freezeAll() {
        freezeData();
        freezeGame();
    }

    public void freezeAndDeleteAll() {
        freezeData();
        freezeGame();
        dataRegistries.forEach((_, reg) -> reg.delete());
        gameRegistries.forEach((_, reg) -> reg.delete());
    }


    private static <T> void deposit(DataRegistry<T> dataReg, BootstrapContext<T> context) {
        dataReg.forEach((key, definition) -> context.register(key, definition.create(key, DefinitionContext.fromBootstrapContext(context))));
    }

    private static <T> void deposit(DataRegistry<T> dataReg, RegistrySetBuilder builder) {
        builder.add(dataReg.getRegistryKey(), context -> deposit(dataReg, context));
    }

    private static <T> void deposit(DataRegistry<T> dataReg, FabricDynamicRegistryProvider.Entries entries) {
        dataReg.forEach((key, definition) -> entries.add(key, definition.create(key, DefinitionContext.fromGeneratorEntries(entries))));
    }

    private static <T> void deposit(GameRegistry<T> gameReg, Registry<? extends Registry<?>> registryRegistry) {
        var registry = (Registry<T>) registryRegistry.getValue((ResourceKey) gameReg.getRegistryKey());
        gameReg.forEach((key, object) -> Registry.register(registry, key, object));
    }
}
