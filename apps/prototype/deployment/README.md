# Prototype Deployment Assembly

This directory assembles the independently built UI and API into one container image. Quarkus serves the compiled Angular files at `/` and the API at `/api` on the same port.

Build from the repository root so the Docker build can access both projects:

```shell
docker build --file apps/prototype/deployment/Dockerfile --tag variability-engineering-framework .
docker run --rm --publish 8080:8080 variability-engineering-framework
```

Open <http://localhost:8080>. The UI calls `/api/status` on the same origin; the Angular development proxy is not involved in the container.

The multi-stage build:

1. installs the locked UI dependencies and builds Angular;
2. copies the Angular browser output into Quarkus' `META-INF/resources` directory;
3. packages the Quarkus application; and
4. copies only the Quarkus runtime into the final non-root image.

Deployment configuration:

- preserve the UI/API project boundary;
- run with the platform-required non-root identity;
- expose HTTP port 8080;
- obtain PostgreSQL settings from the platform environment;
- provide application-specific readiness; and
- operate within the documented CPU and memory limits.
