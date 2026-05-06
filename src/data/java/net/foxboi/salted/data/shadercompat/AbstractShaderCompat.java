package net.foxboi.salted.data.shadercompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.stream.Collectors;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import net.minecraft.data.CachedOutput;

public abstract class AbstractShaderCompat implements ShaderCompat {
    protected final Properties block;
    protected final Properties item;

    public AbstractShaderCompat(Properties block, Properties item) {
        this.block = block;
        this.item = item;
    }

    public AbstractShaderCompat(Path directory) throws IOException {
        this(
                readPropertiesFile(directory.resolve("block.properties")),
                readPropertiesFile(directory.resolve("item.properties"))
        );
    }

    public void write(Path outputDir, CachedOutput out) throws IOException {
        Files.createDirectories(outputDir);

        writePropertiesFile(block, outputDir.resolve("block.properties"), out);
        writePropertiesFile(item, outputDir.resolve("item.properties"), out);
    }

    protected void addBlock(int materialId, String block) {
        add(this.block, "block." + materialId, block);
    }

    protected void addItem(int materialId, String block) {
        add(this.item, "item." + materialId, block);
    }

    private static String add(String value, String add) {
        if (value == null) {
            return add;
        } else {
            return value + " " + add;
        }
    }

    private static void add(Properties properties, String key, String value) {
        properties.setProperty(key, add(properties.getProperty(key), value));
    }

    protected static Properties readPropertiesFile(Path path) throws IOException {
        try (var reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            var props = new Properties();
            props.load(reader);
            return props;
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    protected static void writePropertiesFile(Properties props, Path path, CachedOutput out) throws IOException {
        var sw = new StringWriter();
        props.store(sw, null);

        // props.store adds a timestamp line at the top of the file, causing
        // these files to bypass the cache check. We cannot opt out of this so
        // we remove it here.
        var export = sw.toString().lines()
                .dropWhile(line -> line.startsWith("#"))
                .collect(Collectors.joining(System.lineSeparator()));

        var baos = new ByteArrayOutputStream();
        var hasher = new HashingOutputStream(Hashing.sha1(), baos);
        try (var writer = new PrintWriter(hasher)) {
            writer.print(export);
        }

        out.writeIfNeeded(path, baos.toByteArray(), hasher.hash());
    }
}
