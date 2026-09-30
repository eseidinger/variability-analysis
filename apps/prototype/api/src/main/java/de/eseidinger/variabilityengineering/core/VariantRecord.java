package de.eseidinger.variabilityengineering.core;

import java.util.Map;
import java.util.Objects;

/** An identified candidate with a structural variant and dimension values. */
public record VariantRecord(String id, Variant variant, Map<String, DimensionValue> dimensions) {

    public VariantRecord {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("record ID must be non-blank");
        }
        Objects.requireNonNull(variant, "variant must not be null");
        Objects.requireNonNull(dimensions, "dimensions must not be null");
        dimensions = Map.copyOf(dimensions);
        if (dimensions.keySet().stream().anyMatch(key -> key == null || key.isBlank())) {
            throw new IllegalArgumentException("dimension IDs must be non-blank");
        }
        if (dimensions.values().stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("dimension values must not be null");
        }
    }
}
