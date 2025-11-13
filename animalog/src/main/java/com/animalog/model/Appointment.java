package com.animalog.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long petId;
    
    @Column(nullable = false)
    private LocalDateTime appointmentDatetime;
    
    @Column(nullable = false, length = 200)
    private String purpose;
    
    @Column(nullable = false, length = 100)
    private String veterinarianName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.SCHEDULED;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    public enum Status {
        SCHEDULED, COMPLETED, CANCELLED
    }
    
    // Constructors
    public Appointment() {
    }
    
    public Appointment(Long id, Long petId, LocalDateTime appointmentDatetime, String purpose, String veterinarianName, Status status, String notes) {
        this.id = id;
        this.petId = petId;
        this.appointmentDatetime = appointmentDatetime;
        this.purpose = purpose;
        this.veterinarianName = veterinarianName;
        this.status = status;
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
    
    public LocalDateTime getAppointmentDatetime() {
        return appointmentDatetime;
    }
    
    public void setAppointmentDatetime(LocalDateTime appointmentDatetime) {
        this.appointmentDatetime = appointmentDatetime;
    }
    
    public String getPurpose() {
        return purpose;
    }
    
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    
    public String getVeterinarianName() {
        return veterinarianName;
    }
    
    public void setVeterinarianName(String veterinarianName) {
        this.veterinarianName = veterinarianName;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
}