# ADR-004 – PostgreSQL Persistence Baseline

Status: **Documented direction**.

## Context

The prototype must save and reload models and should use an existing Developer Platform service.

## Decision

Use PostgreSQL for the first application persistence implementation.

## Consequences

The choice does not yet determine relational versus JSONB structure. Other stores require a concrete workload and comparison.

## Validation

A model round trip preserves semantics, and data survives application workload recreation.
