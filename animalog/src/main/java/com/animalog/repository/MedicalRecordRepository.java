package com.animalog.repository;

import com.animalog.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPetId(Long petId);
    List<MedicalRecord> findByVeterinarianName(String veterinarianName);
}