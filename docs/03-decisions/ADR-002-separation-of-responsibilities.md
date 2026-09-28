# ADR-002 – Separate Model, Calculation, and Presentation

Status: **Documented direction**.

## Context

Evaluation must be independently testable and reusable in different interfaces.

## Decision

Define explicit boundaries between the domain model, domain adapters, normalization, expression evaluation, analysis, impact comparison, configuration assistance, portfolio optimization, derived views, application coordination, persistence, and visualization.

## Consequences

UI code cannot redefine counting, feasibility, or optimization semantics. Model constraints remain separate from portfolio constraints and objectives. Persistence, solver implementations, and transport remain replaceable.

## Validation

Run reference analyses without a UI or database and obtain the same normalized results through the application.
