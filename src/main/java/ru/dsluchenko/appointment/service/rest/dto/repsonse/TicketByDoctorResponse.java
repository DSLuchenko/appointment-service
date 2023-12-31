package ru.dsluchenko.appointment.service.rest.dto.repsonse;

import java.time.LocalDate;
import java.time.LocalTime;

public record TicketByDoctorResponse(Long id,
                                     DoctorResponse doctor,
                                     LocalDate appointmentDate,
                                     LocalTime appointmentTime) {
}
