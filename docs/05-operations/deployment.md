# Prototype Deployment

Status: local container build implemented; platform deployment remains to be verified.

The prototype will be deployed through the existing Developer Platform as one container image containing the UI and API. PostgreSQL is provided separately by the platform.

## Local image

Build and run the combined image from the repository root:

```shell
docker build --file apps/prototype/deployment/Dockerfile --tag variability-engineering-framework .
docker run --rm --publish 8080:8080 variability-engineering-framework
```

Quarkus serves the compiled Angular UI at <http://localhost:8080> and the API below `/api`. The image runs as a non-root user.

## Application contract

- Run as the non-root identity required by the Developer Platform.
- Listen on the configured unprivileged HTTP port.
- Obtain PostgreSQL connection parameters through the platform contract.
- Keep domain models independent of deployment configuration.
- Operate within one replica, 0.5 CPU, and 256 MiB memory.
- Bound enumeration and report rejected work clearly.

## Deployment acceptance

1. Build a revision-identifiable image.
2. Deploy it through the Developer Platform.
3. Verify the intended HTTP entry point and application-specific readiness.
4. Save and reload the reference model through PostgreSQL.
5. Recreate the workload and verify persisted data.
6. Run the reference analysis and compare expected results.
7. Record runtime, peak memory, revision, environment, and limitations.

Backup, restore, schema migration, rollback, and incident procedures must be added after their application behavior is implemented and tested. Platform-level monitoring or backup capabilities do not by themselves prove recovery of this application.
