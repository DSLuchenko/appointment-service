package ru.dsluchenko.appointment.service.mapper;

import org.mapstruct.Mapper;
import ru.dsluchenko.appointment.service.model.Doctor;
import ru.dsluchenko.appointment.service.rest.dto.repsonse.DoctorResponse;

@Mapper
public interface DoctorMapper {
    DoctorResponse toDoctorResponse(Doctor doctor);
}
