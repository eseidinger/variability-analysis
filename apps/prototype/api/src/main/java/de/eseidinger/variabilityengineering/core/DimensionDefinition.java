package de.eseidinger.variabilityengineering.core;

import java.util.Objects;

/** Declares cardinality and provenance for a dimension available on every record. */
public record DimensionDefinition(String id, Cardinality cardinality, Origin origin) {

    public enum Cardinality {
        SINGLE,
        MULTI
    }

    public enum Origin {
        IMPORTED,
        DERIVED,
        FEATURE
    }

    public DimensionDefinition {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("dimension ID must be non-blank");
        }
        Objects.requireNonNull(cardinality, "cardinality must not be null");
        Objects.requireNonNull(origin, "origin must not be null");
    }

    public void validate(DimensionValue value) {
        Objects.requireNonNull(value, "dimension value must not be null");
        if (value.state() == DimensionValue.State.KNOWN
                && cardinality == Cardinality.SINGLE
                && value.values().size() != 1) {
            throw new IllegalArgumentException("known single-valued dimension " + id + " must have exactly one value");
        }
    }
}
