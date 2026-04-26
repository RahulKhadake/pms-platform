package com.company.pms.user.service;

import com.company.pms.common.enums.Role;
import com.company.pms.user.dto.UserRequestDTO;
import com.company.pms.user.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    // Get my profile
    UserResponseDTO getMyProfile(String email);

    // Update my profile
    UserResponseDTO updateMyProfile(String email, UserRequestDTO dto);

    // Get all users - ADMIN
    List<UserResponseDTO> getAllUsers();

    // Change user role - ADMIN
    UserResponseDTO changeUserRole(Long userId, Role role);

    // Delete user - ADMIN
    void deleteUser(Long userId);
}