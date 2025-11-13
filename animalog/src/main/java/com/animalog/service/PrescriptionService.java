package com.animalog.service;

import com.animalog.model.Prescription;
import com.animalog.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PrescriptionService {
    
    @Autowired
    private PrescriptionRepository prescriptionRepository;
    
    public Prescription savePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }
    
    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }
    
    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }
    
    public List<Prescription> getPrescriptionsByPet(Long petId) {
        return prescriptionRepository.findByPetId(petId);
    }
    
    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }
    
    public Prescription updatePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }
}