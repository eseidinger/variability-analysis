package de.eseidinger.variabilityengineering.core;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/** An immutable, versioned population of records analyzed together. */
public record AnalysisPopulation(
        String id,
        String version,
        Map<String, ArtifactVersion> provenance,
        Map<String, DimensionDefinition> dimensionDefinitions,
        List<VariantRecord> records) {

    public record ArtifactVersion(String id, String version, String sha256) {
        public ArtifactVersion {
            if (id == null || id.isBlank() || version == null || version.isBlank()) {
                throw new IllegalArgumentException("artifact ID and version must be non-blank");
            }
            if (sha256 == null || !sha256.matches("[0-9a-f]{64}")) {
                throw new IllegalArgumentException("artifact checksum must be lowercase hexadecimal SHA-256");
            }
        }
    }

    public AnalysisPopulation {
        if (id == null || id.isBlank() || version == null || version.isBlank()) {
            throw new IllegalArgumentException("population ID and version must be non-blank");
        }
        Objects.requireNonNull(provenance, "provenance must not be null");
        Objects.requireNonNull(dimensionDefinitions, "dimensionDefinitions must not be null");
        Objects.requireNonNull(records, "records must not be null");
        provenance = Map.copyOf(provenance);
        dimensionDefinitions = Map.copyOf(dimensionDefinitions);
        records = List.copyOf(records);
        if (provenance.isEmpty()) {
            throw new IllegalArgumentException("population provenance must not be empty");
        }
        if (dimensionDefinitions.isEmpty()) {
            throw new IllegalArgumentException("population must declare at least one dimension");
        }
        if (!dimensionDefinitions.keySet().stream().allMatch(key -> key != null && !key.isBlank())) {
            throw new IllegalArgumentException("dimension definition IDs must be non-blank");
        }
        if (!dimensionDefinitions.entrySet().stream().allMatch(entry -> entry.getKey().equals(entry.getValue().id()))) {
            throw new IllegalArgumentException("dimension definition map keys must match definition IDs");
        }
        if (dimensionDefinitions.values().stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("dimension definitions must not be null");
        }
        var recordIds = records.stream().map(VariantRecord::id).collect(Collectors.toSet());
        if (recordIds.size() != records.size()) {
            throw new IllegalArgumentException("record IDs must be unique within a population version");
        }
        for (var record : records) {
            validateRecord(record, dimensionDefinitions);
        }
    }

    private static void validateRecord(VariantRecord record, Map<String, DimensionDefinition> definitions) {
        var undefined = record.dimensions().keySet().stream()
                .filter(id -> !definitions.containsKey(id))
                .collect(Collectors.toSet());
        if (!undefined.isEmpty()) {
            throw new IllegalArgumentException("record " + record.id() + " has undefined dimensions: " + undefined);
        }
        var missing = definitions.keySet().stream()
                .filter(id -> !record.dimensions().containsKey(id))
                .collect(Collectors.toSet());
        if (!missing.isEmpty()) {
            throw new IllegalArgumentException("record " + record.id() + " is missing dimensions: " + missing);
        }
        for (var entry : definitions.entrySet()) {
            entry.getValue().validate(record.dimensions().get(entry.getKey()));
        }
    }

    public Set<String> elementIds() {
        return records.stream()
                .flatMap(record -> record.variant().elementIds().stream())
                .collect(Collectors.toUnmodifiableSet());
    }
}
