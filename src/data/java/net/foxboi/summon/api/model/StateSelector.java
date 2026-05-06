package net.foxboi.summon.api.model;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;

import com.google.gson.JsonObject;

public final class StateSelector implements BaseSelector {
    public static final StateSelector EMPTY = new StateSelector(Map.of());

    private final Map<Property<?>, List<? extends Property.Value<?>>> properties;

    private StateSelector(Map<Property<?>, List<? extends Property.Value<?>>> properties) {
        this.properties = Map.copyOf(properties);
    }

    public <T extends Comparable<T>> StateSelector with(Property<T> property, Collection<T> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("There must be at least one value per property");
        }

        var props = new HashMap<>(properties);
        props.put(property, values.stream().map(it -> new Property.Value<T>(property, it)).toList());
        return new StateSelector(props);
    }

    @SafeVarargs
    public final <T extends Comparable<T>> StateSelector with(Property<T> property, T... values) {
        return with(property, Arrays.asList(values));
    }

    public StateSelector and(StateSelector other) {
        var props = new HashMap<>(properties);
        props.putAll(other.properties);
        return new StateSelector(props);
    }

    public StateSelector and(StatePredicate other) {
        return and(of(other));
    }

    public StateSelector and(BaseSelector other) {
        return and(of(other));
    }

    public Map<Property<?>, List<? extends Property.Value<?>>> properties() {
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

        var that = (StateSelector) obj;
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
                .map(list -> list.stream().map(Property.Value::valueName).collect(Collectors.joining("|")))
                .collect(Collectors.joining(","));
    }

    public static StateSelector empty() {
        return EMPTY;
    }

    public static <T extends Comparable<T>> StateSelector of(Property<T> property, Collection<T> values) {
        return EMPTY.with(property, values);
    }

    @SafeVarargs
    public static <T extends Comparable<T>> StateSelector of(Property<T> property, T... values) {
        return EMPTY.with(property, values);
    }

    public static StateSelector of(StateHolder<?, ?> state) {
        return collect(state.getValues());
    }

    public static StateSelector of(StatePredicate predicate) {
        return collect(predicate.properties().values().stream());
    }

    public static StateSelector of(BaseSelector selector) {
        return switch (selector) {
            case StatePredicate it -> of(it);
            case StateSelector it -> it;
        };
    }

    public static StateSelector concat(StateSelector... selectors) {
        var result = EMPTY;
        for (var selector : selectors) {
            result = result.and(selector);
        }
        return result;
    }

    public static StateSelector concat(Collection<StateSelector> selectors) {
        var result = EMPTY;
        for (var selector : selectors) {
            result = result.and(selector);
        }
        return result;
    }

    private static StateSelector collect(Stream<Property.Value<?>> values) {
        return new StateSelector(values.collect(Collectors.toMap(Property.Value::property, List::of)));
    }

    @Override
    public JsonObject toSelectorJson() {
        var object = new JsonObject();
        for (var entry : properties.entrySet()) {
            var property = entry.getKey();

            var selectorString = entry.getValue()
                    .stream()
                    .map(Property.Value::valueName)
                    .collect(Collectors.joining("|"));

            object.addProperty(property.getName(), selectorString);
        }
        return object;
    }

    @Override
    public boolean isAlwaysTrue() {
        return properties.isEmpty();
    }
}
