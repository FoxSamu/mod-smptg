package net.foxboi.summon.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.jspecify.annotations.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.hash.HashCode;

record Cache(String version, Map<Path, HashCode> data) {
    private static final String HEADER_PREFIX = "// ";

    private static final Cache EMPTY = new Cache("unknown", Map.of());

    @Nullable
    public HashCode get(Path path) {
        return data.get(path);
    }

    public int count() {
        return data.size();
    }

    public static Cache load(Path rootDir, Path cacheFile) {
        if (!Files.isReadable(cacheFile)) {
            return EMPTY;
        }

        try (var reader = Files.newBufferedReader(cacheFile, StandardCharsets.UTF_8)) {
            var header = reader.readLine();
            if (!header.startsWith(HEADER_PREFIX)) {
                throw new IllegalStateException("Missing cache file header");
            }

            var headerFields = header.substring(HEADER_PREFIX.length()).split("\t", 2);

            var savedVersionId = headerFields[0];
            var result = new HashMap<Path, HashCode>();
            reader.lines().forEach(s -> {
                int i = s.indexOf(32);
                result.put(rootDir.resolve(s.substring(i + 1)), HashCode.fromString(s.substring(0, i)));
            });

            return new Cache(savedVersionId, Map.copyOf(result));
        } catch (IOException e) {
            Summon.LOGGER.warn("Failed to parse cache {}, discarding", cacheFile, e);
            return EMPTY;
        }
    }

    public void save(Path rootDir, Path cacheFile, String extraHeaderInfo) {
        try (var output = Files.newBufferedWriter(cacheFile, StandardCharsets.UTF_8)) {

            // Write header
            output.write(HEADER_PREFIX);
            output.write(version);
            output.write('\t');
            output.write(extraHeaderInfo);
            output.newLine();

            // Write hashes
            for (var entry : data.entrySet()) {
                output.write(entry.getValue().toString());
                output.write(32);
                output.write(rootDir.relativize(entry.getKey()).toString());
                output.newLine();
            }
        } catch (IOException e) {
            Summon.LOGGER.warn("Unable write cachefile {}: {}", cacheFile, e);
        }
    }

    public static Builder builder(String version) {
        return new Builder(version);
    }

    static final class Builder {
        final ConcurrentMap<Path, HashCode> data = new ConcurrentHashMap<>();

        final String version;

        Builder(String version) {
            this.version = version;
        }

        Builder put(Path path, HashCode hash) {
            data.put(path, hash);
            return this;
        }

        Cache build() {
            return new Cache(version, ImmutableMap.copyOf(data));
        }
    }
}
