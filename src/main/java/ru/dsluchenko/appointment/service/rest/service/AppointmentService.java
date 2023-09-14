package ru.dsluchenko.appointment.service.rest.service;

import ru.dsluchenko.appointment.service.rest.dto.repsonse.AvailableTicketResponse;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.TakenTicketReponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    List<AvailableTicketResponse> getAvailableTicketsToDoctorByAppointmentDate(UUID doctorUuid, LocalDate appointmentDate);

    TakenTicketReponse takeAvailableTicketByPatient(Long ticketId, UUID patientUuid);

    List<TakenTicketReponse> getAllPatientTickets(UUID patientUuid);
}
