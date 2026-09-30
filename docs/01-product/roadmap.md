# Product Roadmap

Status: planning sequence without confirmed dates or effort estimates.

| Step | Outcome | Acceptance gate |
|---|---|---|
| 1. Semantics and stack | Initial language, UI, types, expressions, identity, and limits selected | Binding prototype contract documented |
| 2. Evaluation core | Validation, bounded enumeration, constraints, presence evaluation, and variant deduplication | Reference fixtures produce expected results |
| 3. Application and persistence | Results view plus PostgreSQL save/load | Round trip and error cases verified |
| 4. First deployment | UI/API image on the Developer Platform | Accessibility, persistence, and resource behavior recorded |
| 5. Interactive analysis | Reorder, hide, combine, filter, and drill through dimensions | View invariants and aggregation rules verified |
| 6. Normalized populations | Generated and imported records share structural signatures, provenance, and analysis contracts | Generated and observed fixtures produce expected record and distinct-variant counts |
| 7. Food-service demonstration | Licensed recipe dataset, documented adapter, ingredient impact, and analytical views | Mapping, derivations, provenance, and reference results verified |
| 8. Impact and configuration | Versioned scenarios and partial-requirement completion | Baseline deltas and compatible completions match exhaustive fixtures |
| 9. Portfolio optimization | Menu constraints, objectives, explanations, and later Pareto alternatives | Small problems match exhaustive reference solutions and all claims identify solver status |
| 10. Framework components | Shared schemas, conformance cases, SDKs, and UI packages | Equivalent normalized results across implementations |
| 11. Experiments and additional domains | Defined algorithm or storage comparisons and cross-domain validation | Workload, method, evidence, and limitations published |

The synthetic fixture remains the first correctness oracle. The food-service demonstration follows interactive analysis so a real observed dataset can validate the abstractions before they are extracted into multiple framework implementations. Optimization starts with small fully enumerable portfolio fixtures; adopting a specialized solver is a later evidence-driven decision.

The selected observed-data correctness oracle is the versioned [food-service MVP v1 fixture](../../examples/food-service/mvp-v1/README.md). Its expected results cover normalization, dimensions, filtering, leverage, and ingredient unavailability.
