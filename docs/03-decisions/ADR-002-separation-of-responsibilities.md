# ADR-002 – Separate Model, Calculation, and Presentation

Status: **Documented direction**.

## Context

Evaluation must be independently testable and reusable in different interfaces.

## Decision

Define explicit boundaries between the domain model, expression evaluation, analysis, derived views, application coordination, persistence, and visualization.

## Consequences

UI code cannot redefine counting or rule semantics. Persistence and transport remain replaceable.

## Validation

Run reference analyses without a UI or database and obtain the same normalized results through the application.
