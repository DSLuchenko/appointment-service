package ru.dsluchenko.appointment.service.soap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class GeneralRule {

    private final LocalDate appointmentDate;

    private final LocalTime scheduleStartTime;

    private final LocalTime durationOfTicket;
    private final int countTickets;
}
