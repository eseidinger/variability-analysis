# ADR-008 – Shared Specification and Conformance Cases

Status: language support is a **Requirement**; this design is **Proposed**.

## Context

Java, Python, and TypeScript components must not drift semantically.

## Decision

Maintain shared interchange schemas, reference models, expected normalized results, and error cases.

## Consequences

Structural schema validation alone is insufficient. Independent SDKs or a shared embedded engine remain an open implementation choice.

## Validation

All supported implementations pass the same conformance suite and produce equivalent normalized output.
