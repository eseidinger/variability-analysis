# Variability Analysis Project Documentation

Reviewed against the project baseline on September 27, 2026. This documentation covers the product, target architecture, architectural decisions, development sequence, and planned operations.

## Getting started

| Area | Key question | Documents |
|---|---|---|
| Product | Who is this for, and what should it achieve? | [Vision](01-product/vision.md), [Use cases](01-product/use-cases.md), [Requirements](01-product/requirements.md), [Roadmap](01-product/roadmap.md) |
| Architecture | How should configuration and variant analysis work? | [Overview](02-architecture/overview.md), [Domain model](02-architecture/domain-model.md), [Analysis view](02-architecture/analysis-view.md), [Persistence and deployment](02-architecture/persistence-and-deployment.md) |
| Decisions | Why was this approach selected? | [ADR index](03-decisions/README.md) |
| Development | What is built first, and how will it be verified? | [Development plan](04-development/development-plan.md), [Quality assurance](04-development/quality-assurance.md), [Open decisions](04-development/open-decisions.md) |
| Traceability | Which sources support the baseline, and how is it maintained? | [Sources and maintenance](sources.md) |
| Operations | How will the prototype be deployed and checked? | [Deployment](05-operations/deployment.md) |

## Status and evidence

The project is in prototype planning. The prototype application is the first deliverable; reusable framework components follow after the domain semantics and interfaces have stabilized.

- **Requirement:** intended project scope, not implementation evidence.
- **Documented direction:** an objective recorded in the current project description.
- **Proposal:** a design requiring confirmation.
- **Open:** unresolved or insufficiently specified.
- **Implemented:** reserved for behavior present in source.
- **Verified:** reserved for a recorded, reproducible check.

No application implementation or successful deployment is currently evidenced in this repository.

## Maintenance

Use product documents for outcomes and scope, architecture documents for system design, ADRs for decisions and alternatives, development documents for sequencing and evidence, and operations documents for executable procedures. Update an ADR before changing an accepted architectural choice. Do not describe proposed or source-inspected behavior as operationally verified.
