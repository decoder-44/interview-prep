package com.interviewprep.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interviewprep.backend.dto.ApiResponse;
import com.interviewprep.backend.dto.AuthenticationResponse;
import com.interviewprep.backend.dto.TokenRefreshRequest;
import com.interviewprep.backend.dto.UserDTO;
import com.interviewprep.backend.dto.UserLoginDTO;
import com.interviewprep.backend.dto.UserRegistrationDTO;
import com.interviewprep.backend.entity.User;
import com.interviewprep.backend.service.UserService;
import com.interviewprep.backend.util.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> register(
            @Valid @RequestBody UserRegistrationDTO registrationDTO) {

        User user = userService.registerUser(registrationDTO);
        String token = jwtUtil.generateToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .user(userDTO)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(authResponse, "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> login(
            @Valid @RequestBody UserLoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .user(userDTO)
                .build();

        return ResponseEntity.ok(ApiResponse.success(authResponse, "Login successful"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(
            @Valid @RequestBody TokenRefreshRequest refreshRequest) {

        if (jwtUtil.validateToken(refreshRequest.getRefreshToken())) {
            String email = jwtUtil.getEmailFromToken(refreshRequest.getRefreshToken());
            String newToken = jwtUtil.generateToken(email);

            User user = userService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            UserDTO userDTO = UserDTO.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();

            AuthenticationResponse authResponse = AuthenticationResponse.builder()
                    .token(newToken)
                    .refreshToken(refreshRequest.getRefreshToken())
                    .user(userDTO)
                    .build();

            return ResponseEntity.ok(ApiResponse.success(authResponse, "Token refreshed successfully"));
        }

        return ResponseEntity.badRequest()
                .body(ApiResponse.error("Invalid refresh token", HttpStatus.BAD_REQUEST.value()));
    }
}
