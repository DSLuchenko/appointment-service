package ru.dsluchenko.appointment.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dsluchenko.appointment.service.model.Doctor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findAllByUuidIn(Collection<UUID> uuids);

    Optional<Doctor> findByUuid(UUID uuid);
}
