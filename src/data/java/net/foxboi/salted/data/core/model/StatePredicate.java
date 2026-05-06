package net.foxboi.salted.data.core.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;

import com.google.gson.JsonObject;

public final class StatePredicate implements BaseSelector {
    public static final StatePredicate EMPTY = new StatePredicate(Map.of());

    private final Map<Property<?>, Property.Value<?>> properties;

    private StatePredicate(Map<Property<?>, Property.Value<?>> properties) {
        this.properties = Map.copyOf(properties);
    }

    public <T extends Comparable<T>> StatePredicate with(Property<T> property, T value) {
        if (property.getInternalIndex(value) < 0) {
            throw new IllegalArgumentException("Value " + value + " not supported by property " + property.getName());
        }

        var props = new HashMap<>(properties);
        props.put(property, new Property.Value<>(property, value));
        return new StatePredicate(props);
    }

    public <T extends Comparable<T>> StatePredicate with(Property.Value<T> value) {
        return with(value.property(), value.value());
    }

    public StatePredicate and(StatePredicate other) {
        var props = new HashMap<>(properties);
        props.putAll(other.properties);
        return new StatePredicate(props);
    }

    public Map<Property<?>, Property.Value<?>> properties() {
        return properties;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        var that = (StatePredicate) obj;
        return properties.equals(that.properties);
    }

    @Override
    public int hashCode() {
        return properties.hashCode();
    }

    @Override
    public String toString() {
        return properties.values()
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    public static StatePredicate empty() {
        return EMPTY;
    }

    public static <T extends Comparable<T>> StatePredicate of(Property<T> property, T value) {
        if (property.getInternalIndex(value) < 0) {
            throw new IllegalArgumentException("Value " + value + " not supported by property " + property.getName());
        }

        var props = new HashMap<Property<?>, Property.Value<?>>();
        props.put(property, new Property.Value<>(property, value));
        return new StatePredicate(props);
    }

    public static StatePredicate of(Property.Value<?>... values) {
        return collect(Stream.of(values));
    }

    public static StatePredicate of(Collection<Property.Value<?>> values) {
        return collect(values.stream());
    }

    public static StatePredicate of(StateHolder<?, ?> state) {
        return collect(state.getValues());
    }

    public static StatePredicate concat(StatePredicate... predicates) {
        return collect(Stream.of(predicates).flatMap(it -> it.properties.values().stream()));
    }

    public static StatePredicate concat(Collection<StatePredicate> predicates) {
        return collect(predicates.stream().flatMap(it -> it.properties.values().stream()));
    }

    private static StatePredicate collect(Stream<Property.Value<?>> values) {
        return new StatePredicate(values.collect(Collectors.toMap(Property.Value::property, Function.identity())));
    }

    @Override
    public JsonObject toSelectorJson() {
        var object = new JsonObject();
        for (var value : properties.values()) {
            object.addProperty(value.property().getName(), value.valueName());
        }
        return object;
    }

    @Override
    public boolean isAlwaysTrue() {
        return properties.isEmpty();
    }
}
