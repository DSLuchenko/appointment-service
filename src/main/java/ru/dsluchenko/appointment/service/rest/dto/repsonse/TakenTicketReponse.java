package ru.dsluchenko.appointment.service.rest.dto.repsonse;

import java.time.LocalDate;
import java.time.LocalTime;

public record TakenTicketReponse(Long id,
                                 DoctorResponse doctor,
                                 PatientReponse patient,
                                 LocalDate appointmentDate,
                                 LocalTime appointmentTime) {
}
