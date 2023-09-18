package ru.dsluchenko.appointment.service.soap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenerateScheduleResult {
    private final String status;
    private final int ticketCount;
    private final String note;

}
