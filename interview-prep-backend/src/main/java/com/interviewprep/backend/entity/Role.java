package com.interviewprep.backend.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class Role {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String INTERVIEWER = "INTERVIEWER";
}