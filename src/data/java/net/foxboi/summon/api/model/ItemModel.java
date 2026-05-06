package net.foxboi.summon.api.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.minecraft.world.level.ItemLike;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface ItemModel {
    JsonElement toJson(ModelSink sink);

    default SimpleItemDispatch dispatch(ItemLike item) {
        return new SimpleItemDispatch(item.asItem(), this);
    }

    static BasicModel model(Model model) {
        return new BasicModel(model);
    }

    static BasicModel model(Model model, Collection<? extends ItemTint> tints) {
        return model(model).withTint(tints);
    }

    static BasicModel model(Model model, ItemTint... tints) {
        return model(model).withTint(tints);
    }

    static BasicModel model(Model model, ItemTint tint) {
        return model(model).withTint(tint);
    }

    final class BasicModel implements ItemModel {
        private final Model model;
        private final List<ItemTint> tints = new ArrayList<>();

        public BasicModel(Model model) {
            this.model = model;
        }

        public BasicModel withTint(ItemTint model) {
            this.tints.add(model);
            return this;
        }

        public BasicModel withTint(ItemTint... models) {
            this.tints.addAll(Arrays.asList(models));
            return this;
        }

        public BasicModel withTint(Collection<? extends ItemTint> models) {
            this.tints.addAll(models);
            return this;
        }

        @Override
        public JsonElement toJson(ModelSink sink) {
            var object = new JsonObject();

            object.addProperty("type", "minecraft:model");
            object.addProperty("model", model.save(sink).toShortString());

            if (!tints.isEmpty()) {
                var tints = new JsonArray();
                for (var tint : this.tints) {
                    tints.add(tint.toJson());
                }

                object.add("tints", tints);
            }

            return object;
        }
    }

    static Composite composite() {
        return new Composite();
    }

    static Composite composite(ItemModel... models) {
        return composite().with(models);
    }

    static Composite composite(Collection<? extends ItemModel> models) {
        return composite().with(models);
    }

    final class Composite implements ItemModel {
        private final List<ItemModel> models = new ArrayList<>();

        public Composite with(ItemModel model) {
            this.models.add(model);
            return this;
        }

        public Composite with(ItemModel... models) {
            this.models.addAll(Arrays.asList(models));
            return this;
        }

        public Composite with(Collection<? extends ItemModel> models) {
            this.models.addAll(models);
            return this;
        }

        @Override
        public JsonElement toJson(ModelSink sink) {
            var object = new JsonObject();

            object.addProperty("type", "minecraft:composite");

            var models = new JsonArray();
            for (var model : this.models) {
                models.add(model.toJson(sink));
            }

            object.add("models", models);
            return object;
        }
    }
}
