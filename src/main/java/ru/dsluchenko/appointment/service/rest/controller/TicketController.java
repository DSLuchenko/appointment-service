package ru.dsluchenko.appointment.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsluchenko.appointment.service.mapper.TicketMapper;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.AvailableTicketResponse;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.TakenTicketResponse;
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

    @GetMapping("/available/byDoctor/{doctorUuid}")
    public ResponseEntity<Collection<AvailableTicketResponse>> getAvailableTicketsByDoctorAndDate(
            @PathVariable UUID doctorUuid,
            @RequestParam LocalDate appointmentDate) {
        return ResponseEntity.ok(ticketMapper.toAvailableTicketsResponse(
                appointmentService.getAvailableTicketsToDoctorByAppointmentDate(doctorUuid, appointmentDate)));
    }

    @GetMapping("/byPatient/{patientUuid}")
    public ResponseEntity<Collection<TakenTicketResponse>> getPatientTickets(
            @PathVariable("patientUuid") UUID patienUuid) {
        return ResponseEntity.ok(ticketMapper.toTakenTicketsResponse(
                appointmentService.getAllTakenTicketsByPatient(patienUuid)));
    }

    @PatchMapping("/{ticketId}")
    public ResponseEntity<TakenTicketResponse> takeAvailableTicketByPatient(
            @PathVariable("ticketId") Long ticketId,
            @RequestBody TakeTicketByPatientRequest takeTicketByPatientRequest) {
        return ResponseEntity.ok(ticketMapper.toTakenTicketResponse(
                appointmentService.takeAvailableTicketToDoctorByPatient(ticketId, takeTicketByPatientRequest.patientUuid())));
    }
}
