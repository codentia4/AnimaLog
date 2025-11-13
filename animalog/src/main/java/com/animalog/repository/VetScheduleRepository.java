package com.animalog.repository;

import com.animalog.model.VetSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VetScheduleRepository extends JpaRepository<VetSchedule, Long> {
    List<VetSchedule> findByVetUserId(Long vetUserId);
    List<VetSchedule> findByVetNameAndIsActive(String vetName, Boolean isActive);
    List<VetSchedule> findByIsActive(Boolean isActive);
}