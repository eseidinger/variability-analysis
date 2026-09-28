# Architecture Decision Records

ADRs separate accepted requirements and documented directions from proposals. Implementation status and operational verification must be recorded independently.

| ADR | Topic | Status |
|---|---|---|
| [001](ADR-001-domain-independent-core.md) | Domain-independent core | Requirement |
| [002](ADR-002-separation-of-responsibilities.md) | Separate model, calculation, and presentation | Documented direction |
| [003](ADR-003-verifiable-models.md) | Start with small, verifiable models | Documented direction |
| [004](ADR-004-postgresql-baseline.md) | PostgreSQL persistence baseline | Documented direction |
| [005](ADR-005-single-container-application.md) | UI and API in one image | Documented direction |
| [006](ADR-006-analysis-tree-view.md) | Analysis tree as a derived view | Proposed |
| [007](ADR-007-portable-expression-ast.md) | Portable expression AST | Proposed |
| [008](ADR-008-conformance-across-languages.md) | Shared specification and conformance cases | Language support required; design proposed |
| [009](ADR-009-workload-driven-data-stores.md) | Evaluate additional data stores against workloads | Documented direction |
| [010](ADR-010-normalized-analysis-population.md) | Normalize generated and observed variant records | Documented direction |
| [011](ADR-011-analysis-dimensions.md) | Separate analysis dimensions from configuration features | Documented direction |
| [012](ADR-012-portfolio-optimization-boundary.md) | Separate portfolio optimization from configuration validity | Documented direction |

## Decision process

Define the problem and affected requirements, compare realistic alternatives, record the decision and consequences, specify validation evidence, and update dependent architecture and planning documents. Supersede accepted records rather than silently rewriting their history.
