package ru.dsluchenko.appointment.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tickets",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uc_tickets_appointment_date_appointment_time_doctor_id",
                        columnNames = {"appointment_date", "appointment_time", "doctor_id"})

        },
        indexes = {
                @Index(
                        name = "idx_tickets_patient_id",
                        columnList = "patient_id"),
                @Index(
                        name = "idx_tickets_appointment_date_doctor_id",
                        columnList = "appointment_date, doctor_id")
        }
)
public class Ticket extends AbstractBaseEntity {
    @Column(name = "appointment_date",
            nullable = false)
    private LocalDate appointmentDate;
    @Column(name = "appointment_time",
            nullable = false)
    private LocalTime appointmentTime;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Ticket(LocalDate appointmentDate, LocalTime appointmentTime, Doctor doctor) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.doctor = doctor;
    }
}
