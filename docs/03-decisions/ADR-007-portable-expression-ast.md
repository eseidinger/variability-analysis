# ADR-007 – Portable Expression AST

Status: **Proposed**.

## Context

Boolean model constraints and presence conditions must have identical meaning in multiple implementation languages.

## Decision

Use a dedicated serializable expression structure instead of native language expressions for model constraints and presence conditions. Numeric portfolio constraints and optimization objectives require a separate contract.

## Consequences

Parsing, type checking, validation, and evaluation follow a shared specification. The concrete schema and textual syntax remain open. This AST must not expand implicitly into an untyped language for portfolio optimization.

## Validation

Each implementation evaluates shared valid and invalid expression fixtures identically.
