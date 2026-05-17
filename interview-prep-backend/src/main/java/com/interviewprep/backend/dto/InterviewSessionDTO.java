package com.interviewprep.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewSessionDTO {
    private Long id;
    private Long interviewId;
    private String status;
    private Integer questionsAttempted;
    private Integer questionsCorrect;
    private Double overallScore;
    private Long totalTimeSpentSeconds;
}