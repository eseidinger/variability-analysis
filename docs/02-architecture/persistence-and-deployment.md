# Persistence and Deployment Architecture

Status: documented prototype direction; schema and operational acceptance remain open.

The first prototype saves and loads configuration models through the Developer Platform PostgreSQL service. Relational tables, JSONB, or a combination must be selected from actual access patterns rather than assumed in advance.

The UI and API are packaged in one container image. The Developer Platform supplies database credentials, HTTP routing, and workload operation. Domain objects do not contain infrastructure configuration.

## Initial constraints

- One application replica.
- 0.5 CPU and 256 MiB memory limit.
- Bounded enumeration with understandable termination behavior.
- Persistence verified after workload recreation.
- Runtime and peak memory recorded for representative fixtures.

Model IDs, versioning, concurrent changes, stored analysis results, schema migration, backup, and recovery procedures remain open. Additional data stores require a defined workload and reproducible comparison.
