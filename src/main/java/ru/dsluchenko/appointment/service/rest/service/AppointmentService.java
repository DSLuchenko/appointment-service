package ru.dsluchenko.appointment.service.rest.service;

import org.springframework.data.domain.PageImpl;
import ru.dsluchenko.appointment.service.model.Ticket;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    List<Ticket> getTicketsByDoctorWithFilter(UUID doctorUuid, LocalDate startDate, LocalDate endDate, boolean onlyAvailable);

    Ticket takeAvailableTicketToDoctorByPatient(Long ticketId, UUID patientUuid);

    PageImpl<Ticket> getTicketsByPatientWithPagination(UUID patientUuid, int page, int size);
}
