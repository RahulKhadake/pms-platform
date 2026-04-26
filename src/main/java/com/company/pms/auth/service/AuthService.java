package com.company.pms.auth.service;

import com.company.pms.auth.dto.LoginRequestDTO;
import com.company.pms.auth.dto.LoginResponseDTO;
import com.company.pms.auth.dto.RegisterRequestDTO;

public interface AuthService {
    void register(RegisterRequestDTO dto);
    LoginResponseDTO login(LoginRequestDTO dto);
}
