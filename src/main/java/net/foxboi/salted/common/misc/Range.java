package net.foxboi.salted.common.misc;

import java.util.OptionalInt;

public record Range(OptionalInt start, OptionalInt end) {
    public static final Range EMPTY = new Range(OptionalInt.empty(), OptionalInt.empty());

    public OptionalInt length() {
        return start.isPresent() && end.isPresent()
                ? OptionalInt.of(end.getAsInt() - start.getAsInt())
                : OptionalInt.empty();
    }

    public boolean isEmpty() {
        return start.isEmpty() && end.isEmpty();
    }

    public boolean isFinite() {
        return start.isPresent() && end.isPresent();
    }

    public Range withStart(OptionalInt start) {
        return new Range(start, end);
    }

    public Range withEnd(OptionalInt end) {
        return new Range(start, end);
    }

    public Range withStart(int start) {
        return new Range(OptionalInt.of(start), end);
    }

    public Range withEnd(int end) {
        return new Range(start, OptionalInt.of(end));
    }
}
