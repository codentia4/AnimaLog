package com.animalog.controller;

import com.animalog.model.User;
import com.animalog.service.EmailService;
import com.animalog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private EmailService emailService;
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("error", "Email already exists!");
            return "register";
        }
        
        userService.registerUser(user);
        
        // Send welcome email
        try {
            emailService.sendWelcomeEmail(user.getEmail(), user.getFullName(), user.getRole().name());
        } catch (Exception e) {
            System.err.println("Failed to send welcome email: " + e.getMessage());
        }
        
        return "redirect:/login?registered";
    }
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }
        
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("OWNER"))) {
            return "redirect:/owner/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("VET"))) {
            return "redirect:/vet/dashboard";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            return "redirect:/admin/dashboard";
        }
        
        return "redirect:/";
    }
}