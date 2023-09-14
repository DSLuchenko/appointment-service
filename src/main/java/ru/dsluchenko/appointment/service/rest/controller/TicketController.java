package ru.dsluchenko.appointment.service.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.AvailableTicketResponse;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.TakenTicketReponse;
import ru.dsluchenko.appointment.service.rest.dto.request.TakeTicketRequest;
import ru.dsluchenko.appointment.service.rest.service.AppointmentService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final AppointmentService appointmentService;

    @Autowired
    public TicketController(AppointmentService appointmentService) {

        this.appointmentService = appointmentService;
    }

    @GetMapping("/available/byDoctor/{doctorUuid}")
    public ResponseEntity<List<AvailableTicketResponse>> getAvailableTicketsByDoctorAndDate(
            @PathVariable UUID doctorUuid,
            @RequestParam LocalDate appointmentDate) {

        List<AvailableTicketResponse> availableTicketsToDoctor =
                appointmentService.getAvailableTicketsToDoctorByAppointmentDate(doctorUuid, appointmentDate);
        return ResponseEntity.ok(availableTicketsToDoctor);
    }

    @GetMapping("/byPatient/{patientUuid}")
    public ResponseEntity<List<TakenTicketReponse>> getPatientTickets(@PathVariable("patientUuid") UUID patienUuid) {
        List<TakenTicketReponse> patientTickets = appointmentService.getAllPatientTickets(patienUuid);
        return ResponseEntity.ok(patientTickets);
    }

    @PatchMapping("/{ticketId}")
    public ResponseEntity<TakenTicketReponse> takeAvailableTicketByPatient(@PathVariable("ticketId") Long ticketId,
                                                                           @RequestBody TakeTicketRequest takeTicketRequest) {

        TakenTicketReponse takenTicket =
                appointmentService.takeAvailableTicketByPatient(ticketId, takeTicketRequest.patientUuid());
        return ResponseEntity.ok(takenTicket);
    }

}
