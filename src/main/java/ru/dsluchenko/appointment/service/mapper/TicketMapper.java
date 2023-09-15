package ru.dsluchenko.appointment.service.mapper;

import org.mapstruct.Mapper;

import ru.dsluchenko.appointment.service.model.Ticket;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.AvailableTicketResponse;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.TakenTicketResponse;

import java.util.Collection;

@Mapper(uses = {
        DoctorMapper.class,
        PatientMapper.class})
public interface TicketMapper {

    AvailableTicketResponse toAvailableTicketResponse(Ticket ticket);

    Collection<AvailableTicketResponse> toAvailableTicketsResponse(Collection<Ticket> tickets);

    TakenTicketResponse toTakenTicketResponse(Ticket ticket);

    Collection<TakenTicketResponse> toTakenTicketsResponse(Collection<Ticket> tickets);
}
