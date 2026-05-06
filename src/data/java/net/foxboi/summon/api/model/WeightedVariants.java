package net.foxboi.summon.api.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraft.util.random.Weighted;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public final class WeightedVariants implements Transformable<WeightedVariants> {
    private final List<Weighted<Variant>> variants = new ArrayList<>();

    private WeightedVariants() {
    }

    public static WeightedVariants of() {
        return new WeightedVariants();
    }

    public static WeightedVariants of(Variant variant, int weight) {
        return new WeightedVariants().with(variant, weight);
    }

    public static WeightedVariants of(Model variant, VariantTransform transform, int weight) {
        return new WeightedVariants().with(variant, transform, weight);
    }

    public static WeightedVariants of(Model variant, int weight) {
        return new WeightedVariants().with(variant, weight);
    }

    public static WeightedVariants of(Variant variant) {
        return new WeightedVariants().with(variant);
    }

    public static WeightedVariants of(Model variant, VariantTransform transform) {
        return new WeightedVariants().with(variant, transform);
    }

    public static WeightedVariants of(Model variant) {
        return new WeightedVariants().with(variant);
    }

    public List<Weighted<Variant>> variants() {
        return List.copyOf(variants);
    }

    public WeightedVariants copy() {
        return new WeightedVariants().with(this);
    }

    public WeightedVariants with(Model variant, int weight) {
        variants.add(new Weighted<>(new BaseVariant(variant), weight));
        return this;
    }

    public WeightedVariants with(Model variant) {
        variants.add(new Weighted<>(new BaseVariant(variant), 1));
        return this;
    }

    public WeightedVariants with(Model variant, VariantTransform transform, int weight) {
        variants.add(new Weighted<>(new BaseVariant(variant).transform(transform), weight));
        return this;
    }

    public WeightedVariants with(Model variant, VariantTransform transform) {
        variants.add(new Weighted<>(new BaseVariant(variant).transform(transform), 1));
        return this;
    }

    public WeightedVariants with(Variant variant, int weight) {
        variants.add(new Weighted<>(variant.copy(), weight));
        return this;
    }

    public WeightedVariants with(Variant variant) {
        variants.add(new Weighted<>(variant.copy(), 1));
        return this;
    }

    public WeightedVariants with(WeightedVariants variants, int weight) {
        variants.variants.forEach(it -> with(it.value(), it.weight() * weight));
        return this;
    }

    public WeightedVariants with(WeightedVariants variants) {
        variants.variants.forEach(it -> with(it.value().copy(), it.weight()));
        return this;
    }

    public WeightedVariants map(Function<Variant, Variant> map) {
        var itr = variants.listIterator();
        while (itr.hasNext()) {
            itr.set(itr.next().map(map));
        }
        return this;
    }

    public WeightedVariants flatMap(BiConsumer<Variant, WeightedVariants> map) {
        var current = List.copyOf(variants);
        variants.clear();

        for (var elem : current) {
            var builder = new WeightedVariants();
            map.accept(elem.value(), builder);
            with(builder, elem.weight());
        }

        return this;
    }

    public WeightedVariants transform(Consumer<VariantTransform> transform) {
        return map(var -> var.transform(transform));
    }

    public WeightedVariants uvlock(boolean uvlock) {
        return map(var -> var.uvlock(uvlock));
    }

    public final WeightedVariants flatTransform(WeightedTransforms transforms) {
        return flatMap((var, variants) -> {
            for (var transform : transforms.transforms()) {
                variants.with(var.copy().transform(transform.value()), transform.weight());
            }
        });
    }

    public final WeightedVariants flatTransform(VariantTransform... transforms) {
        return flatMap((var, variants) -> {
            for (var transform : transforms) {
                variants.with(var.copy().transform(transform));
            }
        });
    }

    public final WeightedVariants flatTransform(Collection<VariantTransform> transforms) {
        return flatMap((var, variants) -> {
            for (var transform : transforms) {
                variants.with(var.copy().transform(transform));
            }
        });
    }

    public JsonElement toJson(ModelSink modelSink) {
        if (variants.size() == 1) {
            return variants.getFirst().value().toJson(modelSink);
        }

        var list = new JsonArray();
        for (var variant : variants) {
            var json = variant.value().toJson(modelSink);
            json.addProperty("weight", variant.weight());
            list.add(json);
        }

        return list;
    }
}
