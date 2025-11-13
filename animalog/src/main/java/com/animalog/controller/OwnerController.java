package com.animalog.controller;

import com.animalog.model.*;
import com.animalog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/owner")
public class OwnerController {
    
    @Autowired
    private PetService petService;
    
    @Autowired
    private AppointmentService appointmentService;
    
    @Autowired
    private UserService userService;
    
    @Autowired(required = false)
    private FileStorageService fileStorageService;
    
    @Autowired(required = false)
    private EmailService emailService;
    
    @Autowired(required = false)
    private AvailabilityService availabilityService;
    
    // ============ DASHBOARD ============
    @GetMapping("/dashboard")
    public String dashboard(Authentication auth, Model model) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            List<Pet> pets = petService.getPetsByOwner(user.getFullName());
            model.addAttribute("user", user);
            model.addAttribute("pets", pets);
            model.addAttribute("totalPets", pets.size());
        }
        
        return "owner/dashboard";
    }
    
    // ============ PET MANAGEMENT ============
    
    @GetMapping("/pets")
    public String pets(Authentication auth, Model model) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            model.addAttribute("pets", petService.getPetsByOwner(user.getFullName()));
        }
        
        return "owner/pets";
    }
    
    @GetMapping("/pets/add")
    public String showAddPetForm(Model model) {
        model.addAttribute("pet", new Pet());
        return "owner/add-pet";
    }
    
    @PostMapping("/pets/add")
    public String addPet(@ModelAttribute Pet pet, 
                        @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                        Authentication auth,
                        RedirectAttributes redirectAttributes) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            pet.setOwnerName(user.getFullName());
            pet.setOwnerPhone(user.getPhone());
            
            // Handle image upload if service is available
            if (fileStorageService != null && imageFile != null && !imageFile.isEmpty()) {
                try {
                    String imageUrl = fileStorageService.storeFile(imageFile);
                    pet.setImageUrl(imageUrl);
                } catch (Exception e) {
                    System.err.println("Failed to upload image: " + e.getMessage());
                }
            }
            
            petService.savePet(pet);
            redirectAttributes.addFlashAttribute("success", "Pet added successfully!");
        }
        
        return "redirect:/owner/pets";
    }
    
    @GetMapping("/pets/{id}")
    public String petDetails(@PathVariable Long id, Model model) {
        Pet pet = petService.getPetById(id).orElse(null);
        model.addAttribute("pet", pet);
        return "owner/pet-details";
    }
    
    @GetMapping("/pets/edit/{id}")
    public String showEditPetForm(@PathVariable Long id, Model model) {
        Pet pet = petService.getPetById(id).orElse(null);
        model.addAttribute("pet", pet);
        return "owner/edit-pet";
    }
    
    @PostMapping("/pets/edit/{id}")
    public String updatePet(@PathVariable Long id, 
                           @ModelAttribute Pet pet,
                           @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                           Authentication auth,
                           RedirectAttributes redirectAttributes) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            Pet existingPet = petService.getPetById(id).orElse(null);
            
            pet.setId(id);
            pet.setOwnerName(user.getFullName());
            pet.setOwnerPhone(user.getPhone());
            
            // Handle image upload if service is available
            if (fileStorageService != null && imageFile != null && !imageFile.isEmpty()) {
                try {
                    // Delete old image if exists
                    if (existingPet != null && existingPet.getImageUrl() != null) {
                        fileStorageService.deleteFile(existingPet.getImageUrl());
                    }
                    
                    String imageUrl = fileStorageService.storeFile(imageFile);
                    pet.setImageUrl(imageUrl);
                } catch (Exception e) {
                    System.err.println("Failed to upload image: " + e.getMessage());
                }
            } else if (existingPet != null) {
                // Keep existing image if no new image uploaded
                pet.setImageUrl(existingPet.getImageUrl());
            }
            
            petService.updatePet(pet);
            redirectAttributes.addFlashAttribute("success", "Pet updated successfully!");
        }
        
        return "redirect:/owner/pets";
    }
    
    @GetMapping("/pets/delete/{id}")
    public String deletePet(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Pet pet = petService.getPetById(id).orElse(null);
        
        // Delete image file if exists
        if (fileStorageService != null && pet != null && pet.getImageUrl() != null) {
            fileStorageService.deleteFile(pet.getImageUrl());
        }
        
        petService.deletePet(id);
        redirectAttributes.addFlashAttribute("success", "Pet deleted successfully!");
        return "redirect:/owner/pets";
    }
    
    // ============ APPOINTMENT MANAGEMENT ============
    
    @GetMapping("/appointments")
    public String appointments(Authentication auth, Model model) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            List<Pet> pets = petService.getPetsByOwner(user.getFullName());
            
            // Get all appointments for owner's pets
            List<Appointment> ownerAppointments = pets.stream()
                .flatMap(pet -> appointmentService.getAppointmentsByPet(pet.getId()).stream())
                .toList();
            
            model.addAttribute("pets", pets);
            model.addAttribute("appointments", ownerAppointments);
        }
        
        return "owner/appointments";
    }
    
    @GetMapping("/appointments/add")
    public String showAddAppointmentForm(Authentication auth, Model model) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        if (user != null) {
            List<Pet> pets = petService.getPetsByOwner(user.getFullName());
            model.addAttribute("pets", pets);
        }
        
        // Get active veterinarians
        List<User> veterinarians = userService.getActiveVeterinarians();
        model.addAttribute("veterinarians", veterinarians);
        model.addAttribute("appointment", new Appointment());
        
        return "owner/add-appointment";
    }
    
    @PostMapping("/appointments/add")
    public String addAppointment(@ModelAttribute Appointment appointment, 
                                Authentication auth,
                                RedirectAttributes redirectAttributes) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        // Check if vet is available (if service exists)
        if (availabilityService != null) {
            if (!availabilityService.isVetAvailable(appointment.getVeterinarianName(), appointment.getAppointmentDatetime())) {
                redirectAttributes.addFlashAttribute("error", "Sorry, the selected veterinarian is not available at this time. Please choose another time slot.");
                return "redirect:/owner/appointments/add";
            }
        }
        
        appointmentService.saveAppointment(appointment);
        
        // Send confirmation email if service exists
        if (emailService != null && user != null) {
            Pet pet = petService.getPetById(appointment.getPetId()).orElse(null);
            if (pet != null) {
                try {
                    emailService.sendAppointmentConfirmation(
                        user.getEmail(),
                        user.getFullName(),
                        pet.getName(),
                        appointment.getAppointmentDatetime(),
                        appointment.getPurpose(),
                        appointment.getVeterinarianName()
                    );
                    redirectAttributes.addFlashAttribute("success", "Appointment booked successfully! Check your email for confirmation.");
                } catch (Exception e) {
                    System.err.println("Failed to send email: " + e.getMessage());
                    redirectAttributes.addFlashAttribute("success", "Appointment booked successfully!");
                }
            }
        } else {
            redirectAttributes.addFlashAttribute("success", "Appointment booked successfully!");
        }
        
        return "redirect:/owner/appointments";
    }
    
    @GetMapping("/appointments/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id, 
                                   Authentication auth,
                                   RedirectAttributes redirectAttributes) {
        String email = auth.getName();
        User user = userService.findByEmail(email).orElse(null);
        
        Appointment appointment = appointmentService.getAppointmentById(id).orElse(null);
        
        if (appointment != null) {
            appointment.setStatus(Appointment.Status.CANCELLED);
            appointmentService.updateAppointment(appointment);
            
            // Send cancellation email if service exists
            if (emailService != null && user != null) {
                Pet pet = petService.getPetById(appointment.getPetId()).orElse(null);
                if (pet != null) {
                    try {
                        emailService.sendAppointmentCancellation(
                            user.getEmail(),
                            user.getFullName(),
                            pet.getName(),
                            appointment.getAppointmentDatetime()
                        );
                    } catch (Exception e) {
                        System.err.println("Failed to send email: " + e.getMessage());
                    }
                }
            }
            
            redirectAttributes.addFlashAttribute("success", "Appointment cancelled successfully!");
        }
        
        return "redirect:/owner/appointments";
    }
}