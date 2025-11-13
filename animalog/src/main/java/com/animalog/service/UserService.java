package com.animalog.service;

import com.animalog.model.User;
import com.animalog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    
    // ADD THIS METHOD
    public List<User> getActiveVeterinarians() {
        return userRepository.findByRoleAndIsActive(User.Role.VET, true);
    }
    
    // ADD THIS METHOD TOO (if not exists)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}