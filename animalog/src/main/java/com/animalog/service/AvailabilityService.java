package com.animalog.service;

import com.animalog.model.Appointment;
import com.animalog.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AvailabilityService {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    public boolean isVetAvailable(String vetName, LocalDateTime requestedDateTime) {
        // For now, simple validation - you can enhance this later
        if (vetName == null || vetName.isEmpty() || requestedDateTime == null) {
            return false;
        }
        
        // Check if requested time is in the future
        if (requestedDateTime.isBefore(LocalDateTime.now())) {
            return false;
        }
        
        // Check if vet has too many appointments at the same time
        List<Appointment> existingAppointments = appointmentRepository.findByVeterinarianName(vetName);
        
        long appointmentsAtSameTime = existingAppointments.stream()
            .filter(apt -> isSameHour(apt.getAppointmentDatetime(), requestedDateTime))
            .filter(apt -> apt.getStatus() == Appointment.Status.SCHEDULED)
            .count();
        
        // Allow maximum 4 appointments per hour
        return appointmentsAtSameTime < 4;
    }
    
    private boolean isSameHour(LocalDateTime dt1, LocalDateTime dt2) {
        if (dt1 == null || dt2 == null) {
            return false;
        }
        return dt1.getYear() == dt2.getYear() &&
               dt1.getMonth() == dt2.getMonth() &&
               dt1.getDayOfMonth() == dt2.getDayOfMonth() &&
               dt1.getHour() == dt2.getHour();
    }
}