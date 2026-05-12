package net.foxboi.summon.api.model;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import com.google.gson.JsonElement;

public abstract class ModelProvider implements DataProvider {
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> lookups;

    public ModelProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookups) {
        this.output = output;
        this.lookups = lookups;
    }

    protected abstract void setup(BlockDispatchSink blocks, ItemDispatchSink items, ModelSink models, HolderLookup.Provider lookups);

    protected boolean shouldCheckClashingModelDefinitions() {
        return false;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return lookups
                .thenApply(lookups -> {
                    return new SinkImpl(lookups, shouldCheckClashingModelDefinitions());
                })
                .thenApplyAsync(sink -> {
                    setup(sink, sink, sink, sink.lookups);
                    return sink;
                })
                .thenCompose(sink -> {
                    return sink.flush(cache, output);
                });
    }

    private static class SinkImpl implements ModelSink, BlockDispatchSink, ItemDispatchSink {
        final Map<Identifier, JsonElement> models = new HashMap<>();
        final Map<Block, JsonElement> blocks = new HashMap<>();
        final Map<Item, JsonElement> items = new HashMap<>();

        final HolderLookup.Provider lookups;
        final boolean checkClashingModelDefinitions;

        SinkImpl(HolderLookup.Provider lookups, boolean checkClashingModelDefinitions) {
            this.lookups = lookups;
            this.checkClashingModelDefinitions = checkClashingModelDefinitions;
        }

        @Override
        public boolean hasModel(Identifier name) {
            return models.containsKey(name);
        }

        @Override
        public boolean save(Identifier name, Supplier<JsonElement> json) {
            if (!models.containsKey(name)) {
                models.put(name, json.get());
                return true;
            } else if (checkClashingModelDefinitions) {
                var jsonElem = json.get();
                if (!models.get(name).equals(jsonElem)) {
                    throw new RuntimeException("Model " + name + " was defined with two distinct definitions");
                }
            }

            return false;
        }

        @Override
        public void save(Block block, Function<ModelSink, JsonElement> json) {
            if (!blocks.containsKey(block)) {
                blocks.put(block, json.apply(this));
            } else {
                throw new RuntimeException("Duplicate dispatch definition for block: " + BuiltInRegistries.BLOCK.getKey(block));
            }
        }

        @Override
        public void save(Item item, Function<ModelSink, JsonElement> json) {
            if (!items.containsKey(item)) {
                items.put(item, json.apply(this));
            } else {
                throw new RuntimeException("Duplicate dispatch definition for item: " + BuiltInRegistries.ITEM.getKey(item));
            }
        }

        static Identifier blockId(Block block) {
            return BuiltInRegistries.BLOCK.getKey(block);
        }

        static Identifier itemId(Item item) {
            return BuiltInRegistries.ITEM.getKey(item);
        }

        CompletableFuture<?> flush(CachedOutput cache, PackOutput output) {
            var modelsDir = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
            var itemsDir = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
            var blockstatesDir = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");

            return CompletableFuture.allOf(
                    DataProvider.saveAll(cache, json -> json, modelsDir::json, models),
                    DataProvider.saveAll(cache, json -> json, block -> blockstatesDir.json(blockId(block)), blocks),
                    DataProvider.saveAll(cache, json -> json, item -> itemsDir.json(itemId(item)), items)
            );
        }
    }
}
