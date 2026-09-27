# Prototype Integration Tests

Black-box tests will verify the built UI and API against PostgreSQL and the reference fixtures.

Initial coverage should include:

- application and API readiness;
- model validation and error responses;
- reference configuration and variant counts;
- save and reload without semantic changes;
- persistence after workload recreation; and
- the packaged UI calling the packaged API.

Test tooling will be selected with the implementation stack.
