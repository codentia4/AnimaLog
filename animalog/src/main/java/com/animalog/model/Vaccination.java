package com.animalog.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vaccinations")
public class Vaccination {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long petId;
    
    @Column(nullable = false, length = 100)
    private String vaccineName;
    
    @Column(nullable = false)
    private LocalDate vaccinationDate;
    
    @Column(nullable = false)
    private LocalDate nextDueDate;
    
    @Column(nullable = false, length = 100)
    private String veterinarianName;
    
    @Column(length = 50)
    private String batchNumber;
    
    // Constructors
    public Vaccination() {
    }
    
    public Vaccination(Long id, Long petId, String vaccineName, LocalDate vaccinationDate, LocalDate nextDueDate, String veterinarianName, String batchNumber) {
        this.id = id;
        this.petId = petId;
        this.vaccineName = vaccineName;
        this.vaccinationDate = vaccinationDate;
        this.nextDueDate = nextDueDate;
        this.veterinarianName = veterinarianName;
        this.batchNumber = batchNumber;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getPetId() {
        return petId;
    }
    
    public void setPetId(Long petId) {
        this.petId = petId;
    }
    
    public String getVaccineName() {
        return vaccineName;
    }
    
    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }
    
    public LocalDate getVaccinationDate() {
        return vaccinationDate;
    }
    
    public void setVaccinationDate(LocalDate vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }
    
    public LocalDate getNextDueDate() {
        return nextDueDate;
    }
    
    public void setNextDueDate(LocalDate nextDueDate) {
        this.nextDueDate = nextDueDate;
    }
    
    public String getVeterinarianName() {
        return veterinarianName;
    }
    
    public void setVeterinarianName(String veterinarianName) {
        this.veterinarianName = veterinarianName;
    }
    
    public String getBatchNumber() {
        return batchNumber;
    }
    
    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
}