package com.interviewprep.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interviewprep.backend.dto.ApiResponse;
import com.interviewprep.backend.dto.UserDTO;
import com.interviewprep.backend.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<UserDTO>> getUserProfile(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserProfile(id);
        return ResponseEntity.ok(ApiResponse.success(userDTO, "User profile fetched successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<UserDTO>> updateUserProfile(
            @PathVariable Long id,
            @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUserProfile(id, userDTO);
        return ResponseEntity.ok(ApiResponse.success(updatedUser, "User profile updated successfully"));
    }
}
