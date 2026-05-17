package com.interviewprep.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionCreateDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    private String hints;

    private String expectedAnswer;

    @NotBlank(message = "Category is required")
    private String category;

    private String difficulty = "MEDIUM";

    private Integer estimatedTimeMinutes = 5;
}