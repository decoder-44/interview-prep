package com.interviewprep.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewDTO {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String difficulty;
    private Integer totalQuestions;
    private Integer estimatedDurationMinutes;
    private Boolean isPublished;
    private Long attemptCount;
}