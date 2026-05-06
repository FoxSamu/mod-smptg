/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.foxboi.summon.api.loot;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public abstract class BlockLootProvider extends BlockLootSubProvider implements DataProvider {
	private final PackOutput.PathProvider outputDir;
	private final CompletableFuture<HolderLookup.Provider> lookups;

	protected BlockLootProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookups) {
		super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), lookups.join());
		this.outputDir = output.createRegistryElementsPathProvider(Registries.LOOT_TABLE);
		this.lookups = lookups;
	}

	@Override
	public abstract void generate();

	@Override
	public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
		generate();

		for (Map.Entry<ResourceKey<LootTable>, LootTable.Builder> entry : map.entrySet()) {
			ResourceKey<LootTable> resourceKey = entry.getKey();

			biConsumer.accept(resourceKey, entry.getValue());
		}

//		if (output.isStrictValidationEnabled()) {
//			Set<Identifier> missing = Sets.newHashSet();
//
//			for (Identifier blockId : BuiltInRegistries.BLOCK.keySet()) {
//				if (blockId.getNamespace().equals(output.getModId())) {
//					Optional<ResourceKey<LootTable>> blockLootTableId = BuiltInRegistries.BLOCK.getValue(blockId).getLootTable();
//
//					if (blockLootTableId.isPresent() && blockLootTableId.get().identifier().getNamespace().equals(output.getModId())) {
//						if (!map.containsKey(blockLootTableId.get())) {
//							missing.add(blockId);
//						}
//					}
//				}
//			}
//
//			if (!missing.isEmpty()) {
//				throw new IllegalStateException("Missing loot table(s) for %s".formatted(missing));
//			}
//		}
	}

	@Override
	public CompletableFuture<?> run(CachedOutput cache) {
		var builders = new HashMap<Identifier, LootTable>();

		return lookups.thenCompose(lookup -> {
			generate((resourceKey, builder) -> {
				if (builders.put(resourceKey.identifier(), builder.setParamSet(LootContextParamSets.BLOCK).build()) != null) {
					throw new IllegalStateException("Duplicate loot table " + resourceKey.identifier());
				}
			});

			var futures = new ArrayList<CompletableFuture<?>>();

            builders.forEach((key, value) -> futures.add(DataProvider.saveStable(
					cache,
					lookup,
					LootTable.DIRECT_CODEC,
					value,
					outputDir.json(key)
			)));

			return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
		});
	}
}
