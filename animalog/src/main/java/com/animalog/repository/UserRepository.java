package com.animalog.repository;

import com.animalog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    
    // ADD THESE METHODS
    List<User> findByRoleAndIsActive(User.Role role, Boolean isActive);
    List<User> findByRole(User.Role role);
}