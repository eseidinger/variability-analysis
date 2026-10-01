# Variability Engineering Framework

A domain-independent framework for modeling, analyzing, configuring, and optimizing systems with many possible variants. Variability analysis is the first planned framework capability and remains the semantic foundation for the broader framework.

## Status

The initial UI/API integration prototype and combined container image have been validated locally. Deployment through the Developer Platform remains to be verified.

The API now contains an in-memory domain-independent analysis core for versioned observed populations, a verified food-service fixture adapter, and PostgreSQL-backed population storage. Food service remains the first UI demonstration. Configuration assistance, portfolio optimization, and reusable framework components follow after the prototype establishes stable semantics and contracts.

## Initial prototype

The first application will:

- define and validate small configuration models;
- calculate valid configurations and their resulting variants with bounded enumeration;
- save and reload models through PostgreSQL;
- present results in a verifiable form; and
- deploy its UI and API as one container image.

The stack-neutral workspace is documented in [`apps/prototype/`](apps/prototype/README.md).

The normative observed-data example is the [food-service MVP v1 fixture](examples/food-service/mvp-v1/README.md).

Interactive analysis views, configuration assistance, portfolio optimization, additional language implementations, reusable UI components, and performance or storage experiments follow in later stages.

## Documentation

| Area | Documents |
|---|---|
| Product | [Vision](docs/01-product/vision.md), [use cases](docs/01-product/use-cases.md), [requirements](docs/01-product/requirements.md), [roadmap](docs/01-product/roadmap.md) |
| Architecture | [Overview](docs/02-architecture/overview.md), [domain model](docs/02-architecture/domain-model.md), [analysis view](docs/02-architecture/analysis-view.md), [food-service MVP semantics](docs/02-architecture/food-service-mvp-semantics.md), [food-service HTTP contract](docs/02-architecture/food-service-http-contract.md), [persistence and deployment](docs/02-architecture/persistence-and-deployment.md) |
| Decisions | [Architecture decision records](docs/03-decisions/README.md) |
| Development | [Development plan](docs/04-development/development-plan.md), [MVP implementation plan](docs/04-development/mvp-implementation-plan.md), [quality assurance](docs/04-development/quality-assurance.md), [open decisions](docs/04-development/open-decisions.md) |
| Operations | [Prototype deployment](docs/05-operations/deployment.md) |
| Traceability | [Sources and maintenance](docs/sources.md) |

See the [documentation index](docs/README.md) for status conventions and maintenance guidance.

## Core distinction

A **configuration** is a complete assignment of feature values. A structural **variant** is the resulting collection of present element usages. A **variant record** is an identified generated or imported candidate with dimensions and provenance. Different configurations or records can produce the same structural variant, so configuration count, record count, and distinct-variant count are separate metrics. A **portfolio** is a selected collection of variant records; in the food-service domain, a dish is a variant record and a menu is a portfolio.

## License

Source code and documentation are licensed under the [Apache License 2.0](LICENSE). See [NOTICE](NOTICE) for attribution. The original synthetic [food-service MVP fixture](examples/food-service/mvp-v1/README.md) is separately dedicated under CC0-1.0. External datasets and third-party materials remain subject to their own licenses.
