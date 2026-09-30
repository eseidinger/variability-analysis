# Food-Service Analysis MVP Semantics

Status: binding MVP contract accepted by [ADR-013](../03-decisions/ADR-013-food-service-analysis-mvp-semantics.md); this document is not implementation evidence.

## Scope

The MVP proves that one domain-independent analysis engine can analyze an observed food-service population. It imports recipe records, normalizes ingredient usages and analysis dimensions, calculates deterministic metrics, supports filtering and ordered grouping, and compares ingredient-unavailability scenarios with an immutable baseline.

The normative input and expected results are the [food-service MVP v1 fixture](../../examples/food-service/mvp-v1/README.md).

Generative model authoring, partial configuration, quantities and units, cost and sustainability metrics, portfolio selection, optimization, combined dimensions, and substitutions are outside this contract.

## Normalized food-service mapping

| Framework concept | MVP food-service meaning |
|---|---|
| Analysis population | One immutable import produced from exact source and transformation versions |
| Variant record | One identified recipe or dish in that population |
| Element | One canonical ingredient |
| Element usage | Binary occurrence of an ingredient in a recipe |
| Structural variant | Unordered set of canonical ingredient IDs |
| Analysis dimension | Cuisine, dish type, diet, or allergen classification |
| Scenario | One or more ingredients becoming unavailable |

A source recipe ID identifies a record only within its source dataset version. Two records remain distinct even when their structural variants are equal.

## Structural identity

Ingredient aliases map to stable canonical ingredient IDs before structural comparison. A structural variant is equal to another variant exactly when their sets of canonical ingredient IDs are equal. Ordering, duplicate source mentions, display names, quantities, units, and recipe identity do not affect equality.

An implementation may hash a deterministically sorted representation for indexing, but set equality defines the semantics and hash equality alone is not sufficient evidence of equality.

## Dimensions and missing values

| Dimension | Cardinality | Expected origin |
|---|---|---|
| Cuisine | Single-valued | Imported or derived |
| Dish type | Single-valued | Imported or derived |
| Diet | Multi-valued | Imported or derived |
| Allergen | Multi-valued | Imported or derived |

Each record has one of these states for each dimension:

- `KNOWN` with exactly one canonical value for a single-valued dimension;
- `KNOWN` with zero or more canonical values for a multi-valued dimension; or
- `UNKNOWN` when the source and documented derivations cannot establish the value.

A known empty multi-valued set means “known to have none” and is not equivalent to `UNKNOWN`. For allergens, an empty set is known only when every normalized ingredient has complete allergen classification under the identified derivation; otherwise the allergen dimension is `UNKNOWN`. For diets, an empty set means that none of the supported diet classifications applies and does not imply an unmodeled category. Every dimension declares its origin as imported or derived. A derived value records the derivation version that produced it.

A record with several values in a grouped multi-valued dimension belongs to each corresponding child group. Those child groups overlap, so their record and variant counts are not generally additive.

## Versions and provenance

Every population records:

- a stable population ID and immutable population version;
- source dataset ID, source version, and SHA-256 checksum;
- adapter ID, version, and SHA-256 checksum;
- mapping ID, version, and SHA-256 checksum; and
- derivation ID, version, and SHA-256 checksum for each applied derivation set.

Identifiers and versions are non-empty, case-sensitive strings. Checksums are lowercase hexadecimal SHA-256 values over the exact referenced artifact bytes. An import with a different source checksum, adapter artifact, mapping artifact, or derivation artifact produces a new population version. Existing population versions are never reinterpreted in place.

Each record retains its source record ID and population version. Rejected source records retain the source record ID, machine-readable reason, and human-readable detail.

## Filters and grouping

All active filter predicates are combined with logical `AND`.

- An inclusion filter over a single-valued dimension matches equality.
- An inclusion filter over a multi-valued dimension matches when the record contains any selected value.
- An exclusion filter rejects a record when its dimension contains any selected value.
- A dimension-state filter explicitly selects `KNOWN` or `UNKNOWN` records.
- An `UNKNOWN` value never satisfies an inclusion or exclusion value filter.
- An element inclusion filter matches records using the canonical element ID.

An allergen-free query must require the allergen dimension to be `KNOWN` and exclude the selected allergen values. Records with `UNKNOWN` allergen state must not be presented as allergen-free.

Grouping receives an ordered list of dimensions. Reordering the list changes the hierarchy but not the selected records or root metrics. Filtering changes the selected population and must be shown in the result. Combining dimensions or user-defined value aggregation is not part of the MVP.

## Metrics

For the selected record set:

- `recordCount` is the number of distinct record identities;
- `variantCount` is the number of distinct structural variants;
- `uniqueElementCount` is the cardinality of the union of element usages;
- `elementRecordCount(e)` is the number of records using element `e`; and
- `elementRecordFrequency(e)` is `elementRecordCount(e) / recordCount`.

An empty selected set has zero counts and an empty element union. Its element frequencies and relative deltas with a zero denominator are `null`, not zero.

MVP element leverage reports, without weighting:

- distinct records using the element;
- distinct known cuisine values among those records; and
- distinct known diet values among those records.

Unknown dimension values do not increase cuisine or diet coverage. Metrics always identify the population version and active filters that define their reference population.

## Ingredient-unavailability scenario

The scenario input names a baseline population version and a non-empty set of canonical ingredient IDs. A baseline record is feasible in the scenario exactly when it uses none of those ingredients. The operation excludes whole records; it does not delete ingredients from structural variants or rewrite recipes.

The result identifies the unavailable ingredients and reports baseline and scenario metrics, lost record IDs, lost structural variants, lost element IDs, and changes grouped by requested dimensions. For a metric `m`, the absolute delta is `scenario(m) - baseline(m)`. The relative delta is the absolute delta divided by `baseline(m)`, or `null` when the baseline value is zero.

The baseline population is immutable. Substitution, source-data mutation, element deletion, and constraint changes require different future scenario operations.

## Binding invariants

An implementation conforms to the MVP semantics only when reference fixtures demonstrate that:

1. alias normalization happens before structural comparison;
2. equal ingredient sets produce one structural variant while preserving separate records;
3. known-empty and unknown classifications remain distinguishable;
4. multi-valued group overlap does not inflate root metrics;
5. grouping order leaves selected records and root metrics unchanged;
6. every result identifies its population and transformation versions; and
7. ingredient unavailability excludes complete affected records and leaves the baseline unchanged.
