package ru.dsluchenko.appointment.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "doctors",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uc_doctors_date_of_birth_fullname",
                        columnNames = {"date_of_birth", "full_name"})},
        indexes = {
                @Index(
                        name = "idx_doctors_uuid",
                        columnList = "uuid")
        }
)

public class Doctor extends AbstractEntityWithPersonalInfo {
    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "doctor_id")
    private List<Ticket> tickets = new ArrayList<>();
}
