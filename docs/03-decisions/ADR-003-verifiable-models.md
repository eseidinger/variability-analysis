# ADR-003 – Start with Small, Fully Verifiable Models

Status: **Documented direction**.

## Context

Correctness and resource behavior must be understood before algorithm comparisons or large datasets.

## Decision

The prototype begins with bounded enumeration and synthetic fixtures whose complete results are known.

## Consequences

Optimized solvers are not prerequisites. Model-size limits and termination behavior must be explicit.

## Validation

Every configuration and resulting variant in the reference fixtures matches the documented expectation.
