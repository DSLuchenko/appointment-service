package ru.dsluchenko.appointment.service.mapper;

import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.springframework.data.domain.PageImpl;
import ru.dsluchenko.appointment.service.model.Ticket;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.TicketByDoctorResponse;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.GetTicketsByPatientResponse;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.TicketByPatientResponse;

import java.util.Collection;

@Mapper(uses = {
        DoctorMapper.class,
        PatientMapper.class})
public interface TicketMapper {

    TicketByDoctorResponse toTicketsByDoctorResponse(Ticket ticket);

    Collection<TicketByDoctorResponse> toTicketsByDoctorResponse(Collection<Ticket> tickets);

    TicketByPatientResponse toTicketsByPatientResponse(Ticket ticket);

    Collection<TicketByPatientResponse> toTicketsByPatientResponse(Collection<Ticket> tickets);

    @Mapping(target = "tickets", source = "content")
    @Mapping(target = "hasNext", expression = "java(pageOfTickets.hasNext())")
    @Mapping(target = "isLast", expression = "java(pageOfTickets.isLast())")
    GetTicketsByPatientResponse toGetTicketsByPatientResponse(PageImpl<Ticket> pageOfTickets);
}
