# ADR-009 – Evaluate Data Stores Against a Workload

Status: **Documented direction**.

## Context

The project supports architecture experiments but has no evidence requiring additional databases.

## Decision

Add a document, graph, cache, or vector store only for a defined application requirement and reproducible experiment.

## Consequences

MongoDB, Neo4j, Apache AGE, Redis, and pgvector are not prototype dependencies by default.

## Validation

Any proposed store is assessed with equivalent data, queries, consistency needs, resource limits, backup and restore, and operational effort.
