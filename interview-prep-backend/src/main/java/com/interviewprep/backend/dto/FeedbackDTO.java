package com.interviewprep.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDTO {
    private Long id;
    private Long interviewSessionId;
    private Long questionId;
    private String feedbackContent;
    private Integer rating;
    private String suggestions;
    private Boolean isAiGenerated;
}