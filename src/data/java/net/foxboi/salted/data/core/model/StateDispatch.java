package net.foxboi.salted.data.core.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;

public sealed class StateDispatch<R> {
    private final Map<StatePredicate, R> entries = new LinkedHashMap<>();

    private StateDispatch() {
    }

    public StateDispatch<R> put(StatePredicate predicate, R result) {
        entries.put(predicate, result);
        return this;
    }

    public <S extends StateHolder<?, S>> StateDispatch<R> applyAllStates(StateDefinition<?, S> stateDefinition, Function<S, R> map) {
        stateDefinition.getPossibleStates().forEach(state -> {
            put(StatePredicate.of(state.getValues().toList()), map.apply(state));
        });
        return this;
    }

    public StateDispatch<R> applyAllStates(Block block, Function<BlockState, R> map) {
        return applyAllStates(block.getStateDefinition(), map);
    }

    public StateDispatch<R> applyAllStates(Fluid fluid, Function<FluidState, R> map) {
        return applyAllStates(fluid.getStateDefinition(), map);
    }

    public <S> StateDispatch<S> map(Function<R, S> map) {
        var dispatch = new StateDispatch<S>();
        forEach((predicate, result) -> {
            dispatch.put(predicate, map.apply(result));
        });
        return dispatch;
    }

    public <S> StateDispatch<S> flatMap(Function<R, StateDispatch<S>> map) {
        var dispatch = new StateDispatch<S>();
        forEach((predicate, result) -> {
            map.apply(result).forEach((otherPredicate, newResult) -> {
                dispatch.put(StatePredicate.concat(predicate, otherPredicate), newResult);
            });
        });
        return dispatch;
    }

    public <S> StateDispatch<S> flatMap(StateDispatch<Function<R, S>> mapDispatch) {
        var dispatch = new StateDispatch<S>();
        forEach((predicate, result) -> {
            mapDispatch.forEach((otherPredicate, map) -> {
                dispatch.put(StatePredicate.concat(predicate, otherPredicate), map.apply(result));
            });
        });
        return dispatch;
    }

    public <S, T> StateDispatch<T> join(StateDispatch<S> other, BiFunction<R, S, T> map) {
        var dispatch = new StateDispatch<T>();
        forEach((predicate, result) -> {
            other.forEach((otherPredicate, otherResult) -> {
                dispatch.put(StatePredicate.concat(predicate, otherPredicate), map.apply(result, otherResult));
            });
        });
        return dispatch;
    }

    public StateDispatch<R> concat(StateDispatch<R> other) {
        var dispatch = new StateDispatch<R>();
        forEach(dispatch::put);
        other.forEach(dispatch::put);
        return dispatch;
    }

    public StateDispatch<R> withCondition(StatePredicate condition) {
        var dispatch = new StateDispatch<R>();
        forEach((predicate, result) -> dispatch.put(predicate.and(condition), result));
        return dispatch;
    }

    public <T extends Comparable<T>> StateDispatch<R> withCondition(Property<T> property, T value) {
        return withCondition(StatePredicate.of(property, value));
    }

    public void forEach(BiConsumer<StatePredicate, R> action) {
        entries.forEach(action);
    }

    public JsonObject toJson(Function<R, JsonElement> elementToJson) {
        var object = new JsonObject();
        forEach((predicate, result) -> object.add(predicate.toString(), elementToJson.apply(result)));
        return object;
    }

    public StateDispatch<R> copy() {
        return map(it -> it);
    }

    public static <R> StateDispatch<R> dispatch() {
        return new StateDispatch<>();
    }

    public static <R> StateDispatch<R> dispatch(UnaryOperator<StateDispatch<R>> op) {
        return op.apply(new StateDispatch<>());
    }

    public static <R> StateDispatch<R> dispatch(R result) {
        return new StateDispatch<R>().put(StatePredicate.EMPTY, result);
    }

    public static <T extends Comparable<T>> StateDispatch<T> valuesOf(Property<T> property) {
        return StateDispatch.dispatch(property, it -> it.apply(Function.identity()));
    }

    public static <T1 extends Comparable<T1>, R> P1<T1, R> dispatch(
            Property<T1> property1
    ) {
        return new P1<>(property1);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, R> P2<T1, T2, R> dispatch(
            Property<T1> property1,
            Property<T2> property2
    ) {
        return new P2<>(property1, property2);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, R> P3<T1, T2, T3, R> dispatch(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3
    ) {
        return new P3<>(property1, property2, property3);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, R> P4<T1, T2, T3, T4, R> dispatch(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3,
            Property<T4> property4
    ) {
        return new P4<>(property1, property2, property3, property4);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>, R> P5<T1, T2, T3, T4, T5, R> dispatch(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3,
            Property<T4> property4,
            Property<T5> property5
    ) {
        return new P5<>(property1, property2, property3, property4, property5);
    }

    public static <T1 extends Comparable<T1>, R> P1<T1, R> dispatch(
            Property<T1> property1,
            UnaryOperator<P1<T1, R>> op
    ) {
        return op.apply(dispatch(property1));
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, R> P2<T1, T2, R> dispatch(
            Property<T1> property1,
            Property<T2> property2,
            UnaryOperator<P2<T1, T2, R>> op
    ) {
        return op.apply(dispatch(property1, property2));
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, R> P3<T1, T2, T3, R> dispatch(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3,
            UnaryOperator<P3<T1, T2, T3, R>> op
    ) {
        return op.apply(dispatch(property1, property2, property3));
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, R> P4<T1, T2, T3, T4, R> dispatch(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3,
            Property<T4> property4,
            UnaryOperator<P4<T1, T2, T3, T4, R>> op
    ) {
        return op.apply(dispatch(property1, property2, property3, property4));
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>, R> P5<T1, T2, T3, T4, T5, R> dispatch(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3,
            Property<T4> property4,
            Property<T5> property5,
            UnaryOperator<P5<T1, T2, T3, T4, T5, R>> op
    ) {
        return op.apply(dispatch(property1, property2, property3, property4, property5));
    }




    public static StateDispatch<Model> model() {
        return StateDispatch.dispatch();
    }

    public static StateDispatch<Model> model(Model result) {
        return StateDispatch.dispatch(result);
    }

    public static <T1 extends Comparable<T1>> StateDispatch.P1<T1, Model> model(
            Property<T1> property1
    ) {
        return StateDispatch.dispatch(property1);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> StateDispatch.P2<T1, T2, Model> model(
            Property<T1> property1,
            Property<T2> property2
    ) {
        return StateDispatch.dispatch(property1, property2);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> StateDispatch.P3<T1, T2, T3, Model> model(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3
    ) {
        return StateDispatch.dispatch(property1, property2, property3);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> StateDispatch.P4<T1, T2, T3, T4, Model> model(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3,
            Property<T4> property4
    ) {
        return StateDispatch.dispatch(property1, property2, property3, property4);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> StateDispatch.P5<T1, T2, T3, T4, T5, Model> model(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3,
            Property<T4> property4,
            Property<T5> property5
    ) {
        return StateDispatch.dispatch(property1, property2, property3, property4, property5);
    }




    public static StateDispatch<WeightedVariants> variants() {
        return StateDispatch.dispatch();
    }

    public static StateDispatch<WeightedVariants> variants(WeightedVariants result) {
        return StateDispatch.dispatch(result);
    }

    public static <T1 extends Comparable<T1>> StateDispatch.P1<T1, WeightedVariants> variants(
            Property<T1> property1
    ) {
        return StateDispatch.dispatch(property1);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> StateDispatch.P2<T1, T2, WeightedVariants> variants(
            Property<T1> property1,
            Property<T2> property2
    ) {
        return StateDispatch.dispatch(property1, property2);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> StateDispatch.P3<T1, T2, T3, WeightedVariants> variants(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3
    ) {
        return StateDispatch.dispatch(property1, property2, property3);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> StateDispatch.P4<T1, T2, T3, T4, WeightedVariants> variants(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3,
            Property<T4> property4
    ) {
        return StateDispatch.dispatch(property1, property2, property3, property4);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> StateDispatch.P5<T1, T2, T3, T4, T5, WeightedVariants> variants(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3,
            Property<T4> property4,
            Property<T5> property5
    ) {
        return StateDispatch.dispatch(property1, property2, property3, property4, property5);
    }




    public static StateDispatch<VariantTransform> transform() {
        return StateDispatch.dispatch();
    }

    public static StateDispatch<VariantTransform> transform(VariantTransform result) {
        return StateDispatch.dispatch(result);
    }

    public static <T1 extends Comparable<T1>> StateDispatch.P1<T1, VariantTransform> transform(
            Property<T1> property1
    ) {
        return StateDispatch.dispatch(property1);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> StateDispatch.P2<T1, T2, VariantTransform> transform(
            Property<T1> property1,
            Property<T2> property2
    ) {
        return StateDispatch.dispatch(property1, property2);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> StateDispatch.P3<T1, T2, T3, VariantTransform> transform(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3
    ) {
        return StateDispatch.dispatch(property1, property2, property3);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> StateDispatch.P4<T1, T2, T3, T4, VariantTransform> transform(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3,
            Property<T4> property4
    ) {
        return StateDispatch.dispatch(property1, property2, property3, property4);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> StateDispatch.P5<T1, T2, T3, T4, T5, VariantTransform> transform(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3,
            Property<T4> property4,
            Property<T5> property5
    ) {
        return StateDispatch.dispatch(property1, property2, property3, property4, property5);
    }





    public static StateDispatch<Function<WeightedVariants, WeightedVariants>> variantsMapper() {
        return StateDispatch.dispatch();
    }

    public static StateDispatch<Function<WeightedVariants, WeightedVariants>> variantsMapper(Function<WeightedVariants, WeightedVariants> result) {
        return StateDispatch.dispatch(result);
    }

    public static <T1 extends Comparable<T1>> StateDispatch.P1<T1, Function<WeightedVariants, WeightedVariants>> variantsMapper(
            Property<T1> property1
    ) {
        return StateDispatch.dispatch(property1);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> StateDispatch.P2<T1, T2, Function<WeightedVariants, WeightedVariants>> variantsMapper(
            Property<T1> property1,
            Property<T2> property2
    ) {
        return StateDispatch.dispatch(property1, property2);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> StateDispatch.P3<T1, T2, T3, Function<WeightedVariants, WeightedVariants>> variantsMapper(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3
    ) {
        return StateDispatch.dispatch(property1, property2, property3);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> StateDispatch.P4<T1, T2, T3, T4, Function<WeightedVariants, WeightedVariants>> variantsMapper(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3,
            Property<T4> property4
    ) {
        return StateDispatch.dispatch(property1, property2, property3, property4);
    }

    public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> StateDispatch.P5<T1, T2, T3, T4, T5, Function<WeightedVariants, WeightedVariants>> variantsMapper(
            Property<T1> property1,
            Property<T2> property2,
            Property<T3> property3,
            Property<T4> property4,
            Property<T5> property5
    ) {
        return StateDispatch.dispatch(property1, property2, property3, property4, property5);
    }

    public static final class P1<T1 extends Comparable<T1>, R> extends StateDispatch<R> {
        private final Property<T1> property1;

        private P1(Property<T1> property1) {
            this.property1 = property1;
        }

        public P1<T1, R> put(T1 t1, R result) {
            put(StatePredicate.of(
                    new Property.Value<>(property1, t1)
            ), result);
            return this;
        }

        public P1<T1, R> apply(Function<T1, R> map) {
            property1.getPossibleValues().forEach(t1 -> put(t1, map.apply(t1)));
            return this;
        }
    }

    public static final class P2<T1 extends Comparable<T1>, T2 extends Comparable<T2>, R> extends StateDispatch<R> {
        private final Property<T1> property1;
        private final Property<T2> property2;

        private P2(Property<T1> property1, Property<T2> property2) {
            this.property1 = property1;
            this.property2 = property2;
        }

        public P2<T1, T2, R> put(T1 t1, T2 t2, R result) {
            put(StatePredicate.of(
                    new Property.Value<>(property1, t1),
                    new Property.Value<>(property2, t2)
            ), result);
            return this;
        }

        public P2<T1, T2, R> apply(BiFunction<T1, T2, R> map) {
            property1.getPossibleValues().forEach(t1 -> {
                property2.getPossibleValues().forEach(t2 -> {
                    put(t1, t2, map.apply(t1, t2));
                });
            });
            return this;
        }
    }

    public static final class P3<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, R> extends StateDispatch<R> {
        private final Property<T1> property1;
        private final Property<T2> property2;
        private final Property<T3> property3;

        private P3(Property<T1> property1, Property<T2> property2, Property<T3> property3) {
            this.property1 = property1;
            this.property2 = property2;
            this.property3 = property3;
        }

        public P3<T1, T2, T3, R> put(T1 t1, T2 t2, T3 t3, R result) {
            put(StatePredicate.of(
                    new Property.Value<>(property1, t1),
                    new Property.Value<>(property2, t2),
                    new Property.Value<>(property3, t3)
            ), result);
            return this;
        }

        public P3<T1, T2, T3, R> apply(Function3<T1, T2, T3, R> map) {
            property1.getPossibleValues().forEach(t1 -> {
                property2.getPossibleValues().forEach(t2 -> {
                    property3.getPossibleValues().forEach(t3 -> {
                        put(t1, t2, t3, map.apply(t1, t2, t3));
                    });
                });
            });
            return this;
        }
    }

    public static final class P4<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, R> extends StateDispatch<R> {
        private final Property<T1> property1;
        private final Property<T2> property2;
        private final Property<T3> property3;
        private final Property<T4> property4;

        private P4(Property<T1> property1, Property<T2> property2, Property<T3> property3, Property<T4> property4) {
            this.property1 = property1;
            this.property2 = property2;
            this.property3 = property3;
            this.property4 = property4;
        }

        public P4<T1, T2, T3, T4, R> put(T1 t1, T2 t2, T3 t3, T4 t4, R result) {
            put(StatePredicate.of(
                    new Property.Value<>(property1, t1),
                    new Property.Value<>(property2, t2),
                    new Property.Value<>(property3, t3),
                    new Property.Value<>(property4, t4)
            ), result);
            return this;
        }

        public P4<T1, T2, T3, T4, R> apply(Function4<T1, T2, T3, T4, R> map) {
            property1.getPossibleValues().forEach(t1 -> {
                property2.getPossibleValues().forEach(t2 -> {
                    property3.getPossibleValues().forEach(t3 -> {
                        property4.getPossibleValues().forEach(t4 -> {
                            put(t1, t2, t3, t4, map.apply(t1, t2, t3, t4));
                        });
                    });
                });
            });
            return this;
        }
    }

    public static final class P5<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>, R> extends StateDispatch<R> {
        private final Property<T1> property1;
        private final Property<T2> property2;
        private final Property<T3> property3;
        private final Property<T4> property4;
        private final Property<T5> property5;

        private P5(Property<T1> property1, Property<T2> property2, Property<T3> property3, Property<T4> property4, Property<T5> property5) {
            this.property1 = property1;
            this.property2 = property2;
            this.property3 = property3;
            this.property4 = property4;
            this.property5 = property5;
        }

        public P5<T1, T2, T3, T4, T5, R> put(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, R result) {
            put(StatePredicate.of(
                    new Property.Value<>(property1, t1),
                    new Property.Value<>(property2, t2),
                    new Property.Value<>(property3, t3),
                    new Property.Value<>(property4, t4),
                    new Property.Value<>(property5, t5)
            ), result);
            return this;
        }

        public P5<T1, T2, T3, T4, T5, R> apply(Function5<T1, T2, T3, T4, T5, R> map) {
            property1.getPossibleValues().forEach(t1 -> {
                property2.getPossibleValues().forEach(t2 -> {
                    property3.getPossibleValues().forEach(t3 -> {
                        property4.getPossibleValues().forEach(t4 -> {
                            property5.getPossibleValues().forEach(t5 -> {
                                put(t1, t2, t3, t4, t5, map.apply(t1, t2, t3, t4, t5));
                            });
                        });
                    });
                });
            });
            return this;
        }
    }
}
