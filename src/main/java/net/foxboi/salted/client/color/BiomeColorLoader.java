package net.foxboi.salted.client.color;

import com.google.gson.JsonParser;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleResourceReloader;
import net.foxboi.salted.common.Smptg;
import net.foxboi.salted.common.color.BiomeColor;
import net.foxboi.salted.common.color.ErrorColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.serialization.JsonOps;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class BiomeColorLoader extends SimpleResourceReloader<Map<Identifier, BiomeColor>> {
    public static final Identifier ID = Smptg.id("colors");

    public static final FileToIdConverter NAMER = FileToIdConverter.json("color");

    public static final Logger LOGGER = LogManager.getLogger(Smptg.ID);

    private static BiomeColor parse(Identifier id, Resource res) {
        try (var reader = res.openAsReader()) {
            var json = JsonParser.parseReader(reader);
            var ops = JsonOps.INSTANCE;
            var result = BiomeColor.CODEC.parse(ops, json);
            return result.getOrThrow();
        } catch (Exception exc) {
            LOGGER.error("Failed to load biome color {}", id, exc);
            return ErrorColor.INSTANCE;
        }
    }

    @Override
    protected Map<Identifier, BiomeColor> prepare(SharedState state) {
        var map = NAMER.listMatchingResources(state.resourceManager());
        var result = new HashMap<Identifier, BiomeColor>();
        map.forEach((path, r) -> {
            var name = NAMER.fileToId(path);
            result.put(name, parse(name, r));
        });

        LOGGER.info("Loaded {} biome color definitions", result.size());
        return result;
    }

    @Override
    protected void apply(Map<Identifier, BiomeColor> prepared, SharedState state) {
        BiomeColorsClient.reloadColors(prepared);
    }
}
