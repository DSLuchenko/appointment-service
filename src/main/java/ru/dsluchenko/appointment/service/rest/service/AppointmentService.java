package ru.dsluchenko.appointment.service.rest.service;

import ru.dsluchenko.appointment.service.model.Ticket;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    List<Ticket> getAvailableTicketsToDoctorByAppointmentDate(UUID doctorUuid, LocalDate appointmentDate);

    Ticket takeAvailableTicketToDoctorByPatient(Long ticketId, UUID patientUuid);

    List<Ticket> getAllTakenTicketsByPatient(UUID patientUuid);
}
