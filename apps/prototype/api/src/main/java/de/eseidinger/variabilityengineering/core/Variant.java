package de.eseidinger.variabilityengineering.core;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/** A structural variant identified solely by its canonical element IDs. */
public record Variant(Set<String> elementIds) {

    public Variant {
        Objects.requireNonNull(elementIds, "elementIds must not be null");
        var normalized = new TreeSet<String>();
        for (var elementId : elementIds) {
            if (elementId == null || elementId.isBlank()) {
                throw new IllegalArgumentException("element IDs must be non-blank");
            }
            normalized.add(elementId);
        }
        elementIds = Collections.unmodifiableSet(normalized);
    }
}
