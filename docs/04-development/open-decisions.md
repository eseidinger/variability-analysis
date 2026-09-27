# Open Decisions

| ID | Decision | Why it matters |
|---|---|---|
| OPEN-01 | Which language and UI are implemented first? | Determines build, tests, and containerization. |
| OPEN-02 | Which feature and expression types enter the first contract? | Determines validation and enumeration semantics. |
| OPEN-03 | What does combining dimensions mean? | Value groups, composite dimensions, and visual summaries differ. |
| OPEN-04 | How are model IDs, versions, and concurrent changes handled? | Determines persistence and result reuse. |
| OPEN-05 | Which PostgreSQL representation fits actual access patterns? | Relational and JSONB designs have different tradeoffs. |
| OPEN-06 | Are analysis results stored or calculated on demand? | Depends on size, reuse, and versioning. |
| OPEN-07 | Which sizes and calculation times are supported? | Requires limits within the resource budget. |
| OPEN-08 | Which metrics are binding and how are they weighted? | Prevents ambiguous counts and invalid aggregation. |
| OPEN-09 | How are invalid configurations explained? | Basic violations and minimal conflicts differ in complexity. |
| OPEN-10 | Which real dataset is selected? | Requires license, mapping, and rule provenance. |
| OPEN-11 | Independent SDKs or a shared embedded engine? | Changes implementation and distribution strategy. |
| OPEN-12 | Which application permissions are required? | Must be defined before broader access. |

OPEN-01, OPEN-02, OPEN-04, and OPEN-07 are prerequisites for the prototype. Resolve OPEN-03 before interactive tree implementation.
