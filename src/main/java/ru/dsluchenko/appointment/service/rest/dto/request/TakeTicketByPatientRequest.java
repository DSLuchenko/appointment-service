package ru.dsluchenko.appointment.service.rest.dto.request;

import java.util.UUID;

public record TakeTicketByPatientRequest(UUID patientUuid) {
}
