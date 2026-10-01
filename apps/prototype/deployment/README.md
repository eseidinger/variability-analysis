# Prototype Deployment Assembly

This directory assembles the independently built UI and API into one container image. Quarkus serves the compiled Angular files at `/` and the API at `/api` on the same port.

Build from the repository root so the Docker build can access both projects:

```shell
docker build --file apps/prototype/deployment/Dockerfile --tag variability-engineering-framework .
docker run --rm --publish 8080:8080 variability-engineering-framework
```

Open <http://localhost:8080>. The UI calls the food-service API on the same origin; the Angular development proxy is not involved in the container.

For local development, use the repository-root [Compose environment](../../../compose.yaml):

```shell
# Start only PostgreSQL for API and UI hot-reload development.
docker compose up -d db

# Start PostgreSQL and build/run the packaged UI + API at http://localhost:8080.
docker compose --profile app up --build

# Remove the development database volume when a clean seed is needed.
docker compose down -v
```

The `db` service exposes PostgreSQL on `localhost:5432` with development-only credentials (`variability`/`variability`). The `app` profile waits for its health check and receives its datasource settings through Compose.

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
- include the immutable food-service fixture for first-start database seeding;
- provide application-specific readiness; and
- operate within the documented CPU and memory limits.
