package com.company.pms.user.service.impl;

import com.company.pms.auth.entity.AuthUser;
import com.company.pms.common.enums.Role;
import com.company.pms.user.dto.UserRequestDTO;
import com.company.pms.user.dto.UserResponseDTO;
import com.company.pms.user.repository.UserRepository;
import com.company.pms.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ Get my profile
    @Override
    public UserResponseDTO getMyProfile(String email) {
        AuthUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return mapToDTO(user);
    }

    // ✅ Update my profile
    @Override
    public UserResponseDTO updateMyProfile(String email, UserRequestDTO dto) {
        AuthUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setName(dto.getName());
        userRepository.save(user);
        return mapToDTO(user);
    }

    // ✅ Get all users - ADMIN
    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ✅ Change role - ADMIN
    @Override
    public UserResponseDTO changeUserRole(Long userId, Role role) {
        AuthUser user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        user.setRole(role);
        userRepository.save(user);
        return mapToDTO(user);
    }

    // ✅ Delete user - ADMIN
    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    // ── map Entity → DTO ──
    private UserResponseDTO mapToDTO(AuthUser user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}

