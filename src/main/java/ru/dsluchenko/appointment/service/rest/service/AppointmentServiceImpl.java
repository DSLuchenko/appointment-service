package ru.dsluchenko.appointment.service.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.dsluchenko.appointment.service.exception.ResourceNotFoundException;
import ru.dsluchenko.appointment.service.model.Doctor;
import ru.dsluchenko.appointment.service.model.Patient;
import ru.dsluchenko.appointment.service.model.Ticket;
import ru.dsluchenko.appointment.service.repository.DoctorRepository;
import ru.dsluchenko.appointment.service.repository.PatientRepository;
import ru.dsluchenko.appointment.service.repository.TicketRepository;


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
    public List<Ticket> getTicketsByDoctorWithFilter(UUID doctorUuid, LocalDate startDate, LocalDate endDate, boolean onlyAvailable) {
        Doctor doctor = doctorRepository
                .findByUuid(doctorUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        List<Ticket> availableTickets =
                onlyAvailable
                        ? ticketRepository.findAllByDoctorAndAppointmentDateBetweenAndPatientIsNull(doctor, startDate, endDate)
                        : ticketRepository.findAllByDoctorAndAppointmentDateBetween(doctor, startDate, endDate);


        return availableTickets;
    }

    @Override
    public Ticket takeAvailableTicketToDoctorByPatient(Long ticketId, UUID patientUuid) {
        Ticket ticket = ticketRepository
                .findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        Patient patient = patientRepository
                .findByUuid(patientUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        ticket.setPatient(patient);
        ticket = ticketRepository.save(ticket);

        return ticket;
    }

    @Override
    public PageImpl<Ticket> getTicketsByPatientWithPagination(UUID patientUuid, int page, int size) {
        Patient patient = patientRepository
                .findByUuid(patientUuid)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        return ticketRepository.findAllByPatient(patient, PageRequest.of(page, size));
    }
}
