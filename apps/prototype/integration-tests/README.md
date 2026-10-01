# Prototype Integration Tests

Black-box tests will verify the built UI and API against PostgreSQL and the reference fixtures.

Initial coverage should include:

- application and API readiness;
- model validation and error responses;
- reference configuration and variant counts;
- save and reload without semantic changes;
- persistence after workload recreation; and
- the packaged UI calling the packaged API.

Run the Compose-backed black-box verification from the repository root:

```shell
apps/prototype/integration-tests/verify-compose.sh
```

The script builds the combined image, starts PostgreSQL and the packaged application, verifies the rendered UI and fixture-backed API queries, restarts the application to verify persistence, reports the application cgroup peak-memory value when available, and tears down its temporary database volume. It requires Docker Compose and curl. By default it uses host port `18080`; set `APP_PORT` to choose another available port.
