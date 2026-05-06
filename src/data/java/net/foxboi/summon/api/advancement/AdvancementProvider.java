
package net.foxboi.summon.api.advancement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public abstract class AdvancementProvider implements DataProvider {
	protected final PackOutput output;
	private final PackOutput.PathProvider pathProvider;
	private final CompletableFuture<HolderLookup.Provider> registryLookup;

	protected AdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		this.output = output;
		this.pathProvider = output.createRegistryElementsPathProvider(Registries.ADVANCEMENT);
		this.registryLookup = registryLookup;
	}

	public abstract void setup(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> consumer);

	public static AdvancementHolder placeholder(Identifier id) {
		return Advancement.Builder.advancement().build(id);
	}

	@Override
	public CompletableFuture<?> run(CachedOutput output) {
		return registryLookup.thenCompose(lookup -> {
			var identifiers = new HashSet<Identifier>();
			var advancements = new HashSet<AdvancementHolder>();

			setup(lookup, advancements::add);

			var futures = new ArrayList<CompletableFuture<?>>();

			for (var advancement : advancements) {
				if (!identifiers.add(advancement.id())) {
					throw new IllegalStateException("Duplicate advancement: " + advancement.id());
				}

				futures.add(DataProvider.saveStable(
						output,
						lookup,
						Advancement.CODEC,
						advancement.value(),
						pathProvider.json(advancement.id())
				));
			}

			return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
		});
	}
}
