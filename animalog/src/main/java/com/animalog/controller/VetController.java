package com.animalog.controller;

import com.animalog.model.*;
import com.animalog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vet")
public class VetController {
    
    @Autowired
    private PetService petService;
    
    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private MedicalRecordService medicalRecordService;
    
    @Autowired
    private VaccinationService vaccinationService;
    
    @Autowired
    private PrescriptionService prescriptionService;
    
    @Autowired
    private UserService userService;
    
 // Add at top with other @Autowired
    @Autowired
    private VetScheduleService scheduleService;

    // Add these new methods for schedule management

    @GetMapping("/schedule")
    public String schedule(Authentication auth, Model model) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            model.addAttribute("schedules", scheduleService.getSchedulesByVetUserId(user.getUserId()));
            model.addAttribute("user", user);
        }
        
        return "vet/schedule";
    }

    @GetMapping("/schedule/add")
    public String showAddScheduleForm(Model model) {
        model.addAttribute("schedule", new VetSchedule());
        return "vet/add-schedule";
    }

    @PostMapping("/schedule/add")
    public String addSchedule(@ModelAttribute VetSchedule schedule, Authentication auth) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            schedule.setVetUserId(user.getUserId());
            schedule.setVetName(user.getFullName());
            scheduleService.saveSchedule(schedule);
        }
        
        return "redirect:/vet/schedule";
    }

    @GetMapping("/schedule/delete/{id}")
    public String deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return "redirect:/vet/schedule";
    }
    
    // ============ DASHBOARD ============
    
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("appointments", appointmentService.getAppointmentsByVet(user.getFullName()));
            model.addAttribute("totalPets", petService.getAllPets().size());
        }
        
        return "vet/dashboard";
    }
    
    // ============ APPOINTMENT MANAGEMENT ============
    
    @GetMapping("/appointments")
    public String appointments(Authentication auth, Model model) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            model.addAttribute("appointments", appointmentService.getAppointmentsByVet(user.getFullName()));
        }
        
        return "vet/appointments";
    }
    
    @GetMapping("/appointments/complete/{id}")
    public String completeAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id).orElse(null);
        
        if (appointment != null) {
            appointment.setStatus(Appointment.Status.COMPLETED);
            appointmentService.updateAppointment(appointment);
        }
        
        return "redirect:/vet/appointments";
    }
    
    // ============ MEDICAL RECORD MANAGEMENT ============
    
    @GetMapping("/medical-records")
    public String medicalRecords(Model model) {
        model.addAttribute("records", medicalRecordService.getAllRecords());
        model.addAttribute("pets", petService.getAllPets());
        return "vet/medical-records";
    }
    
    @GetMapping("/medical-records/add")
    public String showAddRecordForm(Model model) {
        model.addAttribute("record", new MedicalRecord());
        model.addAttribute("pets", petService.getAllPets());
        return "vet/add-medical-record";
    }
    
    @PostMapping("/medical-records/add")
    public String addMedicalRecord(@ModelAttribute MedicalRecord record, Authentication auth) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            record.setVeterinarianName(user.getFullName());
            medicalRecordService.saveMedicalRecord(record);
        }
        
        return "redirect:/vet/medical-records";
    }
    
    @GetMapping("/medical-records/edit/{id}")
    public String showEditRecordForm(@PathVariable Long id, Model model) {
        MedicalRecord record = medicalRecordService.getRecordById(id).orElse(null);
        model.addAttribute("record", record);
        model.addAttribute("pets", petService.getAllPets());
        return "vet/edit-medical-record";
    }
    
    @PostMapping("/medical-records/edit/{id}")
    public String updateMedicalRecord(@PathVariable Long id, @ModelAttribute MedicalRecord record, Authentication auth) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            record.setId(id);
            record.setVeterinarianName(user.getFullName());
            medicalRecordService.updateRecord(record);
        }
        
        return "redirect:/vet/medical-records";
    }
    
    @GetMapping("/medical-records/delete/{id}")
    public String deleteMedicalRecord(@PathVariable Long id) {
        medicalRecordService.deleteRecord(id);
        return "redirect:/vet/medical-records";
    }
    
    // ============ VACCINATION MANAGEMENT ============
    
    @GetMapping("/vaccinations")
    public String vaccinations(Model model) {
        model.addAttribute("vaccinations", vaccinationService.getAllVaccinations());
        model.addAttribute("pets", petService.getAllPets());
        return "vet/vaccinations";
    }
    
    @GetMapping("/vaccinations/add")
    public String showAddVaccinationForm(Model model) {
        model.addAttribute("vaccination", new Vaccination());
        model.addAttribute("pets", petService.getAllPets());
        return "vet/add-vaccination";
    }
    
    @PostMapping("/vaccinations/add")
    public String addVaccination(@ModelAttribute Vaccination vaccination, Authentication auth) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            vaccination.setVeterinarianName(user.getFullName());
            vaccinationService.saveVaccination(vaccination);
        }
        
        return "redirect:/vet/vaccinations";
    }
    
    @GetMapping("/vaccinations/edit/{id}")
    public String showEditVaccinationForm(@PathVariable Long id, Model model) {
        Vaccination vaccination = vaccinationService.getVaccinationById(id).orElse(null);
        model.addAttribute("vaccination", vaccination);
        model.addAttribute("pets", petService.getAllPets());
        return "vet/edit-vaccination";
    }
    
    @PostMapping("/vaccinations/edit/{id}")
    public String updateVaccination(@PathVariable Long id, @ModelAttribute Vaccination vaccination, Authentication auth) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            vaccination.setId(id);
            vaccination.setVeterinarianName(user.getFullName());
            vaccinationService.updateVaccination(vaccination);
        }
        
        return "redirect:/vet/vaccinations";
    }
    
    @GetMapping("/vaccinations/delete/{id}")
    public String deleteVaccination(@PathVariable Long id) {
        vaccinationService.deleteVaccination(id);
        return "redirect:/vet/vaccinations";
    }
    
    // ============ PRESCRIPTION MANAGEMENT ============
    
    @GetMapping("/prescriptions")
    public String prescriptions(Model model) {
        model.addAttribute("prescriptions", prescriptionService.getAllPrescriptions());
        model.addAttribute("pets", petService.getAllPets());
        return "vet/prescriptions";
    }
    
    @GetMapping("/prescriptions/add")
    public String showAddPrescriptionForm(Model model) {
        model.addAttribute("prescription", new Prescription());
        model.addAttribute("pets", petService.getAllPets());
        return "vet/add-prescription";
    }
    
    @PostMapping("/prescriptions/add")
    public String addPrescription(@ModelAttribute Prescription prescription, Authentication auth) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            prescription.setPrescribedBy(user.getFullName());
            prescriptionService.savePrescription(prescription);
        }
        
        return "redirect:/vet/prescriptions";
    }
    
    @GetMapping("/prescriptions/edit/{id}")
    public String showEditPrescriptionForm(@PathVariable Long id, Model model) {
        Prescription prescription = prescriptionService.getPrescriptionById(id).orElse(null);
        model.addAttribute("prescription", prescription);
        model.addAttribute("pets", petService.getAllPets());
        return "vet/edit-prescription";
    }
    
    @PostMapping("/prescriptions/edit/{id}")
    public String updatePrescription(@PathVariable Long id, @ModelAttribute Prescription prescription, Authentication auth) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            prescription.setId(id);
            prescription.setPrescribedBy(user.getFullName());
            prescriptionService.updatePrescription(prescription);
        }
        
        return "redirect:/vet/prescriptions";
    }
    
    @GetMapping("/prescriptions/delete/{id}")
    public String deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return "redirect:/vet/prescriptions";
    }
}