package ru.dsluchenko.appointment.service.soap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.dsluchenko.appointment.service.soap.domain.*;
import ru.dsluchenko.appointment.service.soap.model.GeneralRule;
import ru.dsluchenko.appointment.service.soap.model.IndividualRule;
import ru.dsluchenko.appointment.service.soap.service.DoctorScheduleService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Endpoint
public class DoctorScheduleEndpoint {
    private static final String NAMESPACE_URI =
            "http://dsluchenko.ru/appointment/service/soap/domain";
    private final DoctorScheduleService doctorScheduleService;

    @Autowired
    public DoctorScheduleEndpoint(DoctorScheduleService doctorScheduleService) {
        this.doctorScheduleService = doctorScheduleService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI,
            localPart = "generateGeneralScheduleRequest")
    @ResponsePayload
    public GenerateScheduleResultResponse createGeneralScheduleForAllDoctors(
            @RequestPayload GenerateGeneralScheduleRequest request) {

        List<GeneralRule> rules = request.getGeneralRule()
                .stream()
                .map(this::mapToGeneralRule)
                .toList();

        doctorScheduleService.createGeneralScheduleForAllDoctors(rules);

        GenerateScheduleResultResponse response = new GenerateScheduleResultResponse();
        response.setStatus("OK");

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI,
            localPart = "generateIndividualScheduleRequest")
    @ResponsePayload
    public GenerateScheduleResultResponse createIndividualScheduleForDoctors(
            @RequestPayload GenerateIndividualScheduleRequest request) {

        List<IndividualRule> individualRules = request.getIndividualRule()
                .stream()
                .map(this::mapToIndividualRule)
                .toList();

        doctorScheduleService.createIndividualScheduleForDoctors(individualRules);

        GenerateScheduleResultResponse response = new GenerateScheduleResultResponse();
        response.setStatus("OK");

        return response;
    }

    private GeneralRule mapToGeneralRule(RuleForGeneralSchedule ruleForGeneralSchedule) {
        return new GeneralRule(
                LocalDate.of(
                        ruleForGeneralSchedule.getAppointmentDate().getYear(),
                        ruleForGeneralSchedule.getAppointmentDate().getMonth(),
                        ruleForGeneralSchedule.getAppointmentDate().getDay()),
                LocalTime.of(
                        ruleForGeneralSchedule.getScheduleStartTime().getHour(),
                        ruleForGeneralSchedule.getScheduleStartTime().getMinute()),
                LocalTime.of(
                        ruleForGeneralSchedule.getDurationOfTicket().getHour(),
                        ruleForGeneralSchedule.getDurationOfTicket().getMinute()),
                ruleForGeneralSchedule.getCountTickets());
    }

    private IndividualRule mapToIndividualRule(RuleForIndividualSchedule ruleForIndividualSchedule) {
        IndividualRule individualRule =
                new IndividualRule(UUID.fromString(ruleForIndividualSchedule.getDoctorUuid()));
        individualRule
                .getRules()
                .addAll(ruleForIndividualSchedule
                        .getGeneralRule()
                        .stream()
                        .map(this::mapToGeneralRule)
                        .toList());
        return individualRule;
    }
}
