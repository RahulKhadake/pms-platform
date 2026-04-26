package com.company.pms.auth.service.impl;

import com.company.pms.auth.dto.LoginRequestDTO;
import com.company.pms.auth.dto.LoginResponseDTO;
import com.company.pms.auth.dto.RegisterRequestDTO;
import com.company.pms.auth.entity.AuthUser;
import com.company.pms.auth.repository.AuthRepository;
import com.company.pms.auth.service.AuthService;
import com.company.pms.common.util.JwtUtil;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.company.pms.common.enums.Role;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final AuthRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(AuthRepository repository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ---------------- REGISTER ----------------
    @Override
    public void register(RegisterRequestDTO dto) {

        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("This email is already registered. Please login instead.");
        }

        AuthUser user = new AuthUser();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);

        repository.save(user);
    }

    // ---------------- LOGIN ----------------
    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {

        AuthUser user = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("This email is not registered. Please sign up first."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password. Please try again.");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponseDTO(token, user.getEmail(), user.getRole().name());
    }

    // ---------------- SPRING SECURITY ----------------
    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {

        AuthUser user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new User(
                user.getEmail(),
                user.getPassword(),
                java.util.Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}