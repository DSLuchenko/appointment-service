package ru.dsluchenko.appointment.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsluchenko.appointment.service.mapper.TicketMapper;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.GetTicketsByPatientResponse;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.TicketByDoctorResponse;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.TicketByPatientResponse;
import ru.dsluchenko.appointment.service.rest.dto.request.TakeTicketByPatientRequest;
import ru.dsluchenko.appointment.service.rest.service.AppointmentService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;


@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final AppointmentService appointmentService;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketController(AppointmentService appointmentService,
                            TicketMapper ticketMapper) {
        this.appointmentService = appointmentService;
        this.ticketMapper = ticketMapper;
    }

    @GetMapping(
            value = "/byDoctor/{doctorUuid}",
            produces = "application/json")
    public ResponseEntity<Collection<TicketByDoctorResponse>> getTicketsToDoctorWithFilter(
            @PathVariable UUID doctorUuid,
            @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate startDate,
            @RequestParam(required = false, defaultValue = "7") long period,
            @RequestParam(required = false, defaultValue = "true") boolean onlyAvailable) {

        return ResponseEntity.ok(ticketMapper.toTicketsByDoctorResponse(
                appointmentService.getTicketsByDoctorWithFilter(
                        doctorUuid,
                        startDate,
                        startDate.plusDays(period),
                        onlyAvailable)));
    }

    @GetMapping(
            value = "/byPatient/{patientUuid}",
            produces = "application/json")
    public ResponseEntity<GetTicketsByPatientResponse> getPatientTicketsWithPagination(
            @PathVariable("patientUuid") UUID patientUuid,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        return ResponseEntity.ok(ticketMapper.toGetTicketsByPatientResponse(
                appointmentService.getTicketsByPatientWithPagination(patientUuid, page, size)));
    }

    @PatchMapping(
            value = "/{ticketId}",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<TicketByPatientResponse> takeAvailableTicketByPatient(
            @PathVariable("ticketId") Long ticketId,
            @RequestBody TakeTicketByPatientRequest takeTicketByPatientRequest) {

        return ResponseEntity.ok(ticketMapper.toTicketsByPatientResponse(
                appointmentService.takeAvailableTicketToDoctorByPatient(ticketId, takeTicketByPatientRequest.patientUuid())));
    }
}
