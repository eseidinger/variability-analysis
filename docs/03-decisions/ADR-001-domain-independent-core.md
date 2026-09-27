# ADR-001 – Domain-Independent Core

Status: **Requirement**.

## Context

The model must generalize across unrelated domains.

## Decision

Features, constraints, configurable elements, configurations, and variants are domain-neutral concepts. Concrete terminology and data enter through applications and adapters.

## Consequences

Domain assumptions stay out of shared evaluation logic. Reference fixtures may use specific domains without making them part of the contract.

## Validation

Represent at least two unrelated domains without changing core evaluation semantics.
