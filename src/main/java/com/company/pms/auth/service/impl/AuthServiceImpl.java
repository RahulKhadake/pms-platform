package com.company.pms.auth.service.impl;

import com.company.pms.auth.dto.*;
import com.company.pms.auth.entity.AuthUser;
import com.company.pms.auth.repository.AuthRepository;
import com.company.pms.auth.service.AuthService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final AuthRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------------- REGISTER ----------------
    @Override
    public AuthResponseDTO register(RegisterRequestDTO dto) {

        // check email exists
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            return new AuthResponseDTO("This email is already registered. Please login instead.", false);
        }

        // map DTO → Entity
        AuthUser user = new AuthUser();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        repository.save(user);

        return new AuthResponseDTO("User Registered Successfully", true);
    }

    // ---------------- LOGIN ----------------
    @Override
    public AuthResponseDTO login(LoginRequestDTO dto) {

        AuthUser user = repository.findByEmail(dto.getEmail())
                .orElse(null);

        if (user == null) {
            return new AuthResponseDTO("This email is not registered. Please sign up first.", false);
        }

        boolean isMatch = passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword()
        );

        if (!isMatch) {
            return new AuthResponseDTO("Incorrect password. Please try again.", false);
        }

        return new AuthResponseDTO("Login successful", true);
    }

    // ---------------- SPRING SECURITY ----------------
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AuthUser user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + email)
                );

        return new User(
                user.getEmail(),
                user.getPassword(),
                java.util.Collections.emptyList()
        );
    }
}