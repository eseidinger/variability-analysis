# ADR-013 – Food-Service Analysis MVP Semantics

Status: **Accepted** on September 30, 2026.

## Context

The normalized population, analysis-dimension, and impact boundaries are documented, but several details remain too open for independent API, UI, persistence, and test implementation. The first useful food-service slice needs deterministic identity, missing-value, filtering, counting, provenance, and scenario semantics without committing to quantities or optimization.

## Decision

The MVP analyzes observed recipes through the domain-independent normalized-population model. A recipe is an identified variant record, an ingredient is an element, and a structural variant is the unordered set of canonical ingredient IDs used by the recipe.

Element usage is binary. Quantities and units are neither required nor considered when comparing structural variants. Cuisine and dish type are single-valued dimensions; diets and allergens are multi-valued dimensions. Every dimension value is either known or explicitly unknown, and imported and derived values retain their origin and versioned provenance.

Every imported population identifies immutable dataset, adapter, mapping, and derivation versions and checksums. Changing any of those inputs creates a new population version rather than modifying the meaning of an existing version.

The binding MVP metrics are record count, distinct-variant count, unique-element count, element occurrence by record, and unweighted element leverage across records and known cuisine and diet values. Filters are explicit, grouping order does not change the selected population, and records in multi-valued groups may appear under more than one child.

The first impact operation is element unavailability. A record using any unavailable element is infeasible in the scenario. The baseline remains unchanged, and the result reports absolute and relative changes using the same metrics and filters as the baseline.

The complete normative contract is [Food-Service Analysis MVP Semantics](../02-architecture/food-service-mvp-semantics.md).

## Consequences

The API, UI, database representation, and fixtures can be implemented against one deterministic contract. Counts from overlapping multi-valued groups are not assumed to be additive. Unknown classification remains distinct from a known empty set. Ingredient removal, substitution, quantities, costs, portfolio selection, and optimization are excluded from the MVP rather than receiving implicit semantics.

## Validation

Use the versioned [food-service MVP v1 fixture](../../examples/food-service/mvp-v1/README.md), which contains duplicate structural variants, aliases, known-empty and unknown multi-valued dimensions, and overlapping diet and allergen values. Verify normalized identity, filtering, grouping, leverage, provenance, and element-unavailability deltas against checked-in expected results.
