package com.animalog.service;

import com.animalog.model.Vaccination;
import com.animalog.repository.VaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VaccinationService {
    
    @Autowired
    private VaccinationRepository vaccinationRepository;
    
    public Vaccination saveVaccination(Vaccination vaccination) {
        return vaccinationRepository.save(vaccination);
    }
    
    public List<Vaccination> getAllVaccinations() {
        return vaccinationRepository.findAll();
    }
    
    public Optional<Vaccination> getVaccinationById(Long id) {
        return vaccinationRepository.findById(id);
    }
    
    public List<Vaccination> getVaccinationsByPet(Long petId) {
        return vaccinationRepository.findByPetId(petId);
    }
    
    public void deleteVaccination(Long id) {
        vaccinationRepository.deleteById(id);
    }
    
    public Vaccination updateVaccination(Vaccination vaccination) {
        return vaccinationRepository.save(vaccination);
    }
}