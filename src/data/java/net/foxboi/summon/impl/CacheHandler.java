package net.foxboi.summon.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import net.minecraft.data.CachedOutput;

import com.google.common.hash.Hashing;

class CacheHandler {
	private final Path rootDir;
	private final Path cacheDir;
	private final String versionId;
	private final FileCopyHandler copyHandler;

	private final Map<String, Cache> caches;
	private final Set<String> cachesToWrite = new HashSet<>();
	private final Set<Path> cachePaths = new HashSet<>();

	private final int initialCount;
	private int writtenCount;

	public CacheHandler(Path rootDir, Collection<String> providerIds, String versionId, FileCopyHandler copyHandler) throws IOException {
        var cacheDir = rootDir.resolve(".cache");

		Files.createDirectories(cacheDir);

		var loadedCaches = new HashMap<String, Cache>();
		var initialCount = 0;

		for (var providerId : providerIds) {
			var providerCachePath = getProviderCachePath(cacheDir, providerId);
			cachePaths.add(providerCachePath);

			var providerCache = Cache.load(rootDir, providerCachePath);
			loadedCaches.put(providerId, providerCache);
			initialCount += providerCache.count();
		}

		this.versionId = versionId;
		this.rootDir = rootDir;
		this.cacheDir = cacheDir;
		this.copyHandler = copyHandler;
		this.caches = loadedCaches;
		this.initialCount = initialCount;
	}

	private static Path getProviderCachePath(Path cacheDir, String provider) {
		return cacheDir.resolve(Hashing.sha256().hashString(provider, StandardCharsets.UTF_8).toString());
	}

	public CompletableFuture<UpdateResult> generateUpdate(String providerId, UpdateFunction function) {
		var existingCache = caches.get(providerId);
		if (existingCache == null) {
			throw new IllegalStateException("Provider not registered: " + providerId);
		}

        var output = new CacheUpdater(providerId, versionId, existingCache, rootDir, copyHandler);
        return function.update(output).thenApply(_ -> output.close());
    }

	public void applyUpdate(UpdateResult result) {
		caches.put(result.providerId(), result.cache());
		cachesToWrite.add(result.providerId());

		writtenCount = writtenCount + result.writes();
	}

	public void finish() throws IOException {
		var keep = new HashSet<Path>();

		// Write cache files and determine which files we should keep
        for (var entry : caches.entrySet()) {
            var providerId = entry.getKey();
            var cache = entry.getValue();

            if (cachesToWrite.contains(providerId)) {
                var cachePath = getProviderCachePath(cacheDir, providerId);
                cache.save(rootDir, cachePath, DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(ZonedDateTime.now()) + "\t" + providerId);
            }

            keep.addAll(cache.data().keySet());
        }


		// Purge stale files
        var visitor = new RemovalVisitor(keep);
		Files.walkFileTree(rootDir, visitor);

		Summon.LOGGER.info("Caching:");
		Summon.LOGGER.info("- Total:   {} files", visitor.total);
		Summon.LOGGER.info("- Old:     {} files", initialCount);
		Summon.LOGGER.info("- New:     {} files", keep.size());
		Summon.LOGGER.info("- Removed: {} files", visitor.removed);
		Summon.LOGGER.info("- Written: {} files", writtenCount);
	}

	private class RemovalVisitor extends SimpleFileVisitor<Path> {
		final Set<Path> keep;
		int total, removed;

		RemovalVisitor(Set<Path> keep) {
			this.keep = keep;
		}

		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
			if (cachePaths.contains(file)) {
				return FileVisitResult.CONTINUE;
			}

			total++;
			if (keep.contains(file)) {
				return FileVisitResult.CONTINUE;
			}

			try {
				Files.delete(file);
				copyHandler.remove(file, rootDir);
			} catch (IOException e) {
				Summon.LOGGER.warn("Failed to delete file {}", file, e);
			}

			removed ++;
			return FileVisitResult.CONTINUE;
		}
	}

    @FunctionalInterface
	public interface UpdateFunction {
		CompletableFuture<?> update(CachedOutput output);
	}

	public record UpdateResult(String providerId, Cache cache, int writes) {
	}
}
