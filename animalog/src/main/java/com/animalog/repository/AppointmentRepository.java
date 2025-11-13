package com.animalog.repository;

import com.animalog.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPetId(Long petId);
    List<Appointment> findByStatus(Appointment.Status status);
    List<Appointment> findByVeterinarianName(String veterinarianName);
}