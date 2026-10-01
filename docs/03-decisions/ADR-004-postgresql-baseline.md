# ADR-004 – PostgreSQL Persistence Baseline

Status: **Implemented for the food-service MVP**.

## Context

The prototype must save and reload models and should use an existing Developer Platform service.

## Decision

Use PostgreSQL for the first application persistence implementation.

## Consequences

The MVP uses normalized relational tables for immutable populations, provenance, dimensions, records, element usages, dimension values, and rejected rows. Analyses are calculated on demand. Other stores require a concrete workload and comparison.

## Validation

Flyway applies the schema to PostgreSQL 17, and an automated fixture round trip preserves population, provenance, dimensions, records, rejected rows, and reference metrics.
