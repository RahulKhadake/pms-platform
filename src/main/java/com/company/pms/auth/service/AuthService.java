package com.company.pms.auth.service;

import com.company.pms.auth.dto.AuthResponseDTO;
import com.company.pms.auth.dto.LoginRequestDTO;
import com.company.pms.auth.dto.RegisterRequestDTO;

public interface AuthService {


    AuthResponseDTO register(RegisterRequestDTO dto);
    AuthResponseDTO login(LoginRequestDTO dto);
}
