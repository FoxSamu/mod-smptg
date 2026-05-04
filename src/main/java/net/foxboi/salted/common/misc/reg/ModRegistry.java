package net.foxboi.salted.common.misc.reg;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import it.unimi.dsi.fastutil.objects.Reference2ObjectLinkedOpenHashMap;

public abstract class ModRegistry<T, E> {
    private final String namespace;
    private final ResourceKey<Registry<T>> registryKey;

    private final Map<ResourceKey<T>, E> map = new LinkedHashMap<>();
    private final Map<E, ResourceKey<T>> reverseMap = new Reference2ObjectLinkedOpenHashMap<>();
    private boolean frozen;

    protected ModRegistry(String namespace, ResourceKey<Registry<T>> registryKey) {
        this.namespace = namespace;
        this.registryKey = registryKey;
    }

    public final String getNamespace() {
        return namespace;
    }

    public final ResourceKey<Registry<T>> getRegistryKey() {
        return registryKey;
    }

    void freeze() {
        frozen = true;
    }

    void delete() {
        map.clear();
        reverseMap.clear();
    }

    // Class does not provide any actual register methods because due to type erasure
    // they might clash with overloads added by subclasses. Subclasses instead provide
    // all overloads and call registerEntry method internally. This has the added
    // benefit that the return type of the register methods can be chosen per sublcass
    // without any boilerplate code in this class.

    protected final void registerEntry(ResourceKey<T> key, E entry) {
        if (frozen) {
            throw new IllegalStateException("Registry is frozen");
        }

        if (map.containsKey(key)) {
            throw new IllegalArgumentException("Key " + key.identifier() + " already registered");
        }

        map.put(key, entry);
        reverseMap.put(entry, key);
    }

    protected final ResourceKey<T> reverseLookup(E entry) {
        return reverseMap.get(entry);
    }

    public void forEach(BiConsumer<? super ResourceKey<T>, ? super E> action) {
        map.forEach(action);
    }
}
