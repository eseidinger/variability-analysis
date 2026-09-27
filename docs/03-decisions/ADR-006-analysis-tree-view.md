# ADR-006 – Analysis Tree as a Derived View

Status: **Proposed**.

## Context

Users must reorder, hide, and combine dimensions without changing domain rules.

## Decision

Represent view choices separately in an `AnalysisView`; derive the tree from normalized analysis results.

## Consequences

Projection and grouping require explicit aggregation semantics. Tree identity does not become model identity.

## Validation

Reordering preserves overall results, hiding preserves configurations, and grouping follows documented set and count rules.
