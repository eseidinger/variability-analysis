package de.eseidinger.variabilityengineering.core;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/** Baseline-to-scenario comparison without mutating the baseline population. */
public record ImpactResult(
        ElementUnavailabilityScenario scenario,
        AnalysisResult baseline,
        AnalysisResult scenarioResult,
        Set<String> lostRecordIds,
        Set<Variant> lostVariants,
        Set<String> lostElementIds,
        MetricsDelta rootDelta,
        Map<AnalysisResult.GroupPath, MetricsDelta> groupDeltas) {

    public record MetricsDelta(long recordCount, long variantCount, long uniqueElementCount) {
        public static MetricsDelta between(AnalysisResult.Metrics baseline, AnalysisResult.Metrics scenario) {
            return new MetricsDelta(
                    scenario.recordCount() - baseline.recordCount(),
                    scenario.variantCount() - baseline.variantCount(),
                    scenario.uniqueElementCount() - baseline.uniqueElementCount());
        }
    }

    public ImpactResult {
        Objects.requireNonNull(scenario, "scenario must not be null");
        Objects.requireNonNull(baseline, "baseline must not be null");
        Objects.requireNonNull(scenarioResult, "scenarioResult must not be null");
        Objects.requireNonNull(rootDelta, "rootDelta must not be null");
        lostRecordIds = Set.copyOf(lostRecordIds);
        lostVariants = Set.copyOf(lostVariants);
        lostElementIds = Set.copyOf(lostElementIds);
        groupDeltas = Map.copyOf(groupDeltas);
    }
}
