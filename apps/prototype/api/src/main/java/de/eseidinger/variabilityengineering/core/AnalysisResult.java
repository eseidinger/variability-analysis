package de.eseidinger.variabilityengineering.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Calculated metrics and a derived, ordered analysis tree. */
public record AnalysisResult(
        String populationId,
        String populationVersion,
        AnalysisQuery query,
        Set<String> selectedRecordIds,
        Metrics metrics,
        List<Group> groups) {

    public enum GroupValueKind {
        VALUE,
        KNOWN_EMPTY,
        UNKNOWN
    }

    public record GroupValue(GroupValueKind kind, String value) {
        public GroupValue {
            Objects.requireNonNull(kind, "group value kind must not be null");
            if (kind == GroupValueKind.VALUE && (value == null || value.isBlank())) {
                throw new IllegalArgumentException("value groups require a non-blank value");
            }
            if (kind != GroupValueKind.VALUE && value != null) {
                throw new IllegalArgumentException("special groups must not carry a value");
            }
        }

        public static GroupValue value(String value) {
            return new GroupValue(GroupValueKind.VALUE, value);
        }

        public static GroupValue knownEmpty() {
            return new GroupValue(GroupValueKind.KNOWN_EMPTY, null);
        }

        public static GroupValue unknown() {
            return new GroupValue(GroupValueKind.UNKNOWN, null);
        }
    }

    public record GroupPath(List<GroupKey> keys) {
        public GroupPath {
            Objects.requireNonNull(keys, "keys must not be null");
            keys = List.copyOf(keys);
        }

        public static GroupPath root() {
            return new GroupPath(List.of());
        }
    }

    public record GroupKey(String dimensionId, GroupValue value) {
        public GroupKey {
            if (dimensionId == null || dimensionId.isBlank()) {
                throw new IllegalArgumentException("dimension ID must be non-blank");
            }
            Objects.requireNonNull(value, "group value must not be null");
        }
    }

    public record Metrics(
            long recordCount,
            long variantCount,
            long uniqueElementCount,
            Map<String, Long> elementRecordCounts,
            Map<String, Double> elementRecordFrequencies) {
        public Metrics {
            if (recordCount < 0 || variantCount < 0 || uniqueElementCount < 0) {
                throw new IllegalArgumentException("metric counts must not be negative");
            }
            elementRecordCounts = Map.copyOf(elementRecordCounts);
            elementRecordFrequencies = Map.copyOf(elementRecordFrequencies);
        }
    }

    public record Group(GroupPath path, Metrics metrics, List<Group> children) {
        public Group {
            Objects.requireNonNull(path, "path must not be null");
            Objects.requireNonNull(metrics, "metrics must not be null");
            children = List.copyOf(children);
        }
    }

    public AnalysisResult {
        Objects.requireNonNull(populationId, "populationId must not be null");
        Objects.requireNonNull(populationVersion, "populationVersion must not be null");
        Objects.requireNonNull(query, "query must not be null");
        Objects.requireNonNull(selectedRecordIds, "selectedRecordIds must not be null");
        Objects.requireNonNull(metrics, "metrics must not be null");
        selectedRecordIds = Set.copyOf(selectedRecordIds);
        groups = List.copyOf(groups);
    }
}
