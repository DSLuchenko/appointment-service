CREATE SEQUENCE IF NOT EXISTS doctors_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE doctors
(
    id            BIGINT       NOT NULL DEFAULT nextval('doctors_seq'),
    uuid          UUID         NOT NULL,
    full_name     VARCHAR(255) NOT NULL,
    date_of_birth date         NOT NULL,
    CONSTRAINT pk_doctors PRIMARY KEY (id)
);

ALTER TABLE doctors
    ADD CONSTRAINT uc_doctors_date_of_birth_fullname UNIQUE (date_of_birth, full_name);

ALTER TABLE doctors
    ADD CONSTRAINT uc_doctors_uuid UNIQUE (uuid);

CREATE INDEX idx_doctors_uuid ON doctors (uuid);

ALTER SEQUENCE doctors_seq OWNED BY doctors.id;