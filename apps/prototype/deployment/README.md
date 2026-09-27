# Prototype Deployment Assembly

This directory will assemble the independently built UI and API into the prototype deployment artifact.

The initial Developer Platform contract requires one container image. A typical build may compile the UI first, copy its static output into the runtime image, and run the API as the HTTP entry point. The exact mechanism depends on the selected frameworks.

Deployment configuration must:

- preserve the UI/API project boundary;
- run with the platform-required non-root identity;
- expose one unprivileged HTTP port;
- obtain PostgreSQL settings from the platform environment;
- provide application-specific readiness; and
- operate within the documented CPU and memory limits.
