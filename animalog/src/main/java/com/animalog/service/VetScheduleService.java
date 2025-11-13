package com.animalog.service;

import com.animalog.model.VetSchedule;
import com.animalog.repository.VetScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VetScheduleService {
    
    @Autowired
    private VetScheduleRepository scheduleRepository;
    
    public VetSchedule saveSchedule(VetSchedule schedule) {
        return scheduleRepository.save(schedule);
    }
    
    public List<VetSchedule> getSchedulesByVetUserId(Long vetUserId) {
        return scheduleRepository.findByVetUserId(vetUserId);
    }
    
    public List<VetSchedule> getSchedulesByVetName(String vetName) {
        return scheduleRepository.findByVetNameAndIsActive(vetName, true);
    }
    
    public List<VetSchedule> getAllActiveSchedules() {
        return scheduleRepository.findByIsActive(true);
    }
    
    public Optional<VetSchedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }
    
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}