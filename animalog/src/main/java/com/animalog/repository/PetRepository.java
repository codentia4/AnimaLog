package com.animalog.repository;

import com.animalog.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwnerName(String ownerName);
    List<Pet> findByStatus(Pet.Status status);
}