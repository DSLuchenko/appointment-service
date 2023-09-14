package ru.dsluchenko.appointment.service.rest.dto.request;

import java.util.UUID;

public record TakeTicketRequest(UUID patientUuid) {
}
