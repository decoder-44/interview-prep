package com.interviewprep.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceMetricsDTO {
    private Long id;
    private Double accuracyPercentage;
    private Double speedScore;
    private Double communicationScore;
    private Double problemSolvingScore;
    private Double overallScore;
    private String category;
}