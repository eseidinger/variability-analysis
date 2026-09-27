# Prototype Application

The prototype is split into independent API and UI projects. Each project owns its source code, tests, dependencies, and build configuration.

## Structure

| Path | Responsibility |
|---|---|
| `api/` | Domain validation, analysis, persistence, and HTTP endpoints. |
| `ui/` | Model input, analysis controls, results, and error presentation. |
| `integration-tests/` | Black-box checks across the built UI, API, and PostgreSQL. |
| `deployment/` | Assembly of the independently built projects into the deployable image. |

## Boundaries

- The UI communicates with the API through a versioned HTTP contract.
- The UI does not implement separate constraint, variant, or counting semantics.
- The API does not depend on a specific UI framework.
- PostgreSQL access belongs to the API.
- Shared generated artifacts must come from a documented contract; source code is not copied between projects.
- The deployment layer may serve the built UI and API from one container image without merging their project structures.

Technology-specific manifests will be added after the initial language and UI decisions are accepted.
