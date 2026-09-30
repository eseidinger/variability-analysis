package de.eseidinger.variabilityengineering.adapter.foodservice;

import de.eseidinger.variabilityengineering.core.AnalysisEngine;
import de.eseidinger.variabilityengineering.core.AnalysisQuery;
import de.eseidinger.variabilityengineering.core.DimensionValue;
import de.eseidinger.variabilityengineering.core.ElementUnavailabilityScenario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FoodServiceFixtureAdapterTest {

    private final FoodServiceFixtureAdapter adapter = new FoodServiceFixtureAdapter();
    private final AnalysisEngine engine = new AnalysisEngine();

    @Test
    void importsTheVersionedFixtureIntoTheDomainIndependentCore() throws IOException {
        var imported = adapter.importFixture(fixtureDirectory());
        var population = imported.population();
        var recipeOne = population.records().stream().filter(record -> record.id().equals("recipe-001")).findFirst().orElseThrow();
        var recipeTwo = population.records().stream().filter(record -> record.id().equals("recipe-002")).findFirst().orElseThrow();
        var recipeEleven = population.records().stream().filter(record -> record.id().equals("recipe-011")).findFirst().orElseThrow();

        assertEquals("food-service-mvp", population.id());
        assertEquals("1.0.0", population.version());
        assertEquals(12, population.records().size());
        assertEquals(Set.of("sourceDataset", "mapping", "derivation", "adapterContract", "expectedResults"),
                population.provenance().keySet());
        assertEquals(List.of(new FoodServiceFixtureAdapter.RejectedRecord(
                "recipe-013", "UNMAPPED_INGREDIENT", "No canonical ingredient mapping exists for: chef secret mix")),
                imported.rejectedRecords());
        assertEquals(recipeOne.variant(), recipeTwo.variant());
        assertEquals(DimensionValue.unknown(), recipeEleven.dimensions().get("cuisine"));
        assertEquals(DimensionValue.unknown(), recipeEleven.dimensions().get("diet"));
        assertEquals(DimensionValue.unknown(), recipeEleven.dimensions().get("allergen"));

        var baseline = engine.analyze(population, AnalysisQuery.unfiltered());
        assertEquals(12, baseline.metrics().recordCount());
        assertEquals(11, baseline.metrics().variantCount());
        assertEquals(24, baseline.metrics().uniqueElementCount());

        var impact = engine.compareElementUnavailability(
                population, AnalysisQuery.unfiltered(), new ElementUnavailabilityScenario(Set.of("garlic")));
        assertEquals(4, impact.scenarioResult().metrics().recordCount());
        assertEquals(4, impact.scenarioResult().metrics().variantCount());
        assertEquals(14, impact.scenarioResult().metrics().uniqueElementCount());
        assertEquals(8, impact.lostRecordIds().size());
    }

    @Test
    void rejectsArtifactsWhoseBytesDoNotMatchTheManifest(@TempDir Path temporaryDirectory) throws IOException {
        var copiedFixture = temporaryDirectory.resolve("fixture");
        copyDirectory(fixtureDirectory(), copiedFixture);
        Files.writeString(copiedFixture.resolve("source/recipes.json"), "\n", StandardOpenOption.APPEND);

        var exception = assertThrows(IllegalArgumentException.class, () -> adapter.importFixture(copiedFixture));

        assertTrue(exception.getMessage().contains("artifact checksum mismatch for source/recipes.json"));
    }

    private static Path fixtureDirectory() {
        return Path.of("../../../examples/food-service/mvp-v1").toAbsolutePath().normalize();
    }

    private static void copyDirectory(Path source, Path destination) throws IOException {
        try (Stream<Path> paths = Files.walk(source)) {
            for (var path : paths.toList()) {
                var target = destination.resolve(source.relativize(path));
                if (Files.isDirectory(path)) {
                    Files.createDirectories(target);
                } else {
                    Files.copy(path, target, StandardCopyOption.COPY_ATTRIBUTES);
                }
            }
        }
    }
}
