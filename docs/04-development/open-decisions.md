# Open Decisions

| ID | Decision | Why it matters |
|---|---|---|
| OPEN-02 | Which feature and expression types enter the first contract? | Determines validation and enumeration semantics. |
| OPEN-03 | What does combining dimensions mean? | Value groups, composite dimensions, and visual summaries differ. |
| OPEN-04 | How are model IDs, versions, and concurrent changes handled? | Determines persistence and result reuse. |
| OPEN-05 | Which PostgreSQL representation fits actual access patterns? | Relational and JSONB designs have different tradeoffs. |
| OPEN-06 | Are analysis results stored or calculated on demand? | Depends on size, reuse, and versioning. |
| OPEN-07 | Which sizes and calculation times are supported? | Requires limits within the resource budget. |
| OPEN-08 | Which metrics are binding, what is their reference population, and how are they weighted? | Prevents ambiguous counts, invalid aggregation, and misleading objectives. |
| OPEN-09 | How are invalid configurations explained? | Basic violations and minimal conflicts differ in complexity. |
| OPEN-10 | Which food-service dataset and version are selected? | Requires a compatible license, stable source, usable quantities, mapping, and derivation provenance. |
| OPEN-11 | Independent SDKs or a shared embedded engine? | Changes implementation and distribution strategy. |
| OPEN-12 | Which application permissions are required? | Must be defined before broader access. |
| OPEN-13 | When do quantities or other usage attributes affect structural variant equality? | Changes deduplication, counts, caches, and cross-language conformance. |
| OPEN-14 | How are dataset, adapter, mapping, and derivation versions identified? | Impact and optimization results must be reproducible against an exact population. |
| OPEN-15 | How are missing, multi-valued, and derived dimension values represented? | Determines filtering, grouping, and coverage semantics. |
| OPEN-16 | Which scenario operations are supported first? | Unavailability, removal, substitution, and constraint changes have different semantics. |
| OPEN-17 | Are portfolios sets, multisets, or ordered collections of records? | Determines duplicate offerings, schedules, identity, and optimization variables. |
| OPEN-18 | Which quantity and unit model is required for recipes? | Cost, inventory, and sustainability calculations need normalized, comparable usages. |
| OPEN-19 | What is the portable portfolio-constraint and objective contract? | The Boolean model-rule AST does not cover numeric aggregation or optimization. |
| OPEN-20 | Which optimization guarantees and solver status values are exposed? | Feasible, optimal, bounded, timed-out, and Pareto claims require precise evidence. |

OPEN-02, OPEN-04, and OPEN-07 are prerequisites for the domain prototype. Resolve OPEN-03 and OPEN-15 before interactive tree implementation; OPEN-10, OPEN-13, OPEN-14, and OPEN-18 before the food-service import; and OPEN-17, OPEN-19, and OPEN-20 before portfolio optimization.

## Resolved implementation choices

| Former ID | Decision | Evidence |
|---|---|---|
| OPEN-01 | The first implementation uses a Quarkus API and Angular UI packaged in one container image. | Prototype source, build definitions, and local container documentation. |
