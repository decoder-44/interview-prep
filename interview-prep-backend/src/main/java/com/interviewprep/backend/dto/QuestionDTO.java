package com.interviewprep.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String hints;
    private String expectedAnswer;
    private String category;
    private String difficulty;
    private Integer estimatedTimeMinutes;
    private Long viewCount;
    private Double averageRating;
}