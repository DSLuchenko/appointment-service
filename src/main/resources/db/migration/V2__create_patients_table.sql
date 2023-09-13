CREATE SEQUENCE IF NOT EXISTS patients_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE patients
(
    id            BIGINT       NOT NULL,
    uuid          UUID         NOT NULL,
    full_name     VARCHAR(255) NOT NULL,
    date_of_birth date         NOT NULL,
    CONSTRAINT pk_patients PRIMARY KEY (id)
);

ALTER TABLE patients
    ADD CONSTRAINT uc_patients_date_of_birth_fullname UNIQUE (date_of_birth, full_name);

ALTER TABLE patients
    ADD CONSTRAINT uc_patients_uuid UNIQUE (uuid);

CREATE INDEX idx_patients_uuid ON patients (uuid);