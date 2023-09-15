package ru.dsluchenko.appointment.service.mapper;

import org.mapstruct.Mapper;
import ru.dsluchenko.appointment.service.model.Patient;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.PatientResponse;

@Mapper
public interface PatientMapper {
    PatientResponse toPatientResponse(Patient patient);
}
