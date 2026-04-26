package com.company.pms.auth.controller;

import com.company.pms.auth.dto.LoginRequestDTO;
import com.company.pms.auth.dto.LoginResponseDTO;
import com.company.pms.auth.dto.RegisterRequestDTO;
import com.company.pms.auth.service.AuthService;
import com.company.pms.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ✅ 201 CREATED
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@Valid @RequestBody RegisterRequestDTO dto) {
        authService.register(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User registered successfully"));
    }

    // ✅ 200 OK with token data
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginRequestDTO dto) {
        LoginResponseDTO data = authService.login(dto);
        return ResponseEntity
                .ok(ApiResponse.success("Login successful", data));
    }
}
