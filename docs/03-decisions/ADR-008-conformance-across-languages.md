# ADR-008 – Shared Specification and Conformance Cases

Status: language support is a **Requirement**; this design is **Proposed**.

## Context

Java, Python, and TypeScript components must not drift semantically.

## Decision

Maintain shared interchange schemas, reference models, observed-population fixtures, expected normalized results, structural signatures, and error cases.

## Consequences

Structural schema validation alone is insufficient. Independent SDKs or a shared embedded engine remain an open implementation choice.

## Validation

All supported implementations pass the same conformance suite and produce equivalent normalized records, signatures, counts, and errors.
