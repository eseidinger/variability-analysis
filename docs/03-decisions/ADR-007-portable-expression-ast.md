# ADR-007 – Portable Expression AST

Status: **Proposed**.

## Context

Rules must have identical meaning in multiple implementation languages.

## Decision

Use a dedicated serializable expression structure instead of native language expressions.

## Consequences

Parsing, type checking, validation, and evaluation follow a shared specification. The concrete schema and textual syntax remain open.

## Validation

Each implementation evaluates shared valid and invalid expression fixtures identically.
