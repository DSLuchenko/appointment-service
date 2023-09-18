package ru.dsluchenko.appointment.service.soap.service;

import ru.dsluchenko.appointment.service.soap.model.GeneralRule;
import ru.dsluchenko.appointment.service.soap.model.GenerateScheduleResult;
import ru.dsluchenko.appointment.service.soap.model.IndividualRule;

import java.util.List;


public interface DoctorScheduleService {

    GenerateScheduleResult createGeneralScheduleForAllDoctors(List<GeneralRule> rules);

    GenerateScheduleResult createIndividualScheduleForDoctors(List<IndividualRule> rules);
}
