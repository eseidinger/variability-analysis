package de.eseidinger.variabilityengineering.adapter.foodservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.eseidinger.variabilityengineering.core.AnalysisPopulation;
import de.eseidinger.variabilityengineering.core.DimensionDefinition;
import de.eseidinger.variabilityengineering.core.DimensionValue;
import de.eseidinger.variabilityengineering.core.Variant;
import de.eseidinger.variabilityengineering.core.VariantRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/** Maps the versioned food-service fixture into the domain-independent core. */
public final class FoodServiceFixtureAdapter {

    public record RejectedRecord(String sourceRecordId, String reason, String detail) {
        public RejectedRecord {
            if (sourceRecordId == null || sourceRecordId.isBlank()
                    || reason == null || reason.isBlank()
                    || detail == null || detail.isBlank()) {
                throw new IllegalArgumentException("rejected-record fields must be non-blank");
            }
        }
    }

    public record ImportResult(AnalysisPopulation population, List<RejectedRecord> rejectedRecords) {
        public ImportResult {
            Objects.requireNonNull(population, "population must not be null");
            rejectedRecords = List.copyOf(rejectedRecords);
        }
    }

    private static final String MANIFEST = "manifest.json";
    private static final List<String> REQUIRED_ARTIFACT_ROLES = List.of(
            "sourceDataset", "mapping", "derivation", "adapterContract", "expectedResults");

    private final ObjectMapper objectMapper;

    public FoodServiceFixtureAdapter() {
        this(new ObjectMapper());
    }

    FoodServiceFixtureAdapter(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper must not be null");
    }

    public ImportResult importFixture(Path fixtureDirectory) throws IOException {
        Objects.requireNonNull(fixtureDirectory, "fixtureDirectory must not be null");
        var root = fixtureDirectory.toAbsolutePath().normalize();
        var manifest = readObject(root.resolve(MANIFEST));
        var artifactNodes = verifiedArtifacts(root, manifest);
        var source = readObject(artifactNodes.get("sourceDataset").path());
        var mapping = readObject(artifactNodes.get("mapping").path());
        var derivation = readObject(artifactNodes.get("derivation").path());
        var adapterContract = readObject(artifactNodes.get("adapterContract").path());

        verifyArtifactIdentity(source, artifactNodes.get("sourceDataset"), "datasetId");
        verifyArtifactIdentity(mapping, artifactNodes.get("mapping"), "mappingId");
        verifyArtifactIdentity(derivation, artifactNodes.get("derivation"), "derivationId");
        verifyArtifactIdentity(adapterContract, artifactNodes.get("adapterContract"), "adapterId");
        verifyText(source, "license", text(manifest, "license"), "source dataset");

        var aliases = aliases(mapping);
        var classifications = classifications(derivation);
        var records = new ArrayList<VariantRecord>();
        var rejected = new ArrayList<RejectedRecord>();
        for (var sourceRecord : array(source, "records", "source dataset")) {
            mapRecord(sourceRecord, aliases, classifications, records, rejected);
        }

        var dimensions = Map.of(
                "cuisine", new DimensionDefinition("cuisine", DimensionDefinition.Cardinality.SINGLE, DimensionDefinition.Origin.IMPORTED),
                "dishType", new DimensionDefinition("dishType", DimensionDefinition.Cardinality.SINGLE, DimensionDefinition.Origin.IMPORTED),
                "diet", new DimensionDefinition("diet", DimensionDefinition.Cardinality.MULTI, DimensionDefinition.Origin.DERIVED),
                "allergen", new DimensionDefinition("allergen", DimensionDefinition.Cardinality.MULTI, DimensionDefinition.Origin.DERIVED));
        var provenance = new LinkedHashMap<String, AnalysisPopulation.ArtifactVersion>();
        artifactNodes.forEach((role, artifact) -> provenance.put(role,
                new AnalysisPopulation.ArtifactVersion(artifact.id(), artifact.version(), artifact.sha256())));
        var population = new AnalysisPopulation(
                text(source, "datasetId"),
                text(manifest, "version"),
                provenance,
                dimensions,
                records);
        return new ImportResult(population, rejected);
    }

    private Map<String, VerifiedArtifact> verifiedArtifacts(Path root, JsonNode manifest) throws IOException {
        var result = new HashMap<String, VerifiedArtifact>();
        for (var node : array(manifest, "artifacts", "manifest")) {
            var role = text(node, "role");
            if (result.containsKey(role)) {
                throw new IllegalArgumentException("manifest contains duplicate artifact role: " + role);
            }
            var relativePath = text(node, "path");
            var artifactPath = root.resolve(relativePath).normalize();
            if (!artifactPath.startsWith(root)) {
                throw new IllegalArgumentException("manifest artifact path escapes fixture directory: " + relativePath);
            }
            if (!Files.isRegularFile(artifactPath)) {
                throw new IllegalArgumentException("manifest artifact does not exist: " + relativePath);
            }
            var expectedDigest = text(node, "sha256");
            var actualDigest = sha256(artifactPath);
            if (!expectedDigest.equals(actualDigest)) {
                throw new IllegalArgumentException("artifact checksum mismatch for " + relativePath);
            }
            result.put(role, new VerifiedArtifact(artifactPath, text(node, "id"), text(node, "version"), expectedDigest));
        }
        for (var role : REQUIRED_ARTIFACT_ROLES) {
            if (!result.containsKey(role)) {
                throw new IllegalArgumentException("manifest is missing required artifact role: " + role);
            }
        }
        return Map.copyOf(result);
    }

    private static void verifyArtifactIdentity(JsonNode content, VerifiedArtifact artifact, String idField) {
        verifyText(content, idField, artifact.id(), artifact.path().toString());
        verifyText(content, "version", artifact.version(), artifact.path().toString());
    }

    private static void verifyText(JsonNode node, String field, String expected, String context) {
        var actual = text(node, field);
        if (!actual.equals(expected)) {
            throw new IllegalArgumentException(context + " has " + field + " " + actual + " but manifest requires " + expected);
        }
    }

    private AliasLookup aliases(JsonNode mapping) {
        var caseSensitive = booleanValue(mapping, "caseSensitive", "mapping");
        var aliases = new HashMap<String, String>();
        for (var ingredient : array(mapping, "ingredients", "mapping")) {
            var elementId = text(ingredient, "id");
            for (var sourceValue : array(ingredient, "sourceValues", "mapping ingredient " + elementId)) {
                var alias = aliasKey(textValue(sourceValue, "mapping source value"), caseSensitive);
                var previous = aliases.putIfAbsent(alias, elementId);
                if (previous != null && !previous.equals(elementId)) {
                    throw new IllegalArgumentException("alias maps to multiple canonical elements: " + alias);
                }
            }
        }
        return new AliasLookup(Map.copyOf(aliases), caseSensitive);
    }

    private Map<String, Classification> classifications(JsonNode derivation) {
        var result = new HashMap<String, Classification>();
        for (var ingredient : array(derivation, "ingredients", "derivation")) {
            var id = text(ingredient, "id");
            var classification = new Classification(
                    nullableBoolean(ingredient.get("containsAnimalProduct"), id, "containsAnimalProduct"),
                    nullableBoolean(ingredient.get("containsMeat"), id, "containsMeat"),
                    nullableStrings(ingredient.get("allergens"), id, "allergens"));
            if (result.putIfAbsent(id, classification) != null) {
                throw new IllegalArgumentException("derivation contains duplicate ingredient classification: " + id);
            }
        }
        return Map.copyOf(result);
    }

    private static void mapRecord(
            JsonNode sourceRecord,
            AliasLookup aliases,
            Map<String, Classification> classifications,
            List<VariantRecord> records,
            List<RejectedRecord> rejected) {
        var recordId = text(sourceRecord, "id");
        var elements = new TreeSet<String>();
        for (var ingredient : array(sourceRecord, "ingredients", "source record " + recordId)) {
            var rawIngredient = textValue(ingredient, "ingredient in " + recordId).trim();
            var canonical = aliases.values().get(aliasKey(rawIngredient, aliases.caseSensitive()));
            if (canonical == null) {
                rejected.add(new RejectedRecord(recordId, "UNMAPPED_INGREDIENT",
                        "No canonical ingredient mapping exists for: " + rawIngredient));
                return;
            }
            if (!classifications.containsKey(canonical)) {
                throw new IllegalArgumentException("mapped ingredient lacks classification: " + canonical);
            }
            elements.add(canonical);
        }
        var ingredientClassifications = elements.stream().map(classifications::get).toList();
        var dimensions = Map.of(
                "cuisine", importedDimension(sourceRecord.get("cuisine"), recordId, "cuisine"),
                "dishType", importedDimension(sourceRecord.get("dishType"), recordId, "dishType"),
                "diet", diet(ingredientClassifications),
                "allergen", allergens(ingredientClassifications));
        records.add(new VariantRecord(recordId, new Variant(elements), dimensions));
    }

    private static DimensionValue importedDimension(JsonNode node, String recordId, String field) {
        if (node == null || node.isNull()) {
            return DimensionValue.unknown();
        }
        return DimensionValue.known(Set.of(textValue(node, field + " in " + recordId)));
    }

    private static DimensionValue diet(List<Classification> classifications) {
        if (classifications.stream().anyMatch(classification -> classification.containsAnimalProduct() == null
                || classification.containsMeat() == null)) {
            return DimensionValue.unknown();
        }
        var values = new TreeSet<String>();
        if (classifications.stream().allMatch(classification -> !classification.containsAnimalProduct())) {
            values.add("VEGAN");
        }
        if (classifications.stream().allMatch(classification -> !classification.containsMeat())) {
            values.add("VEGETARIAN");
        }
        return DimensionValue.known(values);
    }

    private static DimensionValue allergens(List<Classification> classifications) {
        if (classifications.stream().anyMatch(classification -> classification.allergens() == null)) {
            return DimensionValue.unknown();
        }
        return DimensionValue.known(classifications.stream()
                .flatMap(classification -> classification.allergens().stream())
                .collect(java.util.stream.Collectors.toUnmodifiableSet()));
    }

    private JsonNode readObject(Path path) throws IOException {
        var node = objectMapper.readTree(Files.readAllBytes(path));
        if (node == null || !node.isObject()) {
            throw new IllegalArgumentException("expected JSON object in " + path);
        }
        return node;
    }

    private static List<JsonNode> array(JsonNode parent, String field, String context) {
        var node = parent.get(field);
        if (node == null || !node.isArray()) {
            throw new IllegalArgumentException(context + " requires array field " + field);
        }
        var values = new ArrayList<JsonNode>();
        node.forEach(values::add);
        return List.copyOf(values);
    }

    private static String text(JsonNode parent, String field) {
        return textValue(parent.get(field), field);
    }

    private static String textValue(JsonNode node, String context) {
        if (node == null || !node.isTextual() || node.textValue().isBlank()) {
            throw new IllegalArgumentException(context + " must be a non-blank string");
        }
        return node.textValue();
    }

    private static boolean booleanValue(JsonNode parent, String field, String context) {
        var node = parent.get(field);
        if (node == null || !node.isBoolean()) {
            throw new IllegalArgumentException(context + " requires Boolean field " + field);
        }
        return node.booleanValue();
    }

    private static Boolean nullableBoolean(JsonNode node, String ingredientId, String field) {
        if (node == null || node.isNull()) {
            return null;
        }
        if (!node.isBoolean()) {
            throw new IllegalArgumentException("classification " + ingredientId + " has non-Boolean " + field);
        }
        return node.booleanValue();
    }

    private static Set<String> nullableStrings(JsonNode node, String ingredientId, String field) {
        if (node == null || node.isNull()) {
            return null;
        }
        if (!node.isArray()) {
            throw new IllegalArgumentException("classification " + ingredientId + " has non-array " + field);
        }
        var values = new TreeSet<String>();
        node.forEach(value -> values.add(textValue(value, "classification " + ingredientId + " " + field)));
        return Set.copyOf(values);
    }

    private static String aliasKey(String value, boolean caseSensitive) {
        var trimmed = value.trim();
        return caseSensitive ? trimmed : trimmed.toLowerCase(Locale.ROOT);
    }

    private static String sha256(Path path) throws IOException {
        try {
            return java.util.HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(path)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }

    private record AliasLookup(Map<String, String> values, boolean caseSensitive) {
    }

    private record VerifiedArtifact(Path path, String id, String version, String sha256) {
    }

    private record Classification(Boolean containsAnimalProduct, Boolean containsMeat, Set<String> allergens) {
    }
}
