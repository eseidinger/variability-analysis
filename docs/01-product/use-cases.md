# Use Cases

Status: proposed use cases derived from the project scope.

## UC-01 — Define and validate a model

An application developer defines features, finite value domains, global constraints, configurable elements, and presence conditions. The application reports structural, reference, and type errors before analysis.

## UC-02 — Analyze configurations and variants

A user calculates valid configurations, their resulting element sets, distinct variants, and explicit metrics such as configuration count and variant count.

## UC-03 — Save and reload a model

A user stores a model in PostgreSQL, reloads it, and receives analysis results with unchanged semantics.

## UC-04 — Explore an analysis view

A user reorders, hides, groups, filters, and expands feature dimensions. View changes do not silently alter the underlying model or counting semantics.

## Reference domains

The initial synthetic fixture is the verification baseline. Later candidates include product configuration, recipes, and software product lines. External datasets require a documented license, field mapping, and distinction between observed data and generative rules.
