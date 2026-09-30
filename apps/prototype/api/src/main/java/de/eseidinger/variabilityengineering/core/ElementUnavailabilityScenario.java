package de.eseidinger.variabilityengineering.core;

import java.util.Objects;
import java.util.Set;

/** A scenario that makes complete records using any named element infeasible. */
public record ElementUnavailabilityScenario(Set<String> unavailableElementIds) {

    public ElementUnavailabilityScenario {
        Objects.requireNonNull(unavailableElementIds, "unavailableElementIds must not be null");
        unavailableElementIds = Set.copyOf(unavailableElementIds);
        if (unavailableElementIds.isEmpty()
                || unavailableElementIds.stream().anyMatch(id -> id == null || id.isBlank())) {
            throw new IllegalArgumentException("scenario requires non-empty, non-blank element IDs");
        }
    }
}
