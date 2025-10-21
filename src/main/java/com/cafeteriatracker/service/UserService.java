package com.cafeteriatracker.service;

import com.cafeteriatracker.model.User;
import com.cafeteriatracker.repository.UserRepository;
// REMOVED UNUSED IMPORTS:
// import jakarta.persistence.metamodel.SingularAttribute;
// import org.springframework.data.jpa.domain.AbstractPersistable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
// REMOVED UNUSED IMPORTS:
// import java.io.Serializable;
// import java.util.Collections;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // Dependency is required for security!

    // Public method required for authentication flow
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // ----------------------------------------------------------------------
    // --- Functional CRUD Service Methods for Controller ---

    /**
     * Retrieves the total count of users in the database.
     */
    public long countUsers() {
        return userRepository.count();
    }

    /**
     * Retrieves all users from the database.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Saves or updates a user, handling password encoding if necessary.
     * CRITICAL: Ensures the password is HASHED before saving to the database.
     */
    public void saveUser(User user) {
        String rawPassword = user.getPassword();

        if (user.getUserId() == null) {
            // New user creation
            if (rawPassword != null && !rawPassword.isEmpty()) {
                user.setPassword(passwordEncoder.encode(rawPassword));
            }
        } else {
            // Existing user update
            Optional<User> existingUserOpt = userRepository.findById(user.getUserId());
            if (existingUserOpt.isPresent()) {
                User existingUser = existingUserOpt.get();
                if (rawPassword != null && !rawPassword.isEmpty()) {
                    // Update password if a new one is provided
                    user.setPassword(passwordEncoder.encode(rawPassword));
                } else {
                    // Retain the existing, hashed password and name if the fields were left blank
                    user.setPassword(existingUser.getPassword());

                    // CRITICAL FIX: Ensure the name is retained if the update request didn't supply it
                    if (user.getName() == null || user.getName().isEmpty()) {
                        user.setName(existingUser.getName());
                    }
                }
            }
        }
        userRepository.save(user);
    }

    /**
     * Fetches a user by ID, throwing an exception if not found.
     */
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));
    }

    /**
     * Deletes a user by ID, throwing an exception if not found.
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    // ----------------------------------------------------------------------
    // --- Complex/Unused User Lookup Method (REMOVED FOR CLEANUP) ---

    // ----------------------------------------------------------------------

    // --- Spring Security Integration: Finds user by email and passes details to Spring ---
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Attempting to load user details for email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found in database: {}", email);
                    return new UsernameNotFoundException("User not found with email: " + email);
                });

        // Create a Spring Security UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(), // Hashed password is passed here
                java.util.Collections.emptyList() // Simple authority list
        );
    }
}
