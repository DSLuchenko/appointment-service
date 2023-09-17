package ru.dsluchenko.appointment.service.repository;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.dsluchenko.appointment.service.model.Doctor;
import ru.dsluchenko.appointment.service.model.Patient;
import ru.dsluchenko.appointment.service.model.Ticket;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @EntityGraph(attributePaths = "doctor")
    PageImpl<Ticket> findAllByPatient(Patient patient, PageRequest pageRequest);

    List<Ticket> findAllByDoctorAndAppointmentDateBetweenAndPatientIsNull(Doctor doctor,
                                                                          @Param("startDate") LocalDate startDate,
                                                                          @Param("endDate") LocalDate endDate);

    List<Ticket> findAllByDoctorAndAppointmentDateBetween(Doctor doctor,
                                                          @Param("startDate") LocalDate startDate,
                                                          @Param("endDate") LocalDate endDate);
}
