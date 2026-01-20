package net.foxboi.salted.data.shadercompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import net.foxboi.salted.common.Smptg;
import net.minecraft.util.Util;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.util.GsonHelper;

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
        var baos = new ByteArrayOutputStream();

        var hasher = new HashingOutputStream(Hashing.sha1(), baos);
        props.store(hasher, null);
        hasher.close();

        out.writeIfNeeded(path, baos.toByteArray(), hasher.hash());
    }
}
