package com.animalog.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long petId;
    
    @Column(nullable = false)
    private LocalDate visitDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecordType recordType;
    
    @Column(nullable = false, length = 100)
    private String veterinarianName;
    
    @Column(columnDefinition = "TEXT")
    private String diagnosis;
    
    @Column(columnDefinition = "TEXT")
    private String treatment;
    
    @Column(length = 200)
    private String medication;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal cost;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    public enum RecordType {
        CHECKUP, VACCINATION, TREATMENT, EMERGENCY
    }
    
    // Constructors
    public MedicalRecord() {
    }
    
    public MedicalRecord(Long id, Long petId, LocalDate visitDate, RecordType recordType, String veterinarianName, String diagnosis, String treatment, String medication, BigDecimal cost, String notes) {
        this.id = id;
        this.petId = petId;
        this.visitDate = visitDate;
        this.recordType = recordType;
        this.veterinarianName = veterinarianName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.medication = medication;
        this.cost = cost;
        this.notes = notes;
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
    
    public LocalDate getVisitDate() {
        return visitDate;
    }
    
    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }
    
    public RecordType getRecordType() {
        return recordType;
    }
    
    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }
    
    public String getVeterinarianName() {
        return veterinarianName;
    }
    
    public void setVeterinarianName(String veterinarianName) {
        this.veterinarianName = veterinarianName;
    }
    
    public String getDiagnosis() {
        return diagnosis;
    }
    
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    public String getTreatment() {
        return treatment;
    }
    
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
    
    public String getMedication() {
        return medication;
    }
    
    public void setMedication(String medication) {
        this.medication = medication;
    }
    
    public BigDecimal getCost() {
        return cost;
    }
    
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}