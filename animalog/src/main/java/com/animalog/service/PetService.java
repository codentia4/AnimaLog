package com.animalog.service;

import com.animalog.model.Pet;
import com.animalog.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    
    @Autowired
    private PetRepository petRepository;
    
    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }
    
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
    
    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }
    
    public List<Pet> getPetsByOwner(String ownerName) {
        return petRepository.findByOwnerName(ownerName);
    }
    
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }
    
    public Pet updatePet(Pet pet) {
        return petRepository.save(pet);
    }
}