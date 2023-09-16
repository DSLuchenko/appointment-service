package ru.dsluchenko.appointment.service.soap.service;

import ru.dsluchenko.appointment.service.soap.model.GeneralRule;
import ru.dsluchenko.appointment.service.soap.model.IndividualRule;

import java.util.List;


public interface DoctorScheduleService {

    void createGeneralScheduleForAllDoctors(List<GeneralRule> rules);

    void createIndividualScheduleForDoctors(List<IndividualRule> rules);
}
