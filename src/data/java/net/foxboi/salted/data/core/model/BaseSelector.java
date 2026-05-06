package net.foxboi.salted.data.core.model;

import com.google.gson.JsonObject;

public sealed interface BaseSelector extends PartSelector permits StatePredicate, StateSelector {
}
