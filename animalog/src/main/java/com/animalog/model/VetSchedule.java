package com.animalog.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "vet_schedules")
public class VetSchedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long vetUserId;
    
    @Column(nullable = false, length = 100)
    private String vetName;
    
    @Column(nullable = false, length = 20)
    private String dayOfWeek; // MONDAY, TUESDAY, etc.
    
    @Column(nullable = false)
    private LocalTime startTime;
    
    @Column(nullable = false)
    private LocalTime endTime;
    
    @Column(nullable = false)
    private Integer maxAppointmentsPerSlot = 4;
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    // Constructors
    public VetSchedule() {
    }
    
    public VetSchedule(Long id, Long vetUserId, String vetName, String dayOfWeek, LocalTime startTime, LocalTime endTime, Integer maxAppointmentsPerSlot, Boolean isActive) {
        this.id = id;
        this.vetUserId = vetUserId;
        this.vetName = vetName;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxAppointmentsPerSlot = maxAppointmentsPerSlot;
        this.isActive = isActive;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getVetUserId() {
        return vetUserId;
    }
    
    public void setVetUserId(Long vetUserId) {
        this.vetUserId = vetUserId;
    }
    
    public String getVetName() {
        return vetName;
    }
    
    public void setVetName(String vetName) {
        this.vetName = vetName;
    }
    
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    public Integer getMaxAppointmentsPerSlot() {
        return maxAppointmentsPerSlot;
    }
    
    public void setMaxAppointmentsPerSlot(Integer maxAppointmentsPerSlot) {
        this.maxAppointmentsPerSlot = maxAppointmentsPerSlot;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}