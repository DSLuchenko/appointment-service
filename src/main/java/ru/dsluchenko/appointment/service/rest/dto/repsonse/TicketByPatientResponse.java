package ru.dsluchenko.appointment.service.rest.dto.repsonse;

import java.time.LocalDate;
import java.time.LocalTime;

public record TicketByPatientResponse(Long id,
                                      DoctorResponse doctor,
                                      PatientResponse patient,
                                      LocalDate appointmentDate,
                                      LocalTime appointmentTime) {
}
