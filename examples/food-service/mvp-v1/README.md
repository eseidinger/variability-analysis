# Food-Service MVP Reference Dataset

Status: normative synthetic fixture for the binding [food-service MVP semantics](../../../docs/02-architecture/food-service-mvp-semantics.md). It is test data, not implementation evidence or culinary guidance.

## Purpose

This fixture is intentionally small and hand-verifiable. It exercises:

- ingredient alias normalization and duplicate source mentions;
- separate recipe identity and structural-variant equality;
- single-valued and multi-valued dimensions;
- overlapping diet and allergen groups;
- known-empty and explicitly unknown classifications;
- one rejected record with an unmapped ingredient;
- element occurrence and leverage metrics; and
- whole-record exclusion for ingredient unavailability.

The recipe names and ingredient combinations were created specifically for this repository. No external recipe collection was copied. This fixture and its artifacts are dedicated under [CC0-1.0](LICENSE), independently of the repository-wide Apache-2.0 license.

## Artifacts

| Path | Role |
|---|---|
| `source/recipes.json` | Thirteen source records: twelve accepted and one deliberately rejected |
| `mapping/ingredient-aliases.json` | Source phrase to canonical ingredient mapping |
| `derivation/ingredient-classifications.json` | Inputs for diet and allergen derivation |
| `adapter/contract.json` | Deterministic reference-adapter behavior |
| `expected/results.json` | Normative normalized records, queries, metrics, and scenario results |
| `manifest.json` | Artifact identifiers, versions, licenses, and SHA-256 checksums |

## Supported classifications

The fixture supports only `VEGAN` and `VEGETARIAN` diet labels and the allergen values listed in the derivation artifact. A known-empty diet set means neither supported diet applies. It does not mean that every possible dietary category was evaluated.

An allergen set is known only if every canonical ingredient has a known allergen classification. The record containing `market-greens` therefore has `UNKNOWN` diet and allergen states. An allergen-free query must exclude that record.

## Reference questions

The expected results answer these MVP questions:

1. How many source records, accepted recipes, structural variants, and ingredients exist?
2. Which recipes are vegan, Italian, tomato-based, or known free of supported allergens?
3. Which ingredients occur in the most recipes?
4. Across how many recipes, cuisines, and diets does garlic provide leverage?
5. What records, structural variants, elements, and cuisine groups are lost when garlic becomes unavailable?

## Change policy

Version `1.0.0` is immutable once used by implementation or conformance tests. Correcting or extending source, mapping, derivation, adapter, or expected-result content requires a new fixture version and new checksums. Formatting changes also change artifact bytes and therefore require new checksums.
