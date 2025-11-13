package com.animalog.service;

import com.animalog.model.Appointment;
import com.animalog.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
    
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }
    
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }
    
    public List<Appointment> getAppointmentsByPet(Long petId) {
        return appointmentRepository.findByPetId(petId);
    }
    
    public List<Appointment> getAppointmentsByVet(String vetName) {
        return appointmentRepository.findByVeterinarianName(vetName);
    }
    
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
    
    public Appointment updateAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
}