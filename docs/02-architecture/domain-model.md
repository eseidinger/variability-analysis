# Domain Model

Status: core concepts are requirements; detailed evaluation semantics remain partly proposed.

| Term | Meaning |
|---|---|
| Feature | Variable describing a configuration. |
| Feature Domain | Permitted values of a feature. |
| Configuration | Assignment of values to features. |
| Constraint | Rule determining whether a configuration is valid. |
| Element | Domain-neutral constituent of a variant. |
| Presence Condition | Rule determining whether an element is present. |
| Variant | Resulting set of present elements. |
| Variant Space | Set of distinct resulting element sets. |

```text
C_possible = D1 × D2 × … × Dn
C_valid    = { c in C_possible | all constraints are satisfied by c }
Variant(c) = { e in Elements | PresenceCondition(e, c) = true }
V_distinct = { Variant(c) | c in C_valid }
```

A configuration is an assignment; a variant is a set of element IDs. Different configurations may produce the same variant, so configuration count and variant count are different metrics.

## Reference fixture

Features are `region = EU | US`, `advanced = false | true`, and `theme = light | dark`. Elements are `base: true`, `reporting: advanced`, and `euPolicy: region = EU`. With no constraints, the fixture has eight valid configurations and four distinct variants. Adding `NOT (region = US AND advanced = true)` produces six valid configurations and three distinct variants.

Initial proposals limit feature types to Boolean and finite enumerations, require complete assignments, reject unknown feature references before evaluation, and compare variants as unordered sets of stable element IDs.
