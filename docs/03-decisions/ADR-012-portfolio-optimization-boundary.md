# ADR-012 – Separate Portfolio Optimization from Configuration Validity

Status: **Documented direction**.

## Context

Rules such as `advanced requires reporting` validate one configuration. Rules such as `at least five vegan dishes`, `at most fifty ingredients`, or `total cost below a budget` constrain a selected collection of records and may require numeric aggregation.

## Decision

Represent a portfolio as a selection of variant records from a versioned candidate population. Define portfolio constraints and optimization objectives separately from Boolean model constraints and presence conditions. Optimization results report input versions, feasibility, objective values, constraint evaluations, solver status, and any optimality claim.

## Consequences

A menu is a portfolio rather than a variant. The portable Boolean expression AST remains small, and solver implementations can evolve behind a stable optimization boundary. Quantities and units require explicit semantics before cost or resource objectives become binding.

## Validation

For a fully enumerable fixture, compare optimizer feasibility and objective values with exhaustive reference results and verify that every returned portfolio uses only records from the identified candidate population.
