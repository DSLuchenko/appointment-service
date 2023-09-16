package ru.dsluchenko.appointment.service.soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dsluchenko.appointment.service.model.Doctor;
import ru.dsluchenko.appointment.service.model.Ticket;
import ru.dsluchenko.appointment.service.repository.DoctorRepository;
import ru.dsluchenko.appointment.service.repository.TicketRepository;
import ru.dsluchenko.appointment.service.soap.model.GeneralRule;
import ru.dsluchenko.appointment.service.soap.model.IndividualRule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {
    private final DoctorRepository doctorRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public DoctorScheduleServiceImpl(DoctorRepository doctorRepository,
                                     TicketRepository ticketRepository) {
        this.doctorRepository = doctorRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void createGeneralScheduleForAllDoctors(List<GeneralRule> generalRules) {
        List<Doctor> doctors = doctorRepository.findAll();
        List<Ticket> tickets = new ArrayList<>();
        generalRules.forEach(rule ->
                doctors.forEach(doctor ->
                        tickets.addAll(
                                generateTicketsForDoctor(
                                        rule.getAppointmentDate(),
                                        rule.getScheduleStartTime(),
                                        rule.getDurationOfTicket(),
                                        rule.getCountTickets(),
                                        doctor
                                )
                        )));
        ticketRepository.saveAll(tickets);
    }


    @Override
    public void createIndividualScheduleForDoctors(List<IndividualRule> individualRules) {
        List<UUID> uuids = individualRules
                .stream()
                .map(IndividualRule::getDoctorUuid)
                .toList();

        Map<UUID, Doctor> doctors =
                doctorRepository.findAllByUuidIn(uuids)
                        .stream()
                        .collect(Collectors.toMap(Doctor::getUuid, doctor -> doctor));

        List<Ticket> tickets = new ArrayList<>();

        individualRules.forEach(individualRule ->
                individualRule.getRules()
                        .forEach(generalRule ->
                                tickets.addAll(
                                        generateTicketsForDoctor(
                                                generalRule.getAppointmentDate(),
                                                generalRule.getScheduleStartTime(),
                                                generalRule.getDurationOfTicket(),
                                                generalRule.getCountTickets(),
                                                doctors.get(individualRule.getDoctorUuid()))))
        );
        ticketRepository.saveAll(tickets);
    }

    private List<Ticket> generateTicketsForDoctor(LocalDate appointmentDate,
                                                  LocalTime scheduleStartTime,
                                                  LocalTime durationOfTicket,
                                                  int countTickets,
                                                  Doctor doctor) {
        LocalTime appointmentTime = scheduleStartTime;
        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < countTickets; i++) {
            tickets.add(new Ticket(appointmentDate, appointmentTime, doctor));
            appointmentTime = appointmentTime
                    .plusHours(durationOfTicket.getHour())
                    .plusMinutes(durationOfTicket.getMinute());
        }

        return tickets;
    }
}
