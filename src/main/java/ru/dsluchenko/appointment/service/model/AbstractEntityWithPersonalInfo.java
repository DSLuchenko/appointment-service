package ru.dsluchenko.appointment.service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntityWithPersonalInfo extends AbstractBaseEntity {
    @Column(name = "uuid",
            unique = true,
            nullable = false)
    private UUID uuid;
    @Column(name = "full_name",
            nullable = false)
    private String fullName;
    @Column(name = "date_of_birth",
            nullable = false)
    private LocalDate dateOfBirth;

}
