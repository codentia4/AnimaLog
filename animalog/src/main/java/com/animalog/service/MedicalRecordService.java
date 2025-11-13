package com.animalog.service;

import com.animalog.model.MedicalRecord;
import com.animalog.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordService {
    
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    
    public MedicalRecord saveMedicalRecord(MedicalRecord record) {
        return medicalRecordRepository.save(record);
    }
    
    public List<MedicalRecord> getAllRecords() {
        return medicalRecordRepository.findAll();
    }
    
    public Optional<MedicalRecord> getRecordById(Long id) {
        return medicalRecordRepository.findById(id);
    }
    
    public List<MedicalRecord> getRecordsByPet(Long petId) {
        return medicalRecordRepository.findByPetId(petId);
    }
    
    public void deleteRecord(Long id) {
        medicalRecordRepository.deleteById(id);
    }
    
    public MedicalRecord updateRecord(MedicalRecord record) {
        return medicalRecordRepository.save(record);
    }
}