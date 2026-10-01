package de.eseidinger.variabilityengineering.http;

import de.eseidinger.variabilityengineering.adapter.foodservice.FoodServiceFixtureAdapter;
import de.eseidinger.variabilityengineering.core.AnalysisPopulation;
import io.smallrye.config.ConfigMapping;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/** Read-only, fixture-backed population source until persistence is introduced. */
@ApplicationScoped
public class FoodServicePopulationStore {

    @ConfigMapping(prefix = "variability.fixture")
    public interface FixtureConfig {
        String directory();
    }

    @Inject
    FixtureConfig fixtureConfig;

    private AnalysisPopulation population;
    private List<FoodServiceFixtureAdapter.RejectedRecord> rejectedRecords;

    @PostConstruct
    void loadFixture() {
        try {
            var imported = new FoodServiceFixtureAdapter().importFixture(Path.of(fixtureConfig.directory()));
            population = imported.population();
            rejectedRecords = imported.rejectedRecords();
        } catch (IOException exception) {
            throw new IllegalStateException("cannot load food-service fixture", exception);
        }
    }

    public AnalysisPopulation population(String populationId) {
        if (!population.id().equals(populationId)) {
            throw new NotFoundException("unknown population: " + populationId);
        }
        return population;
    }

    public List<FoodServiceFixtureAdapter.RejectedRecord> rejectedRecords(String populationId) {
        population(populationId);
        return rejectedRecords;
    }
}
