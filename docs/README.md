# Variability Engineering Framework Documentation

Updated on September 30, 2026 to incorporate the broader variability-engineering vision, the food-service demonstration use case, and the binding food-service analysis MVP semantics. This documentation covers the product, target architecture, architectural decisions, development sequence, and prototype operations.

## Getting started

| Area | Key question | Documents |
|---|---|---|
| Product | Who is this for, and what should it achieve? | [Vision](01-product/vision.md), [Use cases](01-product/use-cases.md), [Requirements](01-product/requirements.md), [Roadmap](01-product/roadmap.md) |
| Architecture | How should modeling, analysis, configuration, and optimization work? | [Overview](02-architecture/overview.md), [Domain model](02-architecture/domain-model.md), [Analysis view](02-architecture/analysis-view.md), [Food-service MVP semantics](02-architecture/food-service-mvp-semantics.md), [Persistence and deployment](02-architecture/persistence-and-deployment.md) |
| Decisions | Why was this approach selected? | [ADR index](03-decisions/README.md) |
| Development | What is built first, and how will it be verified? | [Development plan](04-development/development-plan.md), [Quality assurance](04-development/quality-assurance.md), [Open decisions](04-development/open-decisions.md) |
| Traceability | Which sources support the baseline, and how is it maintained? | [Sources and maintenance](sources.md) |
| Operations | How will the prototype be deployed and checked? | [Deployment](05-operations/deployment.md) |

## Status and evidence

The initial Angular/Quarkus UI/API integration and combined local container image are implemented. Domain modeling, persistence, analysis, and the broader framework capabilities remain planned. The prototype application is the first deliverable; reusable framework components follow after semantics and interfaces have been validated with synthetic fixtures and the food-service reference domain.

- **Requirement:** intended project scope, not implementation evidence.
- **Documented direction:** an objective recorded in the current project description.
- **Proposal:** a design requiring confirmation.
- **Open:** unresolved or insufficiently specified.
- **Accepted:** a binding design choice; acceptance does not by itself prove implementation.
- **Implemented:** reserved for behavior present in source.
- **Verified:** reserved for a recorded, reproducible check.

The repository evidences a UI/API status integration and local container assembly. It does not yet evidence the domain evaluation core, PostgreSQL round trips, the food-service application, optimization, or successful deployment to the Developer Platform.

## Maintenance

Use product documents for outcomes and scope, architecture documents for system design, ADRs for decisions and alternatives, development documents for sequencing and evidence, and operations documents for executable procedures. Update an ADR before changing an accepted architectural choice. Do not describe proposed or source-inspected behavior as operationally verified.
