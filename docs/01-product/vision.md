# Product Vision

Status: documented direction; implementation has not started.

The Variability Analysis Project provides a domain-independent way to describe configuration spaces, evaluate constraints and presence conditions, calculate distinct variants, and explore the results.

Development starts with a small runnable prototype application. Reusable framework components for Java, Python, TypeScript, Angular, and React are created later, after the prototype establishes stable semantics and contracts.

## Target users

- Application developers reuse domain models, calculations, contracts, and visualization components.
- Domain users explore configurations, resulting element sets, and the effects of features.
- Architecture and lab work uses the project as a reproducible workload for algorithm, persistence, and integration comparisons.

## Product boundaries

The project concerns variability, configuration spaces, and sets of configuration-dependent elements. Statistical variance and ANOVA are outside its core function. SaaS tenancy, authentication, a plugin system, and a broad infrastructure catalog are not approved scope.
