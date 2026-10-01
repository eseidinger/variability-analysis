package de.eseidinger.variabilityengineering.persistence;

import de.eseidinger.variabilityengineering.adapter.foodservice.FoodServiceFixtureAdapter;
import de.eseidinger.variabilityengineering.core.AnalysisEngine;
import de.eseidinger.variabilityengineering.core.AnalysisPopulation;
import de.eseidinger.variabilityengineering.core.AnalysisQuery;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class JdbcPopulationRepositoryTest {

    @Inject
    JdbcPopulationRepository repository;

    private final AnalysisEngine engine = new AnalysisEngine();

    @Test
    void preservesTheFixturePopulationAndItsAnalysisAfterARoundTrip() throws IOException {
        var imported = new FoodServiceFixtureAdapter().importFixture(fixtureDirectory());
        var source = imported.population();
        var population = new AnalysisPopulation(
                "food-service-mvp-round-trip",
                "1.0.0",
                source.provenance(),
                source.dimensionDefinitions(),
                source.records());

        var saved = repository.saveIfAbsent(population, imported.rejectedRecords());
        var loaded = repository.find(population.id(), population.version()).orElseThrow();
        var savedAgain = repository.saveIfAbsent(population, imported.rejectedRecords());

        assertEquals(population, saved.population());
        assertEquals(imported.rejectedRecords(), saved.rejectedRecords());
        assertEquals(population, loaded.population());
        assertEquals(imported.rejectedRecords(), loaded.rejectedRecords());
        assertEquals(loaded, savedAgain);

        var result = engine.analyze(loaded.population(), AnalysisQuery.unfiltered());
        assertEquals(12, result.metrics().recordCount());
        assertEquals(11, result.metrics().variantCount());
        assertEquals(24, result.metrics().uniqueElementCount());
        assertTrue(repository.find("missing", "1.0.0").isEmpty());
    }

    private static Path fixtureDirectory() {
        return Path.of("../../../examples/food-service/mvp-v1").toAbsolutePath().normalize();
    }
}
