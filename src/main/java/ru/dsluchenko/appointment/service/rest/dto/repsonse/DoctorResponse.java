package ru.dsluchenko.appointment.service.rest.dto.repsonse;

import java.util.UUID;

public record DoctorResponse(UUID uuid, String fullName) {

}
