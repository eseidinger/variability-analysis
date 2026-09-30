package de.eseidinger.variabilityengineering.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Unweighted reach of one element across a selected population. */
public record ElementLeverage(
        String elementId,
        long recordCount,
        Map<String, Set<String>> knownDimensionValues) {

    public ElementLeverage {
        if (elementId == null || elementId.isBlank()) {
            throw new IllegalArgumentException("element ID must be non-blank");
        }
        if (recordCount < 0) {
            throw new IllegalArgumentException("record count must not be negative");
        }
        Objects.requireNonNull(knownDimensionValues, "knownDimensionValues must not be null");
        knownDimensionValues = knownDimensionValues.entrySet().stream()
                .collect(java.util.stream.Collectors.toUnmodifiableMap(
                        Map.Entry::getKey,
                        entry -> Set.copyOf(entry.getValue())));
    }
}
