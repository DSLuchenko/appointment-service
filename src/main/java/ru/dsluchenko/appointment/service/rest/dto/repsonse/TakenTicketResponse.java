package ru.dsluchenko.appointment.service.rest.dto.repsonse;

import java.time.LocalDate;
import java.time.LocalTime;

public record TakenTicketResponse(Long id,
                                  DoctorResponse doctor,
                                  PatientResponse patient,
                                  LocalDate appointmentDate,
                                  LocalTime appointmentTime) {
}
