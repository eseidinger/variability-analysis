# Requirements

Status: consolidated target requirements. Acceptance criteria describe intended evidence, not completed work.

| ID | Requirement |
|---|---|
| REQ-01 | Support models from different application domains. |
| REQ-02 | Determine element presence using Boolean expressions over features. |
| REQ-03 | Calculate and display variants as distinct sets of present elements. |
| REQ-04 | Provide an analysis tree with one feature dimension per level. |
| REQ-05 | Allow dimensions to be reordered. |
| REQ-06 | Allow dimensions to be hidden or combined with defined aggregation semantics. |
| REQ-07 | Later provide reusable components for Java, Python, and TypeScript. |
| REQ-08 | Later provide visualization components for Angular and React. |

## Prototype acceptance

1. A small model can be entered or imported and validated.
2. A valid model can be saved and reloaded without changing its semantics.
3. Reference fixtures produce their specified configuration counts, variant counts, and element sets.
4. Invalid models and configurations remain distinguishable from valid empty results.
5. The application is deployed as one UI/API container image on the Developer Platform.
6. PostgreSQL data remains available after recreating the application workload.
7. Runtime and peak memory are recorded for bounded fixtures.
8. The revision, model, checks, and limitations are documented.
