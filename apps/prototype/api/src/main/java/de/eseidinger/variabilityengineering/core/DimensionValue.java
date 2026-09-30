package de.eseidinger.variabilityengineering.core;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/** A known set of dimension values or an explicitly unknown value. */
public record DimensionValue(State state, Set<String> values) {

    public enum State {
        KNOWN,
        UNKNOWN
    }

    public DimensionValue {
        Objects.requireNonNull(state, "state must not be null");
        Objects.requireNonNull(values, "values must not be null");
        var normalized = new TreeSet<String>();
        for (var value : values) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("dimension values must be non-blank");
            }
            normalized.add(value);
        }
        if (state == State.UNKNOWN && !normalized.isEmpty()) {
            throw new IllegalArgumentException("unknown dimensions must not carry values");
        }
        values = Collections.unmodifiableSet(normalized);
    }

    public static DimensionValue known(Set<String> values) {
        return new DimensionValue(State.KNOWN, values);
    }

    public static DimensionValue unknown() {
        return new DimensionValue(State.UNKNOWN, Set.of());
    }
}
