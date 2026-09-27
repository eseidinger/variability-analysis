# Quality Assurance and Experiments

Status: proposed verification approach.

| Check | Expectation |
|---|---|
| Enumeration and constraints | Exactly the valid configurations are analyzed. |
| Presence conditions | Each reference configuration produces its expected element set. |
| Deduplication | Equal element sets count as one variant. |
| Invalid versus empty | Invalid configurations remain distinct from valid empty variants. |
| Persistence | Saving and loading preserves semantics. |
| Dimension order | Reordering does not change overall results. |
| Projection | Hiding alone does not remove configurations. |
| Aggregation | Distinct variants use set operations rather than additive counts. |
| Language conformance | Shared inputs produce equivalent normalized results. |

Experiments must state the question, hypothesis, versions, hardware, configuration, input generator, revision, repeated measurements, raw results, and limitations. Record runtime, peak memory, relevant latency distributions, storage and index costs, and operational effort where applicable. A successful deployment is not a benchmark result.
