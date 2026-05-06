package net.foxboi.summon.api.model;

public sealed interface BaseSelector extends PartSelector permits StatePredicate, StateSelector {
}
