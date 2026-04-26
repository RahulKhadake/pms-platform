package com.company.pms.user.controller;

import com.company.pms.common.enums.Role;
import com.company.pms.common.response.ApiResponse;
import com.company.pms.user.dto.UserRequestDTO;
import com.company.pms.user.dto.UserResponseDTO;
import com.company.pms.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ GET my profile — any logged-in user
    @GetMapping("/user/profile")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        UserResponseDTO data = userService.getMyProfile(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success("Profile fetched", data));
    }

    // ✅ UPDATE my profile — any logged-in user
    @PutMapping("/user/profile")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateMyProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO data = userService.updateMyProfile(userDetails.getUsername(), dto);
        return ResponseEntity.ok(ApiResponse.success("Profile updated", data));
    }

    // ✅ GET all users — ADMIN only
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        List<UserResponseDTO> data = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Users fetched", data));
    }

    // ✅ CHANGE role — ADMIN only
    @PutMapping("/admin/users/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponseDTO>> changeRole(
            @PathVariable Long id,
            @RequestParam Role role) {
        UserResponseDTO data = userService.changeUserRole(id, role);
        return ResponseEntity.ok(ApiResponse.success("Role updated to " + role, data));
    }

    // ✅ DELETE user — ADMIN only
    @DeleteMapping("/admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
    }
}

