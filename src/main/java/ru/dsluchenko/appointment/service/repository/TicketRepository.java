package ru.dsluchenko.appointment.service.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dsluchenko.appointment.service.model.Doctor;
import ru.dsluchenko.appointment.service.model.Patient;
import ru.dsluchenko.appointment.service.model.Ticket;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByAppointmentDateAndDoctorAndPatientIsNullOrderByAppointmentTime(LocalDate appointmentDate,
                                                                                         Doctor doctor);
    @EntityGraph(attributePaths = "doctor")
    List<Ticket> findAllByPatientOrderByAppointmentDateAscAppointmentTimeAsc(Patient patient);
}
