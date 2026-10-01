CREATE TABLE analysis_population (
    population_id VARCHAR(255) NOT NULL,
    population_version VARCHAR(255) NOT NULL,
    PRIMARY KEY (population_id, population_version)
);

CREATE TABLE population_artifact (
    population_id VARCHAR(255) NOT NULL,
    population_version VARCHAR(255) NOT NULL,
    artifact_role VARCHAR(255) NOT NULL,
    artifact_id VARCHAR(255) NOT NULL,
    artifact_version VARCHAR(255) NOT NULL,
    sha256 CHAR(64) NOT NULL,
    PRIMARY KEY (population_id, population_version, artifact_role),
    FOREIGN KEY (population_id, population_version)
        REFERENCES analysis_population (population_id, population_version)
        ON DELETE CASCADE
);

CREATE TABLE population_dimension (
    population_id VARCHAR(255) NOT NULL,
    population_version VARCHAR(255) NOT NULL,
    dimension_id VARCHAR(255) NOT NULL,
    cardinality VARCHAR(16) NOT NULL,
    origin VARCHAR(16) NOT NULL,
    PRIMARY KEY (population_id, population_version, dimension_id),
    FOREIGN KEY (population_id, population_version)
        REFERENCES analysis_population (population_id, population_version)
        ON DELETE CASCADE
);

CREATE TABLE population_record (
    population_id VARCHAR(255) NOT NULL,
    population_version VARCHAR(255) NOT NULL,
    record_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (population_id, population_version, record_id),
    FOREIGN KEY (population_id, population_version)
        REFERENCES analysis_population (population_id, population_version)
        ON DELETE CASCADE
);

CREATE TABLE record_element (
    population_id VARCHAR(255) NOT NULL,
    population_version VARCHAR(255) NOT NULL,
    record_id VARCHAR(255) NOT NULL,
    element_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (population_id, population_version, record_id, element_id),
    FOREIGN KEY (population_id, population_version, record_id)
        REFERENCES population_record (population_id, population_version, record_id)
        ON DELETE CASCADE
);

CREATE TABLE record_dimension (
    population_id VARCHAR(255) NOT NULL,
    population_version VARCHAR(255) NOT NULL,
    record_id VARCHAR(255) NOT NULL,
    dimension_id VARCHAR(255) NOT NULL,
    value_state VARCHAR(16) NOT NULL,
    PRIMARY KEY (population_id, population_version, record_id, dimension_id),
    FOREIGN KEY (population_id, population_version, record_id)
        REFERENCES population_record (population_id, population_version, record_id)
        ON DELETE CASCADE,
    FOREIGN KEY (population_id, population_version, dimension_id)
        REFERENCES population_dimension (population_id, population_version, dimension_id)
        ON DELETE CASCADE
);

CREATE TABLE record_dimension_value (
    population_id VARCHAR(255) NOT NULL,
    population_version VARCHAR(255) NOT NULL,
    record_id VARCHAR(255) NOT NULL,
    dimension_id VARCHAR(255) NOT NULL,
    dimension_value VARCHAR(255) NOT NULL,
    PRIMARY KEY (population_id, population_version, record_id, dimension_id, dimension_value),
    FOREIGN KEY (population_id, population_version, record_id, dimension_id)
        REFERENCES record_dimension (population_id, population_version, record_id, dimension_id)
        ON DELETE CASCADE
);

CREATE TABLE rejected_source_record (
    population_id VARCHAR(255) NOT NULL,
    population_version VARCHAR(255) NOT NULL,
    source_record_id VARCHAR(255) NOT NULL,
    reason VARCHAR(255) NOT NULL,
    detail TEXT NOT NULL,
    PRIMARY KEY (population_id, population_version, source_record_id),
    FOREIGN KEY (population_id, population_version)
        REFERENCES analysis_population (population_id, population_version)
        ON DELETE CASCADE
);

CREATE INDEX record_element_by_element
    ON record_element (population_id, population_version, element_id);
