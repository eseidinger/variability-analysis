# Use Cases

Status: proposed use cases derived from the project scope.

## UC-01 — Define and validate a model

An application developer defines features, finite value domains, global constraints, configurable elements, and presence conditions. The application reports structural, reference, and type errors before analysis.

## UC-02 — Analyze configurations and variants

A user calculates valid configurations, their resulting variant records and element usages, distinct structural variants, and explicit metrics such as configuration count, record count, and variant count.

## UC-03 — Save and reload a model

A user stores a model in PostgreSQL, reloads it, and receives analysis results with unchanged semantics.

## UC-04 — Explore an analysis view

A user reorders, hides, groups, filters, and expands analysis dimensions. A dimension may originate from a configuration feature, imported attribute, or documented derivation. View changes do not silently alter the underlying model, population, or counting semantics.

## UC-05 — Import observed variants

An application developer maps an external dataset to identified variant records, analysis dimensions, element usages, and provenance. Importing observations does not require inventing configuration features or presence conditions that were not present in the source.

## UC-06 — Analyze a change scenario

A user defines a change relative to a versioned baseline, such as an unavailable element or an added constraint. The application reports which candidate records, distinct variants, elements, and dimension groups are gained or lost and states the feasibility rule used by the scenario.

## UC-07 — Complete partial requirements

A user supplies a partial assignment or other supported requirements. The application returns compatible complete configurations and their resulting variants, without treating the partial request as a complete configuration.

## UC-08 — Optimize a portfolio

A user selects a candidate population, portfolio constraints, and one or more objectives. The application returns feasible portfolios and, when objectives compete, documented Pareto-optimal alternatives with explanations of coverage, element usage, cost, and other configured metrics.

## Primary demonstration — food service

The first planned real-world application imports recipes as variant records. Ingredients are elements; recipe ingredient occurrences are element usages; cuisine, diet, dish type, and season are analysis dimensions; and a menu is a portfolio of dish records. Ingredient availability drives impact scenarios, while dietary coverage, ingredient count, cost, and sustainability can later drive portfolio constraints and objectives.

Recipe records remain individually identifiable even when two recipes have the same structural element-usage signature. `recordCount` therefore measures candidate dishes, while `variantCount` measures distinct structural variants under the documented equality rule.

## Reference domains

The initial synthetic fixture remains the verification baseline. Food service is the primary real-world demonstration; software product lines, cloud architectures, configurable products, and service portfolios are later validation domains. External datasets require a documented license, field mapping, provenance, and distinction between observed values, derived classifications, and generative rules.
