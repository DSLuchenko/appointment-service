CREATE SEQUENCE IF NOT EXISTS tickets_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE tickets
(
    id               BIGINT NOT NULL DEFAULT nextval('tickets_seq'),
    appointment_date date   NOT NULL,
    appointment_time time WITHOUT TIME ZONE NOT NULL,
    doctor_id        BIGINT NOT NULL,
    patient_id       BIGINT,
    CONSTRAINT pk_tickets PRIMARY KEY (id)
);

ALTER TABLE tickets
    ADD CONSTRAINT uc_tickets_appointment_date_appointment_time_doctor_id UNIQUE (appointment_date, appointment_time, doctor_id);

CREATE INDEX idx_tickets_appointment_date_doctor_id ON tickets (appointment_date, doctor_id);

ALTER TABLE tickets
    ADD CONSTRAINT FK_TICKETS_ON_DOCTOR FOREIGN KEY (doctor_id) REFERENCES doctors (id);

ALTER TABLE tickets
    ADD CONSTRAINT FK_TICKETS_ON_PATIENT FOREIGN KEY (patient_id) REFERENCES patients (id);

CREATE INDEX idx_tickets_patient_id ON tickets (patient_id);

ALTER SEQUENCE tickets_seq OWNED BY tickets.id;