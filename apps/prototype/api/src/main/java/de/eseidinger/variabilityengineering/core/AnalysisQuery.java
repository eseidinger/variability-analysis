package de.eseidinger.variabilityengineering.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Explicit filters and ordered dimensions used to analyze a population. */
public record AnalysisQuery(
        Map<String, DimensionFilter> dimensionFilters,
        Set<String> requiredElementIds,
        List<String> groupingDimensions) {

    public record DimensionFilter(
            DimensionValue.State requiredState,
            Set<String> includedValues,
            Set<String> excludedValues) {
        public DimensionFilter {
            Objects.requireNonNull(includedValues, "includedValues must not be null");
            Objects.requireNonNull(excludedValues, "excludedValues must not be null");
            includedValues = Set.copyOf(includedValues);
            excludedValues = Set.copyOf(excludedValues);
            if (includedValues.stream().anyMatch(value -> value == null || value.isBlank())
                    || excludedValues.stream().anyMatch(value -> value == null || value.isBlank())) {
                throw new IllegalArgumentException("filter values must be non-blank");
            }
            if (requiredState == DimensionValue.State.UNKNOWN
                    && (!includedValues.isEmpty() || !excludedValues.isEmpty())) {
                throw new IllegalArgumentException("unknown filters cannot include or exclude known values");
            }
        }

        public static DimensionFilter any() {
            return new DimensionFilter(null, Set.of(), Set.of());
        }

        public static DimensionFilter state(DimensionValue.State state) {
            return new DimensionFilter(state, Set.of(), Set.of());
        }

        public static DimensionFilter including(Set<String> values) {
            return new DimensionFilter(null, values, Set.of());
        }
    }

    public AnalysisQuery {
        Objects.requireNonNull(dimensionFilters, "dimensionFilters must not be null");
        Objects.requireNonNull(requiredElementIds, "requiredElementIds must not be null");
        Objects.requireNonNull(groupingDimensions, "groupingDimensions must not be null");
        dimensionFilters = Map.copyOf(dimensionFilters);
        requiredElementIds = Set.copyOf(requiredElementIds);
        groupingDimensions = List.copyOf(groupingDimensions);
        if (dimensionFilters.keySet().stream().anyMatch(key -> key == null || key.isBlank())
                || dimensionFilters.values().stream().anyMatch(Objects::isNull)
                || requiredElementIds.stream().anyMatch(id -> id == null || id.isBlank())
                || groupingDimensions.stream().anyMatch(id -> id == null || id.isBlank())) {
            throw new IllegalArgumentException("query identifiers and filters must be non-null and non-blank");
        }
        if (groupingDimensions.size() != Set.copyOf(groupingDimensions).size()) {
            throw new IllegalArgumentException("grouping dimensions must be unique");
        }
    }

    public static AnalysisQuery unfiltered() {
        return new AnalysisQuery(Map.of(), Set.of(), List.of());
    }
}
