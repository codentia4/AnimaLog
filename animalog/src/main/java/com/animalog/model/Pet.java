package com.animalog.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pets")
public class Pet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String name;
    
    @Column(nullable = false, length = 50)
    private String species;
    
    @Column(length = 100)
    private String breed;
    
    private LocalDate dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal weight;
    
    @Column(nullable = false, length = 100)
    private String ownerName;
    
    @Column(length = 15)
    private String ownerPhone;
    
    @Column(length = 50)
    private String microchipId;
    
    // NEW FIELD FOR IMAGE
    @Column(length = 500)
    private String imageUrl;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    public enum Gender {
        MALE, FEMALE, UNKNOWN
    }
    
    public enum Status {
        ACTIVE, INACTIVE
    }
    
    // Constructors
    public Pet() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSpecies() {
        return species;
    }
    
    public void setSpecies(String species) {
        this.species = species;
    }
    
    public String getBreed() {
        return breed;
    }
    
    public void setBreed(String breed) {
        this.breed = breed;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public BigDecimal getWeight() {
        return weight;
    }
    
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    
    public String getOwnerName() {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    
    public String getOwnerPhone() {
        return ownerPhone;
    }
    
    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }
    
    public String getMicrochipId() {
        return microchipId;
    }
    
    public void setMicrochipId(String microchipId) {
        this.microchipId = microchipId;
    }
    
    // NEW GETTER AND SETTER FOR IMAGE
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}