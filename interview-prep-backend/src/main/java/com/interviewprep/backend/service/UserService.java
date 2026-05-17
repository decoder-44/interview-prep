package com.interviewprep.backend.service;

import java.util.Optional;

import com.interviewprep.backend.dto.UserDTO;
import com.interviewprep.backend.dto.UserRegistrationDTO;
import com.interviewprep.backend.entity.User;

public interface UserService {
    User registerUser(UserRegistrationDTO registrationDTO);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    UserDTO getUserProfile(Long id);
    UserDTO updateUserProfile(Long id, UserDTO userDTO);
    boolean existsByEmail(String email);
}
