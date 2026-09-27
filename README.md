# Variability Analysis Project

A domain-independent project for modeling configuration spaces, evaluating constraints and presence conditions, calculating distinct variants, and exploring the results.

## Status

The initial UI/API integration prototype and combined container image have been validated locally. Deployment through the Developer Platform remains to be verified.

Development starts with a small runnable application on the Developer Platform. Reusable framework components for Java, Python, TypeScript, Angular, and React follow after the prototype establishes stable semantics and contracts.

## Initial prototype

The first application will:

- define and validate small configuration models;
- calculate valid configurations and their resulting variants with bounded enumeration;
- save and reload models through PostgreSQL;
- present results in a verifiable form; and
- deploy its UI and API as one container image.

The stack-neutral workspace is documented in [`apps/prototype/`](apps/prototype/README.md).

Interactive analysis views, additional language implementations, reusable UI components, external datasets, and performance or storage experiments follow in later stages.

## Documentation

| Area | Documents |
|---|---|
| Product | [Vision](docs/01-product/vision.md), [use cases](docs/01-product/use-cases.md), [requirements](docs/01-product/requirements.md), [roadmap](docs/01-product/roadmap.md) |
| Architecture | [Overview](docs/02-architecture/overview.md), [domain model](docs/02-architecture/domain-model.md), [analysis view](docs/02-architecture/analysis-view.md), [persistence and deployment](docs/02-architecture/persistence-and-deployment.md) |
| Decisions | [Architecture decision records](docs/03-decisions/README.md) |
| Development | [Development plan](docs/04-development/development-plan.md), [quality assurance](docs/04-development/quality-assurance.md), [open decisions](docs/04-development/open-decisions.md) |
| Operations | [Prototype deployment](docs/05-operations/deployment.md) |
| Traceability | [Sources and maintenance](docs/sources.md) |

See the [documentation index](docs/README.md) for status conventions and maintenance guidance.

## Core distinction

A **configuration** is an assignment of feature values. A **variant** is the resulting set of present elements. Different configurations can produce the same variant, so configuration count and variant count are separate metrics.
