package com.interviewprep.backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;
    private String bio;
    private String role;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}