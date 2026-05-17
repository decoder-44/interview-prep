package com.interviewprep.backend.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "^\\d{10}$";

    public boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }

    public boolean isValidPhoneNumber(String phone) {
        return phone != null && Pattern.matches(PHONE_REGEX, phone);
    }

    public boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.length() >= 2;
    }
}
