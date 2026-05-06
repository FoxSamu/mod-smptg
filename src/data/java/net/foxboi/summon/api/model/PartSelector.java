package net.foxboi.summon.api.model;

import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public sealed interface PartSelector permits PartSelector.And, PartSelector.Or, BaseSelector {
    PartSelector ALWAYS_TRUE = StateSelector.EMPTY;

    JsonObject toSelectorJson();

    boolean isAlwaysTrue();

    static And and() {
        return new And();
    }

    static And and(BaseSelector selector) {
        return new And().and(selector);
    }

    static And and(Collection<? extends BaseSelector> selectors) {
        return new And().and(selectors);
    }

    static And and(BaseSelector... selectors) {
        return new And().and(selectors);
    }

    static Or or() {
        return new Or();
    }

    static Or or(BaseSelector selector) {
        return new Or().or(selector);
    }

    static Or or(Collection<? extends BaseSelector> selectors) {
        return new Or().or(selectors);
    }

    static Or or(BaseSelector... selectors) {
        return new Or().or(selectors);
    }

    private static JsonObject compositeJson(List<BaseSelector> selectors, String key) {
        var array = new JsonArray();
        for (var selector : selectors) {
            array.add(selector.toSelectorJson());
        }

        var object = new JsonObject();
        object.add(key, array);
        return object;
    }

    final class And implements PartSelector {
        private final List<BaseSelector> conjuncts = new ArrayList<>();

        public List<BaseSelector> conjuncts() {
            return conjuncts;
        }

        public And and(BaseSelector selector) {
            conjuncts.add(selector);
            return this;
        }

        public And and(BaseSelector... selectors) {
            conjuncts.addAll(Arrays.asList(selectors));
            return this;
        }

        public And and(Collection<? extends BaseSelector> selectors) {
            conjuncts.addAll(selectors);
            return this;
        }

        @Override
        public JsonObject toSelectorJson() {
            return PartSelector.compositeJson(conjuncts, "AND");
        }

        @Override
        public boolean isAlwaysTrue() {
            for (var conj : conjuncts) {
                if (!conj.isAlwaysTrue()) {
                    return false;
                }
            }

            return true;
        }
    }

    final class Or implements PartSelector {
        private final List<BaseSelector> disjuncts = new ArrayList<>();

        public List<BaseSelector> disjuncts() {
            return disjuncts;
        }

        public Or or(BaseSelector selector) {
            disjuncts.add(selector);
            return this;
        }

        public Or or(BaseSelector... selectors) {
            disjuncts.addAll(Arrays.asList(selectors));
            return this;
        }

        public Or or(Collection<? extends BaseSelector> selectors) {
            disjuncts.addAll(selectors);
            return this;
        }

        @Override
        public JsonObject toSelectorJson() {
            return PartSelector.compositeJson(disjuncts, "OR");
        }

        @Override
        public boolean isAlwaysTrue() {
            for (var disj : disjuncts) {
                if (disj.isAlwaysTrue()) {
                    return true;
                }
            }

            return false;
        }
    }
}
