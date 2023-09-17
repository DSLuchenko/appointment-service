package ru.dsluchenko.appointment.service.rest.dto.repsonse;

import java.util.Collection;

public record GetTicketsByPatientResponse(long totalElements,
                                          int totalPages,
                                          boolean hasNext,
                                          boolean isLast,
                                          Collection<TicketByPatientResponse> tickets) {
}
