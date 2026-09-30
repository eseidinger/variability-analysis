# Open Decisions

| ID | Decision | Why it matters |
|---|---|---|
| OPEN-02 | Which feature and expression types enter the first contract? | Determines validation and enumeration semantics. |
| OPEN-03 | What does combining dimensions mean? | Value groups, composite dimensions, and visual summaries differ. |
| OPEN-04 | How are model IDs, versions, and concurrent changes handled? | Determines persistence and result reuse. |
| OPEN-05 | Which PostgreSQL representation fits actual access patterns? | Relational and JSONB designs have different tradeoffs. |
| OPEN-06 | Are analysis results stored or calculated on demand? | Depends on size, reuse, and versioning. |
| OPEN-07 | Which sizes and calculation times are supported? | Requires limits within the resource budget. |
| OPEN-09 | How are invalid configurations explained? | Basic violations and minimal conflicts differ in complexity. |
| OPEN-11 | Independent SDKs or a shared embedded engine? | Changes implementation and distribution strategy. |
| OPEN-12 | Which application permissions are required? | Must be defined before broader access. |
| OPEN-17 | Are portfolios sets, multisets, or ordered collections of records? | Determines duplicate offerings, schedules, identity, and optimization variables. |
| OPEN-18 | Which quantity and unit model is required for recipes? | Cost, inventory, and sustainability calculations need normalized, comparable usages. |
| OPEN-19 | What is the portable portfolio-constraint and objective contract? | The Boolean model-rule AST does not cover numeric aggregation or optimization. |
| OPEN-20 | Which optimization guarantees and solver status values are exposed? | Feasible, optimal, bounded, timed-out, and Pareto claims require precise evidence. |
| OPEN-21 | Which external food-service dataset and version should follow the MVP fixture? | A broader demonstration requires a compatible license, stable source, usable fields, and documented mapping and derivation provenance. |

OPEN-05 and OPEN-07 remain prerequisites for the persisted food-service MVP. Resolve OPEN-02, OPEN-04, and OPEN-09 before generative-model behavior, OPEN-03 before combined dimensions, OPEN-21 before importing an external food-service dataset, OPEN-18 before quantitative recipe usage, and OPEN-17, OPEN-19, and OPEN-20 before portfolio optimization.

## Resolved implementation choices

| Former ID | Decision | Evidence |
|---|---|---|
| OPEN-01 | The first implementation uses a Quarkus API and Angular UI packaged in one container image. | Prototype source, build definitions, and local container documentation. |
| OPEN-08 | MVP metrics are unweighted record, structural-variant, element, occurrence, and cuisine/diet leverage counts tied to an identified population and filter set. | [ADR-013](../03-decisions/ADR-013-food-service-analysis-mvp-semantics.md) and the binding [MVP semantics](../02-architecture/food-service-mvp-semantics.md). |
| OPEN-10 | The MVP uses the original synthetic `food-service-mvp` dataset version `1.0.0`; a larger external dataset is a separate decision. | The versioned [fixture](../../examples/food-service/mvp-v1/README.md), manifest, and independently checked expected results. |
| OPEN-13 | MVP structural identity uses binary presence of canonical element IDs; quantities and units do not participate. | [ADR-013](../03-decisions/ADR-013-food-service-analysis-mvp-semantics.md). |
| OPEN-14 | Every population records immutable dataset, adapter, mapping, and derivation identifiers, versions, and checksums. | [ADR-013](../03-decisions/ADR-013-food-service-analysis-mvp-semantics.md). |
| OPEN-15 | Cuisine and dish type are single-valued; diets and allergens are multi-valued; known empty and explicit unknown states are distinct; derived values are versioned. | [ADR-013](../03-decisions/ADR-013-food-service-analysis-mvp-semantics.md). |
| OPEN-16 | The first scenario operation is unavailability of one or more elements, which makes every record using one of them infeasible without changing the baseline. | [ADR-013](../03-decisions/ADR-013-food-service-analysis-mvp-semantics.md). |
