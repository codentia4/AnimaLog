package com.animalog.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prescriptions")
public class Prescription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long petId;
    
    @Column(nullable = false, length = 100)
    private String medicationName;
    
    @Column(nullable = false, length = 100)
    private String dosage;
    
    @Column(nullable = false, length = 100)
    private String frequency;
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    @Column(nullable = false)
    private LocalDate endDate;
    
    @Column(nullable = false, length = 100)
    private String prescribedBy;
    
    @Column(columnDefinition = "TEXT")
    private String instructions;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;
    
    public enum Status {
        ACTIVE, COMPLETED, DISCONTINUED
    }
    
    // Constructors
    public Prescription() {
    }
    
    public Prescription(Long id, Long petId, String medicationName, String dosage, String frequency, LocalDate startDate, LocalDate endDate, String prescribedBy, String instructions, Status status) {
        this.id = id;
        this.petId = petId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.prescribedBy = prescribedBy;
        this.instructions = instructions;
        this.status = status;
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
    
    public String getMedicationName() {
        return medicationName;
    }
    
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }
    
    public String getDosage() {
        return dosage;
    }
    
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    
    public String getFrequency() {
        return frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public String getPrescribedBy() {
        return prescribedBy;
    }
    
    public void setPrescribedBy(String prescribedBy) {
        this.prescribedBy = prescribedBy;
    }
    
    public String getInstructions() {
        return instructions;
    }
    
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
}