package net.foxboi.salted.data.core.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;

public final class WeightedTransforms {
    private final List<Weighted<VariantTransform>> transforms = new ArrayList<>();

    private WeightedTransforms() {
    }

    public static WeightedTransforms of() {
        return new WeightedTransforms();
    }

    public List<Weighted<VariantTransform>> transforms() {
        return List.copyOf(transforms);
    }

    public WeightedTransforms copy() {
        return new WeightedTransforms().with(this);
    }

    public WeightedTransforms with(VariantTransform transform, int weight) {
        transforms.add(new Weighted<>(transform.copy(), weight));
        return this;
    }

    public WeightedTransforms with(VariantTransform transform) {
        transforms.add(new Weighted<>(transform.copy(), 1));
        return this;
    }

    public WeightedTransforms with(WeightedTransforms transforms, int weight) {
        transforms.transforms.forEach(it -> with(it.value().copy(), it.weight() * weight));
        return this;
    }

    public WeightedTransforms with(WeightedTransforms transforms) {
        transforms.transforms.forEach(it -> with(it.value().copy(), it.weight()));
        return this;
    }

    public WeightedTransforms map(Function<VariantTransform, VariantTransform> map) {
        var itr = transforms.listIterator();
        while (itr.hasNext()) {
            itr.set(itr.next().map(map));
        }
        return this;
    }

    public WeightedTransforms flatMap(BiConsumer<VariantTransform, WeightedTransforms> map) {
        var current = List.copyOf(transforms);
        transforms.clear();

        for (var elem : current) {
            var builder = new WeightedTransforms();
            map.accept(elem.value().copy(), builder);
            with(builder, elem.weight());
        }

        return this;
    }

    public WeightedTransforms transform(VariantTransform transform) {
        return map(xf -> xf.transform(transform));
    }

    public final WeightedTransforms flatTransform(WeightedTransforms transforms) {
        return flatMap((xf, xfs) -> {
            for (var transform : transforms.transforms) {
                xfs.with(xf.copy().transform(transform.value()), transform.weight());
            }
        });
    }

    public final WeightedTransforms flatTransform(VariantTransform... transforms) {
        return flatMap((xf, xfs) -> {
            for (var transform : transforms) {
                xfs.with(xf.copy().transform(transform));
            }
        });
    }

    public final WeightedTransforms flatTransform(Collection<VariantTransform> transforms) {
        return flatMap((xf, xfs) -> {
            for (var transform : transforms) {
                xfs.with(xf.copy().transform(transform));
            }
        });
    }
}
