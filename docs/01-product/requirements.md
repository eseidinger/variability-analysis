# Requirements

Status: consolidated target requirements. Acceptance criteria describe intended evidence, not completed work.

| ID | Requirement |
|---|---|
| REQ-01 | Support models from different application domains. |
| REQ-02 | Determine element presence using Boolean expressions over features. |
| REQ-03 | Calculate and display variants as distinct sets of present elements. |
| REQ-04 | Provide an analysis tree with one analysis dimension per level. |
| REQ-05 | Allow dimensions to be reordered. |
| REQ-06 | Allow dimensions to be hidden or combined with defined aggregation semantics. |
| REQ-07 | Later provide reusable components for Java, Python, and TypeScript. |
| REQ-08 | Later provide visualization components for Angular and React. |
| REQ-09 | Normalize generated configurations and imported observations into a common population of identified variant records. |
| REQ-10 | Keep analysis dimensions distinct from configuration features so imported and derived attributes can be analyzed without becoming decision variables. |
| REQ-11 | Compare a baseline with explicit change scenarios and report their effect on records, variants, elements, and dimension groups. |
| REQ-12 | Accept partial requirements and return compatible complete configurations and resulting variants with explanations. |
| REQ-13 | Represent a portfolio as a selected collection of variant records, distinct from an individual structural variant. |
| REQ-14 | Support portfolio constraints and objectives without conflating them with Boolean configuration-validity rules. |
| REQ-15 | Later expose feasible and Pareto-optimal portfolio solutions with their objective values, assumptions, and trade-offs. |
| REQ-16 | Preserve source, mapping, and derivation provenance for imported datasets and derived classifications. |

REQ-01 through REQ-06 define the initial analysis capability. REQ-09 and REQ-10 are required before importing the food-service dataset. REQ-11 through REQ-16 are staged framework requirements and are not part of prototype acceptance.

## Prototype acceptance

1. A small model can be entered or imported and validated.
2. A valid model can be saved and reloaded without changing its semantics.
3. Reference fixtures produce their specified configuration counts, variant counts, and element sets.
4. Invalid models and configurations remain distinguishable from valid empty results.
5. The application is deployed as one UI/API container image on the Developer Platform.
6. PostgreSQL data remains available after recreating the application workload.
7. Runtime and peak memory are recorded for bounded fixtures.
8. The revision, model, checks, and limitations are documented.
