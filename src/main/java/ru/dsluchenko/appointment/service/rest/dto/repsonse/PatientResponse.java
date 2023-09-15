package ru.dsluchenko.appointment.service.rest.dto.repsonse;

import java.time.LocalDate;
import java.util.UUID;

public record PatientResponse(UUID uuid,
                              String fullName,
                              LocalDate dateOfBirth){
}
