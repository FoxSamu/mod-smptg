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

package net.foxboi.summon.impl;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import com.google.common.base.Stopwatch;
import net.foxboi.summon.api.DataGenerator;

final class DataGeneratorImpl implements DataGenerator {
	private final String namespace;
	private final PackOutput packOutput;
	private final CompletableFuture<HolderLookup.Provider> lookups;
	private final FileCopyHandler copyHandler;

	private final Set<String> allProviderIds = new HashSet<>();
	private final Map<String, DataProvider> providersToRun = new LinkedHashMap<>();

	public DataGeneratorImpl(String namespace, Path outputDir, CompletableFuture<HolderLookup.Provider> lookups, FileCopyHandler copyHandler) {
        this.namespace = namespace;
        this.packOutput = new PackOutput(outputDir);
        this.lookups = lookups;
        this.copyHandler = copyHandler;
    }

	@Override
	public Pack basePack() {
		return new PackImpl(namespace, packOutput);
	}

	@Override
	public Pack builtinResourcePack(Identifier id) {
		var path = packOutput.getOutputFolder()
				.resolve("resourcepacks")
				.resolve(id.getPath());

		return new PackImpl(id.toString(), new PackOutput(path));
	}

	public void run() throws IOException {
		var cache = new CacheHandler(packOutput.getOutputFolder(), allProviderIds, namespace, copyHandler);

		var totalTime = Stopwatch.createStarted();
		var stopwatch = Stopwatch.createUnstarted();

		providersToRun.forEach((providerId, provider) -> {
			Summon.LOGGER.info("Starting provider: {}", providerId);

			stopwatch.start();
			Objects.requireNonNull(provider);
			cache.applyUpdate(cache.generateUpdate(providerId, provider::run).join());
			stopwatch.stop();

			Summon.LOGGER.info("{} finished after {} ms", providerId, stopwatch.elapsed(TimeUnit.MILLISECONDS));
			stopwatch.reset();
		});

		Summon.LOGGER.info("All providers took: {} ms", totalTime.elapsed(TimeUnit.MILLISECONDS));
		cache.finish();
	}

	public final class PackImpl implements Pack {
		private final String providerPrefix;
		private final PackOutput output;

		private PackImpl(String providerPrefix, PackOutput output) {
			this.providerPrefix = providerPrefix;
			this.output = output;
		}

		public <T extends DataProvider> T addProvider(ProviderFactory<T> factory) {
			var provider = factory.create(output);

			var providerId = providerPrefix + "/" + provider.getName();

			if (!allProviderIds.add(providerId)) {
				throw new IllegalStateException("Duplicate provider: " + providerId);
			}

			providersToRun.put(providerId, provider);
            return provider;
        }

		public <T extends DataProvider> T addProvider(LookupDependentProviderFactory<T> factory) {
			return addProvider(output -> factory.create(output, lookups));
		}
	}
}
