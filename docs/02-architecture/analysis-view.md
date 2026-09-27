# Expression and Analysis View

Status: target design with unresolved interaction semantics.

## Portable expressions

Rules use a dedicated serializable abstract syntax tree rather than native Java, Python, or JavaScript expressions. The initial operation set contains Boolean constants, `AND`, `OR`, `NOT`, Boolean-feature evaluation, and equality or inequality for enumeration values.

## Derived analysis tree

The analysis tree is a view of analysis results, not the underlying domain model. Reordering dimensions changes hierarchy but not constraints, valid configurations, or variants.

| Operation | Required invariant |
|---|---|
| Reordering | Overall results remain unchanged. |
| Hiding or projection | Hidden states are merged, not silently removed. |
| Grouping | Membership and counting rules are explicit. |
| Filtering | The restricted subspace is visible to the user. |
| Drill-down and roll-up | Metrics remain tied to the identified subspace. |

## Metrics

`configurationCount` counts valid configurations. `variantCount` counts distinct element sets. `uniqueElementCount` is the element union size; `commonElements` is the intersection; `optionalElements` is union minus intersection. Frequencies must state whether configurations or variants form the reference population.

Counts aggregate differently: configuration counts can be added across disjoint groups, while variant counts generally cannot because one variant may occur in several groups.
