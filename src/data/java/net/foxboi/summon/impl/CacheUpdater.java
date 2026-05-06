package net.foxboi.summon.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import net.minecraft.data.CachedOutput;

import com.google.common.hash.HashCode;

class CacheUpdater implements CachedOutput {
    private final String provider;

    private final Cache oldCache;
    private final Cache.Builder newCache;

    private final AtomicInteger writes = new AtomicInteger();

    private final Path rootDir;
    private final FileCopyHandler copyHandler;

    private volatile boolean closed;

    CacheUpdater(String provider, String newVersionId, Cache oldCache, Path rootDir, FileCopyHandler copyHandler) {
        this.provider = provider;

        this.oldCache = oldCache;
        this.newCache = Cache.builder(newVersionId);
        this.rootDir = rootDir;
        this.copyHandler = copyHandler;
    }

    private boolean shouldWrite(Path path, HashCode hash) {
        if (!Files.exists(path)) {
            return true; // File does not exist, definitely write file
        }

        if (Objects.equals(oldCache.get(path), hash)) {
            return false; // Hashes equal, file is probably equal too
        }

        return !Objects.equals(oldCache.get(path), hash) || !Files.exists(path);
    }

    @Override
    public void writeIfNeeded(Path path, byte[] input, HashCode hash) throws IOException {
        if (closed) {
            throw new IOException("Already closed");
        }

        if (shouldWrite(path, hash)) {
            writes.incrementAndGet();

            Files.createDirectories(path.getParent());
            Files.write(path, input);
            copyHandler.copy(path, rootDir);
        }

        newCache.put(path, hash);
    }

    public CacheHandler.UpdateResult close() {
        closed = true;
        return new CacheHandler.UpdateResult(provider, newCache.build(), writes.get());
    }
}
