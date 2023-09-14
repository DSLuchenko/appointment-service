package ru.dsluchenko.appointment.service.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsluchenko.appointment.service.model.Doctor;
import ru.dsluchenko.appointment.service.model.Patient;
import ru.dsluchenko.appointment.service.model.Ticket;
import ru.dsluchenko.appointment.service.repository.DoctorRepository;
import ru.dsluchenko.appointment.service.repository.PatientRepository;
import ru.dsluchenko.appointment.service.repository.TicketRepository;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.AvailableTicketResponse;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.DoctorResponse;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.PatientReponse;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.TakenTicketReponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final PatientRepository patientRepository;
    private final TicketRepository ticketRepository;
    private final DoctorRepository doctorRepository;


    @Autowired
    public AppointmentServiceImpl(PatientRepository patientRepository,
                                  TicketRepository ticketRepository,
                                  DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.ticketRepository = ticketRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<AvailableTicketResponse> getAvailableTicketsToDoctorByAppointmentDate(UUID doctorUuid, LocalDate appointmentDate) {
        Doctor doctor = doctorRepository.findByUuid(doctorUuid).orElseThrow();
        List<Ticket> freeTickets =
                ticketRepository.findAllByAppointmentDateAndDoctorAndPatientIsNullOrderByAppointmentTime(
                        appointmentDate,
                        doctor);

        List<AvailableTicketResponse> availableTickets = freeTickets
                .stream()
                .map(t ->
                        new AvailableTicketResponse(t.getId(),
                                new DoctorResponse(
                                        t.getDoctor().getUuid(),
                                        t.getDoctor().getFullName()),
                                t.getAppointmentDate(),
                                t.getAppointmentTime()))
                .toList();

        return availableTickets;
    }

    @Override
    public TakenTicketReponse takeAvailableTicketByPatient(Long ticketId, UUID patientUuid) {
        Patient patient = patientRepository.findByUuid(patientUuid).orElseThrow();

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.setPatient(patient);
        ticket = ticketRepository.save(ticket);

        TakenTicketReponse takenTicket = new TakenTicketReponse(ticket.getId(),
                new DoctorResponse(
                        ticket.getDoctor().getUuid(),
                        ticket.getDoctor().getFullName()),
                new PatientReponse(
                        ticket.getPatient().getUuid(),
                        ticket.getPatient().getFullName(),
                        ticket.getPatient().getDateOfBirth()),
                ticket.getAppointmentDate(),
                ticket.getAppointmentTime());

        return takenTicket;
    }

    @Override
    public List<TakenTicketReponse> getAllPatientTickets(UUID patientUuid) {

        Patient patient = patientRepository.findByUuid(patientUuid).orElseThrow();
        List<TakenTicketReponse> patientTickets = ticketRepository
                .findAllByPatientOrderByAppointmentDateAscAppointmentTimeAsc(patient)
                .stream()
                .map(t ->
                        new TakenTicketReponse(
                                t.getId(),
                                new DoctorResponse(
                                        t.getDoctor().getUuid(),
                                        t.getDoctor().getFullName()),
                                new PatientReponse(
                                        t.getPatient().getUuid(),
                                        t.getPatient().getFullName(),
                                        t.getPatient().getDateOfBirth()),
                                t.getAppointmentDate(),
                                t.getAppointmentTime()))
                .toList();

        return patientTickets;
    }
}
