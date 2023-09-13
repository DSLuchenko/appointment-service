package ru.dsluchenko.appointment.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "patients",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uc_patients_date_of_birth_fullname",
                        columnNames = {"date_of_birth", "full_name"})},
        indexes = {
                @Index(
                        name = "idx_patients_uuid",
                        columnList = "uuid")
        }
)

public class Patient extends AbstractEntityWithPersonalInfo {
}
