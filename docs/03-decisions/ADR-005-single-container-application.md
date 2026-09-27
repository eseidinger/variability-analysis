# ADR-005 – UI and API in a Single Container Image

Status: **Documented direction**.

## Context

The Developer Platform currently provisions one containerized HTTP application per project.

## Decision

Package the first prototype UI and API in one image.

## Consequences

UI and API are not independently deployed in the prototype. PostgreSQL remains a separate platform service.

## Validation

Deploy the image through the platform and verify its HTTP entry point, database connection, and readiness behavior.
